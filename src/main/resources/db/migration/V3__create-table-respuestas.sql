CREATE TABLE respuestas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensaje VARCHAR(100) NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL,
    solucion TINYINT(1),
    topico_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (topico_id) REFERENCES topicos (id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
);