package com.alurachallenge.foro_hub_api.repositorios;

import com.alurachallenge.foro_hub_api.modelos.Respuesta; // Asegúrate de que esta ruta sea correcta
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRespuestaRepositorio extends JpaRepository<Respuesta, Long> {
    // Puedes añadir métodos de búsqueda personalizados aquí si los necesitas, por ejemplo:
    // List<Respuesta> findByTopicoId(Long topicoId);
}