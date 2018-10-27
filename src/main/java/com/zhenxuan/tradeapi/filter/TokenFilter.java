/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.zhenxuan.tradeapi.filter;

import com.alibaba.fastjson.JSONObject;
import com.zhenxuan.tradeapi.common.ResultBody;
import com.zhenxuan.tradeapi.common.enums.ResultStatusCode;
import com.zhenxuan.tradeapi.utils.JsonUtil;
import com.zhenxuan.tradeapi.utils.JwtUtil;
import com.zhenxuan.tradeapi.common.vo.BaseAuthReqVo;
import com.zhenxuan.tradeapi.common.vo.BaseLoginReqVo;
import com.zhenxuan.tradeapi.common.vo.TokenHeaderVo;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * loginUid和authUid统一的Token过滤器，解析并验证token是否匹配参数uid
 */
@Component
public class TokenFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    private static final String headerTokenKey = "x-access-token";
    private static final Set<String> loginTokenVerifyUrl = new HashSet<>();
    private static final Set<String> authTokenVerifyUrl = new HashSet<>();

    static {
        loginTokenVerifyUrl.add("/passport/user/bind-unionid-wa");

        authTokenVerifyUrl.add("/passport/user/info");
        authTokenVerifyUrl.add("/passport/user/update");
        authTokenVerifyUrl.add("/passport/user/have-unionid");
        authTokenVerifyUrl.add("/passport/user/is_fwh_user");
        authTokenVerifyUrl.add("/enter-store");
        authTokenVerifyUrl.add("/be-invited");
        authTokenVerifyUrl.add("/order/create-sku-direct");
    }

    @Value("${token.sign.secret}")
    private String tokenSignSecret;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("init TokenFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest)request;
        String token = httpReq.getHeader(headerTokenKey);
        String path = httpReq.getServletPath();
        if (!loginTokenVerifyUrl.contains(path) && !authTokenVerifyUrl.contains(path)) {
            chain.doFilter(request, response);
            return;
        }
        logger.debug("path={}, token={}", path, token);

        TokenWrapper requestWrapper = new TokenWrapper(httpReq);
        ResultStatusCode failCode = null;
        String failExtraMsg = null;
        do {
            if (StringUtils.isEmpty(token)) {
                failCode = ResultStatusCode.PARAM_ERROR;
                failExtraMsg = "no token";
                break;
            }

            TokenHeaderVo tokenHeaderVo = JwtUtil.verity(token, TokenHeaderVo.class, tokenSignSecret);
            if (tokenHeaderVo == null) {
                failCode = ResultStatusCode.PARAM_ERROR;
                failExtraMsg = "invalid token";
                break;
            }

            byte[] body = requestWrapper.getOriginBody();
            if (body == null) {
                failCode = ResultStatusCode.PARAM_ERROR;
                failExtraMsg = "no request body";
                break;
            }

            if (loginTokenVerifyUrl.contains(path)) {
                BaseLoginReqVo reqVo = JsonUtil.toObject(body, BaseLoginReqVo.class);
                if (reqVo == null || StringUtils.isEmpty(reqVo.getLoginUid())) {
                    failCode = ResultStatusCode.PARAM_ERROR;
                    failExtraMsg = "no login uid in token";
                    break;
                }
                if (!reqVo.getLoginUid().equals(tokenHeaderVo.getLoginUid())) {
                    failCode = ResultStatusCode.PARAM_ERROR;
                    failExtraMsg = "login uid in token do not match the param in body";
                    break;
                }
            }
            if (authTokenVerifyUrl.contains(path)) {
                BaseAuthReqVo reqVo = JsonUtil.toObject(body, BaseAuthReqVo.class);
                if (reqVo == null || StringUtils.isEmpty(reqVo.getAuthUid())) {
                    failCode = ResultStatusCode.PARAM_ERROR;
                    failExtraMsg = "no auth uid in token";
                    break;
                }
                if (!reqVo.getAuthUid().equals(tokenHeaderVo.getAuthUid())) {
                    failCode = ResultStatusCode.PARAM_ERROR;
                    failExtraMsg = "auth uid in token do not match the param in body";
                    break;
                }
            }

            break;
        } while (true);

        if (failCode != null) {
            HttpServletResponse httpResp = (HttpServletResponse) response;
            httpResp.setCharacterEncoding("UTF-8");
            httpResp.setContentType("application/json; charset=utf-8");
            httpResp.setStatus(HttpStatus.SC_OK);
            try {
                if (StringUtils.isEmpty(failExtraMsg)) {
                    httpResp.getWriter().print(JSONObject.toJSONString(ResultBody.failure(failCode)));
                } else {
                    httpResp.getWriter().print(JSONObject.toJSONString(ResultBody.failure(failCode, failExtraMsg)));
                }
            } catch (IOException e) {
                logger.error("write response err:{}", e);
            }
            return;

        } else {
            chain.doFilter(requestWrapper, response);
            return;
        }
    }

    @Override
    public void destroy() {
        logger.info("destroy TokenFilter");
    }
}
