package com.one.alura.foro.domain.repository;

import com.one.alura.foro.domain.modelo.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    List<Respuesta> findByTopicoId(Long id);
}
