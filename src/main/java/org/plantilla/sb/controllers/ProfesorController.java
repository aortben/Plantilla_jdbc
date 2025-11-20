package org.plantilla.sb.controllers;

import org.plantilla.sb.dao.ProfesorDAO;
import org.plantilla.sb.dao.CursoDAO;
import org.plantilla.sb.entities.Profesor;
import org.plantilla.sb.entities.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/profesores")
public class ProfesorController {

    @Autowired
    private ProfesorDAO profesorDAO;

    @Autowired
    private CursoDAO cursoDAO;

    // Listar todos los profesores
    @GetMapping
    public String listProfesores(Model model) throws SQLException {
        List<Profesor> profesores = profesorDAO.listAllProfesores();
        model.addAttribute("profesores", profesores);
        return "profesores"; // nombre de la plantilla Thymeleaf
    }

    // Formulario de nuevo profesor
    @GetMapping("/new")
    public String newProfesorForm(Model model) throws SQLException {
        Profesor profesor = new Profesor();
        List<Curso> todosCursos = cursoDAO.listAllCursos();
        model.addAttribute("profesor", profesor);
        model.addAttribute("todosCursos", todosCursos);
        return "profesores-form"; // nombre de la plantilla Thymeleaf
    }

    // Formulario de edición
    @GetMapping("/edit/{id}")
    public String editProfesorForm(@PathVariable Long id, Model model) throws SQLException {
        Profesor profesor = profesorDAO.getProfesorById(id);
        List<Curso> cursoDelProfesor = profesorDAO.getCursosByProfesorId(id);
        for (Curso curso : cursoDelProfesor) {
            profesor.getCursoIds().add(curso.getId());
        }
        List<Curso> todosCursos = cursoDAO.listAllCursos();
        model.addAttribute("profesor", profesor);
        model.addAttribute("todosCursos", todosCursos);
        return "profesores-form"; // mismo template
    }

    // Guardar nuevo profesor o actualizar existente
    @PostMapping
    public String saveProfesor(@ModelAttribute Profesor profesor) throws SQLException {
        if (profesor.getId() == null) {
            profesorDAO.insertProfesor(profesor);
        } else {
            profesorDAO.updateProfesor(profesor);
            List<Curso> cursosActuales = profesorDAO.getCursosByProfesorId(profesor.getId());
            for (Curso curso : cursosActuales) {
                if (!profesor.getCursoIds().contains(curso.getId())) {
                    profesorDAO.removeCursoFromProfesor(profesor.getId(), curso.getId());
                }
            }
        }
        for (Long cursoId : profesor.getCursoIds()) {
            List<Curso> cursosActuales = profesorDAO.getCursosByProfesorId(profesor.getId());
            boolean existe = false;
            for (Curso curso : cursosActuales) {
                if (curso.getId().equals(cursoId)) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                profesorDAO.addCursoToProfesor(profesor.getId(), cursoId);
            }
        }
        return "redirect:/profesores";
    }

    // Borrar profesor
    @GetMapping("/delete/{id}")
    public String deleteProfesor(@PathVariable Long id) throws SQLException {
        profesorDAO.deleteProfesor(id);
        return "redirect:/profesores";
    }
}


