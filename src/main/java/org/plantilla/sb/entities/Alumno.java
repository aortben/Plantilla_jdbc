package org.plantilla.sb.entities;
import lombok.Data;

@Data
public class Alumno {

    private Long id;
    private String nombre;
    private String apellido;
    private int edad;
    private String email;

    // En una relación 1:N, el N lleva la FK
    private Long cursoId;
}

