package com.karmanno.kalahgame.config.security;

import com.karmanno.kalahgame.web.dto.LoginResponse;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public LoginResponse generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return LoginResponse.builder()
                .token(Jwts.builder()
                        .setSubject(Integer.toString(userPrincipal.getId()))
                        .setIssuedAt(new Date())
                        .setExpiration(expiryDate)
                        .signWith(SignatureAlgorithm.HS512, jwtSecret)
                        .compact())
                .expirationData(expiryDate)
                .build();
    }

    Integer getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Integer.parseInt(claims.getSubject());
    }

    boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Wrong signature of JWT");
        } catch (MalformedJwtException ex) {
            log.error("Malformed JWT");
        } catch (ExpiredJwtException ex) {
            log.error("JWT token has expired");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT body is empty");
        }

        return false;
    }
}
