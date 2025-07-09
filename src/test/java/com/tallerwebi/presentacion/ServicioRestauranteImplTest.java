package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidades.Etiqueta;
import com.tallerwebi.dominio.entidades.Pedido;
import com.tallerwebi.dominio.entidades.PedidoPlato;
import com.tallerwebi.dominio.entidades.Plato;
import com.tallerwebi.infraestructura.RepositorioPlatoImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        // Crear platos
        Plato plato1 = new Plato();
        plato1.setPrecio(100.0);

        Plato plato2 = new Plato();
        plato2.setPrecio(50.0);

        // Crear pedido 1 con 2 platos
        Pedido pedido1 = new Pedido();

        PedidoPlato pp1 = new PedidoPlato();
        pp1.setPedido(pedido1);
        pp1.setPlato(plato1);

        PedidoPlato pp2 = new PedidoPlato();
        pp2.setPedido(pedido1);
        pp2.setPlato(plato2);

        pedido1.setPedidoPlatos(List.of(pp1, pp2));

        // Crear pedido 2 con 1 plato
        Pedido pedido2 = new Pedido();

        PedidoPlato pp3 = new PedidoPlato();
        pp3.setPedido(pedido2);
        pp3.setPlato(plato1);

        pedido2.setPedidoPlatos(List.of(pp3));

        // Mock del repositorio
        when(repositorioPedidoRestaurante.traerPedidosEntregadosPorRestaurante(1L))
                .thenReturn(List.of(pedido1, pedido2));

        // Ejecutar el servicio
        ResumenRestauranteDTO resumen = servicioRestaurante.obtenerResumenDelRestaurante(1L);

        // Verificar
        assertEquals(250.0, resumen.getGananciasTotales());
    }


     /*
    @Test
    public void queObtengaElPlatoMejorYPeorValorado() {
        Plato plato1 = new Plato();
        plato1.setId(1);
        plato1.setNombre("Pizza");

        Plato plato2 = new Plato();
        plato2.setId(2);
        plato2.setNombre("Empanada");

        when(repositorioPlato.buscarPlatoPorId(1)).thenReturn(plato1);
        when(repositorioPlato.buscarPlatoPorId(2)).thenReturn(plato2);

        Map<Integer, Double> promedios = new HashMap<>();
        promedios.put(1, 4.8);
        promedios.put(2, 2.0);

        when(repositorioResena.calcularPromedioCalificacionPorPlato(1L))
                .thenReturn(promedios);

        ResumenRestauranteDTO resumen = servicioRestaurante.obtenerResumenDelRestaurante(1L);

        assertEquals("Pizza", resumen.getMejorValorado().getNombre());
        assertEquals("Empanada", resumen.getPeorValorado().getNombre());
    }
*/
    }

