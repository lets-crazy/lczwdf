package com.lcz.wdf.controller;

import com.lcz.wdf.entity.exception.BizException;
import com.lcz.wdf.entity.request.AddUserRequest;
import com.lcz.wdf.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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

    @PostMapping("/register")
    public String addUser(@Valid @RequestBody AddUserRequest request) throws BizException {
        return userService.addUser(request);
    }
}
