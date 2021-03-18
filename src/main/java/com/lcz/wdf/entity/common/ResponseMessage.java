package com.lcz.wdf.entity.common;

import com.lcz.wdf.constant.ErrorMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 信息包装返回类
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/16 15:41
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {

    private int code;

    private boolean success;

    private String message;

    private Object data;

    public static ResponseMessage error(String msg) {
        return new ResponseMessage(500, false, msg, null);
    }

    /**
     * 构造错误信息的应答消息
     *
     * @param status 错误状态码
     * @param msg 错误消息
     * @return com.trs.ai.ty.common.ResponseMessage 返回给请求方的响应消息
     * @creator linwei
     * @since 2020/9/9 10:57 下午
     */
    public static ResponseMessage errorWithStatus(int status, String msg) {
        return new ResponseMessage(status, false, msg, null);
    }

    public static ResponseMessage ok(Object o) {
        return new ResponseMessage(200, true, null, o);
    }

    public static ResponseMessage okWithStatus(int status, Object o) {
        return new ResponseMessage(status, true, null, o);
    }

    public static ResponseMessage unauthorized(Object o) {
        return new ResponseMessage(401, false, ErrorMessages.UNAUTHORIZED_ROLE, o);
    }

    public static ResponseMessage shirouUnauthorized(String msg) {
        return new ResponseMessage(401, false, msg, null);
    }
}
