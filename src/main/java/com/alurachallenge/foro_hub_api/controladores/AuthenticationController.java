package com.alurachallenge.foro_hub_api.controladores; // Ajusta el paquete

import com.alurachallenge.foro_hub_api.domain.DatosAutenticacion; // Ajusta la ruta de tu DTO
import com.alurachallenge.foro_hub_api.domain.DatosJWTToken;
import com.alurachallenge.foro_hub_api.infra.security.TokenService; // ¡Importa TokenService!
import com.alurachallenge.foro_hub_api.modelos.Usuario; // ¡Importa tu modelo Usuario!
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication; // Importa Authentication
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService; // Inyecta tu TokenService

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid DatosAutenticacion datos) {
        var authToken = new UsernamePasswordAuthenticationToken(datos.email(), datos.contrasena());
        // Intenta autenticar al usuario
        Authentication usuarioAutenticado = authenticationManager.authenticate(authToken);

        // Si la autenticación es exitosa, genera el token
        // Asegúrate de que usuarioAutenticado.getPrincipal() sea de tipo Usuario
        String jwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        // Devuelve el token en la respuesta
        return ResponseEntity.ok(new DatosJWTToken(jwtToken)); // Necesitas crear una clase DatosJWTToken o similar para la respuesta
    }
}