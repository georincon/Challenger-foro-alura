package com.one.alura.foro.domain.repository;

import com.one.alura.foro.domain.dto.topico.DatosListarTopicos;
import com.one.alura.foro.domain.modelo.Topico;
import com.one.alura.foro.domain.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    List<Topico> findByUsuario(Usuario usuarioAutenticado);
}
