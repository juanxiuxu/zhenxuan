package com.zhenxuan.tradeapi.interceptor;

import com.zhenxuan.tradeapi.common.ZXException;
import com.zhenxuan.tradeapi.common.http.BaseResponse;
import com.zhenxuan.tradeapi.common.enums.ResultStatusCode;
import com.zhenxuan.tradeapi.utils.JsonUtil;
import com.zhenxuan.tradeapi.vo.Input;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * Interception for spring mvc controller.
 */
public class ControllerParamsInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ControllerParamsInterceptor.class);

    private Validator validator;

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object[] args = invocation.getArguments();

        BaseResponse response = null;
        Input input = null;
        // 打印请求参数,参数校验
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                if (arg instanceof Input) {
                    logRequest((Input) arg);
                    input = (Input) arg;
                    BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(input, "input");
                    validator.validate(input, bindingResult);
                    parseValidateResult(bindingResult);
                    continue;
                }
            }
        } else {
            logRequest(null);
        }

        Object proceedResult = invocation.proceed();

        // 打印返回参数
        logResponse(proceedResult);
        return proceedResult;
    }

    /**
     * Log http response in json format.
     *
     * @param response
     */
    private void logResponse(Object response) {
        logger.info("Log response :{}", JsonUtil.toString(response));
    }

    /**
     * Log http request.
     *
     * @param input request entity
     *
     * @return request params in JSON string
     */
    private void logRequest(Input input) {
        if (input == null) {
            logger.info("Log request method params: null");
        } else {
            logger.info("Log request method params: {}", JsonUtil.toString(input));
        }
    }

    /**
     * 检查校验结果
     */
    private void parseValidateResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errmsg = new StringBuilder();
            List<FieldError> errorList = bindingResult.getFieldErrors();
            if (errorList.size() > 0) {
                errmsg.append(errorList.get(0).getField());
                errmsg.append(" ");
                errmsg.append(errorList.get(0).getDefaultMessage());
            }
            logger.warn("LOG_PARAM_ERROR: {}", errmsg.toString());
            throw new ZXException(ResultStatusCode.PARAM_ERROR, errmsg.toString());
        }
    }
}
