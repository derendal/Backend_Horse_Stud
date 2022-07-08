package com.example.myHorseServer.security;

import com.example.myHorseServer.model.Gamer;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private final String jwtSecret = "zdtlD3JK56m6wTTgsNFhqzjqP";
    private final String jwtIssuer = "example.io";

    public String generateAccessToken(Gamer user) {
        return Jwts.builder()
                .setSubject(String.format("%s,%s", user.getGamerId(), user.getEmail()))
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[0];
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public String getEmail(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[1];
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            System.out.println(String.format("Invalid JWT signature - %s", ex.getMessage()));
        } catch (MalformedJwtException ex) {
            System.out.println(String.format("Invalid JWT token - %s", ex.getMessage()));
        } catch (ExpiredJwtException ex) {
            System.out.println(String.format("Expired JWT token - %s", ex.getMessage()));
        } catch (UnsupportedJwtException ex) {
            System.out.println(String.format("Unsupported JWT token - %s", ex.getMessage()));
        } catch (IllegalArgumentException ex) {
            System.out.println(String.format("JWT claims string is empty - %s", ex.getMessage()));
        }
        return false;
    }

}
