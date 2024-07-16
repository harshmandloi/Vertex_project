package org.example.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.entity.Entites;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    private static final String SECRET_KEY = "sdfsdfsdfsdddddddddddddddd-----harsh----fdsfseerwedfsdsdfsdf@2@#!";
    public static final long ACCESS_TOKEN_EXPIRY = 86400000; // 1 day in milliseconds
    public static final long REFRESH_TOKEN_EXPIRY = 2592000000L; // 30 days in milliseconds

    public static String generateAccessToken(Entites user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("large_data", "This is a large amount of data. ".repeat(10));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRY))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static String generateRefreshToken(Entites user) {
        return Jwts.builder()
                .setSubject(user.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRY))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
