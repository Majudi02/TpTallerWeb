package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.RepositorioPedidoPlato;
import com.tallerwebi.dominio.ServicioPedidoPlato;
import com.tallerwebi.dominio.ServicioPedidoPlatoImpl;
import com.tallerwebi.dominio.entidades.EstadoPlato;
import com.tallerwebi.dominio.entidades.PedidoPlato;
import com.tallerwebi.infraestructura.RepositorioPedidoPlatoImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Transactional
public class ServicioPedidoPlatoImplTest {

    private SessionFactory sessionFactoryMock;
    private Session sessionMock;
    RepositorioPedidoPlato repositorioMock;
    ServicioPedidoPlato servicio;


    @BeforeEach
    public void setUp(){
        sessionFactoryMock = mock(SessionFactory.class);
        sessionMock = mock(Session.class);
        repositorioMock= mock(RepositorioPedidoPlatoImpl.class);
        when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);
        servicio = new ServicioPedidoPlatoImpl(repositorioMock);

    }


    @Test
    public void dadoQueTengoUnIdQuieroQueMeDevuelvaUnPedidoPlato() {
        Long idBuscado = 1L;

        PedidoPlato pedidoPlatoMock = mock(PedidoPlato.class);
        PedidoPlatoDto dtoEsperado = new PedidoPlatoDto();

        when(pedidoPlatoMock.obtenerDto()).thenReturn(dtoEsperado);
        when(repositorioMock.buscarPorId(idBuscado)).thenReturn(pedidoPlatoMock);

        PedidoPlatoDto resultado = servicio.buscarPorId(idBuscado);

        assertEquals(dtoEsperado, resultado);
    }

    @Test
    public void dadoQueTengoUnPedidoPlatoLoQuieroActualizar(){
        Long id = 1L;
        PedidoPlatoDto dto = new PedidoPlatoDto();
        dto.setId(id);
        dto.setEstadoPlato(EstadoPlato.FINALIZADO);
        dto.setCalificacion(5);

        PedidoPlato pedidoExistente = mock(PedidoPlato.class);

        when(repositorioMock.buscarPorId(id)).thenReturn(pedidoExistente);

        servicio.guardar(dto);

        verify(repositorioMock).buscarPorId(id);
        verify(pedidoExistente).setCalificacion(5);
    }

    @Test
    public void dadoQueTengoUnIdQuieroFinalizarUnPedidoPlato(){
        Long idPedido = 1L;

        servicio.finalizarPedido(idPedido);

        verify(repositorioMock).finalizarPedido(idPedido);
    }

    @Test
    public void dadodQueTengoCalificaionesEnCadaPedidoPlatoQuieroObtenerElPromedioDeCalificaciones(){
        Integer id =1;

        servicio.obtenerPromedioCalificacionPorPlato(id);

        verify(repositorioMock).obtenerPromedioCalificacionPorPlato(id);
    }

}
