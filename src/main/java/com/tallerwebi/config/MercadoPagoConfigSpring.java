package com.tallerwebi.config;

import com.mercadopago.MercadoPagoConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MercadoPagoConfigSpring {

    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken("TEST-3751280516928760-070821-a9281d1c8f6fea1b68553f8353f4ffe8-174016325");
    }
}