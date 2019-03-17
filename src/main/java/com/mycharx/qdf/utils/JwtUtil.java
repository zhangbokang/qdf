package com.mycharx.qdf.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mycharx.qdf.common.Base64ConvertUtil;
import com.mycharx.qdf.exception.QdfCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * The type Jwt util.
 *
 * @author 张卜亢
 * @date 2019.03.14 13:32:29
 */
@Slf4j
@Configuration
public class JwtUtil {

    /**
     * 过期时间改为从配置文件获取
     */
    private static String accessTokenExpireTime;

    /**
     * JWT认证加密私钥(Base64加密)
     */
    private static String encryptJWTKey;

    /**
     * Sets access token expire time.
     *
     * @param accessTokenExpireTime the access token expire time
     * @author 张卜亢
     * @date 2019.03.16 00:28:48
     */
    @Value("${myshiro.shiro.accessTokenExpireTime}")
    public void setAccessTokenExpireTime(String accessTokenExpireTime) {
        JwtUtil.accessTokenExpireTime = accessTokenExpireTime;
    }

    /**
     * Sets encrypt jwt key.
     *
     * @param encryptJWTKey the encrypt jwt key
     * @author 张卜亢
     * @date 2019.03.16 00:28:48
     */
    @Value("${myshiro.shiro.encryptJWTKey}")
    public void setEncryptJWTKey(String encryptJWTKey) {
        JwtUtil.encryptJWTKey = encryptJWTKey;
    }


    /**
     * 校验token是否正确
     *
     * @param token    密钥
     * @return 是否正确 boolean
     * @author 张卜亢
     * @date 2019.03.16 00:28:48
     */
    public static boolean verify(String token) {
        try {
            // 帐号加JWT私钥解密
            String secret = getClaim(token, "username") + Base64ConvertUtil.decode(encryptJWTKey);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
//                    .withClaim("username", username)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            if (e instanceof TokenExpiredException) {
                log.info("Token已过期，执行更新Token。");
                throw new TokenExpiredException("Token已过期，执行更新Token。");
            }
            log.error("JWTToken认证解密出现UnsupportedEncodingException异常:" + e.getMessage());
            throw new QdfCustomException("JWTToken认证解密出现UnsupportedEncodingException异常:" + e.getMessage());
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @param token the token
     * @return token中包含的用户名 username
     * @author 张卜亢
     * @date 2019.03.16 00:28:48
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            log.error("解密Token中的username信息出现JWTDecodeException异常:" + e.getMessage());
            throw new QdfCustomException("解密Token中的username信息出现JWTDecodeException异常:" + e.getMessage());
        }
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     *
     * @param token the token
     * @param claim the claim
     * @return the claim
     * @author 张卜亢
     * @date 2019.03.16 00:29:21
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            log.error("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
            throw new QdfCustomException("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
        }
    }

    /**
     * 生成签名
     *
     * @param username          用户名
     * @param currentTimeMillis the current time millis
     * @return 加密的token string
     * @author 张卜亢
     * @date 2019.03.16 00:28:35
     */
    public static String sign(String username, String currentTimeMillis) {
        try {
        // 帐号加JWT私钥加密
        String secret = username + Base64ConvertUtil.decode(encryptJWTKey);
        Date date = new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpireTime) * 1000);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                .withClaim("username", username)
                .withClaim("currentTimeMillis", currentTimeMillis)
                .withExpiresAt(date)
                .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            log.error("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
            throw new QdfCustomException("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
        }
    }
}
