package com.one.alura.foro.domain.dto.curso;

import com.one.alura.foro.domain.modelo.Curso;

public record DatosListarCursos(long id_curso, String nombre, String categoria) {
    public DatosListarCursos(Curso curso) {
        this(
            curso.getId(),
            curso.getNombre(),
            curso.getCategoria()
        );
    }
}
