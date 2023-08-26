package ua.com.alevel.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public final class JwtUtil {

    private JwtUtil(){throw new IllegalStateException("This is utility class");}

    private static final String SECRET_KEY = "j123jkgk234k2o345p35ktY45kvyp3v45mnmk324vok345pSFm34ok6vk34sLpqlm52kl3j46kv24o5kn6mv23m6vkn23m6vl2m456klk2m3m6vm23lk56vm";

    private static final long EXPIRATION_TIME = 86400000; // 24 hours in milliseconds


    public static String extractUsername(String token){
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return claims.getSubject();
    }

    public static String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
