package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MediosPagoControlador {

    @GetMapping("/medios-pago")
    public ModelAndView irAMediosPago() {
        return new ModelAndView("medios-pago");
    }
}
