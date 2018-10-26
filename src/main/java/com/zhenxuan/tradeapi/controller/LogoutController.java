///**
// * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
// */
//package com.zhenxuan.tradeapi.controller;
//
//import com.zhenxuan.tradeapi.entity.UserLoginEntity;
//import com.zhenxuan.tradeapi.service.impl.UserServiceImpl;
//import com.zhenxuan.tradeapi.utils.NetUtil;
//import com.zhenxuan.tradeapi.uuap.UuapConstants;
//import org.apache.commons.lang.StringUtils;
//import org.jasig.cas.client.util.AssertionHolder;
//import org.jasig.cas.client.validation.Assertion;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.security.Principal;
//
///**
// * 退出UUAP.
// */
//@Controller
//public class LogoutController {
//
//    /** log. */
//    private static final Logger LOG = LoggerFactory.getLogger(LogoutController.class);
//    /** 注入UserService. */
//    @Autowired
//    private UserServiceImpl userService;
//    /** Cache注入. */
////    @Autowired
////    private Cache ehCache;
//
//    /**
//     * 退出UUAP.
//     *
//     * @param request .
//     * @param response .
//     * @return 跳转地址 .
//     */
//    @RequestMapping(value = "/permission/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        try {
//            LOG.debug("User[" + request.getRemoteUser() + "] logout.");
//
//            String username = null;
//            Assertion assertion = AssertionHolder.getAssertion();
//            if (assertion != null) {
//                Principal principal = assertion.getPrincipal();
//                if (principal != null) {
//                    username = principal.getName();
//                }
//            }
//
//            if (StringUtils.isNotBlank(username)) {
//                UserLoginEntity user = new UserLoginEntity();
//                user.setUserName(username);
//                // 置空token
//                userService.deleteTokenByUserName(username);
//            }
//
//
//
//            // 删除缓存中key-value资源信息
////            EhCacheUtil.removeEle(ehCache, "projnameSetEle", "resourceMapEle");
//            request.getSession().invalidate();
//            NetUtil.delCookie(response, "currentUser");
//
//            return "redirect:" + UuapConstants.Base.UUAP_LOGOUT_URL + "?service="
//                    + UuapConstants.Base.UUAP_LOGOUT_SERVICE + "?" + Math.random();
//        } catch (RuntimeException e) {
//            LOG.error("logout error", e);
//        }
//        return "";
//    }
//
//}
