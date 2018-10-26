//package com.zhenxuan.tradeapi.controller;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import com.alibaba.fastjson.JSONObject;
//import com.zhenxuan.tradeapi.common.JsonResult;
//import com.zhenxuan.tradeapi.common.ZXException;
//import com.zhenxuan.tradeapi.service.ProjectService;
//
//
//@Controller
//public class ProjectController {
//
//    private static final Logger LOG = LoggerFactory.getLogger(ProjectController.class);
//
//    @Autowired
//    private ProjectService projectService;
//
//    /**
//     * 查看项目
//     * @param projectPojo
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/getProjectByParam", method = RequestMethod.POST)
//    public Object getProjectByParam(@RequestBody ProjectEntity projectPojo) {
//        LOG.info("getProjectByParam projectPojo:{}" , projectPojo.toString());
//        List<ProjectEntity> projectlist = projectService.selectByParam(projectPojo);
//        JSONObject  obj = new JSONObject();
//        obj.put("projectlist", projectlist);
//        return JsonResult.success(obj);
//    }
//
//    /**
//     * 创建项目
//     * @param projectPojo
//     * @return
//     * @throws ZXException
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/createProject", method = RequestMethod.POST)
//    public Object createProject(@RequestBody ProjectEntity projectPojo) throws ZXException {
//        LOG.info("createProject projectPojo:{}", projectPojo.toString());
//        if (projectPojo.getOwner() ==  null
//                || projectPojo.getProjectName() == null
//                || projectPojo.getProjectUrl() == null) {
//            return JsonResult.paramsError("paramsError");
//        }
//        int returncode = projectService.createProject(projectPojo);
//        return JsonResult.success(returncode);
//    }
//
//    /**
//     * 删除项目
//     * @param projectPojo
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/deleteProject", method = RequestMethod.POST)
//    public Object deleteProject(@RequestBody ProjectEntity projectPojo) {
//        LOG.info("projectPojo.getProjectId():" + projectPojo.getProjectId());
//        if (projectPojo.getProjectId() == 0) {
//            return JsonResult.paramsError("ProjectId error");
//        }
//        int returncode = projectService.deleteProjectById(projectPojo.getProjectId());
//        return JsonResult.success(returncode);
//    }
//
//    /**
//     * 更新项目
//     * @param projectPojo
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/permission/updateProject", method = RequestMethod.POST)
//    public Object updateProject(@RequestBody ProjectEntity projectPojo) {
//        LOG.info("updateProject projectPojo.getProjectId():" + projectPojo.getProjectId());
//        if (projectPojo.getProjectId() == 0) {
//            return JsonResult.paramsError("ProjectId error");
//        }
//        int returncode = projectService.updateProjectById(projectPojo);
//        return JsonResult.success(returncode);
//    }
//}
