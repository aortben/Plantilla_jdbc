package org.plantilla.sb.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Alumno {

    private Long id;
    private String nombre;
    private String apellido;
    private int edad;

    // En una relación 1:N, el N lleva la FK
    private Long cursoId;
}

