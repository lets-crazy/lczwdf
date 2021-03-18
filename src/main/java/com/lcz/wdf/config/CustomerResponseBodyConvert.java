package com.lcz.wdf.config;

import com.lcz.wdf.entity.common.ResponseMessage;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一包装返回值
 */
@ControllerAdvice
public class CustomerResponseBodyConvert implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //已经封装过了就不需要再封装一次
        return !returnType.getParameterType().equals(ResponseMessage.class);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        int status = ((ServletServerHttpResponse) response).getServletResponse().getStatus();
        if (status == HttpStatus.UNAUTHORIZED.value()) {
            return ResponseMessage.unauthorized(o);
        } else if (o instanceof ResponseMessage) {
            return o;
        } else {
            return ResponseMessage.okWithStatus(status, o);
        }
    }
}
