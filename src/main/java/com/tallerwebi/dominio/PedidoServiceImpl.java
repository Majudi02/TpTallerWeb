package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Etiqueta;
import com.tallerwebi.dominio.entidades.Pedido;
import com.tallerwebi.dominio.entidades.Plato;
import com.tallerwebi.dominio.entidades.Restaurante;
import com.tallerwebi.infraestructura.RepositorioPlatoImpl;
import com.tallerwebi.presentacion.PedidoDto;
import com.tallerwebi.presentacion.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {
    private final RepositorioPlatoImpl repositorioPlatoImpl;
    private RepositorioPedido repositorioPedido;

    @Autowired
    public PedidoServiceImpl(RepositorioPlatoImpl repositorioPlatoImpl,RepositorioPedido repositorioPedido) {
        this.repositorioPlatoImpl = repositorioPlatoImpl;
        this.repositorioPedido=repositorioPedido;
    }

    List<Restaurante> restaurantes = List.of(
            new Restaurante("Green Bowl", "Comida Vegana", "/assets/restaurante.png", "calle", 123, "Don Torcuato", "Norte", List.of("Vegana")),
            new Restaurante("Natural Express", "Comida Vegana", "/assets/restaurante.png", "calle", 321, "La Matanza", "Oeste", List.of("Proteica")),
            new Restaurante("Vital Food", "Comida Proteica", "/assets/restaurante.png", "calle", 213, "La Matanza", "Oeste", List.of("Vegana", "Proteica")),
            new Restaurante("La Parrilla del Sur", "Especialidad en carnes a la parrilla", "/assets/restaurante-logo.png", "Av. Corrientes", 1234, "Buenos Aires", "Microcentro", List.of("Proteica")),
            new Restaurante("Sushi Zen", "Lo mejor de la cocina japonesa", "/assets/restaurante-logo.png", "Calle Defensa", 567, "Buenos Aires", "San Telmo", List.of("Proteica", "Sin gluten")),
            new Restaurante("Pizza Napoli", "Pizzas artesanales al horno de leña", "/assets/restaurante-logo.png", "Av. Santa Fe", 890, "Buenos Aires", "Recoleta", List.of("Opciones vegetarianas")),
            new Restaurante("Verde Vivo", "Comida saludable y vegana", "/assets/restaurante-logo.png", "Calle Mendoza", 234, "Mendoza", "Centro", List.of("Vegana", "Vegetariana", "Sin gluten"))
    );


    @Override
    public List<Restaurante> traerRestaurantesDestacados() {
        return this.restaurantes;
    }

    @Override
    public List<PlatoDto> traerPlatosDestacados() {

        return null;
    }

    @Override
    @Transactional
    public List<PlatoDto> traerTodosLosPlatos() {
        List<Plato> platos = this.repositorioPlatoImpl.traerTodosLosPlatos();
        return platos.stream().map(Plato::obtenerDto).collect(Collectors.toList());
    }

    @Override

    public List<PlatoDto> buscarPlatosPorTipoComida(String tipoComida) {
        List<Plato> platos = this.repositorioPlatoImpl.buscarPlatosPorTipoComida(tipoComida);
        return platos.stream().map(Plato::obtenerDto).collect(Collectors.toList());
    }

    public List<PlatoDto> ordenarPlatos(List<PlatoDto> platos, String tipoOrdenar) {
        List<PlatoDto> platosOrdenados = new ArrayList<>(platos);
        if (tipoOrdenar.equals("mayorAMenor")) {
            platosOrdenados.sort(Comparator.comparing(PlatoDto::getPrecio).reversed());
        } else {
            platosOrdenados.sort(Comparator.comparing(PlatoDto::getPrecio));
        }
        return platosOrdenados;
    }


    @Override
    public PedidoDto buscarPedidoActivoPorUsuario() {
        Pedido pedido= this.repositorioPedido.buscarPedidoActivoPorUsuario();
        return  pedido.obtenerDto();
    }

    /*
    @Override
    public void crearPedido(PedidoDto pedido) {

        Pedido pedidoEntidad = pedido.obtenerEntidad(pedido.obtenerPlatosEntidad());
        this.repositorioPedido.crearPedido(pedidoEntidad);
    }

     */

    @Override
    public void agregarPlatoAlPedido(PlatoDto platoDto, UsuarioDTO usuarioDTO) {

        List<Etiqueta> etiquetasEntidad = new ArrayList<>();
        if (platoDto.getEtiquetas() != null) {
            for (EtiquetaDto etiquetaDto : platoDto.getEtiquetas()) {
                Etiqueta etiqueta = new Etiqueta();
                etiqueta.setId(etiquetaDto.getId());
                etiqueta.setNombre(etiquetaDto.getNombre());
                etiquetasEntidad.add(etiqueta);
            }
        }


        Plato platoEntidad = platoDto.obtenerEntidad(etiquetasEntidad);

        this.repositorioPedido.agregarPlatoAlPedido(platoEntidad,usuarioDTO.getId());
    }

    @Override
    public List<PlatoDto> mostrarPlatosDelPedidoActual(Long idUsuario) {

        return this.repositorioPedido.mostrarPlatosDelPedidoActual(idUsuario).stream().map(Plato::obtenerDto).collect(Collectors.toList());
    }


    @Override
    public Double mostrarPrecioTotalDelPedidoActual(Long idUsuario) {
        return this.repositorioPedido.mostrarPrecioTotalDelPedidoActual(idUsuario);
    }

    @Override
    public void finalizarPedido(Long id) {
        this.repositorioPedido.finalizarPedido(id);
    }





/*        @Override
        public void guardarPlatoDb(Plato plato) {

        }

 */


}
