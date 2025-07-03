package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;

import com.tallerwebi.dominio.entidades.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PedidoServiceImplTest {

    private ServicioPlato servicioPlato;
    private RepositorioPedido repositorioPedido;
    private ServicioUsuario servicioUsuario;
    private ServicioPedidoPlato servicioPedidoPlato;
    private PedidoServiceImpl pedidoService;

    @BeforeEach
    public void setUp(){
        servicioPlato = mock(ServicioPlato.class);
        repositorioPedido = mock(RepositorioPedido.class);
        servicioUsuario = mock(ServicioUsuario.class);
        servicioPedidoPlato = mock(ServicioPedidoPlato.class);

        pedidoService = new PedidoServiceImpl(servicioPlato, repositorioPedido, servicioUsuario, servicioPedidoPlato);


    }

    @Test
    public void dadoQueTengoPlatosGuardadosLosQuieroTraerTodos(){
        when(servicioPlato.traerTodosLosPlatos()).thenReturn(Collections.emptyList());

        List<PlatoDto> platos = pedidoService.traerTodosLosPlatos();

        assertNotNull(platos);
        assertEquals(platos.size(), 0);
    }

    @Test
    public void dadoQueTengoUnTipoDeComidaQuieroTraerTodosLosPlatosQueTenganEsaEtiqueta(){
        String tipoComida= "Proteica";

        EtiquetaDto etiquetaProteica = new EtiquetaDto(1, "Proteica");

        PlatoDto plato1 = new PlatoDto();
        plato1.setNombre("Pollo con arroz");
        plato1.setPrecio(950.0);
        plato1.setEtiquetas(List.of(etiquetaProteica));

        PlatoDto plato2 = new PlatoDto();
        plato2.setNombre("Carne al horno");
        plato2.setPrecio(1100.0);
        plato2.setEtiquetas(List.of(etiquetaProteica));

        List<PlatoDto> platos = List.of(plato1, plato2);

        when(servicioPlato.buscarPlatosPorTipoComida(tipoComida)).thenReturn(platos);


        List<PlatoDto> platosObtenidos = pedidoService.buscarPlatosPorTipoComida(tipoComida);

        assertNotNull(platosObtenidos);
        assertEquals(2, platosObtenidos.size());
    }
    @Test
    public void dadoQueCadaPlatoTieneUnPrecioLosQuieroOrdenarDeMayorAMenor(){
        PlatoDto plato1 = new PlatoDto();
        plato1.setNombre("Pollo con arroz");
        plato1.setPrecio(950.0);

        PlatoDto plato2 = new PlatoDto();
        plato2.setNombre("Pizza");
        plato2.setPrecio(1950.0);

        PlatoDto plato3 = new PlatoDto();
        plato3.setNombre("Hamburguesa");
        plato3.setPrecio(1050.0);

        List<PlatoDto> platos = List.of(plato1, plato2,plato3);


        List<PlatoDto> ordenados = pedidoService.ordenarPlatos(platos,"mayorAMenor");

        assertNotNull(ordenados);
        assertEquals(plato2.getPrecio(), ordenados.get(0).getPrecio());

    }

    @Test
    public void dadoQueCadaPlatoTieneUnPrecioLosQuieroOrdenarDemMenorAMayorr(){
        PlatoDto plato1 = new PlatoDto();
        plato1.setNombre("Pollo con arroz");
        plato1.setPrecio(2950.0);

        PlatoDto plato2 = new PlatoDto();
        plato2.setNombre("Pizza");
        plato2.setPrecio(1950.0);

        PlatoDto plato3 = new PlatoDto();
        plato3.setNombre("Hamburguesa");
        plato3.setPrecio(1050.0);

        List<PlatoDto> platos = List.of(plato1, plato2,plato3);


        List<PlatoDto> ordenados = pedidoService.ordenarPlatos(platos,"menorAMayor");

        assertNotNull(ordenados);
        assertEquals(plato3.getPrecio(), ordenados.get(0).getPrecio());
    }


    @Test
    public void dadoQueTengoUnPedidoLeQuieroAgregarUnPlato(){
        EtiquetaDto etiquetaDto = new EtiquetaDto(1, "Proteica");

        PlatoDto platoDto = new PlatoDto();
        platoDto.setId(10);
        platoDto.setNombre("Pollo con arroz");
        platoDto.setPrecio(2950.0);
        platoDto.setDescripcion("Comida rica");
        platoDto.setEtiquetas(List.of(etiquetaDto));
        platoDto.setIdRestaurante(100L);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);

        pedidoService.agregarPlatoAlPedido(platoDto, usuarioDTO);

        verify(repositorioPedido).agregarPlatoAlPedido(argThat(plato -> plato.getNombre().equals("Pollo con arroz") &&
                        plato.getPrecio().equals(2950.0) &&
                        plato.getEtiquetas().size() == 1 &&
                        plato.getEtiquetas().get(0).getNombre().equals("Proteica")),
                eq(1L)
        );
    }

    @Test
    public void dadoQueTengoUnPedidoQuieroMostrarTodosSusPlatos(){
        Long idUsuario = 1L;

        Plato plato1 = new Plato();
        plato1.setId(10);
        plato1.setNombre("Pollo con arroz");
        plato1.setPrecio(1200.0);
        plato1.setEtiquetas(new ArrayList<>());

        Plato plato2 = new Plato();
        plato2.setId(20);
        plato2.setNombre("Carne al horno");
        plato2.setPrecio(1500.0);
        plato2.setEtiquetas(new ArrayList<>());


        PedidoPlato pedidoPlato1 = new PedidoPlato();
        pedidoPlato1.setId(1L);
        pedidoPlato1.setPlato(plato1);

        PedidoPlato pedidoPlato2 = new PedidoPlato();
        pedidoPlato2.setId(2L);
        pedidoPlato2.setPlato(plato2);

        List<PedidoPlato> pedidoPlatosLista = List.of(pedidoPlato1,pedidoPlato2);

        when(repositorioPedido.mostrarPlatosDelPedidoActual(idUsuario)).thenReturn(pedidoPlatosLista);

        List<PedidoPlatoDto> pedioPlatosObtenidos = pedidoService.mostrarPlatosDelPedidoActual(idUsuario);

        assertNotNull(pedioPlatosObtenidos);
        assertEquals(2, pedioPlatosObtenidos.size());
    }

    @Test
    public void dadoQueCadaPlatoTieneUnPrecioQuieroMostrarElPrecioTotalDelPedido() {
        Long idUsuario = 1L;
        Double totalEsperado = 3700.0;

        when(repositorioPedido.mostrarPrecioTotalDelPedidoActual(idUsuario)).thenReturn(totalEsperado);

        Double totalObtenido = pedidoService.mostrarPrecioTotalDelPedidoActual(idUsuario);

        assertEquals(totalEsperado, totalObtenido);
    }


    @Test
    public void dadoQueUnUsuarioTienePedidosLosQuieroMostrarTodosLosPedidosDelUsuario(){
        Long idUsuario = 1L;

        Pedido pedido1 = new Pedido();
        pedido1.setId(1);
        pedido1.setPrecio(1000.0);
        pedido1.setFinalizo(false);
        pedido1.setFecha("2024-06-01");
        pedido1.setPedidoPlatos(new ArrayList<>());

        Pedido pedido2 = new Pedido();
        pedido2.setId(2);
        pedido2.setPrecio(2500.0);
        pedido2.setFinalizo(true);
        pedido2.setFecha("2024-06-02");
        pedido2.setPedidoPlatos(new ArrayList<>());

        List<Pedido> pedidos = List.of(pedido1, pedido2);

        when(repositorioPedido.listarPedidosPorUsuario(idUsuario)).thenReturn(pedidos);


        List<PedidoDto> pedidosObtenidos = pedidoService.listarPedidosPorUsuario(idUsuario);

        assertEquals(2, pedidosObtenidos.size());
    }

    @Test
    public void dadoQueElUsuarioTienePedidosActivosLosQuieroMostrarTodosLosPedidosActivosDelUsuario(){
        Long idUsuario = 1L;

        Pedido pedido1 = new Pedido();
        pedido1.setId(1);
        pedido1.setPrecio(1000.0);
        pedido1.setFecha("2024-06-01");
        pedido1.setPedidoPlatos(new ArrayList<>());
        pedido1.setFinalizo(false);

        Pedido pedido2 = new Pedido();
        pedido2.setId(2);
        pedido2.setPrecio(2500.0);
        pedido2.setFecha("2024-06-02");
        pedido2.setPedidoPlatos(new ArrayList<>());
        pedido2.setFinalizo(true);

        when(repositorioPedido.listarPedidosPorUsuario(idUsuario)).thenReturn(List.of(pedido1, pedido2));

        List<PedidoDto> pedidosObtenidos = pedidoService.listarPedidosActivosPorUsuario(idUsuario);

        assertEquals(1, pedidosObtenidos.size());
    }

    @Test
    public void dadoQueElUsuarioTienePedidosEntregadosLeQuieroMostrarTodosLosPedidosEntregadosDelUsuario(){
        Long idUsuario = 1L;

        Pedido pedido1 = new Pedido();
        pedido1.setId(1);
        pedido1.setPrecio(1000.0);
        pedido1.setFecha("2024-06-01");
        pedido1.setPedidoPlatos(new ArrayList<>());
        pedido1.setFinalizo(true);

        Pedido pedido2 = new Pedido();
        pedido2.setId(2);
        pedido2.setPrecio(2500.0);
        pedido2.setFecha("2024-06-02");
        pedido2.setPedidoPlatos(new ArrayList<>());
        pedido2.setFinalizo(true);

        Pedido pedido3 = new Pedido();
        pedido3.setId(3);
        pedido3.setPrecio(1300.0);
        pedido3.setFecha("2024-04-01");
        pedido3.setPedidoPlatos(new ArrayList<>());
        pedido3.setFinalizo(false);

        when(repositorioPedido.listarPedidosPorUsuario(idUsuario)).thenReturn(List.of(pedido1, pedido2,pedido3));

        List<PedidoDto> pedidosObtenidos = pedidoService.listarPedidosEntregadosPorUsuario(idUsuario);

        assertEquals(2, pedidosObtenidos.size());
    }

    @Test
    public void quieroCrearUnPedido(){
        Long idUsuario = 1L;

        Cliente usuarioMock = new Cliente();
        usuarioMock.setId(idUsuario);
        when(servicioUsuario.buscarPorId(idUsuario)).thenReturn(usuarioMock);

        pedidoService.crearPedido(idUsuario);
        verify(repositorioPedido).crearPedido(argThat(pedido ->
                pedido.getUsuario().equals(usuarioMock) &&
                        pedido.getEstadoPedido() == EstadoPedido.PENDIENTE &&
                        pedido.getPrecio() == 0.0 &&
                        pedido.getPedidoPlatos() != null &&
                        !pedido.isFinalizo()
        ));
    }


    @Test
    public void dadoQueTengoUnPedidoLoQuieroTraerPorId(){
        Integer idPedido = 1;

        Pedido pedido1 = new Pedido();
        pedido1.setId(idPedido);
        pedido1.setPrecio(1000.0);
        pedido1.setFecha("2024-06-01");
        pedido1.setPedidoPlatos(new ArrayList<>());
        pedido1.setFinalizo(true);

        when(repositorioPedido.buscarPorId(idPedido)).thenReturn(pedido1);

        PedidoDto pedidoObtenido = pedidoService.buscarPorId(idPedido);

        assertEquals(pedido1.getId(), pedidoObtenido.getId());
    }








}
