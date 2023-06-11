package com.one.alura.foro.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.one.alura.foro.domain.modelo.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("foro_alura")
                    .withSubject(usuario.getUsername())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion(1))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    public String getSubject(String token) {
        if (token==null) {
            throw new RuntimeException("El token es null");
        }
        DecodedJWT verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("foro_alura")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Error al verificar el token");
        }
        if (verifier.getSubject()==null) {
            throw new RuntimeException("Subject inválido");
        }
        return verifier.getSubject();
    }

    public Instant generarFechaExpiracion(long hours) {
        return LocalDateTime.now().plusHours(hours).toInstant(ZoneOffset.of("-03:00"));
    }
}
