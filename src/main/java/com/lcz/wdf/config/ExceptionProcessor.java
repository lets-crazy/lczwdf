package com.lcz.wdf.config;

import com.lcz.wdf.entity.common.ResponseMessage;
import com.lcz.wdf.entity.exception.BizException;
import com.lcz.wdf.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/16 15:10
 **/
@ControllerAdvice
@Order
@Aspect
@Slf4j
public class ExceptionProcessor {
    static final String BIZ_EXCEPTION_MESSAGE = "业务调用失败";

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public ResponseMessage handBizException(Exception ex) {
        logBizException((BizException) ex);
        return ResponseMessage.error(StringUtil.isEmpty(ex.getMessage()) ? BIZ_EXCEPTION_MESSAGE : ex.getMessage());
    }

    private void logBizException(BizException ex) {
        log.warn("", ex);
    }

}
