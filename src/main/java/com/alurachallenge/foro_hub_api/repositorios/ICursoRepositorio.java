package com.alurachallenge.foro_hub_api.repositorios;

import com.alurachallenge.foro_hub_api.modelos.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // ¡Importa Optional!

@Repository
public interface ICursoRepositorio extends JpaRepository<Curso, Long> {
    // ESTO ES LO QUE NECESITAS AÑADIR:
    Optional<Curso> findByNombre(String nombre);
}