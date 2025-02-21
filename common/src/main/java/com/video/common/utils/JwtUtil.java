package com.video.common.utils;

import com.video.common.exception.VideoException;
import com.video.common.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
public class JwtUtil {
    private final static long tokenExpiration = 60 * 60 * 1000 * 6L;
    private static SecretKey tokenSignKey = Keys.hmacShaKeyFor("M0PKKI6pYGVWWfDZw90a0lTpGYX1d4AQ".getBytes());

    public static String createToken(Long userId, String username) {
        String token = Jwts.builder().
                setSubject("USER_INFO").
                setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)).
                claim("userId", userId).
                claim("username", username).
                signWith(tokenSignKey).
                compact();
        return token;
    }

    public static Claims parseToken(String token) {

        if (token == null) {
            log.info("抛出自定义异常：未登录！");
            throw new VideoException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
        }

        try {
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(tokenSignKey).build();
            Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
            return claimsJws.getBody();
        } catch (ExpiredJwtException e) {
            throw new VideoException(ResultCodeEnum.TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw new VideoException(ResultCodeEnum.TOKEN_INVALID);
        }
    }

    public static void main(String[] args) {
        System.out.println(createToken(1L, "138888888"));
    }
}
