package com.alurachallenge.foro_hub_api.records;

import com.alurachallenge.foro_hub_api.modelos.Respuesta;

import java.time.LocalDateTime;

public record DatosListadoRespuesta(
        Long id,
        String mensaje,
        Long topicoId,
        String nombreTopico,
        String nombreAutor,
        LocalDateTime fechaCreacion
) {
    public DatosListadoRespuesta(Respuesta respuesta) {
        this(respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getTopico().getId(),
                respuesta.getTopico().getTitulo(), // Asumo que Topico tiene getTitulo()
                respuesta.getAutor().getNombre(),   // Asumo que Usuario tiene getNombre()
                respuesta.getFechaCreacion());
    }
}