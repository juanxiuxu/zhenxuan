//package com.zhenxuan.tradeapi.controller;
//
//import java.util.List;
//import com.alibaba.druid.util.StringUtils;
//import com.zhenxuan.tradeapi.common.ZXException;
//import com.zhenxuan.tradeapi.dao.entity.UserLoginEntity;
//import com.zhenxuan.tradeapi.model.MenuPojo;
//import com.zhenxuan.tradeapi.model.PermissionAndResource;
//import com.zhenxuan.tradeapi.model.CreateMenuPojo;
//import com.zhenxuan.tradeapi.model.RoleAndPermissionList;
//import com.zhenxuan.tradeapi.model.RoleAndPermission;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSONObject;
//import com.zhenxuan.tradeapi.common.JsonResult;
//import com.zhenxuan.tradeapi.service.MenuService;
//
//
//@Controller
//public class MenuController {
//
//    private static final Logger LOG = LoggerFactory.getLogger(MenuController.class);
//
//    @Autowired
//    private MenuService menuService;
//
//    /**
//     * 子菜单查询
//     * @param pojo
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/selectMenuByParam", method = RequestMethod.POST)
//    public Object selectMenuByParam(@RequestBody MenuEntity pojo) {
//        LOG.info("selectMenuByParam pojo:{}" , pojo.toString());
//        List<MenuPojo> list = menuService.selectMenuPojoByParam(pojo);
//        JSONObject  obj = new JSONObject();
//        obj.put("menuList", list);
//        return JsonResult.success(obj);
//    }
//
//    /**
//     * 查看子菜单相关的权限
//     * @param pojo
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/getMenuPermissionAndResource", method = RequestMethod.POST)
//    public Object getMenuPermissionAndResource(@RequestBody MenuEntity pojo) {
//        LOG.info("getMenuDetail pojo:{}" , pojo.toString());
//        if (pojo.getMenuId() == 0) {
//            return JsonResult.paramsError("MenuId error");
//        }
//
//        List<PermissionAndResource> permissionAndResourceList = menuService.getPermissionListByMenuId(pojo.getMenuId());
//        JSONObject result = new JSONObject();
//        result.put("permissionList", permissionAndResourceList);
//
//        return JsonResult.success(result);
//    }
//
//    /**
//     * 查询此菜单相关的角色
//     * @param pojo
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/getMenuRoleList", method = RequestMethod.POST)
//    public Object getMenuRoleList(@RequestBody MenuEntity pojo) {
//        LOG.info("getMenuDetail pojo:{}" , pojo.toString());
//        if (pojo.getMenuId() == 0) {
//            return JsonResult.paramsError("MenuId error");
//        }
//
//        List<RoleEntity> roleList = menuService.getRoleListByMenuId(pojo.getMenuId());
//        JSONObject result = new JSONObject();
//        result.put("roleList", roleList);
//        return JsonResult.success(result);
//    }
//
//    /**
//     * 查询此菜单相关用户
//     * @param pojo
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/getMenuUserList", method = RequestMethod.POST)
//    public Object getMenuUserList(@RequestBody MenuEntity pojo) {
//        LOG.info("getMenuDetail pojo:{}" , pojo.toString());
//        if (pojo.getMenuId() == 0) {
//            return JsonResult.paramsError("MenuId error");
//        }
//
//        List<UserLoginEntity> userList = menuService.getUserListByMenuId(pojo.getMenuId());
//        JSONObject result = new JSONObject();
//        result.put("userList", userList);
//        return JsonResult.success(result);
//    }
//
//    /**
//     * 新建菜单
//     * @param pojo
//     * @return
//     * @throws ZXException
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/createMenu", method = RequestMethod.POST)
//    public Object createMenu(@RequestBody CreateMenuPojo pojo) {
//        LOG.info("createMenu pojo:{}" , pojo.toString());
//        if (pojo.getMenu() == null
//                || StringUtils.isEmpty(pojo.getMenu().getMenuName())
//                || StringUtils.isEmpty(pojo.getMenu().getMenuUrl())
//                || StringUtils.isEmpty(pojo.getMenu().getOwner())
//                || pojo.getMenu().getMenuParentId() == 0
//                || pojo.getMenu().getProjectId() == 0
//            ) {
//            return JsonResult.paramsError("paramsError");
//        }
//        MenuEntity menuEntity = menuService.createMenu(pojo);
//        return JsonResult.success(menuEntity);
//    }
//
//    /**
//     * 更新菜单
//     * @param pojo
//     * @return
//     * @throws ZXException
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/updateMenu", method = RequestMethod.POST)
//    public Object updateMenu(@RequestBody CreateMenuPojo pojo) {
//        LOG.info("createMenu pojo:{}" , pojo.toString());
//        if (pojo.getMenu() == null
//                || StringUtils.isEmpty(pojo.getMenu().getMenuName())
//                || StringUtils.isEmpty(pojo.getMenu().getMenuUrl())
//                || StringUtils.isEmpty(pojo.getMenu().getOwner())
//                || pojo.getMenu().getMenuParentId() == 0
//                || pojo.getMenu().getProjectId() == 0
//            ) {
//            return JsonResult.paramsError("paramsError");
//        }
//        menuService.updateMenu(pojo);
//        return JsonResult.success(pojo.getMenu());
//    }
//
//
//
//
//    @ResponseBody
//    @RequestMapping(value = "/permission/deleteMenu", method = RequestMethod.POST)
//    public Object deleteMenu(@RequestBody MenuEntity pojo) {
//        LOG.info(" pojo:{}" , pojo.toString());
//        if (pojo.getMenuId() == 0) {
//            return JsonResult.failure("pojo.getMenuId() == 0");
//        }
//        menuService.deleteMenuById(pojo.getMenuId());
//        return JsonResult.success("");
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/permission/getMenuRelatedToRoleInfo", method = RequestMethod.POST)
//    public Object getMenuRelatedToRoleInfo(@RequestBody MenuEntity pojo) {
//        LOG.info(" pojo:{}" , pojo.toString());
//        if (pojo.getMenuId() == 0) {
//            return JsonResult.failure("pojo.getMenuId() == 0");
//        }
//        List<RoleAndPermission> list = menuService.getMenuRelatedToRoleInfo(pojo.getMenuId());
//        JSONObject result =  new JSONObject();
//        result.put("roleAndPermissionList", list);
//        return JsonResult.success(result);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/permission/deleteMenuRelatedToRoleInfo", method = RequestMethod.POST)
//    public Object deleteMenuRelatedToRoleInfo(@RequestBody RoleAndPermission pojo) {
//        LOG.info(" pojo:{}" , pojo.toString());
//        if (pojo.getMenuId() == 0 || pojo.getRoleId() == 0 || pojo.getPermissionId() == 0) {
//            return JsonResult.failure("参数有误");
//        }
//        menuService.deleteMenuRelatedToRoleInfo(pojo);
//        return JsonResult.success("");
//    }
//
//    /**
//     * 菜单关联到角色
//     * @param pojo
//     * @return
//     * @throws ZXException
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/menuRelateToRole", method = RequestMethod.POST)
//    public Object menuRelateToRole(@RequestBody RoleAndPermissionList pojo) {
//        LOG.info(" pojo:{}" , pojo.toString());
//        if (pojo.getMenuId() == 0 || pojo.getRoleId() == 0 || pojo.getPermissionList() ==  null) {
//            return JsonResult.failure("参数有误");
//        }
//        menuService.menuRelateToRole(pojo);
//        return JsonResult.success("");
//    }
//
//}
