package com.mycharx.qdf.controller;

import com.mycharx.qdf.controller.common.ResponseBean;
import com.mycharx.qdf.entity.QdfUser;
import com.mycharx.qdf.service.QdfUserService;
import com.mycharx.qdf.utils.JedisUtil;
import com.mycharx.qdf.utils.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 一些比较基础比较公共的controller
 *
 * @author 张卜亢
 * @date 2019.03.14 23:05:15
 */
@RestController
public class QdfWebController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QdfWebController.class);

    @Resource
    private QdfUserService qdfUserService;

    /**
     * RefreshToken过期时间
     */
    @Value("${myshiro.shiro.refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @Value("${myshiro.shiro.jwt-cache-key}")
    private String jwtCacheKeyPrefix;

    @PostMapping("/login")
    public ResponseBean login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        QdfUser qdfUser = qdfUserService.checkLogin(username, password);
        // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        JedisUtil.setObject(jwtCacheKeyPrefix + qdfUser.getUsername(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        String token = JwtUtil.sign(qdfUser.getUsername(), currentTimeMillis);
//        httpServletResponse.setHeader("Authorization", token);
//        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
//        return new ResponseBean(HttpStatus.OK.value(), "登录成功(Login Success.)", null);
        return new ResponseBean(200, "Login success", token);
    }

    @GetMapping("/article")
    public ResponseBean article(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
//        subject.checkPermission(request.getRequestURI());
        subject.checkPermission(request.getServletPath());
        if (subject.isAuthenticated()) {
            return new ResponseBean(200, "You are already logged in", null);
        } else {
            return new ResponseBean(200, "You are guest", null);
        }
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public ResponseBean requireAuth() {
        return new ResponseBean(200, "You are authenticated", null);
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    @RequiresPermissions(logical = Logical.AND, value = {"view"})
    public ResponseBean requireRole() {
        return new ResponseBean(200, "You are visiting require_role", null);
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public ResponseBean requirePermission() {
        return new ResponseBean(200, "You are visiting permission require edit,view", null);
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseBean unauthorized() {
        return new ResponseBean(401, "Unauthorized", null);
    }
}
