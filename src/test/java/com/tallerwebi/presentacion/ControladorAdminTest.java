package com.tallerwebi.presentacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tallerwebi.dominio.ServicioAdmin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ControladorAdminTest {

    private ServicioAdmin servicioAdminMock;
    private ControladorAdmin controlador;
    private MockMvc mockMvc;
    private MockHttpSession session;

    @BeforeEach
    public void setUp() {
        servicioAdminMock = mock(ServicioAdmin.class);
        controlador = new ControladorAdmin(servicioAdminMock);
        mockMvc = MockMvcBuilders.standaloneSetup(controlador).build();

        session = new MockHttpSession();
    }

    @Test
    public void cuandoNoHayUsuarioSesionRedirigeALogin() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(redirectedUrl("/nutriya-login"));
    }

    @Test
    public void cuandoUsuarioNoEsAdminRedirigeALogin() throws Exception {
        UsuarioDTO usuario = mock(UsuarioDTO.class);
        when(usuario.getTipoUsuario()).thenReturn("cliente");
        session.setAttribute("usuario", usuario);

        mockMvc.perform(get("/admin/dashboard").session(session))
                .andExpect(redirectedUrl("/nutriya-login"));
    }

    @Test
    void puedoIrAlDashboardSiSoyAdmin() {
        UsuarioDTO usuario = mock(UsuarioDTO.class);
        when(usuario.getTipoUsuario()).thenReturn("admin");

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("usuario")).thenReturn(usuario);

        when(servicioAdminMock.obtenerCantidadPedidosPorFecha(any(), any())).thenReturn(Map.of());
        when(servicioAdminMock.obtenerTotalPedidosFiltrado(any(), any())).thenReturn(10);
        when(servicioAdminMock.obtenerTotalFacturadoFiltrado(any(), any())).thenReturn(1000.0);
        when(servicioAdminMock.obtenerTopPlatosMasVendidos(anyInt())).thenReturn(List.of());
        when(servicioAdminMock.obtenerCantidadPorPlato()).thenReturn(Map.of());
        when(servicioAdminMock.obtenerTopRestaurantesPorCantidadDePedidos(anyInt())).thenReturn(List.of());
        when(servicioAdminMock.obtenerUltimasResenas(anyInt())).thenReturn(List.of());
        when(servicioAdminMock.obtenerCantidadPedidosPorRestaurante()).thenReturn(Map.of());
        when(servicioAdminMock.obtenerPromedioFacturacionDiaria(any(), any())).thenReturn(100.0);

        Model model = new ConcurrentModel();

        LocalDate fechaDesde = LocalDate.of(2025, 1, 1);
        LocalDate fechaHasta = LocalDate.of(2025, 7, 1);

        String viewName = controlador.verDashboard(
                mockRequest,
                fechaDesde,
                fechaHasta,
                model
        );
 
        assertEquals("dashboard", viewName);
        assertTrue(model.containsAttribute("fechasPedidos"));
        assertTrue(model.containsAttribute("cantidadPedidosPorFecha"));
        assertTrue(model.containsAttribute("totalPedidos"));
        assertTrue(model.containsAttribute("totalFacturado"));
        assertTrue(model.containsAttribute("topPlatos"));
        assertTrue(model.containsAttribute("cantidadPorPlato"));
        assertTrue(model.containsAttribute("topRestaurantes"));
        assertTrue(model.containsAttribute("ultimasResenas"));
        assertTrue(model.containsAttribute("cantidadPedidosPorRestaurante"));
        assertTrue(model.containsAttribute("promedioFacturacionDiaria"));
        assertTrue(model.containsAttribute("fechaDesde"));
        assertTrue(model.containsAttribute("fechaHasta"));
    }
}
