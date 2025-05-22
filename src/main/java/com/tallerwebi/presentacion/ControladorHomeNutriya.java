package com.tallerwebi.presentacion;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorHomeNutriya {

    @GetMapping("/nutriya")
    public String mostrarNutriya() {
        return "nutriya";
    }
}
