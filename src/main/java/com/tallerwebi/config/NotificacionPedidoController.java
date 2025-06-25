package com.tallerwebi.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class NotificacionPedidoController {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping("/restaurante/notificaciones-pedidos")
    public SseEmitter conectarRestaurante() {
        SseEmitter emitter = new SseEmitter(0L); // sin timeout
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(e -> emitters.remove(emitter));

        return emitter;
    }

    public void notificarMensaje(String mensaje) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("nuevo-plato")
                        .data(mensaje));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }
}
