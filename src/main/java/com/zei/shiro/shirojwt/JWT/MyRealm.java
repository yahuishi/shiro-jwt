package com.zei.shiro.shirojwt.JWT;

import com.zei.shiro.shirojwt.JWT.JWTToken;
import com.zei.shiro.shirojwt.JWT.JWTUtil;
import com.zei.shiro.shirojwt.entity.User;
import com.zei.shiro.shirojwt.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class MyRealm extends AuthorizingRealm{

    @Autowired
    UserService userService;

    @Override
    public boolean supports(AuthenticationToken token){
        return token instanceof JWTToken;
    }

    /**
     * 处理权限
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = JWTUtil.getUserName(principalCollection.toString());
        User user = userService.getUser(userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(user.getRole());
        Set<String> permission = new HashSet<>(Arrays.asList(user.getPermission().split(",")));
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /**
     * 处理登陆
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        String userName = JWTUtil.getUserName(token);
        if(userName == null){
            throw new AuthenticationException("token已过期");
        }

        User user = userService.getUser(userName);
        if(user == null){
            throw new AuthenticationException("用户不存在");
        }

        if(!JWTUtil.verify(token,userName,user.getPassword())){
            throw new AuthenticationException("密码错误");
        }

        return new SimpleAuthenticationInfo(token,token,"myRealm");
    }
}
