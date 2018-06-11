package br.cubas.usercontrol.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexControllers {

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("/index");
    }
}
