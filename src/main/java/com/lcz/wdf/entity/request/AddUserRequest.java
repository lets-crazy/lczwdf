package com.lcz.wdf.entity.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 用户添加
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/18 14:35
 */
@Setter
@Getter
public class AddUserRequest {

    @NotBlank(message = "账号缺失！")
    private String account;

    @NotBlank(message = "密码缺失！")
    private String password;

    @NotBlank(message = "姓名缺失！")
    private String name;
}
