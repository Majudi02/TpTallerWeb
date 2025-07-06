package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Pago;
import com.tallerwebi.dominio.entidades.Pedido;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
public class RepositorioPagoImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioPagoImpl repositorio;

    @BeforeEach
    public void setUp(){
        repositorio=new RepositorioPagoImpl(sessionFactory);
    }


    @Test
    public void dadoQueTengoUnPagoLoQuieroGuardarEnLaBaseDeDatos(){
        Pedido pedido = new Pedido();
        pedido.setId(1);
        sessionFactory.getCurrentSession().save(pedido);

        Pago pago = new Pago();
        pago.setIdPagoMercadoPago(1234567890L);
        pago.setEstado("aprobado");
        pago.setMetodoPago("visa");
        pago.setTipoPago("credit_card");
        pago.setMonto(1999.99);
        pago.setMoneda("ARS");
        pago.setFechaCreacion(LocalDateTime.now());
        pago.setFechaAprobacion(LocalDateTime.now());
        pago.setCorreoPagador("cliente@example.com");
        pago.setPedido(pedido);

        repositorio.guardar(pago);
        Pago pagoRecuperado = sessionFactory.getCurrentSession().get(Pago.class, pago.getId());

        assertNotNull(pagoRecuperado);
    }

    @Test
    public void dadoQueTengoUnPagoGuardadoLoQuieroPoderBuscarPorElIdDelPedido(){
        Pedido pedido = new Pedido();
        pedido.setId(1);
        sessionFactory.getCurrentSession().save(pedido);

        Pago pago = new Pago();
        pago.setIdPagoMercadoPago(1234567890L);
        pago.setEstado("aprobado");
        pago.setMetodoPago("visa");
        pago.setTipoPago("credit_card");
        pago.setMonto(1999.99);
        pago.setMoneda("ARS");
        pago.setFechaCreacion(LocalDateTime.now());
        pago.setFechaAprobacion(LocalDateTime.now());
        pago.setCorreoPagador("cliente@example.com");
        pago.setPedido(pedido);
        repositorio.guardar(pago);

        Pago pagoBuscado = repositorio.buscarPorPedidoId(pedido.getId());

        assertEquals(pago.getEstado(), pagoBuscado.getEstado());
    }
}
