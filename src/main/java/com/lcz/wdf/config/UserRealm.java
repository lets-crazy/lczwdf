package com.lcz.wdf.config;

import com.lcz.wdf.entity.Permission;
import com.lcz.wdf.entity.Role;
import com.lcz.wdf.entity.Users;
import com.lcz.wdf.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 用户realm
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/16 9:49
 */
public class UserRealm extends AuthorizingRealm {

    @Resource private UserService userService;

    /** 提供用户信息返回权限信息 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String account = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 根据用户名查询当前用户拥有的角色
        Set<Role> roles = userService.findRoles(account);
        for (Role role : roles) {
            // 将角色名称提供给info
            authorizationInfo.addRole(role.getName());
        }
        // 根据用户名查询当前用户权限
        Set<Permission> permissions = userService.findPermissions(account);
        for (Permission permission : permissions) {
            // 将权限名称提供给info
            authorizationInfo.addStringPermission(permission.getPermission());
        }
        return authorizationInfo;
    }

    /** 提供账户信息返回认证信息 */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        String account = (String) token.getPrincipal();
        Users user = userService.findByAccount(account);
        if (user == null) {
            // 用户名不存在抛出异常
            throw new UnknownAccountException();
        }
        if (user.getIsLocked() == 1) {
            // 用户被管理员锁定抛出异常
            throw new LockedAccountException();
        }
        return new SimpleAuthenticationInfo(
                user.getAccount(),
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName());
    }


}
