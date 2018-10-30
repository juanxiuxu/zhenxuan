package com.zhenxuan.tradeapi.service.impl;

import com.zhenxuan.tradeapi.common.ZXException;
import com.zhenxuan.tradeapi.common.enums.ResultStatusCode;
import com.zhenxuan.tradeapi.domain.UserWXInfo;
import com.zhenxuan.tradeapi.dao.entity.ShopItem;
import com.zhenxuan.tradeapi.dao.entity.UserAuthEntity;
import com.zhenxuan.tradeapi.dao.entity.UserLoginEntity;
import com.zhenxuan.tradeapi.dao.mapper.UserAuthMapper;
import com.zhenxuan.tradeapi.dao.mapper.UserLoginMapper;
import com.zhenxuan.tradeapi.service.UserService;
import com.zhenxuan.tradeapi.thirdparty.DynamoDbService;
import com.zhenxuan.tradeapi.thirdparty.WXCode2Session;
import com.zhenxuan.tradeapi.thirdparty.WXGetUserInfo;
import com.zhenxuan.tradeapi.utils.GlobalIdUtil;
import com.zhenxuan.tradeapi.utils.JwtUtil;
import com.zhenxuan.tradeapi.common.vo.*;
import com.zhenxuan.tradeapi.common.vo.weixin.WXGetUserInfoRespVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${token.sign.expired.sec}")
    private int tokenSignExpired;

    @Value("${token.sign.secret}")
    private String tokenSignSecret;

    @Resource
    private UserLoginMapper userLoginMapper;

    @Resource
    private UserAuthMapper userAuthMapper;

    @Autowired
    private WXCode2Session wxLoginAuth;

    @Autowired
    private WXGetUserInfo wxGetUserInfo;

    @Autowired
    private DynamoDbService dynamoDbService;

    @Override
    public LoginRespVo login(LoginReqVo loginReqVo) {
        LoginRespVo loginRespVo = new LoginRespVo();
        TokenHeaderVo tokenHeaderVo = new TokenHeaderVo();

        UserWXInfo userWXInfo = wxLoginAuth.execute(loginReqVo.getCode());

        UserLoginEntity loginEntity = new UserLoginEntity();
        boolean isNewUser = loginInternal(loginReqVo, userWXInfo, loginEntity);
        loginRespVo.setNewUser(isNewUser ? 1 : 0);
        String unionId = loginEntity.getUnionId();
        loginRespVo.setHasUnionId(!StringUtils.isEmpty(unionId));

        if (!StringUtils.isEmpty(unionId)) {
            logger.debug("unionId not empty for user code:{}", loginReqVo.getCode());

            UserAuthEntity authEntity = userAuthMapper.selectEntityByUnionId(unionId);
            // 如果tbl_user_auth中存在该用户，member等重要信息以该表为准
            if (authEntity != null) {
                logger.debug("user code:{} had authorized unionId. authUid:{}", loginReqVo.getCode(), authEntity.getAuthUid());

                tokenHeaderVo.setAuthUid(authEntity.getAuthUid());
                tokenHeaderVo.setAvatar(authEntity.getAvatar());
                tokenHeaderVo.setUserName(authEntity.getNickName());

                //loginRespVo.setToken(JwtUtil.sign(new TokenHeaderVo(loginEntity.getLoginUid(), authEntity.getUid(), authEntity.getAvatar(), authEntity.getNickName()), TOKEN_EXPIRED));
                // loginRespVo.setUid(authEntity.getUid());
                loginRespVo.setMember(authEntity.getMember());

//                loginRespVo.setFwhUser(StringUtils.isEmpty(authEntity.getFwhOpenId()) ? 0 : 1);
//                loginRespVo.setTid(authEntity.getTid());
//                loginRespVo.setIuid(authEntity.getIuid());
//                return loginRespVo;
            }
        }

        // 如果用户只存在于tbl_user_login中，则以该表为准，缺少authUid,member等关键的用户信息
        tokenHeaderVo.setLoginUid(loginEntity.getLoginUid());
        loginRespVo.setToken(JwtUtil.sign(tokenHeaderVo, (long)tokenSignExpired * 1000, tokenSignSecret));
        loginRespVo.setLoginUid(loginEntity.getLoginUid());

        loginRespVo.setFwhUser(StringUtils.isEmpty(loginEntity.getFwhOpenId()) ? 0 : 1);
        loginRespVo.setTid(loginEntity.getTid());
        loginRespVo.setIuid(loginEntity.getIuid());

        return loginRespVo;
    }

    /**
     * 登陆事务
     * @param loginReqVo
     * @param userWXInfo
     * @return true: 新用户， false: 老用户
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean loginInternal(LoginReqVo loginReqVo, UserWXInfo userWXInfo, UserLoginEntity userEntity) {
        UserLoginEntity entity = userLoginMapper.selectEntityByUniqueKey(loginReqVo.getAppType(), userWXInfo.getOpenid());
        boolean isNewUser = (entity == null);

        if (entity == null) {
            UserLoginEntity newUser = new UserLoginEntity();
            newUser.setLoginUid(GlobalIdUtil.newLoginUid());
            newUser.setWxAppType(loginReqVo.getAppType());
            newUser.setWxOpenId(userWXInfo.getOpenid());
            newUser.setWxSessionKey(userWXInfo.getSessionKey());
            newUser.setUnionId(userWXInfo.getUnionId());
            newUser.setLastLogin((new Date()));
            userLoginMapper.insertLoginInfo(newUser);
            userEntity.copyFrom(newUser);

        } else {
            entity.setWxSessionKey(userWXInfo.getSessionKey());
            if (StringUtils.isEmpty(entity.getUnionId())) {
                entity.setUnionId(userWXInfo.getUnionId());
            }
            entity.setLastLogin((new Date()));
            userLoginMapper.updateLoginInfoByUniqueKey(entity);
            userEntity.copyFrom(entity);
        }

        return isNewUser;
    }

    @Override
    public String auth(AuthReqVo authReqVo) {
        UserLoginEntity loginEntity = verifyLoginUid(authReqVo.getLoginUid());
        if (StringUtils.isEmpty(loginEntity.getWxSessionKey())) {
            throw new ZXException(ResultStatusCode.USER_NOT_LOGGED_IN);
        }

        WXGetUserInfoRespVo respVo = wxGetUserInfo.execute(authReqVo, loginEntity);
        return authInternal(loginEntity, respVo);
    }

    /**
     * 认证事务
     * @param loginEntity
     * @param respVo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public String authInternal(UserLoginEntity loginEntity, WXGetUserInfoRespVo respVo) {
        String unionId = respVo.getUnionId();
        String uid = loginEntity.getLoginUid();
        UserAuthEntity authEntity = userAuthMapper.selectEntityByUnionId(unionId);

        if (authEntity == null) {
            UserAuthEntity newAuth = new UserAuthEntity(respVo);
            newAuth.setWxAppType(loginEntity.getWxAppType());
            newAuth.setAuthUid(uid);
            newAuth.setBalance(loginEntity.getBalance());
            newAuth.setIuid(loginEntity.getIuid());
            userAuthMapper.insertPersona(newAuth);

            // 同时更新tbl_user_login的unionId
            userLoginMapper.updateUnionIdByUid(uid, unionId);
            return uid;

        } else {
            userAuthMapper.updatePersonaByUnionId(unionId, respVo.getAvatarUrl(), respVo.getNickName(), respVo.getGender());
            return authEntity.getAuthUid();
        }
    }

    @Override
    public UserLoginEntity verifyLoginUid(String loginUid) {
        UserLoginEntity loginEntity = userLoginMapper.selectEntityByUid(loginUid);
        if (loginEntity == null) {
            throw new ZXException(ResultStatusCode.USER_NOT_EXISTS);
        }

        return loginEntity;
    }


    @Override
    public UserAuthEntity verifyAuthUid(String authUid) {
        UserAuthEntity authEntity = userAuthMapper.selectEntityByUid(authUid);
        if (authEntity == null) {
            throw new ZXException(ResultStatusCode.USER_NOT_EXISTS);
        }

        return authEntity;
    }

    @Override
    public UpdatePersonaRespVo updatePersona(UpdatePersonaReqVo updatePersonaReqVo) {
        verifyAuthUid(updatePersonaReqVo.getAuthUid());
        userAuthMapper.updatePersonaByUid(updatePersonaReqVo.getAuthUid(), updatePersonaReqVo.getAvatar(),
                updatePersonaReqVo.getName(), updatePersonaReqVo.getGender());

        UserAuthEntity entity = new UserAuthEntity();
        entity.setAuthUid(updatePersonaReqVo.getAuthUid());
        entity.setAvatar(updatePersonaReqVo.getAvatar());
        entity.setNickName(updatePersonaReqVo.getName());
        entity.setGender(updatePersonaReqVo.getGender());

        return new UpdatePersonaRespVo(entity);
    }

    @Override
    public GetPersonaRespVo getPersona(GetPersonaReqVo getPersonaReqVo) {
        UserAuthEntity authEntity = verifyAuthUid(getPersonaReqVo.getAuthUid());
        return new GetPersonaRespVo(authEntity);
    }

    @Override
    public GetUserActionRespVo getAction(GetUserActionReqVo actionReqVo) {
        UserLoginEntity loginEntity = verifyLoginUid(actionReqVo.getLoginUid());
        return new GetUserActionRespVo(loginEntity);
    }

    @Override
    public EnterShopRespVo enterShop(EnterShopReqVo enterShopReqVo) {
        UserAuthEntity uidAuthEntity = verifyAuthUid(enterShopReqVo.getAuthUid());

        // 进入自己的店铺，啥也不做
        if (enterShopReqVo.getTid() == enterShopReqVo.getAuthUid()) {
            return null;
        }

        EnterShopRespVo respVo = new EnterShopRespVo();

        UserAuthEntity tidAuthEntity = userAuthMapper.selectEntityByUid(enterShopReqVo.getTid());
        if (tidAuthEntity != null) {
            String tid = tidAuthEntity.getAuthUid();
            respVo.setAvater(tidAuthEntity.getAvatar());
            respVo.setUid(tid);
            respVo.setNickName(tidAuthEntity.getNickName());

            ShopItem shopItem = dynamoDbService.read(ShopItem.class, tid, false);
            respVo.copy(shopItem);
        }

        if (uidAuthEntity != null) {
            String iuid = uidAuthEntity.getIuid();
            UserAuthEntity iuidAuthEntity = userAuthMapper.selectEntityByUid(iuid);
            if (iuidAuthEntity != null) {
                respVo.setIuidName(iuidAuthEntity.getNickName());
            }
        }

        return respVo;
    }

    @Override
    public BeInvitedRespVo beInvited(BeInvitedReqVo beInvitedReqVo) {
        UserAuthEntity uidAuthEntity = verifyAuthUid(beInvitedReqVo.getAuthUid());
        String newIuid = beInvitedReqVo.getIuid();
        UserAuthEntity iuidAuthEntity = verifyAuthUid(newIuid);

        // 不能自己邀请自己
        if (beInvitedReqVo.getAuthUid() == beInvitedReqVo.getIuid()) {
            throw new ZXException(ResultStatusCode.DONOT_INVITED_SELF);
        }

        BeInvitedRespVo respVo = new BeInvitedRespVo();

        if (StringUtils.isEmpty(uidAuthEntity.getIuid())) {
            userAuthMapper.updateActionByUid(uidAuthEntity.getAuthUid(), "", newIuid, "", 0, 0);
        }

        ShopItem shopItem = dynamoDbService.read(ShopItem.class, newIuid, false);
        respVo.setIuid(newIuid);
        respVo.copy(shopItem);

        return respVo;
    }
}
