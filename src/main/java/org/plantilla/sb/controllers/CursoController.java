package org.plantilla.sb.controllers;

import org.plantilla.sb.dao.CursoDAO;
import org.plantilla.sb.entities.Curso;
import org.plantilla.sb.entities.Profesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoDAO cursoDAO;

    @GetMapping
    public List<Curso> getAllCursos() throws SQLException {
        return cursoDAO.listAllCursos();
    }

    @GetMapping("/{id}")
    public Curso getCurso(@PathVariable Long id) throws SQLException {
        return cursoDAO.getCursoById(id);
    }

    @PostMapping
    public String createCurso(@RequestBody Curso curso) {
        try {
            cursoDAO.insertCurso(curso);
            return "Curso creado correctamente";
        } catch (SQLException e) {
            return "Error al crear curso: " + e.getMessage();
        }
    }

    @PutMapping("/{id}")
    public String updateCurso(@PathVariable Long id, @RequestBody Curso curso) {
        try {
            curso.setId(id);
            cursoDAO.updateCurso(curso);
            return "Curso actualizado correctamente";
        } catch (SQLException e) {
            return "Error al actualizar curso: " + e.getMessage();
        }
    }

    @DeleteMapping("/{id}")
    public String deleteCurso(@PathVariable Long id) {
        try {
            cursoDAO.deleteCurso(id);
            return "Curso eliminado correctamente";
        } catch (SQLException e) {
            return "Error al eliminar curso: " + e.getMessage();
        }
    }

    @GetMapping("/{id}/profesores")
    public List<Profesor> getProfesores(@PathVariable Long id) throws SQLException {
        return cursoDAO.getProfesoresByCursoId(id);
    }

    @PostMapping("/{id}/profesores/{profesorId}")
    public String addProfesor(@PathVariable Long id, @PathVariable Long profesorId) {
        try {
            cursoDAO.addProfesorToCurso(id, profesorId);
            return "Profesor añadido al curso";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    @DeleteMapping("/{id}/profesores/{profesorId}")
    public String removeProfesor(@PathVariable Long id, @PathVariable Long profesorId) {
        try {
            cursoDAO.removeProfesorFromCurso(id, profesorId);
            return "Profesor eliminado del curso";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }
}
