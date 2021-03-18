package com.trs.ai.ty.entity.request;


import lombok.Getter;
import lombok.Setter;

/**
 * 用于接收前端发来的登录验证信息
 * @author yang.jinyu
 * @date 2020/6/5
 * @version 1.0
 **/
@Setter
@Getter
public class UserLoginRequest {

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;
}
