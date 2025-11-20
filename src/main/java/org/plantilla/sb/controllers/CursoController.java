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

    @GetMapping
    public String list(Model model) throws SQLException {
        model.addAttribute("items", cursoDAO.list());
        return "cursos";
    }

    @GetMapping("/new")
    public String newForm(Model model) throws SQLException {
        model.addAttribute("item", new Curso());
        model.addAttribute("relacionados", profesorDAO.list());
        return "cursos-form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) throws SQLException {
        Curso curso = cursoDAO.getById(id);
        List<Profesor> profesoresRelacionados = cursoDAO.getRelacionados(id);
        for (Profesor profesor : profesoresRelacionados) {
            curso.getProfesorIds().add(profesor.getId());
        }
        model.addAttribute("item", curso);
        model.addAttribute("relacionados", profesorDAO.list());
        return "cursos-form";
    }

    @PostMapping
    public String save(@ModelAttribute Curso curso) {
        try {
            Set<Profesor> profesoresSeleccionados = new HashSet<>();
            for (Long id : curso.getProfesorIds()) {
                Profesor p = profesorDAO.getById(id);
                if (p != null) {
                    profesoresSeleccionados.add(p);
                }
            }
            curso.setProfesores(profesoresSeleccionados);

            if (curso.getId() == null) {
                cursoDAO.insert(curso);
            } else {
                cursoDAO.update(curso);
                List<Profesor> profesoresActuales = cursoDAO.getRelacionados(curso.getId());
                for (Profesor profesor : profesoresActuales) {
                    if (!curso.getProfesorIds().contains(profesor.getId())) {
                        cursoDAO.removeRelacion(curso.getId(), profesor.getId());
                    }
                }
            }
            for (Long profesorId : curso.getProfesorIds()) {
                List<Profesor> profesoresActuales = cursoDAO.getRelacionados(curso.getId());
                boolean existe = false;
                for (Profesor profesor : profesoresActuales) {
                    if (profesor.getId().equals(profesorId)) {
                        existe = true;
                        break;
                    }
                }
                if (!existe) {
                    cursoDAO.addRelacion(curso.getId(), profesorId);
                }
            }
            return "redirect:/cursos";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        try {
            cursoDAO.delete(id);
            return "redirect:/cursos";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }


}
