package com.one.alura.foro.domain.dto.curso;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroCurso(
        @NotBlank
        String nombre,
        @NotBlank
        String categoria ) {
}
