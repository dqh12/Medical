package com.henu.common.util;

import io.jsonwebtoken.*;

import java.util.Date;
import org.springframework.util.StringUtils;

public class JwtHelper {
    //过期时间
    private static long tokenExpiration = 24*60*60*1000;
    //签名秘钥
    private static String tokenSignKey = "123456";

    //根据参数生成token,登录成功生成
    public static String createToken(Long userId, String userName) {
        String token = Jwts.builder()
                .setSubject("Medical-USER")//主题，放用户的详细信息
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))//token过期时间
                .claim("userId", userId)//用户ID
                .claim("userName", userName)//用户name
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)//加密方式和加密密码
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }
    //JWT解析

    //根据token字符串得到用户id
    public static Long getUserId(String token) {
        if(StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer)claims.get("userId");
        return userId.longValue();
    }

    //根据token字符串得到用户名称
    public static String getUserName(String token) {
        if(StringUtils.isEmpty(token)) return "";

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("userName");
    }

    public static void main(String[] args) {
        String token = JwtHelper.createToken(1L, "lucy");
        System.out.println(token);
        System.out.println(JwtHelper.getUserId(token));
        System.out.println(JwtHelper.getUserName(token));
    }
}
