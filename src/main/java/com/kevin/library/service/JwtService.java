package com.kevin.library.service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {
	private final Key key;

    public JwtService() {
        String secret = "MyJwtAuthKey123456MyJwtAuthKey123456";
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }
    //存放已經登出的Jwt
    private final Set<String> blacklistedTokens = new HashSet<>();
    
    //把登出的Jwt放到Set
    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }
    
    //檢查Jwt有沒有在Set
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
    
    //驗證token
    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                                .setSigningKey(key)
                                .build()
                                .parseClaimsJws(token)
                                .getBody();
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //產生token
    public String generateToken(Integer userId) throws Exception{
    	 LocalDateTime dateTime = LocalDateTime.now().plusMinutes(10);
         Date expireTime = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

         return Jwts.builder()
                 .claim("userId", userId)
                 .setExpiration(expireTime)
                 .signWith(key)
                 .compact();

    }
    //從token取出user id
    public Integer getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        return claims.get("userId", Integer.class);
    }
}
