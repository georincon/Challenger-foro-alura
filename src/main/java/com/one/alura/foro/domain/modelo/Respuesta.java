package com.one.alura.foro.domain.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.one.alura.foro.domain.respuesta.DatosActualizarRespuesta;
import com.one.alura.foro.domain.respuesta.DatosRegistroRespuesta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "respuestas")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private Boolean solucion = false;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

    public Respuesta(DatosRegistroRespuesta datosRegistroRespuesta, Usuario usuario, Topico topico) {
        this.mensaje = datosRegistroRespuesta.mensaje();
        this.usuario = usuario;
        this.topico = topico;
        this.topico.setEstatus(StatusTopico.NO_SOLUCIONADO);
    }

    public void acualizar(DatosActualizarRespuesta datosActualizarRespuesta) {
        if(datosActualizarRespuesta.mensaje()!=null && !datosActualizarRespuesta.mensaje().equals("")) {
            this.mensaje = datosActualizarRespuesta.mensaje();
        }
    }

}

