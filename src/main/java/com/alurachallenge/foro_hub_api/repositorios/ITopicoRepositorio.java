package com.alurachallenge.foro_hub_api.repositorios;

import com.alurachallenge.foro_hub_api.modelos.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITopicoRepositorio extends JpaRepository<Topico, Long> {
    // ESTO ES LO QUE NECESITAS AÑADIR:
    boolean existsByTituloAndMensaje(String titulo, String mensaje);
}