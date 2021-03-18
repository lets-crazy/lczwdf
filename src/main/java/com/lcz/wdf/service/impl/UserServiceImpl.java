package com.lcz.wdf.service.impl;

import com.lcz.wdf.dao.PermissionMapper;
import com.lcz.wdf.dao.RoleMapper;
import com.lcz.wdf.dao.UsersMapper;
import com.lcz.wdf.entity.Permission;
import com.lcz.wdf.entity.Role;
import com.lcz.wdf.entity.Users;
import com.lcz.wdf.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

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

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public String getIndex() {
        return usersMapper.selectByPrimaryKey(1).toString();
    }

    @Override
    public Set<Role> findRoles(String account) {
        return roleMapper.selectByAccount(account);
    }

    @Override
    public Set<Permission> findPermissions(String account) {
        return permissionMapper.selectByAccount(account);
    }

    @Override
    public Users findByAccount(String account) {
        return usersMapper.selectByAccount(account);
    }
}
