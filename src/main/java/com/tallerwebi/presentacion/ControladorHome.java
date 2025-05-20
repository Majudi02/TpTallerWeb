package com.tallerwebi.presentacion;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControladorHome {

    @GetMapping("/nutriya")
    public String mostrarNutriya() {
        return "nutriya";
    }

    @GetMapping("/nutriya-login")
    public String mostrarLogin() {
        return "nutriya-login";
    }
}
