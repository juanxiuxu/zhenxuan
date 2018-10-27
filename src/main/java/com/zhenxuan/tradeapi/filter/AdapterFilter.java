//package com.zhenxuan.tradeapi.filter;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.zhenxuan.tradeapi.common.AccessResult;
//import com.zhenxuan.tradeapi.common.JsonResult;
//import com.zhenxuan.tradeapi.common.ResultBody;
//import com.zhenxuan.tradeapi.constants.Constants;
//import com.zhenxuan.tradeapi.common.enums.ResultStatusCode;
//import com.zhenxuan.tradeapi.dao.entity.UserLoginEntity;
//import com.zhenxuan.tradeapi.service.CurrentUserService;
//import com.zhenxuan.tradeapi.service.HistoryService;
//import com.zhenxuan.tradeapi.service.RoleService;
//import com.zhenxuan.tradeapi.service.impl.UserServiceImpl;
//import com.zhenxuan.tradeapi.utils.DesUtil;
//import com.zhenxuan.tradeapi.utils.DigestUtil;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//
//import javax.annotation.Resource;
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.*;
//
///**
// * Created by silencehere on 16/10/13.
// */
//public class AdapterFilter implements Filter {
//
//    /** log. */
//    private static final Logger LOG = LoggerFactory.getLogger(AdapterFilter.class);
//
//    /**
//     * 权限回调：密钥.
//     */
//    @Value("${authority.callback.secretkey}")
//    private String secretkey;
//
//    @Resource
//    private UserServiceImpl userService;
//
//    @Resource
//    private RoleService roleService;
//
//    @Resource
//    private CurrentUserService currentUserService;
//
//    @Resource
//    public HistoryService historyService;
//
//    @Resource
//    private UserMapper userMapper;
//
//
//
//    public void init(FilterConfig filterConfig) throws ServletException {
//        LOG.info("init");
//    }
//
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
//            throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        String servletPath = request.getServletPath();
//
//        if ("/permission/externalAccessController".equals(servletPath)) {
//            externalAccessController(request, response);
//        } else if ("/permission/externalGetUserController".equals(servletPath)) {
//            externalGetUserController(request, response);
//        } else if ("/permission/externalGetUserResource".equals(servletPath)) {
//            externalGetUserResource(request, response);
//        } else if ("/permission/externalGetUserRole".equals(servletPath)) {
//            externalGetUserRole(request, response);
//        } else if ("/permission/applyResult".equals(servletPath)) {
//            applyRoleResult(request, response);
//        } else if ("/permission/getCurrentUserResource".equals(servletPath)) {
//            responseJson(response, JsonResult.success(currentUserService.getCurrentUserResource()));
//        } else if ("/permission/getUserListByRoleId".equals(servletPath)) {
//            getUserListByRoleId(request, response);
//        }  else {
//            filterChain.doFilter(request, response);
//        }
//
//    }
//
//    private void getUserListByRoleId(HttpServletRequest request, HttpServletResponse response) {
//        String roleId = request.getParameter("roleId");
//        RoleEntity roleE =  new RoleEntity();
//        roleE.setRoleId(Integer.parseInt(roleId));
//        List<RoleEntity> roleList = new ArrayList<RoleEntity>();
//        roleList.add(roleE);
//        responseJson(response, userMapper.selectByRoleList(roleList));
//    }
//
//
//    public void destroy() {
//        LOG.info("adapterFilter destroy:");
//    }
//
//
//    /**
//     * 返回json结果
//     * @param response
//     * @param accessResult
//     */
//    private void responseJson(ServletResponse response, Object accessResult) {
//        PrintWriter out = null;
//        try {
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json; charset=utf-8");
//            out = response.getWriter();
//            out.append(JSONObject.toJSONString(accessResult));
//        } catch (IOException e) {
//            LOG.error("ExternalConnectionFilter response error.", e);
//        } finally {
//            if (out != null) {
//                out.close();
//            }
//        }
//    }
//
//    /**================================= 我是分界线,下面是处理获取用户角色的逻辑==============================**/
//
//    private void externalGetUserRole(HttpServletRequest request, HttpServletResponse response) {
//        LOG.info("externalGetUserRole");
//        String token = request.getParameter("token");
//        UserLoginEntity userEntity = new UserLoginEntity();
//        userEntity.setToken(token);
//        List<UserLoginEntity> userList = userService.selectByParam(userEntity);
//        ResultBody result = null;
//        if (StringUtils.isNotEmpty(token) && !"0".equals(token) && CollectionUtils.isNotEmpty(userList)) {
//            List<RoleEntity> roleList = roleService.selectRoleByUserId(userList.get(0).getUserId());
//            JSONObject data = new JSONObject();
//            data.put("roleList", roleList);
//            result = new ResultBody(ResultStatusCode.SUCCESS, data);
//        } else {
//            JSONObject data = new JSONObject();
//            data.put("roleList", new ArrayList<String>());
//            result = new ResultBody(ResultStatusCode.SUCCESS, data);
//        }
//        responseJson(response, result);
//    }
//
//    /**================================= 我是分界线,下面是处理获取用户可用资源的逻辑==============================**/
//
//    private void externalGetUserResource(HttpServletRequest request, HttpServletResponse response) {
//        LOG.info("externalGetUserResource");
//        String token = request.getParameter("token");
//        UserLoginEntity userEntity = new UserLoginEntity();
//        userEntity.setToken(token);
//        List<UserLoginEntity> userList = userService.selectByParam(userEntity);
//        ResultBody result = null;
//        if (StringUtils.isNotEmpty(token) && !"0".equals(token) && CollectionUtils.isNotEmpty(userList)) {
//            List<String> resourceList = userService.getUserResourceByUserId(userList.get(0).getUserId());
//            JSONObject data = new JSONObject();
//            data.put("resourceList", resourceList);
//            result = new ResultBody(ResultStatusCode.SUCCESS, data);
//        } else {
//            JSONObject data = new JSONObject();
//            data.put("resourceList", new ArrayList<String>());
//            result = new ResultBody(ResultStatusCode.SUCCESS, data);
//        }
//        responseJson(response, result);
//
//    }
//
//    /**================================= 我是分界线,下面是处理获取用户信息的逻辑==============================**/
//
//    private void externalGetUserController(HttpServletRequest request, HttpServletResponse response) {
//        LOG.info("externalGetUserController");
//        String token = request.getParameter("token");
//        UserLoginEntity userEntity = new UserLoginEntity();
//        userEntity.setToken(token);
//        List<UserLoginEntity> userList = userService.selectByParam(userEntity);
//        JSONObject responseJSONObject = new JSONObject();
//
//        if (StringUtils.isNotEmpty(token) && !"0".equals(token) && CollectionUtils.isNotEmpty(userList)) {
//            responseJSONObject.put("status", 0);
//            responseJSONObject.put("id", userList.get(0).getUserId());
//            responseJSONObject.put("username", userList.get(0).getUserName());
//            responseJSONObject.put("realname", userList.get(0).getName());
//        } else {
//            responseJSONObject.put("status", 1);
//        }
//        responseJson(response, responseJSONObject);
//
//    }
//
//    /**================================= 我是分界线,下面是处理申请结果的逻辑==============================**/
//
//    public void applyRoleResult(HttpServletRequest request, HttpServletResponse response) {
//        LOG.info("applyRoleResult");
//        String starterName = request.getParameter("starterName");
//        String processId = request.getParameter("GWFP_PROCESS_ID");
//        String processState = request.getParameter("processState");
//
//        LOG.info("starterName:" + starterName + " processId:" + processId
//                + " processState:" + processState);
//        String result = currentUserService.applyOver(starterName, processId, Integer.parseInt(processState));
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json; charset=utf-8");
//        try {
//            response.getWriter().print(result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**================================= 我是分界线,下面是处理权限的逻辑==============================**/
//
//    /**
//     * 处理是否有权限
//     * @param request
//     * @param response
//     */
//    public void externalAccessController(HttpServletRequest request, HttpServletResponse response) {
//        LOG.info("externalAccessController begin");
//        AccessResult accessResult = null;
//        long nowtime = new Date().getTime();
//        String app = request.getParameter("app");
//        String timestamp = request.getParameter("timestamp");
//        String url = request.getParameter("url");
//        String token = request.getParameter("token");
//        String sign = request.getParameter("sign");
//
//        LOG.info("nowtime:" + nowtime + " app:" + app + " timestamp:" + timestamp + " url:" + url
//                + " token:" + token + " sign:" + sign);
//
//        if (StringUtils.isBlank(app) || StringUtils.isBlank(timestamp)
//                || StringUtils.isBlank(url) || StringUtils.isBlank(token) || StringUtils.isBlank(sign)) {
//            accessResult = new AccessResult(ResultStatusCode.PARAMS_ERROR, "参数错误", String.valueOf(nowtime));
//            LOG.error("参数错误");
//        } else if (nowtime > (Long.parseLong(timestamp) + Constants.HALF_HOUR_MILLISECOND)) {
//            accessResult = new AccessResult(ResultStatusCode.TIME_OUT, "超时", String.valueOf(nowtime));
//            LOG.error("连接超时");
//        } else if (!verifySignature(app, timestamp, url, token, sign)) {
//            LOG.error("验签失败 verifySignature param(app:" + app + ";timestamp:" + timestamp
//                    + ";url:" + url + ";token:" + token + ";sign:" + sign + ")");
//            accessResult = new AccessResult(ResultStatusCode.SIGN_ERROR, "验签失败", String.valueOf(nowtime));
////        } else if (checkCache(app, url, token)) {
////            // 在缓存里面
////            accessResult = new AccessResult(ResultStatusCode.SUCCESS, "验证通过", String.valueOf(nowtime));
////            // 更新用户最后访问时间
////            userService.updateLastAccessTimeByToken(token);
//        } else {
//            // 没有缓存时,根据token查找用户,并判断是否有权限
//            UserLoginEntity dao = new UserLoginEntity();
//            dao.setToken(token);
//            List<UserLoginEntity> userEntityList = userService.selectByParam(dao);
//            LOG.info("userEntityList:" + JSON.toJSONString(userEntityList));
//            if (userEntityList == null || userEntityList.size() == 0 || userEntityList.get(0) == null) {
//                LOG.error("用户不存在或者token过期");
//                accessResult = new AccessResult(ResultStatusCode.USER_NOT_EXIST, "用户不存在或者token过期",
//                        String.valueOf(nowtime));
//            } else {
//                List<String> resourceList = userService.getUserResourceByUserId(userEntityList.get(0).getUserId());
//                if (resourceList == null || resourceList.isEmpty() || !resourceList.contains(url)) {
//                    LOG.info("用户没有权限");
//                    accessResult = new AccessResult(ResultStatusCode.NO_PERMISSION, "用户没有权限", String.valueOf(nowtime));
//                } else {
//                    accessResult = new AccessResult(ResultStatusCode.SUCCESS, "验证通过", String.valueOf(nowtime));
//                    accessResult.setUserName(userEntityList.get(0).getUserName());
//                    // 更新用户最后访问时间
//                    userService.updateLastAccessTimeByToken(token);
//                    HistoryEntity historyEntity = new HistoryEntity();
//                    historyEntity.setUserName(userEntityList.get(0).getUserName());
//                    historyEntity.setResourceUrl(url);
//                    historyService.insert(historyEntity);
//                    LOG.info(userEntityList.get(0).getUserName() + "验证通过");
//                }
//            }
//
//        }
//        responseInfo(response, accessResult);
//    }
//
//    /**
//     * 返回json形式的验证结果.
//     *
//     * @param response
//     * @param accessResult
//     */
//    private void responseInfo(ServletResponse response, AccessResult accessResult) {
//        PrintWriter out = null;
//        try {
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json; charset=utf-8");
//            sign(accessResult);
//            out = response.getWriter();
//            out.append(JSONObject.toJSONString(accessResult));
//        } catch (IOException e) {
//            LOG.error("ExternalConnectionFilter response error.", e);
//        } finally {
//            if (out != null) {
//                out.close();
//            }
//        }
//    }
//
//    /**
//     * 生成外联地址用户签名信息 .
//     *
//     * @param accessResult .
//     * @return 签名信息.
//     */
//    private String sign(AccessResult accessResult) {
//
//        String result = null;
//        try {
//
//            String sourceStr = "status=" + String.valueOf(accessResult.getStatus()) + "&msg="
//                    + accessResult.getMsg() + "&timestamp=" + accessResult.getTimestamp()
//                    + "&key=" + DesUtil.decrypt(secretkey);
//
//            String sign = DigestUtil.md5(sourceStr);
//            accessResult.setSign(sign);
//        } catch (RuntimeException e) {
//            LOG.error("ExternalConnectionFilter sign error.", e);
//            return null;
//        }
//        return result;
//    }
//
//    /**
//     * 检查缓存中是否有记录,有则返回true,没有则返回false
//     * @param app
//     * @param url
//     * @param token
//     * @return
//     */
////    private boolean checkCache(String app, String url, String token) {
////        HashSet<String> urlSet = null;
////        Element appUrlMapEle = ehCache.get(token);
////        if (appUrlMapEle != null) {
////            HashMap<String, HashSet<String>> appUrlMap =
////                    (HashMap<String, HashSet<String>>) appUrlMapEle.getObjectValue();
////            if (appUrlMap != null && appUrlMap.get(app) != null) {
////                urlSet = appUrlMap.get(app);
////            }
////        }
////        if (urlSet != null && urlSet.contains(url)) {
////            return true;
////        }
////        return false;
////    }
//
//    /**
//     * 验证签名 .
//     * @param app
//     * @param timestamp
//     * @param url
//     * @param token
//     * @param sourceSign
//     * @return
//     */
//    private boolean verifySignature(String app, String timestamp, String url, String token, String sourceSign) {
//
//        if (StringUtils.isBlank(app)
//                || StringUtils.isBlank(timestamp)
//                || StringUtils.isBlank(url)
//                || StringUtils.isBlank(token)
//                || StringUtils.isBlank(sourceSign)) {
//            return false;
//        }
//        String targetSign = "";
//        try {
//            String sourceStr =
//                    "app=" + app + "&timestamp=" + timestamp + "&token=" + token
//                            + "&url=" + url + "&key="
//                            + DesUtil.decrypt(secretkey);
//            LOG.info("secretkey:" + secretkey);
//            LOG.info("DesUtils.decryptKey(secretkey):" + DesUtil.decrypt(secretkey));
//            LOG.info("sourceStr:" + sourceStr);
//            targetSign = DigestUtil.md5(sourceStr);
//            LOG.info("targetSign:" + targetSign);
//        } catch (RuntimeException e) {
//            LOG.error("ExternalConnectionFilter verifySignature error.", e);
//            return false;
//        }
//        return targetSign.equalsIgnoreCase(sourceSign);
//    }
//}
