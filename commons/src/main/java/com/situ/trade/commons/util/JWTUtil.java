package com.situ.trade.commons.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT的工具类
 */
public class JWTUtil {

    //有效期为
    public static final Long JWT_TTL = 60 * 60 *1000L;// 60 * 60 *1000  一个小时

    //设置 密钥
    public static final String JWT_KEY = "bobzyh";

    public static String getUUID(){
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    /**
     * 用户信息创建JWT,默认有效期30分钟
     * @param user
     * @return
     */
    public static String createJWT(UserDetails user) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(user);
            System.out.println("用户的JSON");
            System.out.println(json);
            return createJWT(json,JWT_TTL);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 用户信息创建JWT, 指定有效期，单位秒
     * @param user
     * @param ttlMillis
     * @return
     */
    public static String createJWT(UserDetails user, Long ttlMillis) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(user);
            return createJWT(json, ttlMillis);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成jtw
     * @param subject token中要存放的数据（json格式）
     * @return
     */
    public static String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, 30*60*1000L, getUUID());// 设置过期时间
        return builder.compact();
    }

    /**
     * 生成jtw
     * @param subject token中要存放的数据（json格式）
     * @param ttlMillis token超时时间
     * @return
     */
    public static String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());// 设置过期时间
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if(ttlMillis==null){
            ttlMillis= JWTUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)              //唯一的ID
                .setSubject(subject)   // 主题  可以是JSON数据
                .setIssuer("bobzyh")     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, secretKey) //使用HS256对称加密算法签名, 第二个参数为秘钥
                .setExpiration(expDate);
    }

    /**
     * 创建token
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);// 设置过期时间
        return builder.compact();
    }

    /**
     * 生成加密后的秘钥 secretKey
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JWTUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解析
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    /**
     * 解析为用户
     * @param jwt
     * @return
     * @throws Exception
     */
    public static UserDetails getUserDetails(String jwt, Class<? extends UserDetails> cls) throws Exception {
        Claims claims = JWTUtil.parseJWT(jwt);
        String json = claims.getSubject();
        ObjectMapper mapper = new ObjectMapper();
        UserDetails user = null;
        user = mapper.readValue(json, cls);

        return user;
    }

    public static UserDetails getUserDetailsList(String jwt, Class<? extends UserDetails> ...cls) throws Exception {
        Claims claims = JWTUtil.parseJWT(jwt);
        String json = claims.getSubject();
        ObjectMapper mapper = new ObjectMapper();
        if (cls != null && cls.length > 0) {
            for (Class<? extends UserDetails> cl : cls) {
                try {
                    UserDetails user = mapper.readValue(json, cl);
                    return user;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


//    public static void main(String[] args) throws Exception {
//
//        // 加密
//        String jwt = createJWT("2123");
//        System.out.println(jwt);
//
//        // 解密
//        Claims claims = parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2Y2QzZGU0NWRiN2I0MjVlOWJlZTAzYzUyNjY2ODhhYiIsInN1YiI6IjIxMjMiLCJpc3MiOiJzZyIsImlhdCI6MTcxMDgzMTc4NCwiZXhwIjoxNzEwODM1Mzg0fQ.k6RPyIvKX-mrS26YbyaDNVLlihGqTQDeLj2gsrNokCk");
//        String subject = claims.getSubject();
//        System.out.println(subject);
//
//    }

}
