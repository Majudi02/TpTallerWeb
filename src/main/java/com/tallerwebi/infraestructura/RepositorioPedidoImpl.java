    package com.tallerwebi.infraestructura;

    import com.tallerwebi.dominio.PlatoDto;
    import com.tallerwebi.dominio.RepositorioPedido;
    import com.tallerwebi.dominio.Usuario;
    import com.tallerwebi.dominio.entidades.*;
    import com.tallerwebi.presentacion.UsuarioDTO;
    import org.hibernate.SessionFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Repository;

    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.List;

    @Repository("repositorioPedido")
    public class RepositorioPedidoImpl implements RepositorioPedido {

        private SessionFactory sessionFactory;


        @Autowired
        public RepositorioPedidoImpl(SessionFactory sessionFactory){
            this.sessionFactory=sessionFactory;
        }



        @Override
        public Pedido buscarPedidoActivoPorUsuario() {
            String hql = "FROM Pedido p WHERE p.finalizo = false";
            return sessionFactory.getCurrentSession()
                    .createQuery(hql, Pedido.class)
                    .uniqueResult();
        }

        @Override
        public void crearPedido(Pedido pedido) {
            sessionFactory.getCurrentSession().save(pedido);
        }

        @Override
        public void agregarPlatoAlPedido(Plato plato,Long idUsuario){
            Pedido pedidoBuscado = this.buscarPedidoActivoPorUsuario(idUsuario);

            if (pedidoBuscado == null) {
                pedidoBuscado = new Pedido();
                pedidoBuscado.setUsuario(sessionFactory.getCurrentSession().get(UsuarioNutriya.class, idUsuario));
                pedidoBuscado.setPlatos(new ArrayList<>());
                pedidoBuscado.setFinalizo(false);
                pedidoBuscado.setFecha(String.valueOf(LocalDateTime.now()));
                pedidoBuscado.setPrecio(0.0);
                pedidoBuscado.setEstadoPedido(EstadoPedido.PENDIENTE);
            }

            pedidoBuscado.getPlatos().add(plato);
            pedidoBuscado.setPrecio(pedidoBuscado.getPrecio() + plato.getPrecio());

            sessionFactory.getCurrentSession().saveOrUpdate(pedidoBuscado);
        }


        @Override
        public List<Plato> mostrarPlatosDelPedidoActual(Long idUsuario) {
            Pedido pedido = buscarPedidoActivoPorUsuario(idUsuario);
            if (pedido != null) {
                return pedido.getPlatos();
            }
            return new ArrayList<>();
        }



        @Override
        public Double mostrarPrecioTotalDelPedidoActual(Long idUsuario) {
            Pedido pedido = this.buscarPedidoActivoPorUsuario(idUsuario);
            return (pedido != null && pedido.getPrecio() != null) ? pedido.getPrecio() : 0.0;
        }




        @Override
        public Pedido buscarPedidoActivoPorUsuario(Long idUsuario) {
            UsuarioNutriya usuario = sessionFactory.getCurrentSession().get(UsuarioNutriya.class, idUsuario);

            String hql = "FROM Pedido p WHERE p.usuario = :usuario AND p.finalizo = false";
            return sessionFactory.getCurrentSession()
                    .createQuery(hql, Pedido.class)
                    .setParameter("usuario", usuario)
                    .uniqueResult();
        }

        @Override
        public void agregarPlatoAlPedido(Long idUsuario,Plato plato){
            Pedido pedidoBuscado= this.buscarPedidoActivoPorUsuario(idUsuario);
            if (pedidoBuscado!=null){
                pedidoBuscado.getPlatos().add(plato);
                sessionFactory.getCurrentSession().saveOrUpdate(pedidoBuscado);
            }
        }

        @Override
        public void finalizarPedido(Long id) {
            Pedido pedidoBuscado = this.buscarPedidoActivoPorUsuario(id);

            pedidoBuscado.setFinalizo(true);

            sessionFactory.getCurrentSession().saveOrUpdate(pedidoBuscado);
        }




    }
