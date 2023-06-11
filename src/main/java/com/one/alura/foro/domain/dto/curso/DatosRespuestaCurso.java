package com.one.alura.foro.domain.dto.curso;

import com.one.alura.foro.domain.modelo.Curso;

public record DatosRespuestaCurso(Long id, String nombre, String categoria) {
    public DatosRespuestaCurso(Curso curso) {
        this(
            curso.getId(),
            curso.getNombre(),
            curso.getCategoria()
        );
    }
}
