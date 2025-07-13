package com.alurachallenge.foro_hub_api.infra.security;

import com.alurachallenge.foro_hub_api.modelos.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String apiSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("Foro HUB API")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error al generar token JWT", exception);
        }
    }

    public String getSubject(String token) {
        if (token == null) {
            System.out.println("TokenService: El token recibido es nulo."); // Añade esta línea
            return null;
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            DecodedJWT verifier = JWT.require(algorithm)
                    .withIssuer("Foro HUB API")
                    .build()
                    .verify(token);
            System.out.println("TokenService: Token verificado exitosamente. Subject: " + verifier.getSubject()); // Añade esta línea
            return verifier.getSubject();
        } catch (JWTVerificationException exception) {
            System.err.println("TokenService ERROR: Token JWT inválido o expirado. Mensaje: " + exception.getMessage()); // Asegúrate de que esta línea esté, es clave.
            return null;
        }
    }

    private Instant generarFechaExpiracion() {
        // Genera la fecha de expiración sumando el valor de jwtExpiration (en milisegundos)
        return LocalDateTime.now().plusSeconds(jwtExpiration / 1000).toInstant(ZoneOffset.of("-04:00"));
    }
}