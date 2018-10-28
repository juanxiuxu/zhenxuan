package com.zhenxuan.tradeapi.controller;

import com.zhenxuan.tradeapi.common.ResultBody;
import com.zhenxuan.tradeapi.common.constants.Constants;
import com.zhenxuan.tradeapi.common.vo.*;
import com.zhenxuan.tradeapi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Controller
public class UserController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Resource
    private UserService userService;

    /**
     * 测试使用
     * @return
     */
    @RequestMapping(value = "/debug/hello", method = RequestMethod.GET)
    @ResponseBody
    public Object debugHello() {
        logger.info("debug hello");
        return ResultBody.success("world");
    }

    /**
     * 用户登陆
     * @param loginReqVo
     * @return
     */
    @RequestMapping(value = "/passport/login/js-wa", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestBody LoginReqVo loginReqVo) {
        LoginRespVo loginRespVo = userService.login(loginReqVo);
        return ResultBody.success(loginRespVo);
    }

    /**
     * 主动认证账号后上传加密数据
     * @param authReqVo
     * @return
     */
    @RequestMapping(value = "/passport/user/bind-unionid-wa", method = RequestMethod.POST)
    @ResponseBody
    public Object auth(@RequestBody AuthReqVo authReqVo,
                       @RequestParam(value = Constants.LOGIN_UID) String uid) {
        authReqVo.setLoginUid(uid);
        String authUid = userService.auth(authReqVo);
        return ResultBody.success(authUid);
    }

    @RequestMapping(value = "/passport/user/info", method = RequestMethod.POST)
    @ResponseBody
    public Object getPersona(@RequestBody GetPersonaReqVo personaReqVo,
                             @RequestParam(value = Constants.AUTH_UID) String uid) {
        personaReqVo.setAuthUid(uid);
        GetPersonaRespVo respVo = userService.getPersona(personaReqVo);
        return ResultBody.success(respVo);
    }

    /**
     * 更新用户画像
     * @param updatePersonaReqVo
     * @return
     */
    @RequestMapping(value = "/passport/user/update", method = RequestMethod.POST)
    @ResponseBody
    public Object updatePersona(@RequestBody UpdatePersonaReqVo updatePersonaReqVo,
                                @RequestParam(value = Constants.AUTH_UID) String uid) {
        updatePersonaReqVo.setAuthUid(uid);
        UpdatePersonaRespVo respVo = userService.updatePersona(updatePersonaReqVo);
        return ResultBody.success(respVo);
    }

    /**
     * 获取用户行为，包括是否认证过（有unionid），关注过公众号，tid, iuid
     * @param actionReqVo
     * @return
     */
    @RequestMapping(value = {"/passport/user/have-unionid", "/passport/user/is_fwh_user" }, method = RequestMethod.POST)
    @ResponseBody
    public Object getAction(@RequestBody GetUserActionReqVo actionReqVo,
                            @RequestParam(value = Constants.LOGIN_UID) String uid) {
        actionReqVo.setLoginUid(uid);
        GetUserActionRespVo respVo = userService.getAction(actionReqVo);
        return ResultBody.success(respVo);
    }

    /**
     * 从dynamodb获取店铺信息
     * @param enterShopReqVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/enter-store", method = RequestMethod.POST)
    public Object enterStore(@RequestBody EnterShopReqVo enterShopReqVo,
                             @RequestParam(value = Constants.AUTH_UID) String uid) {
        enterShopReqVo.setAuthUid(uid);
        EnterShopRespVo respVo = userService.enterShop(enterShopReqVo);
        return ResultBody.success(respVo);
    }

    @ResponseBody
    @RequestMapping(value = "/be-invited", method = RequestMethod.POST)
    public Object beInvited(@RequestBody BeInvitedReqVo beInvitedReqVo,
                            @RequestParam(value = Constants.AUTH_UID) String uid) {
        beInvitedReqVo.setAuthUid(uid);
        BeInvitedRespVo respVo = userService.beInvited(beInvitedReqVo);
        return ResultBody.success(respVo);
    }
}
