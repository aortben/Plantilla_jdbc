package org.plantilla.sb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home controller responsible for handling the root URL
 * and loading the main index page.
 */
@Controller
public class HomeController {

    /**
     * Handles the root endpoint ("/") and returns the index view.
     *
     * @param model Model object for passing data to the view.
     * @return the index page template.
     */
    @GetMapping("/")
    public String home(Model model) {
        return "index"; // Points to templates/index.html
    }
}