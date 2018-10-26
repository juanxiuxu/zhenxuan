//package com.zhenxuan.tradeapi.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.zhenxuan.tradeapi.common.JsonResult;
//import com.zhenxuan.tradeapi.model.HistoryPojo;
//import com.zhenxuan.tradeapi.service.HistoryService;
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
//public class HistoryController {
//
//    private static final Logger LOG = LoggerFactory.getLogger(HistoryController.class);
//
//    @Autowired
//    private HistoryService historyService;
//
//
//    /**
//     *查询历史记录
//     * @param historyPojo
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/getHistoryByParam", method = RequestMethod.POST)
//    public Object getHistoryByParam(@RequestBody HistoryPojo historyPojo) {
//        LOG.info("UserController getUserByParam userPojo:{}" , historyPojo.toString());
//        if (historyPojo == null || historyPojo.geteTime() == null || historyPojo.getsTime() == null) {
//            return JsonResult.failure("时间段必选");
//        }
//        JSONObject result = new JSONObject();
//        result.put("historyList", historyService.selectByParams(historyPojo));
//        return JsonResult.success(result);
//    }
//
//
//}
