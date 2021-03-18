package com.lcz.wdf.entity.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author li.chengzhen
 */
@Setter
@Getter
public class UserLoginRequest {

    /**
     * 账号
     */
    @NotBlank(message = "账号缺失！")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码缺失！")
    private String password;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码缺失！")
    private String code;
}
