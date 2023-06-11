CREATE TABLE topicos(
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL UNIQUE,
    mensaje VARCHAR(100) NOT NULL UNIQUE,
    fecha_creacion TIMESTAMP NOT NULL,
    estatus VARCHAR(100) NOT NULL,
    curso_id VARCHAR(100) NOT NULL,
    usuario_id BIGINT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
);