package com.zhenxuan.tradeapi.thirdparty;

import com.zhenxuan.tradeapi.common.ZXException;
import com.zhenxuan.tradeapi.common.enums.ResultStatusCode;
import com.zhenxuan.tradeapi.entity.UserLoginEntity;
import com.zhenxuan.tradeapi.utils.AesUtil;
import com.zhenxuan.tradeapi.utils.JsonUtil;
import com.zhenxuan.tradeapi.vo.AuthReqVo;
import com.zhenxuan.tradeapi.vo.weixin.WXGetUserInfoRespVo;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信认证，解密数据获取用户基本信息
 */
@Component
public class WXGetUserInfo {

    private static final Logger logger = LoggerFactory.getLogger(WXGetUserInfo.class);

    @Value("${wx.zhenxuan.appid}")
    public String wxZxAppId;

    public WXGetUserInfoRespVo execute(AuthReqVo authReqVo, UserLoginEntity loginEntity) {
        byte[] aesKey = Base64.decodeBase64(loginEntity.getWxSessionKey());
        byte[] ciphertext = Base64.decodeBase64(authReqVo.getEncryptedData());
        byte[] iv = Base64.decodeBase64(authReqVo.getIv());
        byte[] plaintext = AesUtil.decrypt(AesUtil.Mode.PKCS7, ciphertext, aesKey, iv);
        if (plaintext == null || plaintext.length == 0) {
            throw new ZXException(ResultStatusCode.DECRYPT_USERINFO_FAILED);
        }
        logger.info("user[{}]'s plaintext is {}", authReqVo.getLoginUid(), new String(plaintext));

        WXGetUserInfoRespVo respVo = JsonUtil.toObject(plaintext, WXGetUserInfoRespVo.class);
        if (respVo.getWatermark() == null || !wxZxAppId.equals(respVo.getWatermark().appid)) {
            throw new ZXException(ResultStatusCode.UNMATCHED_APPID);
        }

        return respVo;
    }

    public static void main(String[] args) {
        String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
        String ivData = "r7BXXKkLb8qrSNn05n0qiA==";
        String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";

        byte[] aesKey = Base64.decodeBase64(sessionKey);
        byte[] ciphertext = Base64.decodeBase64(encryptedData);
        byte[] iv = Base64.decodeBase64(ivData);
        System.out.printf("iv size=%d\n", iv.length);
        byte[] plaintext = AesUtil.decrypt(AesUtil.Mode.PKCS7, ciphertext, aesKey, iv);
        if (plaintext == null || plaintext.length == 0) {
            System.out.println("plaintext is empty");
        } else {
            System.out.printf("plaintext length is %d,  %s\n", plaintext.length, new String(plaintext));
        }
    }
}
