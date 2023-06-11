package com.one.alura.foro.domain.dto.topico;

import com.one.alura.foro.domain.modelo.StatusTopico;
import com.one.alura.foro.domain.modelo.Topico;
import com.one.alura.foro.utils.DateTimeUtils;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(Long id_topico, String usuario, String titulo, String mensaje, LocalDateTime fechaCreacion, StatusTopico estatus, String curso) {
    public DatosRespuestaTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getUsuario().getNombre(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstatus(),
                topico.getCurso().getNombre()
        );
    }

    public String getFechaCreacion() {
        return DateTimeUtils.formatDateTime(fechaCreacion);
    }
}
