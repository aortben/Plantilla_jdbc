package org.plantilla.sb.controllers;

import org.plantilla.sb.dao.ProfesorDAO;
import org.plantilla.sb.entities.Profesor;
import org.plantilla.sb.entities.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {

    @Autowired
    private ProfesorDAO profesorDAO;

    @GetMapping
    public List<Profesor> getAllProfesores() throws SQLException {
        return profesorDAO.listAllProfesores();
    }

    @GetMapping("/{id}")
    public Profesor getProfesor(@PathVariable Long id) throws SQLException {
        return profesorDAO.getProfesorById(id);
    }

    @PostMapping
    public String createProfesor(@RequestBody Profesor profesor) {
        try {
            profesorDAO.insertProfesor(profesor);
            return "Profesor creado correctamente";
        } catch (SQLException e) {
            return "Error al crear profesor: " + e.getMessage();
        }
    }

    @PutMapping("/{id}")
    public String updateProfesor(@PathVariable Long id, @RequestBody Profesor profesor) {
        try {
            profesor.setId(id);
            profesorDAO.updateProfesor(profesor);
            return "Profesor actualizado correctamente";
        } catch (SQLException e) {
            return "Error al actualizar profesor: " + e.getMessage();
        }
    }

    @DeleteMapping("/{id}")
    public String deleteProfesor(@PathVariable Long id) {
        try {
            profesorDAO.deleteProfesor(id);
            return "Profesor eliminado correctamente";
        } catch (SQLException e) {
            return "Error al eliminar profesor: " + e.getMessage();
        }
    }

    @GetMapping("/{id}/cursos")
    public List<Curso> getCursos(@PathVariable Long id) throws SQLException {
        return profesorDAO.getCursosByProfesorId(id);
    }

    @PostMapping("/{id}/cursos/{cursoId}")
    public String addCurso(@PathVariable Long id, @PathVariable Long cursoId) {
        try {
            profesorDAO.addCursoToProfesor(id, cursoId);
            return "Curso añadido al profesor";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    @DeleteMapping("/{id}/cursos/{cursoId}")
    public String removeCurso(@PathVariable Long id, @PathVariable Long cursoId) {
        try {
            profesorDAO.removeCursoFromProfesor(id, cursoId);
            return "Curso eliminado del profesor";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }
}

