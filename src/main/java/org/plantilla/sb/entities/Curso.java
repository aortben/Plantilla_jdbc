package org.plantilla.sb.entities;

import lombok.Data;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

@Data
public class Curso {

    private Long id;
    private String nombre;

    // Relación N:M con Profesor (no es JPA, solo Java) Para no repetir un mismo valor
    private Set<Profesor> profesores = new HashSet<>();

    // Campo temporal para Thymeleaf (solo almacena IDs)
    private Set<Long> profesorIds = new HashSet<>();

    // Relación 1:N con RA
    private List<Object> resultadosAprendizaje = new ArrayList<>();
}

