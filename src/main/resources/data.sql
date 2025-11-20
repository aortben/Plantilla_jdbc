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
INSERT INTO alumno (id, nombre, apellido, edad, email, curso_id)
VALUES (1, 'Ana', 'López', 20, 'ana.lopez@example.com', 1);

INSERT INTO alumno (id, nombre, apellido, edad, email, curso_id)
VALUES (2, 'Carlos', 'Martínez', 22, 'carlos.martinez@example.com', 1);

INSERT INTO alumno (id, nombre, apellido, edad, email, curso_id)
VALUES (3, 'Lucía', 'García', 21, 'lucia.garcia@example.com', 2);

-- ------------------------
-- Resultados de Aprendizaje
-- ------------------------
INSERT INTO ra (id, nombre, descripcion, curso_id) VALUES (1, 'RA1: Conceptos básicos', 'Comprender los conceptos fundamentales de matemáticas', 1);
INSERT INTO ra (id, nombre, descripcion, curso_id) VALUES (2, 'RA2: Cálculo avanzado', 'Dominar técnicas de cálculo avanzado', 1);
INSERT INTO ra (id, nombre, descripcion, curso_id) VALUES (3, 'RA1: Mecánica clásica', 'Entender las leyes de Newton y movimiento', 2);
INSERT INTO ra (id, nombre, descripcion, curso_id) VALUES (4, 'RA2: Termodinámica', 'Conocer principios de termodinámica', 2);
INSERT INTO ra (id, nombre, descripcion, curso_id) VALUES (5, 'RA1: Período histórico', 'Analizar eventos históricos importantes', 3);

