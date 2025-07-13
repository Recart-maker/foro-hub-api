package com.alurachallenge.foro_hub_api.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
        @NotBlank // Valida que el título no sea nulo ni vacío
        String titulo,
        @NotBlank // Valida que el mensaje no sea nulo ni vacío
        String mensaje,
        @NotNull // Valida que el ID del autor no sea nulo
        Long idAutor, // Suponemos que el autor se referencia por su ID
        @NotBlank // Valida que el curso no sea nulo ni vacío
        String curso
) {}