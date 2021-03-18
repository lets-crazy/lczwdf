package com.lcz.wdf.service;

import com.lcz.wdf.entity.Permission;
import com.lcz.wdf.entity.Role;
import com.lcz.wdf.entity.Users;
import com.lcz.wdf.entity.exception.BizException;
import com.lcz.wdf.entity.request.AddUserRequest;

import java.util.Set;

public interface UserService {
    String getIndex();

    Set<Role> findRoles(String account);

    Set<Permission> findPermissions(String account);

    Users findByAccount(String account);

    String addUser(AddUserRequest request) throws BizException;
}
