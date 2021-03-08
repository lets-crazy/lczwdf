package com.lcz.wdf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 用户 @TableName users */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    /**
     * id
     *
     * @mbg.generated 2021-03-05 16:41:24
     */
    private Integer id;

    /**
     * 账号。登陆用
     *
     * @mbg.generated 2021-03-05 16:41:24
     */
    private String account;

    /**
     * 密码
     *
     * @mbg.generated 2021-03-05 16:41:24
     */
    private String password;

    /**
     * 名称
     *
     * @mbg.generated 2021-03-05 16:41:24
     */
    private String name;
}
