package com.molistore.application.util.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtilImpl implements JwtUtil {

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access_token.expiration}")
    private Long accessTokenExpiration;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    /**
     * The claims tell you, at minimum:
     *
     * Who this person is and the URI to their user resource (the sub claim)
     * What this person can access with this token (the scope claim)
     * When the token expires. Your API should be using this when it verifies the token.
     * Because the token is signed with a secret key you can verify its signature and implicitly trust what is being claimed
     * @param token
     * @param claimsResolver
     * @param <T>
     * @return
     */
    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        final Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Sets the hashing algorithm
     * Gets the current date for the Issued At claim
     * Uses the SECRET_KEY static property to generate the signing key
     * Uses the fluent API to add the claims and sign the JWT
     * Sets the expiration date
     * @param claims  The claims tell you, at minimum:
     * <p> - Who this person is and the URI to their user resource (the sub claims)
     * <p> - What this person can access with this token (the scope claim)
     * <p> - When the token expires. Your API should be using this when it verifies the token.
     * <p>Because the token is signed with a secret key you can verify its signature and implicitly trust what is being claimed
     * <p>
     * @param subject userName from {@link UserDetails}
     * @return Builds the JWT and serializes it to a compact, URL-safe string
     */
    private String createToken(Map<String, Object> claims, String subject) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SIGNATURE_ALGORITHM.getJcaName());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(accessTokenExpiration))
                .signWith(SIGNATURE_ALGORITHM, signingKey)
                .compact();
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
}
