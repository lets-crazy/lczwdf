package com.lcz.wdf.entity.exception;

/**
 * Created by wangxuan on 2017/5/10.
 */
public class IllegalDataFormatException extends Exception {

    public IllegalDataFormatException() {

        super();
    }

    public IllegalDataFormatException(String message) {

        super(message);
    }

    public IllegalDataFormatException(String message, Throwable cause) {

        super(message, cause);
    }

    public IllegalDataFormatException(Throwable cause) {

        super(cause);
    }

    protected IllegalDataFormatException(String message, Throwable cause,
                                         boolean enableSuppression,
                                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
