package com.one.alura.foro.domain.dto.topico;

import com.one.alura.foro.domain.respuesta.RespuestaDTO;
import com.one.alura.foro.domain.modelo.StatusTopico;
import com.one.alura.foro.domain.modelo.Topico;
import com.one.alura.foro.utils.DateTimeUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record DatosListarTopicos(long id_topico, String usuario, String curso, String titulo, StatusTopico estatus, LocalDateTime fechaCreacion, List<RespuestaDTO> listaRespuestas) {
    public DatosListarTopicos(Topico topico) {

        this(
            topico.getId(),
            topico.getUsuario().getNombre(),
            topico.getCurso().getNombre(),
            topico.getTitulo(),
            topico.getEstatus(),
            topico.getFechaCreacion(),
            topico.getRespuestas()
                .stream()
                .map(RespuestaDTO::new)
                .collect(Collectors.toList())
        );
    }

    public String getFechaCreacion() {
        return DateTimeUtils.formatDateTime(fechaCreacion);
    }
}
