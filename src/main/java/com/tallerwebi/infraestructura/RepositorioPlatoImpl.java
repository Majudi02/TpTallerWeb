    package com.tallerwebi.infraestructura;


    import com.tallerwebi.dominio.RepositorioPlato;
    import com.tallerwebi.dominio.entidades.Plato;
    import org.hibernate.SessionFactory;
    import org.springframework.stereotype.Repository;

    import java.util.List;

    @Repository("repositorioPlato")
    public class RepositorioPlatoImpl implements RepositorioPlato {

        private SessionFactory sessionFactory;

        public RepositorioPlatoImpl(SessionFactory sessionFactory){this.sessionFactory = sessionFactory;}

        @Override
        public Boolean crearPlato(Plato plato) {
            this.sessionFactory.getCurrentSession().save(plato);
            return true;
        }

        @Override
        public Plato buscarPlatoPorId(Integer id) {

            return sessionFactory.getCurrentSession().get(Plato.class,id);
        }

        @Override
        public List<Plato> buscarPlatosPorTipoComida(String tipoComida) {
            return sessionFactory.getCurrentSession()
                    .createQuery("SELECT p FROM Plato p JOIN p.etiquetas e WHERE e.nombre = :nombreEtiqueta", Plato.class)
                    .setParameter("nombreEtiqueta", tipoComida)
                    .getResultList();
        }

        @Override
        public List<Plato> traerTodosLosPlatos() {
            return sessionFactory.getCurrentSession()
                    .createQuery("FROM Plato", Plato.class)
                    .getResultList();
        }

        @Override
        public Boolean editarEtiquetas(Plato platoConEtiquetasActualizadas) {
            Plato plato = sessionFactory.getCurrentSession().get(Plato.class, platoConEtiquetasActualizadas.getId());
            if (plato == null) {
                return false;
            }

            plato.setEtiquetas(platoConEtiquetasActualizadas.getEtiquetas());
            return true;
        }

        @Override
        public Boolean actualizarPlato(com.tallerwebi.dominio.entidades.Plato plato) {
            this.sessionFactory.getCurrentSession().update(plato);
            return true;
        }

        @Override
        public List<Plato> buscarPlatosPorEtiquetasDelCliente(Long idCliente) {
            String hql = "select distinct p from Plato p join p.etiquetas e " +
                    "where e.id in (" +
                    "   select ce.id from Cliente c join c.etiquetas ce where c.id = :idCliente" +
                    ")";
            return sessionFactory.getCurrentSession()
                    .createQuery(hql, Plato.class)
                    .setParameter("idCliente", idCliente)
                    .setMaxResults(4)
                    .getResultList();
        }

        @Override
        public void actualizarPrecioConDescuento(Integer id, Double nuevoPrecioConDescuento) {
            String hql = "UPDATE Plato SET precioConDescuento = :precio WHERE id = :id";
            sessionFactory.getCurrentSession()
                    .createQuery(hql)
                    .setParameter("precio", nuevoPrecioConDescuento)
                    .setParameter("id", id)
                    .executeUpdate();
        }

        @Override
        public void quitarDescuento(Integer idPlato) {
            String hql = "UPDATE Plato SET precioConDescuento = null WHERE id = :id";
            sessionFactory.getCurrentSession()
                    .createQuery(hql)
                    .setParameter("id", idPlato)
                    .executeUpdate();
        }

        @Override
        public List<Plato> buscarPlatosEnPromocion() {
            String hql = "SELECT p FROM Plato p WHERE p.precioConDescuento IS NOT NULL";
             return sessionFactory.getCurrentSession()
                    .createQuery(hql, Plato.class)
                    .setMaxResults(4)
                     .getResultList();
        }


    }
