package com.one.alura.foro.domain.dto.usuario;

import com.one.alura.foro.domain.modelo.Usuario;

public record DatosRespuestaUsuario(long id, String nombre, String email, String contrasenia) {
    public DatosRespuestaUsuario(Usuario usuario) {
        this(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getEmail(),
            usuario.getContrasenia()
        );
    }
}
