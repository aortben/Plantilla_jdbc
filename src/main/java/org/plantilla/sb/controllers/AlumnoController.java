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

    // Lista alumnos para Thymeleaf
    @GetMapping
    public String listarAlumnos(Model model) throws SQLException {
        List<Alumno> alumnos = alumnoDAO.listAllAlumnos();
        model.addAttribute("alumnos", alumnos);
        return "alumnos";
    }

    @GetMapping("/new")
    public String nuevoAlumno(Model model) throws SQLException {
        Alumno alumno = new Alumno();
        model.addAttribute("alumno", alumno);

        List<Curso> todosCursos = alumnoDAO.listAllCursos();
        model.addAttribute("todosCursos", todosCursos);

        return "alumnos-form";
    }

    // Formulario para editar alumno
    @GetMapping("/{id}/edit")
    public String editarAlumno(@PathVariable Long id, Model model) throws SQLException {
        Alumno alumno = alumnoDAO.getAlumnoById(id);
        if (alumno == null) {
            return "redirect:/alumnos";
        }
        List<Curso> cursosDelAlumno = alumnoDAO.getCursosByAlumnoId(id);
        for (Curso curso : cursosDelAlumno) {
            alumno.getCursoIds().add(curso.getId());
        }
        model.addAttribute("alumno", alumno);

        List<Curso> todosCursos = alumnoDAO.listAllCursos();
        model.addAttribute("todosCursos", todosCursos);

        return "alumnos-form";
    }

    // Guardar alumno (nuevo o editado)
    @PostMapping
    public String guardarAlumno(@ModelAttribute Alumno alumno) throws SQLException {
        if (alumno.getId() == null) {
            alumnoDAO.insertAlumno(alumno);
        } else {
            alumnoDAO.updateAlumno(alumno);
            List<Curso> cursosActuales = alumnoDAO.getCursosByAlumnoId(alumno.getId());
            for (Curso curso : cursosActuales) {
                if (!alumno.getCursoIds().contains(curso.getId())) {
                    alumnoDAO.removeCursoFromAlumno(alumno.getId(), curso.getId());
                }
            }
        }
        for (Long cursoId : alumno.getCursoIds()) {
            List<Curso> cursosActuales = alumnoDAO.getCursosByAlumnoId(alumno.getId());
            boolean existe = false;
            for (Curso curso : cursosActuales) {
                if (curso.getId().equals(cursoId)) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                alumnoDAO.addCursoToAlumno(alumno.getId(), cursoId);
            }
        }
        return "redirect:/alumnos";
    }

    // Borrar alumno
    @GetMapping("/delete/{id}")
    public String borrarAlumno(@PathVariable Long id) throws SQLException {
        alumnoDAO.deleteAlumno(id);
        return "redirect:/alumnos";
    }
}


