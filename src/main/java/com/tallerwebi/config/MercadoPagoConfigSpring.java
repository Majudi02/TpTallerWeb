package com.tallerwebi.config;

import com.mercadopago.MercadoPagoConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MercadoPagoConfigSpring {

    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken("TEST-692578912920910-062811-02dde1dc32f2487a20f5adb1cefe0728-303115883");
    }
}