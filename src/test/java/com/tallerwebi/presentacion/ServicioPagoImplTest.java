package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.RepositorioPago;
import com.tallerwebi.dominio.ServicioPago;
import com.tallerwebi.dominio.ServicioPagoImpl;
import com.tallerwebi.dominio.entidades.Pago;
import com.tallerwebi.infraestructura.RepositorioPagoImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Transactional
public class ServicioPagoImplTest {

    private SessionFactory sessionFactoryMock;
    private Session sessionMock;
    private RepositorioPago repositorioMock;
    ServicioPago servicio;

    @BeforeEach
    public void setUp(){
        sessionFactoryMock = mock(SessionFactory.class);
        sessionMock = mock(Session.class);
        repositorioMock = mock(RepositorioPagoImpl.class);
        when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);
        servicio = new ServicioPagoImpl(repositorioMock);
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
}
