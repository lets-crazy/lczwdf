package com.lcz.wdf.controller;

import com.lcz.wdf.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户控制层
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/8 11:22
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource private UserService userService;

    @GetMapping("/index")
    public String sayHello(){
        return userService.getIndex();
    }
}
