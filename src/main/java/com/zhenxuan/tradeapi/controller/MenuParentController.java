//package com.zhenxuan.tradeapi.controller;
//
//import com.alibaba.druid.util.StringUtils;
//import com.alibaba.fastjson.JSONObject;
//import com.zhenxuan.tradeapi.common.JsonResult;
//import com.zhenxuan.tradeapi.service.MenuParentService;
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
//public class MenuParentController {
//
//    private static final Logger LOG = LoggerFactory.getLogger(MenuParentController.class);
//
//    @Autowired
//    private MenuParentService menuParentService;
//
//    /**
//     * 获取父菜单列表
//     * @param pojo
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/getMenuParentByParam", method = RequestMethod.POST)
//    public Object getMenuParentByParam(@RequestBody MenuParentEntity pojo) {
//        LOG.info("getMenuParentByParam pojo:{}" , pojo.toString());
//        List<MenuParentEntity> list = menuParentService.selectMenuParentByParam(pojo);
//        JSONObject  obj = new JSONObject();
//        obj.put("menuParent", list);
//        return JsonResult.success(obj);
//    }
//
//    /**
//     * 创建父菜单
//     * @param pojo
//     * @return
//     * @throws Exception
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/createMenuParent", method = RequestMethod.POST)
//    public Object createMenuParent(@RequestBody MenuParentEntity pojo) {
//        LOG.info("createMenuParent pojo:{}" , pojo.toString());
//        if (StringUtils.isEmpty(pojo.getMenuParentName())) {
//            return JsonResult.paramsError("name null");
//        }
//        MenuParentEntity result = menuParentService.createtMenuParent(pojo);
//        return JsonResult.success(result);
//    }
//
//    /**
//     * 删除父菜单
//     * @param pojo
//     * @return
//     * @throws Exception
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/deleteMenuParent", method = RequestMethod.POST)
//    public Object deleteMenuParent(@RequestBody MenuParentEntity pojo) {
//        LOG.info("deleteMenuParent pojo:{}" , pojo.toString());
//        if (pojo.getMenuParentId() == 0) {
//            return JsonResult.failure("pojo.getMenuParentId() == 0");
//        }
//        menuParentService.deleteMenuParentById(pojo.getMenuParentId());
//        return JsonResult.success("");
//    }
//
//    /**
//     * 更新父菜单
//     * @param pojo
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/updateMenuParent", method = RequestMethod.POST)
//    public Object updateMenuParent(@RequestBody MenuParentEntity pojo) {
//        LOG.info("updateMenuParent pojo:{}" , pojo.toString());
//        if (pojo.getMenuParentId() == 0 || org.apache.commons.lang.StringUtils.isEmpty(pojo.getMenuParentName())) {
//            return JsonResult.paramsError("paramsError");
//        }
//        menuParentService.updateMenuParentById(pojo);
//        return JsonResult.success("");
//    }
//
//}
