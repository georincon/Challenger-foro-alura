package com.one.alura.foro.domain.modelo;

import com.one.alura.foro.domain.dto.curso.DatosActualizarCurso;
import com.one.alura.foro.domain.dto.curso.DatosRegistroCurso;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "cursos")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String categoria;
    @OneToMany(mappedBy = "curso", cascade = CascadeType.REMOVE)
    private List<Topico> topicos;

    public Curso(DatosRegistroCurso datosRegistroCurso) {
        this.nombre = datosRegistroCurso.nombre();
        this.categoria = datosRegistroCurso.categoria();
    }

    public void actualizar(DatosActualizarCurso datosActualizarCurso) {
        if (datosActualizarCurso.nombre()!=null && !datosActualizarCurso.nombre().equals("")) {
            this.nombre = datosActualizarCurso.nombre();
        }

        if (datosActualizarCurso.categoria()!=null && !datosActualizarCurso.categoria().equals("")) {
            this.categoria = datosActualizarCurso.categoria();
        }
    }
}
