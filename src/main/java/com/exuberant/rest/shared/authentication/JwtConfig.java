package com.exuberant.rest.shared.authentication;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.exuberant.rest.util.Constants.SUB;

/**
 * Created by rakesh on 06-Nov-2017.
 */
@Component
public class JwtConfig {

    @Autowired
    private SecretService secretService;

    public String generateToken(String userId) {
        String token = Jwts.builder()
                .setIssuer(secretService.getIssuer())
                .setSubject(secretService.getSubject())
                .claim(SUB, userId)
                .setIssuedAt(Date.from(Instant.now()))
                //.setExpiration(Date.from(Instant.ofEpochSecond(4622470422L))) // Sat Jun 24 2116 15:33:42 GMT-0400 (EDT)
                .signWith(SignatureAlgorithm.HS256, secretService.getHS256SecretBytes())
                .compact();
        return token;
    }

    public Jws<Claims> decode(String jwt) throws UnsupportedEncodingException {
        Jws<Claims> jws = Jwts.parser()
                .requireIssuer(secretService.getIssuer())
                .requireSubject(secretService.getSubject())
                .setSigningKeyResolver(secretService.getSigningKeyResolver())
                .parseClaimsJws(jwt);
        return jws;
    }

    public String dynamicBuilderSpecific() throws UnsupportedEncodingException {
        Map<String, Object> claims = new HashMap<>();
        claims.put("First", "Second");
        JwtBuilder builder = Jwts.builder();

        claims.forEach((key, value) -> {
            switch (key) {
                case "iss":
                    ensureType(key, value, String.class);
                    builder.setIssuer((String) value);
                    break;
                case "sub":
                    ensureType(key, value, String.class);
                    builder.setSubject((String) value);
                    break;
                case "aud":
                    ensureType(key, value, String.class);
                    builder.setAudience((String) value);
                    break;
                case "exp":
                    ensureType(key, value, Long.class);
                    builder.setExpiration(Date.from(Instant.ofEpochSecond(Long.parseLong(value.toString()))));
                    break;
                case "nbf":
                    ensureType(key, value, Long.class);
                    builder.setNotBefore(Date.from(Instant.ofEpochSecond(Long.parseLong(value.toString()))));
                    break;
                case "iat":
                    ensureType(key, value, Long.class);
                    builder.setIssuedAt(Date.from(Instant.ofEpochSecond(Long.parseLong(value.toString()))));
                    break;
                case "jti":
                    ensureType(key, value, String.class);
                    builder.setId((String) value);
                    break;
                default:
                    builder.claim(key, value);
            }
        });

        builder.signWith(SignatureAlgorithm.HS256, secretService.getHS256SecretBytes());

        return builder.compact();
    }

    private void ensureType(String registeredClaim, Object value, Class expectedType) {
        boolean isCorrectType = expectedType.isInstance(value) || expectedType == Long.class && value instanceof Integer;

        if (!isCorrectType) {
            String msg = "Expected type: " + expectedType.getCanonicalName() + " for registered claim: '" + registeredClaim + "', but got value: " + value + " of type: " + value.getClass()
                    .getCanonicalName();
            throw new JwtException(msg);
        }
    }
}
