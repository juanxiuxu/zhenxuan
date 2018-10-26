package com.zhenxuan.tradeapi.common;

import com.google.common.base.Throwables;
import com.zhenxuan.tradeapi.common.enums.ResultStatusCode;
import com.zhenxuan.tradeapi.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ExceptionHandler implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);


    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object o, Exception e) {
        Object obj;
        if (e instanceof ZXException) {
            ZXException zxe = (ZXException) e;
            if (StringUtils.isEmpty(zxe.getExtraErrMsg())) {
                obj = ResultBody.failure(zxe.getResultStatusCode());
            } else {
                obj = ResultBody.failure(zxe.getResultStatusCode(), zxe.getExtraErrMsg());
            }
            logger.info("code:{}, extra msg:{}, err:{}", zxe.getResultStatusCode(), zxe.getExtraErrMsg(), Throwables.getStackTraceAsString(e));
        } else {
            obj = ResultBody.failure(ResultStatusCode.INTERNAL_ERROR);
            logger.error("err:{}", Throwables.getStackTraceAsString(e));
        }
        
        try {
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.getWriter().write(JsonUtil.toString(obj));
        } catch (IOException e1) {
            logger.error("write exception err:{}", Throwables.getStackTraceAsString(e1));
        }
        return new ModelAndView();
    }
}
