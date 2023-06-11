package com.one.alura.foro.domain.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacionUsuario(
        @NotBlank
        String email,
        @NotBlank
        String contrasenia) {
}
