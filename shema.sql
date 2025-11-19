CREATE DATABASE IF NOT EXISTS centro;
USE centro;

-- =======================
--   TABLA CURSO
-- =======================
CREATE TABLE curso (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL
);

-- =======================
--   TABLA PROFESOR
-- =======================
CREATE TABLE profesor (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL
);

-- =======================
--   TABLA ALUMNO
--   (1:N con curso)
-- =======================
CREATE TABLE alumno (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    edad INT NOT NULL,
    curso_id BIGINT,
    FOREIGN KEY (curso_id) REFERENCES curso(id)
);

-- ===========================
--   TABLA N:M CURSO-PROFESOR
-- ===========================
CREATE TABLE curso_profesor (
    curso_id BIGINT NOT NULL,
    profesor_id BIGINT NOT NULL,
    PRIMARY KEY (curso_id, profesor_id),

    FOREIGN KEY (curso_id) REFERENCES curso(id),
    FOREIGN KEY (profesor_id) REFERENCES profesor(id)
);
