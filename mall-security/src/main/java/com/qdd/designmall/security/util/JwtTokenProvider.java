package com.qdd.designmall.security.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final String jwtSecret = "UAGdBmVFndHKxRYJCqvaH8FgJaHUu3zmpfVssWVfXAg=";
    private final int jwtExpirationMs = 86400000; // 1 day

    public String generateToken(String  username) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(stringToKey(jwtSecret))
                .compact();
    }

    public static String keyToString(SecretKey key) {
        // 将密钥编码为Base64字符串
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static SecretKey stringToKey(String keyString) {
        // 将Base64字符串解码为字节数组
        byte[] keyBytes = Base64.getDecoder().decode(keyString);
        // 使用字节数组创建密钥
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static void main(String[] args) {
        SecretKey key = Jwts.SIG.HS256.key().build();

        // 将密钥转换为字符串
        String keyString = keyToString(key);
        System.out.println("Key as string: " + keyString);

        // 将字符串还原为密钥
        SecretKey restoredKey = stringToKey(keyString);

        // 测试还原的密钥是否和原始密钥相同
        System.out.println("Original key: " + key);
        System.out.println("Restored key: " + restoredKey);
        System.out.println("Keys are equal: " + key.equals(restoredKey));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(stringToKey(jwtSecret))
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(stringToKey(jwtSecret))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}