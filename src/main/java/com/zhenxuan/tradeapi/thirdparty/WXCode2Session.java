package com.zhenxuan.tradeapi.thirdparty;

import com.google.common.base.Throwables;
import com.zhenxuan.tradeapi.common.ZXException;
import com.zhenxuan.tradeapi.common.http.HttpJSONHelper;
import com.zhenxuan.tradeapi.common.enums.ResultStatusCode;
import com.zhenxuan.tradeapi.domain.UserWXInfo;
import com.zhenxuan.tradeapi.vo.weixin.WXCode2SessionRespVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信登陆认证。获取微信的用户openid和unionid
 */
@Component
public class WXCode2Session {

    private static final Logger logger = LoggerFactory.getLogger(WXCode2Session.class);

    @Value("${wx.code2session}")
    public String wxLoginAuthUrl;

    @Value("${wx.zhenxuan.appid}")
    public String wxZxAppId;

    @Value("${wx.zhenxuan.secret}")
    public String wxZxSecret;

    public UserWXInfo execute(String code) {
        WXCode2SessionRespVo respVo;
        int retry = 2;
        do {
            try {
                String url = String.format("%s?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                        wxLoginAuthUrl, wxZxAppId, wxZxSecret, code);
                respVo = HttpJSONHelper.instance().get(url, WXCode2SessionRespVo.class);
                logger.debug("weixin Code2Session response:{}", respVo);

            } catch (Exception e) {
                logger.error("fail to do login auth for weixin. err:{}", Throwables.getStackTraceAsString(e));
                throw new ZXException(ResultStatusCode.HTTP_ERROR);
            }

            switch (respVo.getErrCode()) {
                case 0:
                    return new UserWXInfo(respVo);
                case 40029:
                    logger.error("invalid code of weixin session or code had been used");
                    throw new ZXException(ResultStatusCode.INVALID_CODE_ERROR);
//                respVo.setOpenid("testOpenId");
//                respVo.setSessionKey("testSessionKey");
//                return new UserWXInfo(respVo);
                case 40163:
                    logger.error("code of weixin session had been used");
                    throw new ZXException(ResultStatusCode.INVALID_CODE_ERROR);
                case 45011:
                case -1:
                    logger.warn("waiting moment for weixin. errcode:{}", respVo.getErrCode());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        logger.error("sleep error. err:{}", Throwables.getStackTraceAsString(e));
                    }
                    break;
                default:
                    logger.debug("unknown weixin response errcode:{}", respVo.getErrCode());
                    throw new ZXException(ResultStatusCode.HTTP_ERROR);
            }
        } while (retry-- > 0);
        throw new ZXException(ResultStatusCode.HTTP_ERROR);
    }
}
