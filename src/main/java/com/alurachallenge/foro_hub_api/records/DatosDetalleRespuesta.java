package com.alurachallenge.foro_hub_api.records;

import com.alurachallenge.foro_hub_api.modelos.Respuesta;

import java.time.LocalDateTime;

public record DatosDetalleRespuesta(
        Long id,
        String mensaje,
        Long topicoId,
        String nombreTopico,
        LocalDateTime fechaCreacion,
        Long autorId,
        String nombreAutor,
        Boolean solucion
) {
    public DatosDetalleRespuesta(Respuesta respuesta) {
        this(respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getTopico().getId(),
                respuesta.getTopico().getTitulo(),
                respuesta.getFechaCreacion(),
                respuesta.getAutor().getId(),
                respuesta.getAutor().getNombre(),
                respuesta.getSolucion());
    }
}