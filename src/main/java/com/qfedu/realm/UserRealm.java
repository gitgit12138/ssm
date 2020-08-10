package com.qfedu.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jayus
 * @create 2020-07-23-11:27 下午
 */
/*
shiro认证流程
1.构造SecurityManager流程
2.subject.login提交认证
3.SecurityManager.login执行认证
4.Authenticator执行认证
5.从realm中获取认证信息
*/
public class UserRealm extends AuthorizingRealm {

    /**
     * 设置realm的名称
     * @param name
     */
//    @Override
//    public void setName(String name) {
//        super.setName("userRealm");
//    }

    /**
     * 用于授权
     * @param principalCollection
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取身份信息
        String username = (String) principalCollection.getPrimaryPrincipal();
        //根据身份信息从数据库中查询权限数据
        //这里使用静态模拟，实际需要开发者自己执行sql查询判断

        //封装权限信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        //权限
        Set<String> s = new HashSet<String>();
        s.add("user:create");
        s.add("user:update");
        simpleAuthorizationInfo.setStringPermissions(s);

        //角色
        Set<String> r = new HashSet<String>();
        r.add("role1");
        simpleAuthorizationInfo.setRoles(r);
        return simpleAuthorizationInfo;
    }

    /**
     * 用于认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        /*
        token是用户输入的
        第一步从token中取出身份信息
         */
        String userCode = (String) authenticationToken.getPrincipal();
        System.out.println(userCode);
//      String userPassword = new String((char[]) authenticationToken.getCredentials());
//      System.out.println(userPassword);

        /*
        第二步根据用户输入的userCode从数据库中进行查询
        如果查询不到返回null
        如果查询到返回认证信息
         */
        //模拟从数据库中查询到密码
        Md5Hash md5Hash = new Md5Hash("123","yan",2);
        String password = md5Hash.toString();
        System.out.println(password);
        //模拟从数据库中查询到的盐
        String salt = "yan";
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userCode,password, ByteSource.Util.bytes(salt),this.getName());

        return simpleAuthenticationInfo;


    }
}
