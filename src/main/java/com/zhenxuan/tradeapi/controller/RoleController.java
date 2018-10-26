//package com.zhenxuan.tradeapi.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.zhenxuan.tradeapi.common.JsonResult;
//import com.zhenxuan.tradeapi.model.CreateRolePojo;
//import com.zhenxuan.tradeapi.model.UpdateRolePojo;
//import com.zhenxuan.tradeapi.service.RoleService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.List;
//
//
//@Controller
//public class RoleController {
//
//    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);
//
//    @Autowired
//    private RoleService roleService;
//
//    @RequestMapping(value = "/permission/hello", method = RequestMethod.GET)
//    public Object hello() {
//        return JsonResult.success("permission world");
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/permission/getRoleByParam", method = RequestMethod.POST)
//    public Object getRoleByParam(@RequestBody RoleEntity pojo) {
//        LOG.info("RolePojo:{}" , pojo.toString());
//        List<RoleEntity> userlist = roleService.selectByParam(pojo);
//        JSONObject  obj = new JSONObject();
//        obj.put("rolelist", userlist);
//        return JsonResult.success(obj);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/permission/createRole", method = RequestMethod.POST)
//    public Object createRole(@RequestBody CreateRolePojo createRolePojo) throws Exception{
//        LOG.info("createRole createRolePojo:{}" , createRolePojo.toString());
//        LOG.info(createRolePojo.getUserList().toString());
//        roleService.createRole(createRolePojo);
//        return JsonResult.success("");
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/permission/deleteRole", method = RequestMethod.POST)
//    public Object deleteRole(@RequestBody RoleEntity pojo) {
//        LOG.info("deleteUser pojo:{}" , pojo.toString());
//        if (pojo.getRoleId() == 0) {
//            return JsonResult.paramsError("roleId error");
//        }
//        roleService.deleteRoleById(pojo.getRoleId());
//        return JsonResult.success("");
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/permission/updateRole", method = RequestMethod.POST)
//    public Object updateRole(@RequestBody UpdateRolePojo pojo) {
//        LOG.info("updateRole pojo:{}" , pojo.toString());
//        if (pojo.getRole().getRoleId() == 0) {
//            return JsonResult.paramsError("roleId error");
//        }
//        roleService.updateRoleById(pojo);
//        return JsonResult.success("");
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/permission/getRoleDetailById", method = RequestMethod.POST)
//    public Object getRoleDetailById(@RequestBody RoleEntity pojo) throws Exception {
//        LOG.info("getRoleDetailById pojo:{}" , pojo.toString());
//        return JsonResult.success(roleService.getRoleDetailById(pojo.getRoleId()));
//    }
//
////    @ResponseBody
////    @RequestMapping(value = "/getPermissionListByRoles", method = RequestMethod.POST)
////    public Object menuRelateToRole(@RequestBody RoleEntity pojo) {
////        LOG.info("createMenu pojo:{}" , pojo.toString());
////        List<RoleEntity> roleList = new ArrayList<RoleEntity>();
////        roleList.add(pojo);
////        return JsonResult.success(roleService.getPermissionListByRoles(roleList));
////    }
//}
