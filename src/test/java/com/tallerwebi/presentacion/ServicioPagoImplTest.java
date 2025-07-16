package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.infraestructura.RepositorioPagoImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Transactional
public class ServicioPagoImplTest {

    private SessionFactory sessionFactoryMock;
    private Session sessionMock;
    private RepositorioPago repositorioMock;
    private ServicioEmail servicioEmailMock;
    ServicioPago servicio;

    @BeforeEach
    public void setUp(){
        sessionFactoryMock = mock(SessionFactory.class);
        sessionMock = mock(Session.class);
        repositorioMock = mock(RepositorioPagoImpl.class);
        when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);
        servicio = new ServicioPagoImpl(repositorioMock);
        servicioEmailMock = mock(ServicioEmail.class);
        servicio.setServicioEmail(servicioEmailMock);
    }

    @Test
    public void dadoQueTengoUnPagoLoQuieroGuardar() {
        Pago pago = new Pago();
        pago.setEstado("aprobado");
        pago.setMetodoPago("visa");
        pago.setTipoPago("credit_card");

        servicio.guardarPago(pago);

        verify(repositorioMock).guardar(pago);
    }

    @Test
    public void dadoQueTengoUnPagoGuardadoLoQuieroPoderBuscarPorElIdDelPedido() {

        Integer idPedido = 123;
        Pago pagoEsperado = new Pago();
        when(repositorioMock.buscarPorPedidoId(idPedido)).thenReturn(pagoEsperado);


        Pago resultado = servicio.obtenerPagoPorIdPedido(idPedido);

        assertEquals(pagoEsperado, resultado);
        verify(repositorioMock).buscarPorPedidoId(idPedido);
    }


    @Test
    public void queSeEnvíeEmailCuandoElPagoTieneCliente() {
        // Arrange
        UsuarioNutriya usuario = new UsuarioNutriya() {
            @Override
            public String tipoUsuario() {
                return "CLIENTE";
            }
        };
        usuario.setEmail("cliente@nutriya.com");

        Pedido pedido = new Pedido();
        pedido.setId(123);
        pedido.setUsuario(usuario);
        pedido.setPedidoPlatos(new ArrayList<>());

        Pago pago = new Pago();
        pago.setEstado("aprobado");
        pago.setMetodoPago("visa");
        pago.setTipoPago("credit_card");
        pago.setMonto(150.0);
        pago.setPedido(pedido);

        // Act
        servicio.guardarPago(pago);

        // Assert
        verify(repositorioMock).guardar(pago);
        verify(servicioEmailMock).enviarEmail(
                eq("cliente@nutriya.com"),
                eq("Confirmación de pago - NutriYa"),
                contains("pedido #123")
        );
    }




    @Test
    public void queNoSeEnvíeEmailSiNoHayCliente() {
        // Arrange
        Pedido pedido = new Pedido();
        pedido.setId(999);
        pedido.setUsuario(null);

        Pago pago = new Pago();
        pago.setPedido(pedido);
        pago.setMonto(80.0);

        // Act
        servicio.guardarPago(pago);

        // Assert
        verify(repositorioMock).guardar(pago);
        verify(servicioEmailMock, never()).enviarEmail(any(), any(), any());
    }

    @Test
    public void queNoSeEnvíeEmailSiNoHayPedido() {
        // Arrange
        Pago pago = new Pago();
        pago.setPedido(null);
        pago.setMonto(50.0);

        // Act
        servicio.guardarPago(pago);

        // Assert
        verify(repositorioMock).guardar(pago);
        verify(servicioEmailMock, never()).enviarEmail(any(), any(), any());
    }


}
