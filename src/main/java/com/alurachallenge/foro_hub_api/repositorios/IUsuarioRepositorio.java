package com.alurachallenge.foro_hub_api.repositorios;

import com.alurachallenge.foro_hub_api.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Optional; // ¡Importa Optional!

public interface IUsuarioRepositorio extends JpaRepository<Usuario, Long> {
    // Modifica la firma para que devuelva Optional<Usuario>
    Optional<Usuario> findByEmail(String email);
}