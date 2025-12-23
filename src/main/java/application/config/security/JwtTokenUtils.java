package application.config.security;

import application.user.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenUtils  implements Serializable {
    private final Long serializable=9999L;
    private final String secret="194DCB9BB43DDE5EA474B1FD4366C2134ADVAW7777A";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserEntity userEntity) {
        return Jwts.builder()
                .setSubject(userEntity.getUsername())
                .claim("authorities", userEntity.getUsername())
                .claim("id", userEntity.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000+30)) /// / 7days
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())

                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token) {
        return parseToken(token).getSubject();
    }

    public Date getExpiration(String token) {
        return parseToken(token).getExpiration();
    }


}
