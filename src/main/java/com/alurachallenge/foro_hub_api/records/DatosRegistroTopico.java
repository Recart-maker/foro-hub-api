package com.alurachallenge.foro_hub_api.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotNull Long autorId, // <-- Asegúrate de que el nombre del componente es 'autorId' (tipo Long)
        @NotNull Long cursoId  // <-- Asegúrate de que el nombre del componente es 'cursoId' (tipo Long)
) {
    // ¡IMPORTANTE! No añadas ningún método aquí, los getters se generan automáticamente.
    // Por ejemplo, para 'autorId' se generará el método 'autorId()',
    // y para 'cursoId' se generará el método 'cursoId()'.
}