package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Etiqueta;
import com.tallerwebi.dominio.entidades.Plato;
import com.tallerwebi.dominio.entidades.Restaurante;
import com.tallerwebi.dominio.entidades.UsuarioRestaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServicioRestauranteImpl implements ServicioRestaurante {
    /*
    private static final List<Restaurante> restaurantesVista = List.of(
            new Restaurante("Green Bowl", "Comida Vegana", "/assets/restaurante.png", "calle", 123, "Don Torcuato", "Norte", List.of("Vegana")),
            new Restaurante("Natural Express", "Comida Vegana", "/assets/restaurante.png", "calle", 321, "La Matanza", "Oeste", List.of("Proteica")),
            new Restaurante("Vital Food", "Comida Proteica", "/assets/restaurante.png", "calle", 213, "La Matanza", "Oeste", List.of("Vegana", "Proteica")),
            new Restaurante("La Parrilla del Sur", "Especialidad en carnes a la parrilla", "/assets/restaurante-logo.png", "Av. Corrientes", 1234, "Buenos Aires", "Microcentro", List.of("Proteica")),
            new Restaurante("Sushi Zen", "Lo mejor de la cocina japonesa", "/assets/restaurante-logo.png", "Calle Defensa", 567, "Buenos Aires", "San Telmo", List.of("Proteica", "Sin gluten")),
            new Restaurante("Pizza Napoli", "Pizzas artesanales al horno de leña", "/assets/restaurante-logo.png", "Av. Santa Fe", 890, "Buenos Aires", "Recoleta", List.of("Opciones vegetarianas")),
            new Restaurante("Verde Vivo", "Comida saludable y vegana", "/assets/restaurante-logo.png", "Calle Mendoza", 234, "Mendoza", "Centro", List.of("Vegana", "Vegetariana", "Sin gluten"))
    );
    */
    private final RepositorioUsuarioRestaurante repositorioUsuarioRestaurante;
    private final RepositorioPlato repositorioPlato;
    private EtiquetaService etiquetaService;



    @Autowired
    public ServicioRestauranteImpl(RepositorioUsuarioRestaurante repositorioUsuarioRestaurante, RepositorioPlato repositorioPlato, EtiquetaService etiquetaService) {
        this.repositorioUsuarioRestaurante = repositorioUsuarioRestaurante;
        this.repositorioPlato = repositorioPlato;
        this.etiquetaService = etiquetaService;
    }

    @Override
    public void inicializarDatos() {
        if (repositorioUsuarioRestaurante.buscarTodosLosRestaurantes().isEmpty()) {

            List<Restaurante> restaurantesVista = List.of(
                    new Restaurante("Green Bowl", "Comida Vegana", "/assets/restaurante.png", "calle", 123, "Don Torcuato", "Norte", List.of("Vegana")),
                    new Restaurante("Natural Express", "Comida Vegana", "/assets/restaurante.png", "calle", 321, "La Matanza", "Oeste", List.of("Proteica")),
                    new Restaurante("Vital Food", "Comida Proteica", "/assets/restaurante.png", "calle", 213, "La Matanza", "Oeste", List.of("Vegana", "Proteica")),
                    new Restaurante("La Parrilla del Sur", "Especialidad en carnes a la parrilla", "/assets/restaurante-logo.png", "Av. Corrientes", 1234, "Buenos Aires", "Microcentro", List.of("Proteica")),
                    new Restaurante("Sushi Zen", "Lo mejor de la cocina japonesa", "/assets/restaurante-logo.png", "Calle Defensa", 567, "Buenos Aires", "San Telmo", List.of("Proteica", "Sin gluten")),
                    new Restaurante("Pizza Napoli", "Pizzas artesanales al horno de leña", "/assets/restaurante-logo.png", "Av. Santa Fe", 890, "Buenos Aires", "Recoleta", List.of("Opciones vegetarianas")),
                    new Restaurante("Verde Vivo", "Comida saludable y vegana", "/assets/restaurante-logo.png", "Calle Mendoza", 234, "Mendoza", "Centro", List.of("Vegana", "Vegetariana", "Sin gluten"))
            );

            for (int i = 0; i < restaurantesVista.size(); i++) {
                UsuarioRestaurante usuario = new UsuarioRestaurante();
                usuario.setEmail("email" + (i + 1) + "@email.com");
                usuario.setRestaurante(restaurantesVista.get(i));
                repositorioUsuarioRestaurante.guardar(usuario);
            }
        }
    }


    @Override
    public Restaurante obtenerRestaurante(String nombre) {
        List<Restaurante> restaurantes = repositorioUsuarioRestaurante.buscarTodosLosRestaurantes();
        for (Restaurante rest : restaurantes) {
            if (rest.getNombre().equalsIgnoreCase(nombre)) {
                return rest;
            }
        }
        return null;
    }

    @Override
    public List<Restaurante> traerRestaurantesDestacados() {
        return repositorioUsuarioRestaurante.traerRestaurantesDestacados();
    }

    @Override
    public Restaurante obtenerRestaurantePorId(Long idRestaurante) {
        List<Restaurante> restaurantes = repositorioUsuarioRestaurante.buscarTodosLosRestaurantes();
        for (Restaurante rest : restaurantes) {
            if (Objects.equals(rest.getId(), idRestaurante)) {
                return rest;
            }
        }
        return null;
    }

    @Override
    public List<Restaurante> obtenerRestaurantes() {
        return repositorioUsuarioRestaurante.buscarTodosLosRestaurantes();
    }

    @Override
    public List<Restaurante> obtenerRestaurantesPorZona(String zona) {
        List<Restaurante> restaurantes = repositorioUsuarioRestaurante.buscarTodosLosRestaurantes();
        List<Restaurante> restaurantesObtenidos = new ArrayList<>();
        for (Restaurante rest : restaurantes) {
            if (rest.getZona().equalsIgnoreCase(zona)) {
                restaurantesObtenidos.add(rest);
            }
        }
        return restaurantesObtenidos;
    }

    @Override
    public List<Restaurante> buscarPorTipoComida(String tipoComida) {
        List<Restaurante> restaurantes = repositorioUsuarioRestaurante.buscarTodosLosRestaurantes();
        List<Restaurante> restaurantesObtenidos = new ArrayList<>();
        for (Restaurante r : restaurantes) {
            if (r.getTiposComida().contains(tipoComida)) {
                restaurantesObtenidos.add(r);
            }
        }
        return restaurantesObtenidos;
    }

    @Override
    public List<Restaurante> buscarPorTipoComidaYZona(String zona, String tipoComida) {
        List<Restaurante> restaurantes = repositorioUsuarioRestaurante.buscarTodosLosRestaurantes();
        List<Restaurante> resultados = new ArrayList<>();
        for (Restaurante rest : restaurantes) {
            boolean coincideZona = (zona == null || zona.isEmpty()) || rest.getZona().equalsIgnoreCase(zona);

            boolean coincideTipo = false;
            if (tipoComida == null || tipoComida.isEmpty()) {
                coincideTipo = true;
            } else {
                for (String tipo : rest.getTiposComida()) {
                    if (tipo.equalsIgnoreCase(tipoComida)) {
                        coincideTipo = true;
                        break;
                    }
                }
            }

            if (coincideZona && coincideTipo) {
                resultados.add(rest);
            }
        }
        return resultados;
    }

    @Override
    @Transactional
    public Boolean guardarPlato(PlatoDto platoDto) {
        List<Etiqueta> etiquetas = new ArrayList<>();
        if (platoDto.getEtiquetas() != null) {
            for (EtiquetaDto etiquetaDto : platoDto.getEtiquetas()) {
                Etiqueta etiqueta = new Etiqueta();
                etiqueta.setId(etiquetaDto.getId());
                etiqueta.setNombre(etiquetaDto.getNombre());
                etiquetas.add(etiqueta);
            }
        }

        Plato plato = platoDto.obtenerEntidad(etiquetas);
        Restaurante restaurante = this.obtenerRestaurantePorUsuarioId(platoDto.getIdRestaurante());
        plato.setRestaurante(restaurante);

        return this.repositorioPlato.crearPlato(plato);
    }

    @Override
    public Boolean actualizarPlato(PlatoDto platoDto) {
        Plato platoExistente = this.repositorioPlato.buscarPlatoPorId(platoDto.getId());
        if (platoExistente == null) {
            return false;
        }

        if (platoDto.getNombre() != null && !platoDto.getNombre().trim().isEmpty()) {
            platoExistente.setNombre(platoDto.getNombre());
        }

        if (platoDto.getDescripcion() != null && !platoDto.getDescripcion().trim().isEmpty()) {
            platoExistente.setDescripcion(platoDto.getDescripcion());
        }

        if (platoDto.getPrecio() != null) {
            platoExistente.setPrecio(platoDto.getPrecio());
        }
        if (platoDto.getEtiquetasIds() != null && !platoDto.getEtiquetasIds().isEmpty()) {
            List<Etiqueta> etiquetas = new ArrayList<>();
            for (Integer idEtiqueta : platoDto.getEtiquetasIds()) {
                EtiquetaDto etiqueta = etiquetaService.buscarEtiquetaPorId(idEtiqueta);
                if (etiqueta != null) {
                    etiquetas.add(etiqueta.obtenerEntidad());
                }
            }
            platoExistente.setEtiquetas(etiquetas);

        }

        if (platoDto.getImagen() != null && !platoDto.getImagen().trim().isEmpty()) {
            platoExistente.setImagen(platoDto.getImagen());
        }

        return this.repositorioPlato.actualizarPlato(platoExistente);
    }

    @Override
    public PlatoDto obtenerPlatoPorId(Integer id) {
        Plato plato = this.repositorioPlato.buscarPlatoPorId(id);
        return plato.obtenerDto();
    }

    @Override
    public List<PlatoDto> traerTodosLosPlatos() {
        List<Plato> platos = this.repositorioPlato.traerTodosLosPlatos();
        return platos.stream().map(Plato::obtenerDto).collect(Collectors.toList());
    }

    @Override
    public void guardarImagen(PlatoDto platoDto, MultipartFile imagen) {
        if (!imagen.isEmpty()) {
            try {
                String rutaProyecto = System.getProperty("user.dir");
                String rutaBase = rutaProyecto + "/src/main/webapp/resources/assets/imagenesPlatos/";
                Files.createDirectories(Paths.get(rutaBase));

                String extension = imagen.getOriginalFilename().substring(imagen.getOriginalFilename().lastIndexOf("."));
                String nombreArchivo = UUID.randomUUID() + extension;
                Path rutaDestino = Paths.get(rutaBase, nombreArchivo);

                Files.copy(imagen.getInputStream(), rutaDestino);

                platoDto.setImagen("/assets/imagenesPlatos/" + nombreArchivo);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<PlatoDto> obtenerPlatosDelRestaurante(Long idRestaurante) {
        Restaurante restaurante = obtenerRestaurantePorId(idRestaurante);

        List<PlatoDto> platosObtenidos = new ArrayList<>();
        List<Plato> platos = repositorioPlato.traerTodosLosPlatos();

        for (Plato plato : platos) {
            if (plato.getRestaurante() != null && plato.getRestaurante().equals(restaurante)) {
                PlatoDto platoDto = plato.obtenerDto();
                platosObtenidos.add(platoDto);
            }
        }

        return platosObtenidos;
    }

    @Override
    public Restaurante obtenerRestaurantePorUsuarioId(Long usuarioId) {
        UsuarioRestaurante usuarioRestaurante = repositorioUsuarioRestaurante.buscarPorUsuarioId(usuarioId);
        return usuarioRestaurante.getRestaurante();
    }

}


