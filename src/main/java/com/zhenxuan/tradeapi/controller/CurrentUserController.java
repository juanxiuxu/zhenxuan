///**
// * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
// */
//package com.zhenxuan.tradeapi.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.zhenxuan.tradeapi.common.JsonResult;
//import com.zhenxuan.tradeapi.model.ApplyRolePojo;
//import com.zhenxuan.tradeapi.model.MenuListPojo;
//import com.zhenxuan.tradeapi.service.CurrentUserService;
//import com.zhenxuan.tradeapi.service.ProjectService;
//import com.zhenxuan.tradeapi.service.RoleService;
//import com.zhenxuan.tradeapi.service.UuapService;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//import java.util.UUID;
//
///**
// * 退出UUAP.
// */
//@Controller
//public class CurrentUserController {
//
//    /** log. */
//    private static final Logger LOG = LoggerFactory.getLogger(CurrentUserController.class);
//
//    /** 注入UserService. */
//    @Autowired
//    private CurrentUserService currentUserService;
//
//    @Autowired
//    private ProjectService projectService;
//
//    @Autowired
//    private RoleService roleService;
//
//    @Autowired
//    private UuapService uuapService;
//
//    /**
//     * 获得当前用户可以申请的角色
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/getCouldApplyRole")
//    public Object getCouldApplyRole() {
//        String uid = uuapService.getCurrentUserName();
//        List<RoleEntity> roleEntityList = roleService.getRoleWithoutCurrentUser(uid);
//        JSONObject result = new JSONObject();
//        result.put("roleList", roleEntityList);
//        return JsonResult.success(result);
//    }
//
//    /**
//     * 申请角色
//     * @param pojo
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/applyRoles", method = RequestMethod.POST)
//    public Object applyRoles(@RequestBody ApplyRolePojo pojo) {
//        LOG.info("applyRoles:" + pojo.toString());
//        if (CollectionUtils.isEmpty(pojo.getRoleList())) {
//            return JsonResult.paramsError("role list null");
//        }
//        for (RoleEntity role : pojo.getRoleList()) {
//            if (role.getRoleId() == 0
//                    || StringUtils.isEmpty(role.getRoleName())
//                    || StringUtils.isEmpty(role.getOwner())) {
//                return JsonResult.paramsError("roleId or roleName error");
//            }
//        }
//        currentUserService.applyRole(pojo);
//        return JsonResult.success("");
//    }
//
//    /**
//     * 查看申请信息
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/getApplyInfo")
//    public Object getApplyInfo() {
//        return JsonResult.success(currentUserService.getApplyInfo());
//    }
//
//
//    /**
//     * 提交自定义菜单
//     * @param pojo
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/submitShortCut", method = RequestMethod.POST)
//    public Object submitShortCut(@RequestBody MenuListPojo pojo) {
//        if (pojo == null || CollectionUtils.isEmpty(pojo.getMenuList())) {
//            return JsonResult.paramsError("参数异常");
//        }
//        if (pojo.getMenuList().size() > 5) {
//            return JsonResult.paramsError("不能多于5个菜单");
//        }
//        currentUserService.submitShortCut(pojo);
//        return JsonResult.success("");
//    }
//
//    /**
//     * 获取快捷菜单
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/getShortCut", method = RequestMethod.POST)
//    public Object getShortCut() {
//        return JsonResult.success(currentUserService.getShortCut());
//    }
//
//
//    /**
//     * 获取菜单树
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/getUserMenuList", method = RequestMethod.POST)
//    public Object getUserMenuList() {
//        return JsonResult.success(currentUserService.getCurrentUserMenuTree());
//    }
//
//
//    /**
//     * 获取用户资源列表
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/getUserResourceList", method = RequestMethod.POST)
//    public Object getUserResourceList() {
//        return JsonResult.success(currentUserService.getCurrentUserResource());
//    }
//
//
//    /**
//     * 根据申请id获取菜单树
//     * @param pojo
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/getPermissionTreeByApplyId", method = RequestMethod.POST)
//    public Object getPermissionTreeByApplyId(@RequestBody ApplyEntity pojo) {
//        return JsonResult.success(currentUserService.getApplyPermissionTree(pojo));
//    }
//
//    /**
//     * 重定向
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/permission/redirect")
//    public ModelAndView redirect(HttpServletRequest request) {
//        String redirectUrl = "";
//        String menuUrl = request.getParameter("menuUrl");
//        String projectId = request.getParameter("projectId");
//        String bizId = request.getParameter("bizId");
//        String processId = request.getParameter("processId");
//        String erp = request.getParameter("erp");
//        String activityId = request.getParameter("activityId");
//        LOG.info("menuUrl " + menuUrl);
//        LOG.info("projectId " + projectId);
//        ProjectEntity projectEntity = new ProjectEntity();
//        projectEntity.setProjectId(Long.parseLong(projectId));
//        List<ProjectEntity> plist = projectService.selectByParam(projectEntity);
//        if (CollectionUtils.isNotEmpty(plist)) {
//            redirectUrl += plist.get(0).getProjectUrl();
//            redirectUrl += menuUrl;
//            String token = currentUserService.getToken();
//            redirectUrl += ("?token=" + token);
//            redirectUrl += ("&userName=" + uuapService.getCurrentUserName());
//            redirectUrl += ("&random=" + UUID.randomUUID().toString());
//            if (StringUtils.isNotBlank(erp)) {
//                redirectUrl += ("&erp=" + erp);
//                redirectUrl += ("&bizId=" + bizId);
//                redirectUrl += ("&processId=" + processId);
//                redirectUrl += ("&activityId=" + activityId);
//            }
//        }
//        ModelAndView mav = new ModelAndView();
//        if (StringUtils.isNotBlank(redirectUrl)) {
//            LOG.info("redirect:" + redirectUrl);
//            mav.setViewName("redirect:http://" + redirectUrl);
//        }
//        return mav;
//    }
//
//
//
//}
