package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.RepositorioMensaje;
import com.tallerwebi.dominio.ServicioMensaje;
import com.tallerwebi.dominio.ServicioMensajeImpl;
import com.tallerwebi.dominio.entidades.Mensaje;
import com.tallerwebi.infraestructura.RepositorioMensajeImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Transactional
public class ServicioMensajeImplTest {

    private SessionFactory sessionFactoryMock;
    private Session sessionMock;
    private RepositorioMensaje repositorioMock;
    private ServicioMensaje servicio;


    @BeforeEach
    public void setUp(){
        sessionFactoryMock = mock(SessionFactory.class);
        sessionMock = mock(Session.class);
        repositorioMock = mock(RepositorioMensajeImpl.class);
        when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);
        servicio = new ServicioMensajeImpl(repositorioMock);
    }

    @Test
    public void dadoQueTengoUnMensajeLoQuieroGuardar(){
        Mensaje mensaje = new Mensaje();
        mensaje.setId(1L);
        mensaje.setContenido("Hola");

        servicio.guardarMensaje(mensaje);

        verify(repositorioMock).guardar(mensaje);
    }

    @Test
    public void dadoQueTengoUnIdQuieroObtenerSusMensajes(){
        Long idPedido = 1L;
        Mensaje mensaje = new Mensaje();
        mensaje.setId(100L);
        mensaje.setContenido("Hola!");
        mensaje.setFecha(LocalDateTime.now());

        Mensaje mensaje2 = new Mensaje();
        mensaje2.setId(100L);
        mensaje2.setContenido("Soy el repartidor");
        mensaje2.setFecha(LocalDateTime.now());

        when(repositorioMock.traerMensajesPorPedido(idPedido)).thenReturn(List.of(mensaje,mensaje2));

        List<MensajeDto> menajesObtenidos = servicio.traerMensajesPorPedido(idPedido);

        assertEquals(2,menajesObtenidos.size());
    }

}
