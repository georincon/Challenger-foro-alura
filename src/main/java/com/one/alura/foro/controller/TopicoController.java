package com.one.alura.foro.controller;

import com.one.alura.foro.domain.dto.topico.*;
import com.one.alura.foro.domain.modelo.Curso;
import com.one.alura.foro.domain.modelo.Topico;
import com.one.alura.foro.domain.modelo.Usuario;
import com.one.alura.foro.domain.repository.CursoRepository;
import com.one.alura.foro.domain.repository.TopicoRepository;
import com.one.alura.foro.infra.exception.RecursoNoEncontradoException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/topicos")
@Tag(name = "Tópicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;


    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder, @AuthenticationPrincipal Usuario usuarioAutenticado) {
        Optional<Curso> optionalCurso = cursoRepository.findById(datosRegistroTopico.curso_id());
        if (optionalCurso.isPresent()) {
            Curso curso = optionalCurso.get();
            Topico topico = topicoRepository.save(new Topico(datosRegistroTopico, usuarioAutenticado, curso));
            URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
            return ResponseEntity.created(url).body(new DatosRespuestaTopico(topico));
        } else {
           throw new RecursoNoEncontradoException("El curso con ID " + datosRegistroTopico.curso_id() + " no existe");
        }
    }

    @GetMapping
    public ResponseEntity<Page<DatosListarTopicos>> listarTopicos(
        @PageableDefault(page = 0, size = 10, sort = "fechaCreacion", direction = Sort.Direction.DESC)
        Pageable paginacion) {
            return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(DatosListarTopicos::new));
    }

    @GetMapping ("/{id}")
    public ResponseEntity<TopicoDTO> listarTopicoPorId(@PathVariable("id") Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new TopicoDTO(topico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico, @AuthenticationPrincipal Usuario usuarioAutenticado) {
        Optional<Topico> optionalTopico = topicoRepository.findById(datosActualizarTopico.id());
        if (optionalTopico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Topico topico = optionalTopico.get();
        if (!topico.getUsuario().equals(usuarioAutenticado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        topico.actualizar(datosActualizarTopico);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity eliminarTopico(@PathVariable("id") long id, @AuthenticationPrincipal Usuario usuarioAutenticado) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Topico topico = optionalTopico.get();
        if (!topico.getUsuario().equals(usuarioAutenticado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        topicoRepository.delete(topico);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user")
    public ResponseEntity<List<DatosListarTopicos>> listarTopicoPorUsuario(@AuthenticationPrincipal Usuario usuarioAutenticado) {
        List<DatosListarTopicos> listarTopicosUsuario = topicoRepository.findByUsuario(usuarioAutenticado)
                .stream()
                .map(DatosListarTopicos::new)
                .toList();
        return ResponseEntity.ok(listarTopicosUsuario);

    }

}
