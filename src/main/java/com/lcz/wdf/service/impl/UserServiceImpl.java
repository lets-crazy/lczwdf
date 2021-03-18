package com.lcz.wdf.service.impl;

import com.lcz.wdf.dao.PermissionMapper;
import com.lcz.wdf.dao.RoleMapper;
import com.lcz.wdf.dao.UsersMapper;
import com.lcz.wdf.entity.Permission;
import com.lcz.wdf.entity.Role;
import com.lcz.wdf.entity.Users;
import com.lcz.wdf.entity.exception.BizException;
import com.lcz.wdf.entity.request.AddUserRequest;
import com.lcz.wdf.service.UserService;
import com.lcz.wdf.utils.PasswordHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 用户业务层实现
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/5 16:13
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource private UsersMapper usersMapper;

    @Resource private RoleMapper roleMapper;

    @Resource private PermissionMapper permissionMapper;

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

    @Override
    public String addUser(AddUserRequest request) throws BizException {
        Users users = new Users();
        users.setAccount(request.getAccount());
        users.setName(request.getName());
        users.setIsLocked(0);
        users.setPassword(request.getPassword());
        PasswordHelper.encryptPassword(users);
        int insert = usersMapper.insert(users);
        if (insert == 1) {
            return String.format("用户注册成功！账号：%s，用户名：%s", users.getAccount(), users.getName());
        } else {
            throw new BizException("用户注册失败！");
        }
    }
}
