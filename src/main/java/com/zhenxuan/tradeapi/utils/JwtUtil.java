package com.zhenxuan.tradeapi.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Throwables;
import com.zhenxuan.tradeapi.vo.TokenHeaderVo;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // private static final String SECRET = "XX#$%()(#*!()!KL<><MQLMNQNQJQK sdfkjsdrow32234545df>?N<:{LWPW_hisen";
    private static final String EXP = "exp";
    private static final String PAYLOAD = "payload";

    /**
     * 生成Token:jwt
     * @param object 传入的加密对象 - 放入PAYLOAD
     * @param maxAge 过期事件,单位毫秒
     * @param <T>
     * @return
     */
    public static <T> String sign(T object, long maxAge, String secret) {
        Map<String, Object> map = new HashMap<String, Object>();
        String jsonString = JsonUtil.toString(object);
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        long exp = System.currentTimeMillis() + maxAge;
        logger.debug("JWTUtil 当前时间:{}", new DateTime().toString("yyyy-MM-dd HH:mm:ss EE"));
        logger.debug("JWTUtil 过期时间:{}", new DateTime(exp).toString("yyyy-MM-dd HH:mm:ss EE"));
        String token = null;
        try {
            token = JWT.create()
                    .withHeader(map)//header
                    .withClaim(PAYLOAD, jsonString)//存放的内容 json
                    .withClaim(EXP, new DateTime(exp).toDate())//超时时间
                    .sign(Algorithm.HMAC256(secret));//密钥
        } catch (Exception e) {
            logger.error("fail to jwt sign. err:{}", Throwables.getStackTraceAsString(e));
        }
        return token;
    }

    public static <T> T verity(String token, Class<T> classT, String secret)  {
        JWTVerifier verifier = null;
        try {
            verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            DecodedJWT jwt = verifier.verify(token); // 如果超时,直接抛出运行时异常
            Map<String, Claim> claims = jwt.getClaims();
            if (claims.containsKey(EXP) && claims.containsKey(PAYLOAD)) {
                long tokenTime = claims.get(EXP).asDate().getTime();
                long now = new Date().getTime();
                // 判断令牌是否已经超时
                if (tokenTime > now) {
                    String json = claims.get(PAYLOAD).asString();
                    // 把json转回对象，返回
                    return JSON.parseObject(json, classT);
                }
            }
        } catch (Exception e) {
            logger.error("fail to jwt verity. err:{}", Throwables.getStackTraceAsString(e));
        }
        return null;
    }

    public static void main(String[] args) {
        String secret = "6bac8008717948769a15be2e7def4714";
        long age = 60*60*24 * 1000;

        TokenHeaderVo vo = new TokenHeaderVo();
        vo.setLoginUid("myloguid");
        vo.setAvatar("http://pan.baidu.com");
        vo.setUserName("myusername");

        String token = JwtUtil.sign(vo, age, secret);
        System.out.printf("token=%s\n", token);

        TokenHeaderVo newVo = JwtUtil.verity(token, TokenHeaderVo.class, secret);
        System.out.printf("loginUid=%s, avatar=%s, name=%s, authUid=%s\n",
                newVo.getLoginUid(), newVo.getAvatar(), newVo.getUserName(), newVo.getAuthUid());
    }
}
