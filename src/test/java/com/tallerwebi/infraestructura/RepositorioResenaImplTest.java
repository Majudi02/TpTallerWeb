package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Cliente;
import com.tallerwebi.dominio.entidades.Resena;
import com.tallerwebi.dominio.entidades.Restaurante;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
public class RepositorioResenaImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioResenaImpl repositorioResena;

    @BeforeEach
    public void setUp() {
        repositorioResena = new RepositorioResenaImpl(sessionFactory);
    }

    @Test
    public void queSePuedaGuardarUnaResena() {
        Cliente cliente = new Cliente();
        sessionFactory.getCurrentSession().save(cliente);

        Restaurante restaurante = new Restaurante();
        sessionFactory.getCurrentSession().save(restaurante);

        Resena resena = new Resena();
        resena.setCliente(cliente);
        resena.setRestaurante(restaurante);
        resena.setComentario("Muy buena comida");

        repositorioResena.guardar(resena);

        Resena resenaGuardada = sessionFactory.getCurrentSession().get(Resena.class, resena.getId());
        assertNotNull(resenaGuardada);
        assertEquals("Muy buena comida", resenaGuardada.getComentario());
    }

    @Test
    public void queSePuedanObtenerResenasPorRestaurante() {
        Restaurante restaurante = new Restaurante();
        sessionFactory.getCurrentSession().save(restaurante);

        Cliente cliente = new Cliente();
        sessionFactory.getCurrentSession().save(cliente);

        Resena resena1 = new Resena();
        resena1.setCliente(cliente);
        resena1.setRestaurante(restaurante);
        resena1.setComentario("Muy buena comida");
        Resena resena2 = new Resena();
        resena2.setCliente(cliente);
        resena2.setRestaurante(restaurante);
        resena2.setComentario("Muy buena comida");
        sessionFactory.getCurrentSession().save(resena1);
        sessionFactory.getCurrentSession().save(resena2);

        List<Resena> resenas = repositorioResena.obtenerResenasPorRestaurante(restaurante.getId());

        assertEquals(2, resenas.size());
    }

    @Test
    public void queSeGuardeUnaResenaEnBaseDeDatos() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Lean");
        sessionFactory.getCurrentSession().save(cliente);

        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("Green Eat");
        sessionFactory.getCurrentSession().save(restaurante);

        Resena resena = new Resena();
        resena.setCliente(cliente);
        resena.setRestaurante(restaurante);
        resena.setComentario("Muy buena comida");
        resena.setFecha(LocalDateTime.now());

        repositorioResena.guardar(resena);

        Resena resenaGuardada = sessionFactory.getCurrentSession().get(Resena.class, resena.getId());
        assertNotNull(resenaGuardada);
        assertEquals("Muy buena comida", resenaGuardada.getComentario());
        assertEquals(cliente.getId(), resenaGuardada.getCliente().getId());
        assertEquals(restaurante.getId(), resenaGuardada.getRestaurante().getId());
    }

}

