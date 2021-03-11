package com.lcz.wdf.service.impl;

import com.lcz.wdf.dao.UsersMapper;
import com.lcz.wdf.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户业务层实现
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/5 16:13
 **/
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UsersMapper usersMapper;
    @Override
    public String getIndex() {
        return usersMapper.selectByPrimaryKey(1).toString();
    }
}
