package org.plantilla.sb.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Curso {

    private Long id;
    private String nombre;

    // Relación N:M con Profesor (no es JPA, solo Java) Para no repetir un mismo valor
    private Set<Profesor> profesores = new HashSet<>();

    // Usar List permite añadir profesores repetidos
    //private List<Profesor> profesores = new ArrayList<>();
}

