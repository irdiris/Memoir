package com.example.implmentation.Security;

import com.example.implmentation.Models.User.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class TokenGenerator {
    private final long expirationTime= 70000;
    private final UserRepository userRepository;

    private final String securityKey="614E645267556B58703273357638792F423F4528482B4D6251655368566D5971";

    public TokenGenerator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return buildToken( userDetails, expirationTime);
    }

public Claims  extractClaims(UserDetails userDetails){
    Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
    claims.put("role", userDetails.getAuthorities());
return  claims;
}


    private String buildToken(
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
               // .setClaims(extractClaims(userDetails))
                .setSubject(userDetails.getUsername())
                .claim("role",userDetails.getAuthorities().toString().replace("[","").replace("]",""))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(securityKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

