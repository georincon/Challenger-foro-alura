package com.one.alura.foro.domain.dto.topico;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotNull
        Long id,
        String titulo,
        String mensaje
) {
}
