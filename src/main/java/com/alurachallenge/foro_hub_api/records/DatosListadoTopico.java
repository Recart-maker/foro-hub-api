package com.alurachallenge.foro_hub_api.records;

import com.alurachallenge.foro_hub_api.modelos.StatusTopico; // <--- ¡IMPORTA TU ENUM StatusTopico!
import com.alurachallenge.foro_hub_api.modelos.Topico;
import jakarta.validation.constraints.NotBlank; // Puede que no sea necesario si solo es para listar

import java.time.LocalDateTime;

public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        StatusTopico status, // <--- ¡CAMBIADO A StatusTopico!
        String autorNombre,
        String cursoNombre
) {
    // Constructor que toma un objeto Topico y extrae la información relevante
    public DatosListadoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(), // <--- Ahora esto coincide con el tipo del componente "status"
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre()
        );
    }
}