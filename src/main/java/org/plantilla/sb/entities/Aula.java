package org.plantilla.sb.entities;

public class Aula {
    private Long id;
    private String nombre;
    private String edificio;
    private Integer planta;
    private Long cursoId;

    public Aula() {}

    public Aula(String nombre, String edificio, Integer planta, Long cursoId) {
        this.nombre = nombre;
        this.edificio = edificio;
        this.planta = planta;
        this.cursoId = cursoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public Integer getPlanta() {
        return planta;
    }

    public void setPlanta(Integer planta) {
        this.planta = planta;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }
}
