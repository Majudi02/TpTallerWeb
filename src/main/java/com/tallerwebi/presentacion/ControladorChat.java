package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioMensaje;
import com.tallerwebi.dominio.entidades.Mensaje;
import com.tallerwebi.dominio.MensajeEnviado;
import com.tallerwebi.dominio.MensajeRecibido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ControladorChat {

    private ServicioMensaje servicioMensaje;
    private final SimpMessagingTemplate template;


    @Autowired
    public ControladorChat(ServicioMensaje servicioMensaje, SimpMessagingTemplate template) {
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

    @GetMapping("/chat/mensajes/{pedidoId}")
    @ResponseBody
    public List<MensajeDto> obtenerMensajes(@PathVariable Long pedidoId) {
        return servicioMensaje.traerMensajesPorPedido(pedidoId);
    }
}
