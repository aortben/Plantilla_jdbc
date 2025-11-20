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

    // PATRÓN REUTILIZABLE: Usa find and replace para cambiar "ProfesorDAO" y "profesorDAO"
    // simultáneamente en todos los métodos asociados al inyector.
    @Autowired
    private ProfesorDAO profesorDAO;

    // PATRÓN REUTILIZABLE: Inyector secundario para entidades relacionadas (N:M, 1:N)
    @Autowired
    private CursoDAO cursoDAO;

    // PATRÓN GENÉRICO - Método list: obtiene todas las entidades
    // Atributos del modelo: "items" (lista de entidades)
    @GetMapping
    public String list(Model model) throws SQLException {
        List<Profesor> profesores = profesorDAO.list();
        model.addAttribute("items", profesores);
        return "profesores";
    }

    // PATRÓN GENÉRICO - Método newForm: formulario para crear nueva entidad
    // Atributos del modelo: "item" (entidad vacía), "relacionados" (lista de opciones)
    @GetMapping("/new")
    public String newForm(Model model) throws SQLException {
        Profesor profesor = new Profesor();
        List<Curso> cursos = cursoDAO.list();
        model.addAttribute("item", profesor);
        model.addAttribute("relacionados", cursos);
        return "profesores-form";
    }

    // PATRÓN GENÉRICO - Método editForm: formulario para editar entidad existente
    // Carga entidad, relaciones N:M y opciones disponibles
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) throws SQLException {
        Profesor profesor = profesorDAO.getById(id);
        List<Curso> cursosRelacionados = profesorDAO.getRelacionados(id);
        for (Curso curso : cursosRelacionados) {
            profesor.getCursoIds().add(curso.getId());
        }
        List<Curso> cursos = cursoDAO.list();
        model.addAttribute("item", profesor);
        model.addAttribute("relacionados", cursos);
        return "profesores-form";
    }

    // PATRÓN GENÉRICO - Método save: inserta o actualiza entidad y gestiona relaciones N:M
    // Lógica: insert si id es null, update si existe, sincroniza relaciones N:M
    @PostMapping
    public String save(@ModelAttribute Profesor profesor) throws SQLException {
        if (profesor.getId() == null) {
            profesorDAO.insert(profesor);
        } else {
            profesorDAO.update(profesor);
            List<Curso> cursosActuales = profesorDAO.getRelacionados(profesor.getId());
            for (Curso curso : cursosActuales) {
                if (!profesor.getCursoIds().contains(curso.getId())) {
                    profesorDAO.removeRelacion(profesor.getId(), curso.getId());
                }
            }
        }
        for (Long cursoId : profesor.getCursoIds()) {
            List<Curso> cursosActuales = profesorDAO.getRelacionados(profesor.getId());
            boolean existe = false;
            for (Curso curso : cursosActuales) {
                if (curso.getId().equals(cursoId)) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                profesorDAO.addRelacion(profesor.getId(), cursoId);
            }
        }
        return "redirect:/profesores";
    }

    // PATRÓN GENÉRICO - Método delete: elimina entidad por ID
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws SQLException {
        profesorDAO.delete(id);
        return "redirect:/profesores";
    }
}


