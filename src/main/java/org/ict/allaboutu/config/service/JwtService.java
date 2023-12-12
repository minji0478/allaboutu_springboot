package org.ict.allaboutu.config.service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.ict.allaboutu.member.domain.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }


    public String generateToken(Member member) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", member.getAdmin());
        extraClaims.put("userNum", member.getUserNum());
        return buildToken(extraClaims, member, jwtExpiration);

    }

    public String generateRefreshToken(Member member) {
        return generateRefreshToken(new HashMap<>(), member);
    }

    public String generateRefreshToken(Map<String, Object> extraClaims, Member member) {
        return buildToken(extraClaims, member, refreshExpiration);
    }

    public boolean isTokenValid(String token, Member member) {
        final String username = extractUsername(token);
        return (username.equals(member.getUserId())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        byte[] keyByte = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyByte);
    }

    private String buildToken(Map<String, Object> extraClaims, Member member, long expiration) {
        return Jwts.builder().setClaims(extraClaims).setSubject(member.getUserId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }
}
