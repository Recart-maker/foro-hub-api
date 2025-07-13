package com.alurachallenge.foro_hub_api.infra.security;

import com.alurachallenge.foro_hub_api.repositorios.IUsuarioRepositorio; // Asegúrate de la importación correcta
import com.alurachallenge.foro_hub_api.modelos.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    // ¡¡ESTAS SON LAS LÍNEAS QUE FALTABAN Y CAUSABAN EL ERROR!!
    @Autowired
    private TokenService tokenService;

    @Autowired
    private IUsuarioRepositorio usuarioRepository;
    // --------------------------------------------------------

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Añade estas líneas al principio del método
        String requestURI = request.getRequestURI();
        System.out.println("=== SecurityFilter: Procesando solicitud a " + requestURI + " ===");

        String authHeader = request.getHeader("Authorization");
        System.out.println("Auth Header recibido: " + authHeader); // Añade esta línea

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");
            System.out.println("Token extraído: " + token); // Añade esta línea

            String subject = tokenService.getSubject(token); // Extrae el email del token
            System.out.println("Subject (email) del token: " + subject); // Añade esta línea

            if (subject != null) {
                Usuario usuario = (Usuario) usuarioRepository.findByEmail(subject)
                        .orElse(null);

                System.out.println("Usuario encontrado en DB: " + (usuario != null ? usuario.getEmail() : "NULL (No encontrado)")); // Añade esta línea

                if (usuario != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            usuario, null, usuario.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("Autenticación establecida en SecurityContextHolder para: " + usuario.getEmail()); // Añade esta línea
                } else {
                    System.out.println("ERROR: No se pudo encontrar el usuario para el subject del token.");
                }
            } else {
                System.out.println("ERROR: Subject del token es nulo o inválido.");
            }
        } else {
            System.out.println("No hay token Bearer en el header de autorización o es una ruta pública.");
        }
        filterChain.doFilter(request, response);
        System.out.println("=== SecurityFilter: Fin del procesamiento de " + requestURI + " ===");
    }
}