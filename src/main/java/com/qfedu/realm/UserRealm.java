package com.qfedu.realm;

import com.qfedu.pojo.Permission;
import com.qfedu.pojo.Role;
import com.qfedu.pojo.User;
import com.qfedu.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

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

    //因为需要获取处理过的数据库数据所以需要一个UserService对象
    @Autowired
    private UserService userService;

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
     * 查询当前用户的所有角色和权限，并且进行授权
     * @param principalCollection
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取身份信息
        User user = (User) principalCollection.getPrimaryPrincipal();
        //根据身份信息从数据库中查询权限数据

        User userRolePerm = userService.selectUserByUsername(user);
        //System.out.println(userRolePerm);

        //封装权限信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        //角色
        Set<String> rolenames = new HashSet<String>();
        for (Role role:userRolePerm.getRoles()) {
            rolenames.add(role.getRolename());
        }
        simpleAuthorizationInfo.setRoles(rolenames);

        //权限
        Set<String> pernames = new HashSet<String>();
        for (Permission permission:userRolePerm.getPerms()) {
            pernames.add(permission.getPername());
        }
        simpleAuthorizationInfo.setStringPermissions(pernames);

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
        //String userPassword = new String((char[]) authenticationToken.getCredentials());
        /*
        第二步根据用户输入的userCode从数据库中进行查询
        如果查询不到返回null
        如果查询到返回认证信息
         */
        User user = userService.selectByUsername(userCode);


        if (user == null){
            throw new UnknownAccountException();
        } else{
            //此时已经把user保存到shiro的session中
            return new SimpleAuthenticationInfo(user,user.getPassword(),ByteSource.Util.bytes(user.getSalt()),this.getName());
        }


    }

    //如果修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
    //清除缓存,在service中，权限修改后调用
    public void clearCached() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }
}
