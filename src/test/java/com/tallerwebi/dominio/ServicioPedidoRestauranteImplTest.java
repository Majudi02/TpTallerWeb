package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioPedidoRestauranteImplTest {
    private RepositorioPedidoRestaurante repoPedido;
    private RepositorioPedidoPlato repoPedidoPlato;
    private ServicioPedidoRestaurante servicio;
    private RepositorioPedido repositorioPedido;

    @BeforeEach
    void setup() {
        repoPedido = mock(RepositorioPedidoRestaurante.class);
        repoPedidoPlato = mock(RepositorioPedidoPlato.class);
        servicio = new ServicioPedidoRestauranteImpl(repoPedido, repoPedidoPlato,repositorioPedido);
    }

    @Test
    void puedoHacerUnPedidoConElFlujoCompletoDeClienteRestauranteRepartidor() {

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);


        Plato plato = new Plato();
        plato.setId(1);
        plato.setRestaurante(restaurante);


        Pedido pedido = new Pedido();
        pedido.setId(100);
        pedido.setUsuario(cliente);
        pedido.setPedidoPlatos(new ArrayList<>());
        pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
        pedido.setFinalizo(false);

        PedidoPlato pedidoPlato = new PedidoPlato();
        pedidoPlato.setId(10L);
        pedidoPlato.setPedido(pedido);
        pedidoPlato.setPlato(plato);
        pedidoPlato.setEstadoPlato(EstadoPlato.PENDIENTE);

        pedido.getPedidoPlatos().add(pedidoPlato);


        when(repoPedidoPlato.buscarPorId(10L)).thenReturn(pedidoPlato);
        when(repoPedido.traerPedidosListosParaRetirar()).thenReturn(Collections.singletonList(pedido));


        when(repoPedido.buscarPorId(100)).thenReturn(pedido);

        doAnswer(invocation -> {
            Integer id = invocation.getArgument(0);
            if (id.equals(pedido.getId())) {
                pedido.setEstadoPedido(EstadoPedido.ENTREGADO);
                pedido.setFinalizo(true);
            }
            return null;
        }).when(repoPedido).entregarPedido(anyInt());


        servicio.finalizarPlatoPedido(10L);
        servicio.confirmarPedidoListoParaEnviar(pedido.getId());


        assertEquals(EstadoPlato.FINALIZADO, pedidoPlato.getEstadoPlato());


        assertTrue(pedido.todosLosPlatosFinalizados());


        assertEquals(EstadoPedido.LISTO_PARA_ENVIAR, pedido.getEstadoPedido());


        List<Pedido> listos = repoPedido.traerPedidosListosParaRetirar();
        assertFalse(listos.isEmpty());


        servicio.entregarPedido(100);
        assertEquals(EstadoPedido.ENTREGADO, pedido.getEstadoPedido());
        assertTrue(pedido.isFinalizo());
    }
}
