package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.infraestructura.RepositorioPlatoImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Transactional
public class ServicioRestauranteImplTest {

    private SessionFactory sessionFactoryMock;
    private Session sessionMock;
    private RepositorioPlato repositorioPlato;
    private RepositorioPedidoRestaurante repositorioPedidoRestaurante;
    private RepositorioResena repositorioResena;
    private RepositorioPedidoPlato repositorioPedidoPlato;
    private ServicioRestauranteImpl servicioRestaurante;

    @BeforeEach
    public void setUp() {
        sessionFactoryMock = mock(SessionFactory.class);
        sessionMock = mock(Session.class);

        when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);

        repositorioPlato = mock(RepositorioPlato.class);
        repositorioPedidoRestaurante = mock(RepositorioPedidoRestaurante.class);
        repositorioPedidoPlato = mock(RepositorioPedidoPlato.class);
        repositorioResena = mock(RepositorioResena.class);

        servicioRestaurante = new ServicioRestauranteImpl(
                null, // repositorioUsuarioRestaurante no se usa acá
                repositorioPlato,
                repositorioPedidoRestaurante,
                repositorioPedidoPlato,
                repositorioResena,
                null // etiquetaService no se usa en estos tests

        );
    }

        @Test
        public void dadoQueTengoUnIdDeUnPlatoQuieroQueMeLoBusqueYMeLoDevueva(){
            Integer idBuscado=1;
            Plato plato = new Plato();
            plato.setId(1);
            plato.setNombre("Milanesa");
            plato.setDescripcion("Original");
            plato.setPrecio(1000.5);
            plato.setEtiquetas(new ArrayList<>());


            RepositorioPlato repositorioPlatoMock = mock(RepositorioPlato.class);
            when(repositorioPlatoMock.buscarPlatoPorId(idBuscado)).thenReturn(plato);

            Plato platoObtenido = repositorioPlatoMock.buscarPlatoPorId(idBuscado);
            PlatoDto dto=platoObtenido.obtenerDto();


            assertThat(dto.getId(),is(1));
            assertThat(dto.getNombre(),is("Milanesa"));
        }



        @Test
        public void dadoQueCreoUnPlatoLoQuieroGuardarEnLaBaseDeDatos(){
            List<String> nombres = List.of("Saludable", "Vegetariano");
            List<Etiqueta> etiquetas = new ArrayList<>();
            Integer contador=0;
            for (String nombre : nombres) {
                contador+=1;
                Etiqueta etiqueta = new Etiqueta();
                etiqueta.setId(contador);
                etiqueta.setNombre(nombre);
                etiquetas.add(etiqueta);
            }

            Plato platoMock = mock(Plato.class);
            when(platoMock.getId()).thenReturn(1);
            when(platoMock.getNombre()).thenReturn("Milanesa");
            when(platoMock.getDescripcion()).thenReturn("Milanesa con pasa muy ricas");
            when(platoMock.getPrecio()).thenReturn(1000.5);
            when(platoMock.getEtiquetas()).thenReturn(etiquetas);


            repositorioPlato = mock(RepositorioPlato.class);
            when(repositorioPlato.crearPlato(platoMock)).thenReturn(true);

            Boolean resultado = repositorioPlato.crearPlato(platoMock);

            assertThat(resultado, is(true));
            verify(repositorioPlato).crearPlato(platoMock);
        }

    @Test
    public void dadoQueTengoUnPlatoLoQuieroActualizar() {
        Plato plato = new Plato();
        plato.setId(1);
        plato.setNombre("Milanesa");
        plato.setDescripcion("Original");
        plato.setPrecio(1000.5);

        RepositorioPlato repositorioPlato = mock(RepositorioPlato.class);
        when(repositorioPlato.actualizarPlato(any(Plato.class))).thenReturn(true);

        plato.setDescripcion("Milanesa con pure");


        Boolean actualizado = repositorioPlato.actualizarPlato(plato);

        assertTrue(actualizado);
        assertThat(plato.getDescripcion(), is("Milanesa con pure"));
    }


    @Test
    public void queCalculeCorrectamenteLasGananciasTotales() {
        Plato plato1 = new Plato();
        plato1.setPrecio(100.0);

        Plato plato2 = new Plato();
        plato2.setPrecio(50.0);

        Pedido pedido1 = new Pedido();

        PedidoPlato pp1 = new PedidoPlato();
        pp1.setPedido(pedido1);
        pp1.setPlato(plato1);

        PedidoPlato pp2 = new PedidoPlato();
        pp2.setPedido(pedido1);
        pp2.setPlato(plato2);

        pedido1.setPedidoPlatos(List.of(pp1, pp2));

        Pedido pedido2 = new Pedido();

        PedidoPlato pp3 = new PedidoPlato();
        pp3.setPedido(pedido2);
        pp3.setPlato(plato1);

        pedido2.setPedidoPlatos(List.of(pp3));

        when(repositorioPedidoRestaurante.traerPedidosEntregadosPorRestaurante(1L))
                .thenReturn(List.of(pedido1, pedido2));

        ResumenRestauranteDTO resumen = servicioRestaurante.obtenerResumenDelRestaurante(1L);

        assertEquals(250.0, resumen.getGananciasTotales());
    }



    @Test
    public void queCalculeElPlatoMejorYPeorValoradoCorrectamente() {
        Long idRestaurante = 1L;

        Plato plato1 = new Plato();
        plato1.setId(1);
        plato1.setNombre("Ensalada");

        Plato plato2 = new Plato();
        plato2.setId(2);
        plato2.setNombre("Pizza");

        Plato plato3 = new Plato();
        plato3.setId(3);
        plato3.setNombre("Sopa");

        List<Plato> platosCalificados = List.of(plato1, plato2, plato3);

        when(repositorioPedidoPlato.obtenerPlatosConCalificacionesPorRestaurante(idRestaurante))
                .thenReturn(platosCalificados);

        when(repositorioPedidoPlato.obtenerPromedioCalificacionPorPlato(1)).thenReturn(4.0);
        when(repositorioPedidoPlato.obtenerPromedioCalificacionPorPlato(2)).thenReturn(2.0);
        when(repositorioPedidoPlato.obtenerPromedioCalificacionPorPlato(3)).thenReturn(5.0);

        ResumenRestauranteDTO resumen = servicioRestaurante.obtenerResumenDelRestaurante(idRestaurante);

        assertNotNull(resumen.getMejorValorado());
        assertNotNull(resumen.getPeorValorado());

        assertEquals("Sopa", resumen.getMejorValorado().getNombre());
        assertEquals("Pizza", resumen.getPeorValorado().getNombre());
    }


    @Test
    public void dadoQueTengoLos3PlatosMenosPedidosLosQuieroObtener() {
        Etiqueta etiqueta1 = new Etiqueta();
        etiqueta1.setNombre("Proteica");

        Long idRestaurante = 1L;

        Plato plato1 = new Plato();
        plato1.setId(1);
        plato1.setNombre("Plato 1");
        plato1.setEtiquetas(List.of(etiqueta1));

        Plato plato2 = new Plato();
        plato2.setId(2);
        plato2.setNombre("Plato 2");
        plato2.setEtiquetas(List.of(etiqueta1));

        Plato plato3 = new Plato();
        plato3.setId(3);
        plato3.setNombre("Plato 3");
        plato3.setEtiquetas(List.of(etiqueta1));

        List<Plato> platosMenosPedidos = List.of(plato1, plato2, plato3);

        when(repositorioPedidoPlato.traerLos3PlatosMenosPedidos(idRestaurante)).thenReturn(platosMenosPedidos);

        List<PlatoDto> resultado = servicioRestaurante.traerLos3platosMenosPedidos(idRestaurante);

        assertEquals(3, resultado.size());
    }

    @Test
    public void queCalculeCorrectamenteCantidadDeVecesPedidos() {
        Plato plato1 = new Plato();
        plato1.setId(1);
        plato1.setNombre("Ensalada");
        plato1.setPrecio(100.0);

        Plato plato2 = new Plato();
        plato2.setId(2);
        plato2.setNombre("Milanesa");
        plato2.setPrecio(150.0);

        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();

        PedidoPlato pp1 = new PedidoPlato();
        pp1.setPlato(plato1);
        pp1.setPedido(pedido1);

        PedidoPlato pp2 = new PedidoPlato();
        pp2.setPlato(plato1);
        pp2.setPedido(pedido2);

        PedidoPlato pp3 = new PedidoPlato();
        pp3.setPlato(plato2);
        pp3.setPedido(pedido1);

        pedido1.setPedidoPlatos(List.of(pp1, pp3));
        pedido2.setPedidoPlatos(List.of(pp2));

        when(repositorioPedidoRestaurante.traerPedidosEntregadosPorRestaurante(1L))
                .thenReturn(List.of(pedido1, pedido2));

        when(repositorioPedidoPlato.obtenerPlatosPorRestaurante(1L))
                .thenReturn(List.of(pp1, pp2, pp3));

        when(repositorioPedidoPlato.obtenerPlatosConCalificacionesPorRestaurante(1L))
                .thenReturn(List.of());

        ResumenRestauranteDTO resumen = servicioRestaurante.obtenerResumenDelRestaurante(1L);

        assertNotNull(resumen.getMasPedido());
        assertNotNull(resumen.getMenosPedido());

        assertEquals("Ensalada", resumen.getMasPedido().getNombre());
        assertEquals("Milanesa", resumen.getMenosPedido().getNombre());

        assertEquals(2L, resumen.getCantidadMasPedido());
        assertEquals(1L, resumen.getCantidadMenosPedido());

        assertEquals(100.0 + 150.0 + 100.0, resumen.getGananciasTotales());
    }






}

