package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.BusquedaResultadoDto;
import com.tallerwebi.dominio.ServicioBusqueda;
import com.tallerwebi.dominio.SugerenciaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ControladorBusquedaTest {

    private ControladorBusqueda controladorBusqueda;
    private ServicioBusqueda servicioBusquedaMock;
    private Model modelMock;
    private HttpServletRequest httpServletRequestMock;

    @BeforeEach
    public void setUp() {
        servicioBusquedaMock = mock(ServicioBusqueda.class);
        controladorBusqueda = new ControladorBusqueda(servicioBusquedaMock);
        modelMock = mock(Model.class);
        httpServletRequestMock = mock(HttpServletRequest.class);

        HttpSession sessionMock = mock(HttpSession.class);
        when(httpServletRequestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(null);
    }


    @Test
    public void buscarConTextoVacioDevuelveElResultadoDeBusquedaConMensajeYResultadoNulo() {
        String vista = controladorBusqueda.buscar(null, httpServletRequestMock, modelMock);

        assertThat(vista, equalToIgnoringCase("resultado-busqueda"));
        verify(modelMock).addAttribute("mensaje", "Por favor ingresa un texto para buscar.");
        verify(modelMock).addAttribute("resultado", null);
        verify(modelMock, never()).addAttribute(eq("textoBusqueda"), any());
    }

    @Test
    public void buscarConTextoValidoAgregaYMuestraElResultadoYTextoBusqueda() {
        BusquedaResultadoDto resultadoMock = new BusquedaResultadoDto(new ArrayList<>(), new ArrayList<>());
        when(servicioBusquedaMock.buscarRestaurantesYPlatos("pizza")).thenReturn(resultadoMock);

        String vista = controladorBusqueda.buscar("pizza", httpServletRequestMock, modelMock);

        assertThat(vista, equalToIgnoringCase("resultado-busqueda"));
        verify(modelMock).addAttribute("resultado", resultadoMock);
        verify(modelMock).addAttribute("textoBusqueda", "pizza");
        verify(modelMock, never()).addAttribute(eq("mensaje"), any());
    }

    @Test
    public void sugerenciasDevuelveListaDeSugerencias() {
        SugerenciaDto sugerencia = new SugerenciaDto("Sushi Roll", "restaurante", 1L);
        List<SugerenciaDto> lista = new ArrayList<>();
        lista.add(sugerencia);

        when(servicioBusquedaMock.obtenerSugerencias("sus")).thenReturn(lista);

        List<SugerenciaDto> resultado = controladorBusqueda.sugerencias("sus");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Sushi Roll", resultado.get(0).getTexto());
    }

}
