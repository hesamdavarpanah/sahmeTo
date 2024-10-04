package com.backend.sahmeto.authentication.securities;


import com.backend.sahmeto.authentication.services.UserDetailImplement;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;

    private SecretKey getSigningKey() {
        byte[] keBytes = Decoders.BASE64.decode(jwtSecret);
        return new SecretKeySpec(keBytes, "HmacSHA256");
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailImplement userDetailImplement = (UserDetailImplement) authentication.getPrincipal();

        return Jwts.builder().subject(userDetailImplement.getUsername()).issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(getSigningKey()).compact();
    }

    public String getUserNameFromJwtToken(String authToken) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(authToken).getPayload().getSubject();
    }

    public boolean validationJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(authToken);
            return true;
        } catch (SignatureException signatureException) {
            LOGGER.error("Invalid JWT signature: {}", signatureException.getMessage());
        } catch (MalformedJwtException malformedJwtException) {
            LOGGER.error("Invalid JWT token: {}", malformedJwtException.getMessage());
        } catch (ExpiredJwtException expiredJwtException) {
            LOGGER.error("JWT token is expired: {}", expiredJwtException.getMessage());
        } catch (UnsupportedJwtException unsupportedJwtException) {
            LOGGER.error("JWT token is unsupported: {}", unsupportedJwtException.getMessage());
        } catch (IllegalArgumentException illegalArgumentException) {
            LOGGER.error("JWT claims string is empty: {}", illegalArgumentException.getMessage());
        }
        return false;
    }
}
