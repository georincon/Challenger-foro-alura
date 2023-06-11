package com.one.alura.foro.domain.dto.curso;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarCurso(
        @NotNull
        Long id,
        String nombre,
        String categoria) {
}
