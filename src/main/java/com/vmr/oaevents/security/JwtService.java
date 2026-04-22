package com.vmr.oaevents.security;

import com.vmr.oaevents.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    // private final JwtBuilder jwtBuilder;
    private final SecretKey secretKey;

    public String generateToken(Usuario usuario) {
        Instant now = Instant.now();
        Date expiryDate = Date.from(now.plus(7, ChronoUnit.DAYS));

        return Jwts.builder()
                // id del usuario
                // .subject(String.valueOf(usuario.getId()))
                .subject(String.valueOf(usuario.getEmail()))
                // La clave secreta para firmar el token y saber que es nuestro cuando lleguen las peticiones del frontend
                .signWith(secretKey, SignatureAlgorithm.HS256)
                // Fecha emisión del token
                .issuedAt(Date.from(now))
                // Fecha de expiración del token
                .expiration(expiryDate)
                // información personalizada: rol o roles, username, email, avatar...
                // .claim("role", user.getRole())
                .claim("email", usuario.getEmail())
                //.claim("avatar", user.getAvatarUrl())
                // Construye el token
                .compact();
    }

    public boolean isTokenValid(String token, Usuario usuario) {
        String email = extractEmail(token);
        return email.equals(usuario.getEmail()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return expiration.before(new Date());
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}
