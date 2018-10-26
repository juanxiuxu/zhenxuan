//package com.zhenxuan.tradeapi.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.zhenxuan.tradeapi.common.JsonResult;
//import com.zhenxuan.tradeapi.common.ZXException;
//import com.zhenxuan.tradeapi.model.RoleListPojo;
//import com.zhenxuan.tradeapi.service.MenuTreeService;
//import org.apache.commons.collections4.CollectionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//
//@Controller
//public class MenuTreeController {
//
//    private static final Logger LOG = LoggerFactory.getLogger(MenuTreeController.class);
//
//    @Autowired
//    private MenuTreeService menuTreeService;
//
//
//    /**
//     * 获取已知角色列表的菜单树
//     * @param roleListPojo
//     * @return
//     * @throws ZXException
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/getMenuTreeByRoleList", method = RequestMethod.POST)
//    public Object getMenuTreeByRoleList(@RequestBody RoleListPojo roleListPojo) throws ZXException {
//        LOG.info("getMenuTreeByRoleList CreateUserPojo:{}" , roleListPojo.toString());
//        if (CollectionUtils.isEmpty(roleListPojo.getRoleList())) {
//            return JsonResult.success("");
//        }
//        JSONObject result = menuTreeService.getMenuTreeByRoleList(roleListPojo.getRoleList(), true);
//
//        return JsonResult.success(result);
//    }
//
//
//    /**
//     * 获取当前角色列表下还可以申请的菜单树
//     * @param roleListPojo
//     * @return
//     * @throws ZXException
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/getCouldApplyMenuTreeByRoleList", method = RequestMethod.POST)
//    public Object getCouldApplyMenuTreeByRoleList(@RequestBody RoleListPojo roleListPojo) {
//        LOG.info("roleListPojo:{}" , roleListPojo.toString());
//        if (CollectionUtils.isEmpty(roleListPojo.getRoleList())) {
//            return JsonResult.paramsError("role error");
//        }
//        JSONObject result = menuTreeService.getExceptMenuTreeByRoleList(roleListPojo.getRoleList(), true);
//        return JsonResult.success(result);
//    }
//
//    /**
//     * 获取所有的菜单树
//     * @return
//     * @throws ZXException
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/getAllMenuTree", method = RequestMethod.POST)
//    public Object getAllMenuTree() {
//        LOG.info("getAllMenuTree");
//        JSONObject result = menuTreeService.getAllMenuTree(true);
//        return JsonResult.success(result);
//    }
//
//}
