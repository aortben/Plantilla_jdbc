package org.plantilla.sb.controllers;

import org.plantilla.sb.dao.AulaDAO;
import org.plantilla.sb.dao.CursoDAO;
import org.plantilla.sb.entities.Aula;
import org.plantilla.sb.entities.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/aulas")
public class AulaController {

    @Autowired
    private AulaDAO aulaDAO;

    @Autowired
    private CursoDAO cursoDAO;

    @GetMapping
    public String list(Model model) throws SQLException {
        model.addAttribute("items", aulaDAO.list());
        return "aulas";
    }

    @GetMapping("/new")
    public String newForm(Model model) throws SQLException {
        Aula aula = new Aula();
        model.addAttribute("item", aula);
        model.addAttribute("relacionados", cursoDAO.list());
        return "aulas-form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) throws SQLException {
        Aula aula = aulaDAO.getById(id);
        model.addAttribute("item", aula);
        model.addAttribute("relacionados", cursoDAO.list());
        return "aulas-form";
    }

    @PostMapping
    public String save(@ModelAttribute Aula aula) throws SQLException {
        if (aula.getId() == null) {
            aulaDAO.insert(aula);
        } else {
            aulaDAO.update(aula);
        }
        return "redirect:/aulas";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws SQLException {
        aulaDAO.delete(id);
        return "redirect:/aulas";
    }
}
