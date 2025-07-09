package com.tallerwebi.config;

import com.mercadopago.MercadoPagoConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MercadoPagoConfigSpring {

    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken("TEST-1016484376935227-070822-9c807e26f34ef0d5b09a443522bfa58f-174016325");
    }
}