package com.one.alura.foro.infra.errores;

import com.one.alura.foro.infra.exception.RecursoNoEncontradoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404(EntityNotFoundException e) {
        ErrorRespuesta errorResponse = new ErrorRespuesta(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e) {
        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorRespuesta> tratarErrorIntegridad(SQLIntegrityConstraintViolationException e) {
        String mensajeError = e.getMessage();
        ErrorRespuesta errorRespuesta = new ErrorRespuesta(mensajeError);
        return ResponseEntity.badRequest().body(errorRespuesta);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorRespuesta> tratarErrorNoEncontrado(RecursoNoEncontradoException e) {
        String mensajeError =  e.getMessage();
        ErrorRespuesta errorRespuesta = new ErrorRespuesta(mensajeError);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorRespuesta);
    }

    private record DatosErrorValidacion(String campo, String error) {
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}