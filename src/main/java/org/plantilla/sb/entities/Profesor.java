package org.plantilla.sb.entities;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Profesor {

    private Long id;
    private String nombre;
    private String apellido;

    // Relación inversa N:M
    private Set<Curso> cursos = new HashSet<>();
}

