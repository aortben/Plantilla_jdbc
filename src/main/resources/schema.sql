DROP DATABASE IF EXISTS examenSB;
CREATE DATABASE examenSB;
USE examenSB;

CREATE TABLE IF NOT EXISTS curso (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS profesor (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS alumno (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    edad INT NOT NULL,
    email VARCHAR(150),
    curso_id BIGINT,
    FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE SET NULL
    -- Si borras un curso, los alumnos mantienen NULL en curso_id
);

CREATE TABLE IF NOT EXISTS curso_profesor (
    curso_id BIGINT NOT NULL,
    profesor_id BIGINT NOT NULL,
    PRIMARY KEY (curso_id, profesor_id),
    FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE CASCADE,
    FOREIGN KEY (profesor_id) REFERENCES profesor(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS alumno_curso (
    alumno_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    PRIMARY KEY (alumno_id, curso_id),
    FOREIGN KEY (alumno_id) REFERENCES alumno(id) ON DELETE CASCADE,
    FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ra (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    curso_id BIGINT NOT NULL,
    FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS aula (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    edificio VARCHAR(100),
    planta INT,
    curso_id BIGINT,
    UNIQUE KEY (curso_id),
    FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE SET NULL
);


