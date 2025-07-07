package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioResena;
import com.tallerwebi.dominio.entidades.Resena;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("repositorioResena")
public class RepositorioResenaImpl implements RepositorioResena {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioResenaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Resena resena) {
        sessionFactory.getCurrentSession().save(resena);
    }

    @Override
    public List<Resena> obtenerUltimasPorRestaurante(Long restauranteId, int cantidad) {
        return sessionFactory.getCurrentSession().createQuery(
                        "FROM Resena r WHERE r.restaurante.id = :id ORDER BY r.fecha DESC", Resena.class)
                .setParameter("id", restauranteId)
                .setMaxResults(cantidad)
                .list();
    }

    @Override
    public List<Resena> obtenerResenasPorRestaurante(Long restauranteId) {
        String hql = "FROM Resena r WHERE r.restaurante.id = :restauranteId ORDER BY r.fecha DESC";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Resena.class)
                .setParameter("restauranteId", restauranteId)
                .getResultList();
    }

    @Override
    public Map<Integer, Double> calcularPromedioCalificacionPorPlato(Long idRestaurante) {
        String hql = "SELECT pp.plato.id, AVG(r.calificacion) " +
                "FROM Resena r " +
                "JOIN r.restaurante rest " +
                "JOIN Pedido p ON p.restaurante.id = rest.id " +
                "JOIN PedidoPlato pp ON pp.pedido.id = p.id " +
                "WHERE rest.id = :idRestaurante AND r.calificacion IS NOT NULL " +
                "GROUP BY pp.plato.id";

        List<Object[]> resultados = sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("idRestaurante", idRestaurante)
                .getResultList();

        Map<Integer, Double> promedios = new HashMap<>();
        for (Object[] row : resultados) {
            Integer platoId = (Integer) row[0];
            Double promedio = (Double) row[1];
            promedios.put(platoId, promedio);
        }

        return promedios;
    }

}

