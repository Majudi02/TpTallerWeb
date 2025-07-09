package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioAdmin;
import com.tallerwebi.dominio.entidades.Admin;
import com.tallerwebi.dominio.entidades.Resena;
import com.tallerwebi.dominio.entidades.Restaurante;
import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.dominio.entidades.UsuarioNutriya;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ControladorAdmin {

    private final ServicioAdmin servicioAdmin;

    @Autowired
    public ControladorAdmin(ServicioAdmin servicioAdmin) {
        this.servicioAdmin = servicioAdmin;
    }

    @GetMapping("/admin/dashboard")
    public String verDashboard(HttpServletRequest request,
                               @RequestParam(name = "fechaDesde", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
                               @RequestParam(name = "fechaHasta", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
                               Model model) {

        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");

        if (usuario == null || !"admin".equalsIgnoreCase(usuario.getTipoUsuario())) {
            return "redirect:/nutriya-login";
        }

        // Definimos fechas para filtrar el gráfico (default últimos 30 días)
        LocalDate filtroDesdeGrafico = (fechaDesde != null) ? fechaDesde : LocalDate.now().minusDays(30);
        LocalDate filtroHastaGrafico = (fechaHasta != null) ? fechaHasta : LocalDate.now();

        // Pedidos por fecha para gráfico
        Map<LocalDate, Integer> pedidosPorFecha = servicioAdmin.obtenerCantidadPedidosPorFecha(filtroDesdeGrafico, filtroHastaGrafico);
        List<String> fechasPedidos = pedidosPorFecha.keySet().stream()
                .map(LocalDate::toString)
                .collect(Collectors.toList());

        List<Integer> cantidadPedidosPorFecha = new ArrayList<>(pedidosPorFecha.values());

        // Totales filtrados (si filtroDesdeTotales es null, mostrar todos)
        Integer totalPedidos = servicioAdmin.obtenerTotalPedidosFiltrado(fechaDesde, fechaHasta);
        Double totalFacturado = servicioAdmin.obtenerTotalFacturadoFiltrado(fechaDesde, fechaHasta);

        // Otros datos no filtrados (o podrías también filtrar si querés)
        List<PlatoDto> topPlatos = servicioAdmin.obtenerTopPlatosMasVendidos(5);
        Map<Integer, Integer> cantidadPorPlato = servicioAdmin.obtenerCantidadPorPlato();
        List<Restaurante> topRestaurantes = servicioAdmin.obtenerTopRestaurantesPorCantidadDePedidos(3);
        List<Resena> ultimasResenas = servicioAdmin.obtenerUltimasResenas(5);
        Map<Long, Integer> cantidadPedidosPorRestaurante = servicioAdmin.obtenerCantidadPedidosPorRestaurante();
        Double promedioFacturacionDiaria = servicioAdmin.obtenerPromedioFacturacionDiaria(fechaDesde, fechaHasta);

        // Enviar todo al modelo
        model.addAttribute("fechasPedidos", fechasPedidos);
        model.addAttribute("cantidadPedidosPorFecha", cantidadPedidosPorFecha);
        model.addAttribute("totalPedidos", totalPedidos);
        model.addAttribute("totalFacturado", totalFacturado);
        model.addAttribute("topPlatos", topPlatos);
        model.addAttribute("cantidadPorPlato", cantidadPorPlato);
        model.addAttribute("topRestaurantes", topRestaurantes);
        model.addAttribute("ultimasResenas", ultimasResenas);
        model.addAttribute("cantidadPedidosPorRestaurante", cantidadPedidosPorRestaurante);
        model.addAttribute("promedioFacturacionDiaria", promedioFacturacionDiaria);
        model.addAttribute("fechaDesde", fechaDesde);
        model.addAttribute("fechaHasta", fechaHasta);

        return "dashboard";
    }

}
