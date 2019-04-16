package com.zei.shiro.shirojwt.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JWTUtil {

    //过期时间5分钟
    private static final long TIMEOUT = 5*60*1000;

    /**
     * 校验token
     * @param token
     * @param userName
     * @param password
     * @return
     */
    public static boolean verify(String token, String userName, String password){

        try {
            Algorithm algorithm = Algorithm.HMAC256(password);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username",userName).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取token中的信息
     * @param token
     * @return
     */
    public static String getUserName(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            String username = jwt.getClaim("username").asString();

            return username;
        } catch (JWTDecodeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 每5分钟重新生成token
     * @param userName
     * @param password
     * @return
     */
    public static String sign(String userName, String password){
        try {
            Date date = new Date(System.currentTimeMillis()+TIMEOUT);
            Algorithm algorithm = Algorithm.HMAC256(password);
            return JWT.create().withClaim("username",userName).withExpiresAt(date).sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
