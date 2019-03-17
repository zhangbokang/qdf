package com.mycharx.qdf.shiro;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mycharx.qdf.common.Constant;
import com.mycharx.qdf.common.StringUtil;
import com.mycharx.qdf.entity.QdfPermission;
import com.mycharx.qdf.entity.QdfRole;
import com.mycharx.qdf.entity.QdfUser;
import com.mycharx.qdf.service.QdfUserService;
import com.mycharx.qdf.shiro.jwt.JwtToken;
import com.mycharx.qdf.utils.JedisUtil;
import com.mycharx.qdf.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * The type My realm.
 *
 * @author 张卜亢
 * @date 2019.03.14 13:38:17
 */
@Slf4j
@Component
public class MyRealm extends AuthorizingRealm {

    @Resource
    private QdfUserService qdfUserService;

    @Value("${myshiro.shiro.jwt-cache-key}")
    private String jwtCacheKeyPrefix;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
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
        log.info("权限配置-->MyRealm.doGetAuthorizationInfo()");

        String username = JwtUtil.getUsername(principals.toString());
        QdfUser user = qdfUserService.findByUsername(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //设置相应角色的权限信息
        for (QdfRole role : user.getRoles()) {
            //设置角色
            simpleAuthorizationInfo.addRole(role.getRole());
            for (QdfPermission p : role.getPermissions()) {
                //设置权限
                simpleAuthorizationInfo.addStringPermission(p.getPermission());
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getUsername(token);
        // 帐号为空
        if (StringUtil.isBlank(username)) {
            throw new AuthenticationException("Token中帐号为空(The account in Token is empty.)");
        }

        QdfUser qdfUser = qdfUserService.findByUsername(username);

        if (qdfUser == null) {
            throw new AuthenticationException("该帐号不存在(The account does not exist.)");
        }

//        if (!JwtUtil.verify(token)) {
//            throw new AuthenticationException("Username or password error");
//        }

        //这里将 密码对比 注销掉,否则 无法锁定  要将密码对比 交给 密码比较器
        if (qdfUser.getState() == 1) {
            throw new UnauthorizedException("账号已被禁用,请联系管理员！");
        }

        // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        if (JwtUtil.verify(token) && JedisUtil.exists(jwtCacheKeyPrefix + username)) {
            // 获取RefreshToken的时间戳
            String currentTimeMillisRedis = JedisUtil.getObject(jwtCacheKeyPrefix + username).toString();
            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
            if (JwtUtil.getClaim(token, Constant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
//                return new SimpleAuthenticationInfo(token, token, "userRealm");
                return new SimpleAuthenticationInfo(token, token, this.getName());
            }
        }
        throw new TokenExpiredException("Token已过期(Token expired or incorrect.)");
    }

}
