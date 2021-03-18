package com.lcz.wdf.entity.exception;

/**
 * 用于处理各种业务时的运行时异常
 * Created by he.lang on 2017/7/7.
 */
public class BizRuntimeException extends RuntimeException {

    /**
     * 其他问题
     */
    public static final Integer OTHER_ERROR_CODE = 0;
    /**
     * 图片有问题
     */
    public static final Integer PROBLEM_IMAGE_CODE = 1;
    /**
     * OCR结果为空
     */
    public static final Integer EMPTY_CONTENT_CODE = 2;

    /**
     * 释放之后再使用
     */
    public static final Integer USE_RELEASED_RES_CODE = 3;

    private final Integer errorCode;

    public BizRuntimeException(String message, Throwable cause, Integer errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BizRuntimeException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

}
