package com.one.alura.foro.controller;

import com.one.alura.foro.domain.dto.usuario.DatosActualizarUsuario;
import com.one.alura.foro.domain.dto.usuario.DatosListarUsuarios;
import com.one.alura.foro.domain.dto.usuario.DatosRegistroUsuario;
import com.one.alura.foro.domain.dto.usuario.DatosRespuestaUsuario;
import com.one.alura.foro.domain.modelo.Usuario;
import com.one.alura.foro.domain.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping ("/usuarios")
@Tag(name = "Usuarios")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario, UriComponentsBuilder uriComponentsBuilder) {
        Usuario usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario));
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosRespuestaUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity<List<DatosListarUsuarios>> listarUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll().stream().map(DatosListarUsuarios::new).toList());
    }

    @GetMapping ("/{id}")
    public ResponseEntity<DatosRespuestaUsuario> listarUsuarioPorId(@PathVariable("id") long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRespuestaUsuario(usuario));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> modificarUsuario(@RequestBody DatosActualizarUsuario datosActualizarUsuario, @AuthenticationPrincipal Usuario usuarioAutenticado) {
        usuarioAutenticado.actualizar(datosActualizarUsuario);
        usuarioRepository.save(usuarioAutenticado);
        return ResponseEntity.ok(new DatosRespuestaUsuario(usuarioAutenticado));
    }

    @DeleteMapping ()
    public ResponseEntity eliminarUsuario(@AuthenticationPrincipal Usuario usuarioAutenticado) {
        usuarioRepository.delete(usuarioAutenticado);
        return ResponseEntity.noContent().build();
    }
}
