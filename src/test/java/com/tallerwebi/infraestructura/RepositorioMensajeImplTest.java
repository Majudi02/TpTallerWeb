package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Mensaje;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
public class RepositorioMensajeImplTest {
    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioMensajeImpl repositorio;

    @BeforeEach
    public void setUp(){ repositorio=new RepositorioMensajeImpl(sessionFactory);}

    @Test
    public void dadoQueTengoUnMensajeLoQuieroGuardarEnLaBaseDeDatos(){
        Mensaje mensaje = new Mensaje();
        mensaje.setId(1L);
        mensaje.setContenido("Hola");

        repositorio.guardar(mensaje);
        Mensaje mensajeRecuperado = sessionFactory.getCurrentSession().get(Mensaje.class, mensaje.getId());

        assertEquals(mensajeRecuperado.getContenido(),mensaje.getContenido());
    }

    @Test
    public void dadoQueTengoMensajesGuardadosLoQuieroPoderBuscarPorElIdDelPedido(){
        Long idPedido = 1L;
        Mensaje mensaje = new Mensaje();
        mensaje.setId(1L);
        mensaje.setContenido("Soy el cliente");
        mensaje.setPedidoId(idPedido);

        Mensaje mensaje2 = new Mensaje();
        mensaje2.setId(1L);
        mensaje2.setContenido("Soy el repartidor");
        mensaje2.setPedidoId(idPedido);

        repositorio.guardar(mensaje);
        repositorio.guardar(mensaje2);

        List<Mensaje> mensajesObtenidos = repositorio.traerMensajesPorPedido(idPedido);

        assertEquals(2, mensajesObtenidos.size());
        assertEquals(mensajesObtenidos.get(0).getContenido(),mensaje.getContenido());
        assertEquals(mensajesObtenidos.get(1).getContenido(),mensaje2.getContenido());
    }
}
