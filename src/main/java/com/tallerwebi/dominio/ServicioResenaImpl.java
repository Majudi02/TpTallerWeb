package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Cliente;
import com.tallerwebi.dominio.entidades.Resena;
import com.tallerwebi.dominio.entidades.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ServicioResenaImpl implements ServicioResena {

    private final RepositorioResena repositorioResena;
    private final RepositorioUsuarioNutriya repositorioUsuarioNutriya;
    private final RepositorioUsuarioRestaurante repositorioUsuarioRestaurante;

    @Autowired
    public ServicioResenaImpl(RepositorioResena repositorioResena,
                              RepositorioUsuarioNutriya repositorioUsuarioNutriya,
                              RepositorioUsuarioRestaurante repositorioUsuarioRestaurante) {
        this.repositorioResena = repositorioResena;
        this.repositorioUsuarioNutriya = repositorioUsuarioNutriya;
        this.repositorioUsuarioRestaurante = repositorioUsuarioRestaurante;
    }

    @Override
    public void guardarResena(Long restauranteId, Long clienteId, String comentario, Integer calificacion) {
        Cliente cliente = (Cliente) repositorioUsuarioNutriya.buscarPorId(clienteId);
        Restaurante restaurante = repositorioUsuarioRestaurante.buscarRestaurantePorId(restauranteId);

        Resena resena = new Resena();
        resena.setComentario(comentario);
        resena.setCalificacion(calificacion);
        resena.setCliente(cliente);
        resena.setRestaurante(restaurante);
        resena.setFecha(LocalDateTime.now());

        repositorioResena.guardar(resena);
    }


    @Override
    public List<Resena> obtenerUltimasResenas(Long restauranteId, int cantidad) {
        return repositorioResena.obtenerUltimasPorRestaurante(restauranteId, cantidad);
    }

    @Override
    public List<Resena> obtenerResenasPorRestaurante(Long idRestaurante) {
        return repositorioResena.obtenerResenasPorRestaurante(idRestaurante);
    }

}
