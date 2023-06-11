package com.one.alura.foro.domain.repository;

import com.one.alura.foro.domain.modelo.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
