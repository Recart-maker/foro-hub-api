package com.alurachallenge.foro_hub_api.records;

import com.alurachallenge.foro_hub_api.modelos.StatusTopico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DatosActualizacionTopico(
        String titulo, // Ya no es @NotBlank aquí, porque podría ser una actualización parcial
        String mensaje, // Ya no es @NotBlank aquí, porque podría ser una actualización parcial
        StatusTopico status // Puede ser nulo si no se actualiza
) {
        // Los records generan automáticamente los getters.
        // Si necesitas validación, puedes usar @NotBlank y @NotNull directamente en los campos si son obligatorios en la actualización.
        // Si permites que solo se actualicen ciertos campos, puedes hacerlos opcionales aquí (sin @NotBlank/@NotNull).
        // Sin embargo, el desafío menciona "las mismas reglas de negocio del registro de un tópico deben aplicarse también en su actualización",
        // lo que podría implicar que si se envía el campo, debe ser válido.
        // Para simplificar, los dejamos sin @NotBlank/NotNull aquí, y la lógica de "no nulo/vacío" se haría en el servicio.
}