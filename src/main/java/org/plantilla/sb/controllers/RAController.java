package org.plantilla.sb.controllers;

import org.plantilla.sb.dao.RADAO;
import org.plantilla.sb.dao.CursoDAO;
import org.plantilla.sb.entities.RA;
import org.plantilla.sb.entities.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/ra")
public class RAController {

    @Autowired
    private RADAO raDAO;

    @Autowired
    private CursoDAO cursoDAO;

    @GetMapping
    public String listRA(Model model) throws SQLException {
        List<RA> resultados = raDAO.listAllRA();
        model.addAttribute("resultados", resultados);
        return "ra";
    }

    @GetMapping("/new")
    public String newRAForm(Model model) throws SQLException {
        RA ra = new RA();
        List<Curso> todosCursos = cursoDAO.listAllCursos();
        model.addAttribute("ra", ra);
        model.addAttribute("todosCursos", todosCursos);
        return "ra-form";
    }

    @GetMapping("/edit/{id}")
    public String editRAForm(@PathVariable Long id, Model model) throws SQLException {
        RA ra = raDAO.getRAById(id);
        List<Curso> todosCursos = cursoDAO.listAllCursos();
        model.addAttribute("ra", ra);
        model.addAttribute("todosCursos", todosCursos);
        return "ra-form";
    }

    @PostMapping
    public String saveRA(@ModelAttribute RA ra) throws SQLException {
        if (ra.getId() == null) {
            raDAO.insertRA(ra);
        } else {
            raDAO.updateRA(ra);
        }
        return "redirect:/ra";
    }

    @GetMapping("/delete/{id}")
    public String deleteRA(@PathVariable Long id) throws SQLException {
        raDAO.deleteRA(id);
        return "redirect:/ra";
    }
}
