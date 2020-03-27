package com.example.demo01.shiro;

import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义实现 ShiroRealm，包含认证和授权两大模块
 *
 * @author wxd
 */
@Component
@RequiredArgsConstructor
public class ShiroRealm extends AuthorizingRealm {


    private SimpleAuthorizationInfo simpleAuthorizationInfo;

    /**
     * 授权模块，获取用户角色和权限
     *
     * @param principal principal
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {

        // 获取用户角色集

        System.out.println("*****************************************");

        // 获取用户权限集
        List<String> pers = new ArrayList<>();
        //这些权限的添加，后期我们可以和数据库相连，调用dao层方法，获取mapper文件中sql语句查询的数据库信息放在此处。
        pers.add("item:select");
        pers.add("user:view");

        //创建向前台传递权限的SimpleAuthorizationInfo对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //调用addStringPermissions方法将装有权限的集合作为参数赋给SimpleAuthorizationInfo对象传给前台
        //有角色还可以在这加角色
        simpleAuthorizationInfo.addStringPermissions(pers);

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {


        return new SimpleAuthenticationInfo("admin", "123456", "admin");
    }

    /**
     * 清除当前用户权限缓存
     * 使用方法：在需要清除用户权限的地方注入 ShiroRealm,
     * 然后调用其 clearCache方法。
     */
    public void clearCache() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }
}
