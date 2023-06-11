package com.one.alura.foro.controller;

import com.one.alura.foro.domain.dto.curso.DatosActualizarCurso;
import com.one.alura.foro.domain.dto.curso.DatosListarCursos;
import com.one.alura.foro.domain.dto.curso.DatosRegistroCurso;
import com.one.alura.foro.domain.dto.curso.DatosRespuestaCurso;
import com.one.alura.foro.domain.modelo.Curso;
import com.one.alura.foro.domain.repository.CursoRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

import java.util.List;

@RestController
@RequestMapping("/cursos")
@Tag(name = "Cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaCurso> registrarCurso(@RequestBody @Valid DatosRegistroCurso datosRegistroCurso, UriComponentsBuilder uriComponentsBuilder) {
        Curso curso = cursoRepository.save(new Curso(datosRegistroCurso));
        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosRespuestaCurso(curso));
    }

    @GetMapping
    public ResponseEntity<List<DatosListarCursos>> listadoCursos() {
        return ResponseEntity.ok(cursoRepository.findAll().stream().map(DatosListarCursos::new).toList());
    }

    @GetMapping ("/{id}")
    public ResponseEntity<DatosRespuestaCurso> listarCursoPorId(@PathVariable("id") long id) {
        Curso curso = cursoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRespuestaCurso(curso));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaCurso> modificarCurso(@RequestBody @Valid DatosActualizarCurso datosActualizarCurso) {
        Curso curso = cursoRepository.getReferenceById(datosActualizarCurso.id());
        curso.actualizar(datosActualizarCurso);
        return ResponseEntity.ok(new DatosRespuestaCurso(curso));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity eliminarCurso(@PathVariable("id") long id) {
        Curso curso = cursoRepository.getReferenceById(id);
        cursoRepository.delete(curso);
        return ResponseEntity.noContent().build();
    }
}
