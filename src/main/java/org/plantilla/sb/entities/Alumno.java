package org.plantilla.sb.entities;
import lombok.Data;

@Data
public class Alumno {

    private Long id;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String email;

    // En una relación 1:N, el N lleva la FK
    private Long cursoId;

    // Relación N:M con Curso
    private java.util.Set<Curso> cursos = new java.util.HashSet<>();

    // Campo temporal para Thymeleaf (solo almacena IDs)
    private java.util.Set<Long> cursoIds = new java.util.HashSet<>();
}

