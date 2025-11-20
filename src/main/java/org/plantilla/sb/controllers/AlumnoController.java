package org.plantilla.sb.controllers;

import org.springframework.ui.Model;
import org.plantilla.sb.dao.AlumnoDAO;
import org.plantilla.sb.entities.Alumno;
import org.plantilla.sb.entities.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.HashSet;

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoDAO alumnoDAO;

    @GetMapping
    public String list(Model model) throws SQLException {
        List<Alumno> alumnos = alumnoDAO.list();
        model.addAttribute("items", alumnos);
        return "alumnos";
    }

    @GetMapping("/new")
    public String newForm(Model model) throws SQLException {
        Alumno alumno = new Alumno();
        model.addAttribute("item", alumno);
        List<Curso> cursos = alumnoDAO.listCursos();
        model.addAttribute("relacionados", cursos);
        return "alumnos-form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) throws SQLException {
        Alumno alumno = alumnoDAO.getById(id);
        if (alumno == null) {
            return "redirect:/alumnos";
        }
        List<Curso> cursosDelAlumno = alumnoDAO.getRelacionados(id);
        for (Curso curso : cursosDelAlumno) {
            alumno.getCursoIds().add(curso.getId());
        }
        model.addAttribute("item", alumno);
        List<Curso> cursos = alumnoDAO.listCursos();
        model.addAttribute("relacionados", cursos);
        return "alumnos-form";
    }

    @PostMapping
    public String save(@ModelAttribute Alumno alumno) throws SQLException {
        if (alumno.getId() == null) {
            alumnoDAO.insert(alumno);
        } else {
            alumnoDAO.update(alumno);
            List<Curso> cursosActuales = alumnoDAO.getRelacionados(alumno.getId());
            for (Curso curso : cursosActuales) {
                if (!alumno.getCursoIds().contains(curso.getId())) {
                    alumnoDAO.removeRelacion(alumno.getId(), curso.getId());
                }
            }
        }
        for (Long cursoId : alumno.getCursoIds()) {
            List<Curso> cursosActuales = alumnoDAO.getRelacionados(alumno.getId());
            boolean existe = false;
            for (Curso curso : cursosActuales) {
                if (curso.getId().equals(cursoId)) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                alumnoDAO.addRelacion(alumno.getId(), cursoId);
            }
        }
        return "redirect:/alumnos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws SQLException {
        alumnoDAO.delete(id);
        return "redirect:/alumnos";
    }
}


