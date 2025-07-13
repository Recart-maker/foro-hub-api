package com.alurachallenge.foro_hub_api.records;

import jakarta.validation.constraints.NotNull;

public record DatosActualizacionRespuesta(
        String mensaje,
        Boolean solucion // Permitir actualizar si es solución
) {
}