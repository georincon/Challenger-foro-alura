package com.one.alura.foro.domain.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.one.alura.foro.domain.dto.usuario.DatosActualizarUsuario;
import com.one.alura.foro.domain.dto.usuario.DatosRegistroUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String contrasenia;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Topico> topicos;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private List<Respuesta> respuestas;

    public Usuario(DatosRegistroUsuario datosRegistroUsuario) {
        this.nombre = datosRegistroUsuario.nombre();
        this.email = datosRegistroUsuario.email();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.contrasenia = passwordEncoder.encode(datosRegistroUsuario.contrasenia());
    }

    public void actualizar(DatosActualizarUsuario datosActualizarUsuario) {
        if (datosActualizarUsuario.nombre()!=null) {
            this.nombre = datosActualizarUsuario.nombre();
        }
        if (datosActualizarUsuario.contrasenia()!=null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            this.contrasenia = passwordEncoder.encode(datosActualizarUsuario.contrasenia());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return contrasenia;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
