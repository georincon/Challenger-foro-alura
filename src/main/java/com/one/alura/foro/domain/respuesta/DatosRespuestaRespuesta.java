package com.one.alura.foro.domain.respuesta;

import com.one.alura.foro.domain.modelo.Respuesta;
import com.one.alura.foro.utils.DateTimeUtils;

import java.time.LocalDateTime;

public record DatosRespuestaRespuesta(
        long id_respuesta, String usuario, String topico, String mensaje, LocalDateTime fechaCreacion, boolean solucion) {
    public DatosRespuestaRespuesta(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getUsuario().getNombre(),
                respuesta.getTopico().getTitulo(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getSolucion());
    }

    public String getFechaCreacion() {
        return DateTimeUtils.formatDateTime(fechaCreacion);
    }
}
