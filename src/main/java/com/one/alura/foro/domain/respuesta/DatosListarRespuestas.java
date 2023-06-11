package com.one.alura.foro.domain.respuesta;

import com.one.alura.foro.domain.modelo.Respuesta;
import com.one.alura.foro.utils.DateTimeUtils;

import java.time.LocalDateTime;

public record DatosListarRespuestas(long id_respuesta, String topico, String usuario, String mensaje, LocalDateTime fechaCreacion, boolean solucion) {
    public DatosListarRespuestas(Respuesta respuesta) {
        this(respuesta.getId(), respuesta.getTopico().getTitulo(),  respuesta.getUsuario().getNombre(), respuesta.getMensaje(), respuesta.getFechaCreacion(), respuesta.getSolucion());
    }
    public String getFechaCreacion() {
        return DateTimeUtils.formatDateTime(fechaCreacion);
    }
}
