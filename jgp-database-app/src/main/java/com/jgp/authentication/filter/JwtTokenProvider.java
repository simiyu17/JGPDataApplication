package com.jgp.authentication.filter;

import com.jgp.authentication.domain.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.Function;

@Component
@Slf4j
public class JwtTokenProvider {

    private JwtTokenProvider(){}

    public static  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        Key key = Keys.hmacShaKeyFor(SecurityConstants.SECRET.getBytes());
        return Jwts.parser().setSigningKey(key)
                .build().parseClaimsJws(token).getBody();
    }

    private static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public static boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public static String createToken(AppUser user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("user_id", user.getId())
                .claim("user_full_name", user.getUserFullName())
                .claim("user_email", user.getUsername())
                .claim("user_partner_name", Objects.isNull(user.getPartner()) ? "-" : user.getPartner().getPartnerName())
                .claim("user_partner_type", Objects.isNull(user.getPartner()) ? "-" : user.getPartner().getType())
                .claim("user_partner_id", Objects.isNull(user.getPartner()) ? "-" : user.getPartner().getId())
                .claim("user_position", user.getDesignation())
                .claim("user_registration", user.getDateCreated().format(DateTimeFormatter.ofPattern("MMM, yyyy")))
                .claim("roles", new HashSet<>(Collections.singletonList("USER")))
                .claim("force_change_password", user.isForceChangePass())
                .issuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getBytes())
                .compact();
    }

    private static String createTokenFromClaims(Claims claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getBytes()).compact();
    }
}
