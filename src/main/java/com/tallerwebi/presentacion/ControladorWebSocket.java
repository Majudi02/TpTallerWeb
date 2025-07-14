package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioMensaje;
import com.tallerwebi.dominio.entidades.chat.Mensaje;
import com.tallerwebi.dominio.entidades.chat.MensajeEnviado;
import com.tallerwebi.dominio.entidades.chat.MensajeRecibido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ControladorWebSocket {

    private ServicioMensaje servicioMensaje;
    private final SimpMessagingTemplate template;


    @Autowired
    public ControladorWebSocket(ServicioMensaje servicioMensaje, SimpMessagingTemplate template) {
        this.servicioMensaje = servicioMensaje;
        this.template = template;
    }

    @MessageMapping("/chat")
    public void recibirMensaje(MensajeRecibido recibido) {
        Mensaje mensaje = new Mensaje();
        mensaje.setContenido(recibido.getMensaje());
        mensaje.setRemitenteId(recibido.getRemitenteId());
        mensaje.setDestinatarioId(recibido.getDestinatarioId());
        mensaje.setPedidoId(recibido.getPedidoId());
        mensaje.setFecha(LocalDateTime.now());

        servicioMensaje.guardarMensaje(mensaje);

        MensajeEnviado enviado = new MensajeEnviado();
        enviado.setContenido(mensaje.getContenido());
        enviado.setRemitenteId(mensaje.getRemitenteId());
        enviado.setPedidoId(mensaje.getPedidoId());

        template.convertAndSend("/topic/chat/pedido/" + recibido.getPedidoId(), enviado);

    }


}
