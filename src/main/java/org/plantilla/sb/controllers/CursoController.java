package org.plantilla.sb.controllers;

import org.plantilla.sb.dao.CursoDAO;
import org.plantilla.sb.dao.ProfesorDAO;
import org.plantilla.sb.entities.Curso;
import org.plantilla.sb.entities.Profesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoDAO cursoDAO;

    @Autowired
    private ProfesorDAO profesorDAO; // Para listar todos los profesores en el select

    // Listar todos los cursos
    @GetMapping
    public String listCursos(Model model) throws SQLException {
        model.addAttribute("cursos", cursoDAO.listAllCursos());
        return "cursos"; // nombre del HTML para listar cursos
    }

    // Mostrar formulario para crear nuevo curso
    @GetMapping("/new")
    public String newCursoForm(Model model) throws SQLException {
        model.addAttribute("curso", new Curso());
        model.addAttribute("todosProfesores", profesorDAO.listAllProfesores());
        return "cursos-form"; // nombre del HTML del formulario
    }

    // Mostrar formulario para editar curso
    @GetMapping("/edit/{id}")
    public String editCursoForm(@PathVariable Long id, Model model) throws SQLException {
        Curso curso = cursoDAO.getCursoById(id);
        model.addAttribute("curso", curso);
        model.addAttribute("todosProfesores", profesorDAO.listAllProfesores());
        return "cursos-form";
    }

    @PostMapping
    public String saveCurso(@ModelAttribute Curso curso) {
        try {
            // Llenar la lista de Profesores según los IDs seleccionados
            Set<Profesor> profesoresSeleccionados = new HashSet<>();
            for (Long id : curso.getProfesorIds()) {
                Profesor p = profesorDAO.getProfesorById(id);
                if (p != null) {
                    profesoresSeleccionados.add(p);
                }
            }
            curso.setProfesores(profesoresSeleccionados);

            if (curso.getId() == null) {
                cursoDAO.insertCurso(curso);
            } else {
                cursoDAO.updateCurso(curso);
            }
            return "redirect:/cursos";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }

    // Borrar curso
    @GetMapping("/delete/{id}")
    public String deleteCurso(@PathVariable Long id) {
        try {
            cursoDAO.deleteCurso(id);
            return "redirect:/cursos";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }


}
