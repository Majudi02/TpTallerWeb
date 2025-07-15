package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.MensajeEnviado;
import com.tallerwebi.dominio.MensajeRecibido;
import com.tallerwebi.dominio.ServicioMensaje;
import com.tallerwebi.dominio.entidades.Mensaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ControladorChatTest {
    private ServicioMensaje servicioMensajeMock;
    private SimpMessagingTemplate templateMock;
    private ControladorChat controladorChat;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        servicioMensajeMock = mock(ServicioMensaje.class);
        templateMock = mock(SimpMessagingTemplate.class);
        controladorChat = new ControladorChat(servicioMensajeMock, templateMock);

        mockMvc= MockMvcBuilders.standaloneSetup(controladorChat).build();
    }

    @Test
    public void dadoUnMensajeRecibidoDeberiaGuardarloYEnviarPorWebSocket() {
        MensajeRecibido recibido = new MensajeRecibido();
        recibido.setMensaje("Hola desde test");
        recibido.setRemitenteId(1L);
        recibido.setDestinatarioId(2L);
        recibido.setPedidoId(5L);

        controladorChat.recibirMensaje(recibido);

        verify(servicioMensajeMock).guardarMensaje(any(Mensaje.class));

        verify(templateMock).convertAndSend(eq("/topic/chat/pedido/5"), any(MensajeEnviado.class));
    }

    @Test
    public void dadoUnPedidoIdDeberiaRetornarMensajes() throws Exception {
        Mensaje mensaje = new Mensaje();
        mensaje.setId(1L);
        mensaje.setContenido("Hola");
        mensaje.setFecha(LocalDateTime.now());

        MensajeDto mensajeDto = new MensajeDto(mensaje);
        mensajeDto.setContenido("Hola");
        when(servicioMensajeMock.traerMensajesPorPedido(1L)).thenReturn(List.of(mensajeDto));

        mockMvc.perform(get("/chat/mensajes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].contenido").value("Hola"));
    }

}
