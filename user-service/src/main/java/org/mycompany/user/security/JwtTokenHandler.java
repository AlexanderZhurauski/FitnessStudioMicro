package org.mycompany.user.security;

import io.jsonwebtoken.*;
import org.mycompany.user.security.api.IExtendedUserDetails;
import org.mycompany.user.security.api.ITokenHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class JwtTokenHandler implements ITokenHandler {

    private JWTProperty jwtProperty;
    private static final String FULL_NAME = "fio";
    private static final String USER_ID = "uid";

    public JwtTokenHandler(JWTProperty jwtProperty) {
        this.jwtProperty = jwtProperty;
    }

    @Override
    public String generateAccessToken(IExtendedUserDetails user) {
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put(FULL_NAME, user.getFullName());
        userDetails.put(USER_ID, user.getUserID());

        return Jwts.builder()
                .setSubject(user.getUsername())
                .addClaims(userDetails)
                .setIssuer(this.jwtProperty.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))// 1 week
                .signWith(SignatureAlgorithm.HS512, this.jwtProperty.getSecret())
                .compact();
    }

    @Override
    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(this.jwtProperty.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    @Override
    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(this.jwtProperty.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    @Override
    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(this.jwtProperty.getSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            //logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            //logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            //logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            //logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            //logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }

    @Override
    public String getFullName(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(this.jwtProperty.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.get(FULL_NAME, String.class);
    }

    @Override
    public String getUserID(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(this.jwtProperty.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.get(USER_ID, String.class);
    }
}
