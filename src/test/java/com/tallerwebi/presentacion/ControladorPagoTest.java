package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PedidoService;
import com.tallerwebi.dominio.ServicioPago;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.entidades.Pago;
import com.tallerwebi.dominio.entidades.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControladorPagoTest {

    private PedidoService pedidoServiceMock;
    private ServicioPago servicioPagoMock;
    private ServicioUsuario servicioUsuarioMock;
    private ControladorPago controlador;

    @BeforeEach
    public void setUp() {
        pedidoServiceMock = mock(PedidoService.class);
        servicioPagoMock = mock(ServicioPago.class);
        servicioUsuarioMock = mock(ServicioUsuario.class);
        controlador = new ControladorPago(pedidoServiceMock, servicioPagoMock, servicioUsuarioMock);
    }

    @Test
    public void queRedirijaAVistaExito() {
        String vista = controlador.pagoExitoso();
        assertEquals("pago-exitoso", vista);
    }

    @Test
    public void queRedirijaAVistaFallido() {
        String vista = controlador.pagoFallido();
        assertEquals("pago-fallido", vista);
    }

    @Test
    public void queRedirijaAVistaPendiente() {
        String vista = controlador.pagoPendiente();
        assertEquals("pago-pendiente", vista);
    }

    @Test
    public void queSePuedaGuardarUnPagoYRedirigir() {
        // Arrange
        Pago pago = new Pago();
        Pedido pedido = new Pedido();
        pedido.setId(5);
        pago.setPedido(pedido);

        // Act
        ModelAndView resultado = controlador.guardarPago(pago);

        // Assert
        verify(servicioPagoMock).guardarPago(pago);
        assertEquals("redirect:/pedido/exito", resultado.getViewName());
    }

    @Test
    public void queSeMuestreElPagoExitosoEnLaVista() {
        // Arrange
        Pago pago = new Pago();
        pago.setId(1L);
        pago.setMonto(1500.0);

        when(servicioPagoMock.obtenerPagoPorIdPedido(1)).thenReturn(pago);

        // Act
        ModelAndView resultado = controlador.pagoExitoso(1);

        // Assert
        assertEquals("pago-exitoso", resultado.getViewName());
        ModelMap modelo = (ModelMap) resultado.getModel();
        assertEquals(pago, modelo.get("pago"));
    }
}
