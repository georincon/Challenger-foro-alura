package com.one.alura.foro.controller;

import com.one.alura.foro.domain.respuesta.*;
import com.one.alura.foro.domain.modelo.Respuesta;
import com.one.alura.foro.domain.modelo.Topico;
import com.one.alura.foro.domain.modelo.Usuario;
import com.one.alura.foro.domain.repository.RespuestaRepository;
import com.one.alura.foro.domain.repository.TopicoRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/respuestas")
@Tag(name = "Respuestas")
public class RespuestaController {

    @Autowired
    RespuestaRepository respuestaRepository;
    @Autowired
    TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaRespuesta> registrarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta, UriComponentsBuilder uriComponentsBuilder, @AuthenticationPrincipal Usuario usuarioAutenticado) {
        Optional<Topico> opcionalTopico = topicoRepository.findById(datosRegistroRespuesta.id_topico());
        if (opcionalTopico.isPresent()) {
            Topico topico = opcionalTopico.get();
            Respuesta respuesta = respuestaRepository.save(new Respuesta(datosRegistroRespuesta, usuarioAutenticado, topico));
            URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
            return ResponseEntity.created(url).body(new DatosRespuestaRespuesta(respuesta));
        } else {
            throw new IllegalArgumentException("El Topico con ID " + datosRegistroRespuesta.id_topico() + " no existe");
        }
    }

    @GetMapping
    public ResponseEntity<List<DatosListarRespuestas>> listarRespuestas() {
        return ResponseEntity.ok(respuestaRepository.findAll().stream().map(DatosListarRespuestas::new).toList());
    }

    @GetMapping ("/{id}")
    public ResponseEntity<DatosRespuestaRespuesta> listarRespuestaPorId(@PathVariable("id") long id) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRespuestaRespuesta(respuesta));
    }

    @GetMapping ("/topico/{idTopico}")
    public ResponseEntity<List<RespuestaDTO>> listarRespuestaPorTopico(@PathVariable("idTopico") Long id) {
        return ResponseEntity.ok(respuestaRepository.findByTopicoId(id).stream().map(RespuestaDTO::new).toList());
    }

    @PutMapping
    @Transactional
    public ResponseEntity<RespuestaDTO> actualizarRespuesta(@RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta, @AuthenticationPrincipal Usuario usuarioAutenticado) {
        Respuesta respuesta = respuestaRepository.getReferenceById(datosActualizarRespuesta.id());
        if (!respuesta.getUsuario().equals(usuarioAutenticado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        respuesta.acualizar(datosActualizarRespuesta);
        return ResponseEntity.ok(new RespuestaDTO(respuesta));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity eliminarRespuesta(@PathVariable("id") long id, @AuthenticationPrincipal Usuario usuarioAutenticado) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        if (!respuesta.getUsuario().equals(usuarioAutenticado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        respuestaRepository.delete(respuesta);
        return ResponseEntity.noContent().build();
    }

}
