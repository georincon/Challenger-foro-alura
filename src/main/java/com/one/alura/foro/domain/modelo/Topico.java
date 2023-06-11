package com.one.alura.foro.domain.modelo;

import com.one.alura.foro.domain.dto.topico.DatosActualizarTopico;
import com.one.alura.foro.domain.dto.topico.DatosRegistroTopico;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "topicos")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private StatusTopico estatus = StatusTopico.NO_RESPONDIDO;
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<Respuesta> respuestas;

    public Topico(DatosRegistroTopico datosRegistroTopico, Usuario usuario, Curso curso) {
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.usuario = usuario;
        this.curso = curso;
    }

    public void actualizar(DatosActualizarTopico datosActualizarTopico) {
        if (datosActualizarTopico.titulo()!=null && !datosActualizarTopico.titulo().equals("")) {
            this.titulo = datosActualizarTopico.titulo();
        }
        if (datosActualizarTopico.mensaje()!=null && !datosActualizarTopico.mensaje().equals("")) {
            this.mensaje = datosActualizarTopico.mensaje();
        }
    }
}
