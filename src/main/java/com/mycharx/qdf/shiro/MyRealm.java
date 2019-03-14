package com.mycharx.qdf.shiro;

import com.mycharx.qdf.entity.QdfPermission;
import com.mycharx.qdf.entity.QdfRole;
import com.mycharx.qdf.entity.QdfUser;
import com.mycharx.qdf.service.QdfUserService;
import com.mycharx.qdf.utils.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * The type My realm.
 *
 * @author 张卜亢
 * @date 2019.03.14 13:38:17
 */
@Service
public class MyRealm extends AuthorizingRealm {

    private static final Logger _logger = LoggerFactory.getLogger(MyRealm.class);

    @Resource
    private QdfUserService qdfUserService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        /*
         * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行，
         * 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
         * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了，
         * 缓存过期之后会再次执行。
         */
        _logger.info("权限配置-->MyRealm.doGetAuthorizationInfo()");

        String username = JWTUtil.getUsername(principals.toString());
        QdfUser user = qdfUserService.findByUsername(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        simpleAuthorizationInfo.addRole(user.getRole());
        //设置相应角色的权限信息
        for (QdfRole role : user.getRoles()) {
            //设置角色
            simpleAuthorizationInfo.addRole(role.getRole());
            for (QdfPermission p : role.getPermissions()) {
                //设置权限
                simpleAuthorizationInfo.addStringPermission(p.getPermission());
            }
        }
//        Set<String> permission = new HashSet<>(Arrays.asList(user.getPermission().split(",")));
//        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;

//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        ManagerInfo managerInfo = (ManagerInfo) principals.getPrimaryPrincipal();
//
//
//
//        return authorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }

        QdfUser qdfUser = qdfUserService.findByUsername(username);
        if (qdfUser == null) {
            throw new AuthenticationException("User didn't existed!");
        }

        if (! JWTUtil.verify(token, username, qdfUser.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}
