package org.plantilla.sb.controllers;

import org.plantilla.sb.dao.AlumnoDAO;
import org.plantilla.sb.entities.Alumno;
import org.plantilla.sb.entities.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoDAO alumnoDAO;

    // Listar todos
    @GetMapping
    public List<Alumno> getAllAlumnos() throws SQLException {
        return alumnoDAO.listAllAlumnos();
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public Alumno getAlumno(@PathVariable Long id) throws SQLException {
        return alumnoDAO.getAlumnoById(id);
    }

    // Crear
    @PostMapping
    public String createAlumno(@RequestBody Alumno alumno) {
        try {
            alumnoDAO.insertAlumno(alumno);
            return "Alumno creado correctamente";
        } catch (SQLException e) {
            return "Error al crear alumno: " + e.getMessage();
        }
    }

    // Actualizar
    @PutMapping("/{id}")
    public String updateAlumno(@PathVariable Long id, @RequestBody Alumno alumno) {
        try {
            alumno.setId(id);
            alumnoDAO.updateAlumno(alumno);
            return "Alumno actualizado correctamente";
        } catch (SQLException e) {
            return "Error al actualizar alumno: " + e.getMessage();
        }
    }

    // Borrar
    @DeleteMapping("/{id}")
    public String deleteAlumno(@PathVariable Long id) {
        try {
            alumnoDAO.deleteAlumno(id);
            return "Alumno eliminado correctamente";
        } catch (SQLException e) {
            return "Error al eliminar alumno: " + e.getMessage();
        }
    }

    // Obtener cursos del alumno
    @GetMapping("/{id}/cursos")
    public List<Curso> getCursos(@PathVariable Long id) throws SQLException {
        return alumnoDAO.getCursosByAlumnoId(id);
    }

    // Añadir curso a alumno
    @PostMapping("/{id}/cursos/{cursoId}")
    public String addCurso(@PathVariable Long id, @PathVariable Long cursoId) {
        try {
            alumnoDAO.addCursoToAlumno(id, cursoId);
            return "Curso añadido al alumno";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Quitar curso de alumno
    @DeleteMapping("/{id}/cursos/{cursoId}")
    public String removeCurso(@PathVariable Long id, @PathVariable Long cursoId) {
        try {
            alumnoDAO.removeCursoFromAlumno(id, cursoId);
            return "Curso eliminado del alumno";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }
}
