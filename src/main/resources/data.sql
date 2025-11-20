-- ------------------------
-- Profesores
-- ------------------------
INSERT INTO profesor (id, nombre, apellido) VALUES (1, 'Juan', 'Pérez');
INSERT INTO profesor (id, nombre, apellido) VALUES (2, 'María', 'Gómez');
INSERT INTO profesor (id, nombre, apellido) VALUES (3, 'Luis', 'Rodríguez');

-- ------------------------
-- Cursos
-- ------------------------
INSERT INTO curso (id, nombre) VALUES (1, 'Matemáticas');
INSERT INTO curso (id, nombre) VALUES (2, 'Física');
INSERT INTO curso (id, nombre) VALUES (3, 'Historia');

INSERT INTO curso_profesor (curso_id, profesor_id) VALUES (1, 1);
INSERT INTO curso_profesor (curso_id, profesor_id) VALUES (1, 2);
INSERT INTO curso_profesor (curso_id, profesor_id) VALUES (2, 2);
INSERT INTO curso_profesor (curso_id, profesor_id) VALUES (3, 3);

-- ------------------------
-- Alumnos
-- ------------------------
INSERT INTO alumno (id, nombre, apellido, edad, curso_id)
VALUES (1, 'Ana', 'López', 20, 1);

INSERT INTO alumno (id, nombre, apellido, edad, curso_id)
VALUES (2, 'Carlos', 'Martínez', 22, 1);

INSERT INTO alumno (id, nombre, apellido, edad, curso_id)
VALUES (3, 'Lucía', 'García', 21, 2);
