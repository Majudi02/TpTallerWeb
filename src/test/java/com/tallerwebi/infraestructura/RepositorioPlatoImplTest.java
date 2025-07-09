package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioPlato;
import com.tallerwebi.dominio.entidades.Cliente;
import com.tallerwebi.dominio.entidades.Etiqueta;
import com.tallerwebi.dominio.entidades.Plato;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
public class RepositorioPlatoImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioPlato repositorio;

    @BeforeEach
    public void setUp(){
        repositorio=new RepositorioPlatoImpl(sessionFactory);
    }

    @Test
    public void dadoQueTengoUnPlatoLoQuieroGuardarEnLaBaseDeDatos(){
        Plato plato = new Plato();
        plato.setNombre("Milanesa");
        plato.setPrecio(80.0);
        repositorio.crearPlato(plato);
        Plato platoRecuperado = sessionFactory.getCurrentSession().get(Plato.class, plato.getId());
        assert(platoRecuperado.getNombre().equals("Milanesa"));
    }

    @Test
    public void dadoQueTengoUnPlatoLoQuieroBuscarPorSuId(){
        Plato plato = new Plato();
        plato.setNombre("Milanesa");
        plato.setPrecio(80.0);
        repositorio.crearPlato(plato);

        Plato platoBuscado = repositorio.buscarPlatoPorId(plato.getId());

        assert(platoBuscado.getNombre().equals("Milanesa"));
    }

    @Test
    public void dadoQueTengoPlatosLosQuieroBuscarPorSuTipoDeComida(){
        Etiqueta etiqueta = new Etiqueta();
        etiqueta.setNombre("Proteica");
        sessionFactory.getCurrentSession().save(etiqueta);

        Plato plato = new Plato();
        plato.setNombre("Pollo con arroz");
        plato.setPrecio(1200.0);
        plato.setEtiquetas(List.of(etiqueta));

        repositorio.crearPlato(plato);

        var platosEncontrados = repositorio.buscarPlatosPorTipoComida("Proteica");

        assertTrue(platosEncontrados.stream().anyMatch(p -> p.getNombre().equals("Pollo con arroz")));
    }

    @Test
    public void dadoQueUnPlatoTieneEtiquetasSeLasQuieroModificar(){
        Etiqueta etiquetaOriginal = new Etiqueta();
        etiquetaOriginal.setNombre("Proteica");
        sessionFactory.getCurrentSession().save(etiquetaOriginal);

        Plato plato = new Plato();
        plato.setNombre("Pollo con arroz");
        plato.setPrecio(1200.0);
        plato.setEtiquetas(List.of(etiquetaOriginal));
        repositorio.crearPlato(plato);


        Etiqueta nuevaEtiqueta1 = new Etiqueta();
        nuevaEtiqueta1.setNombre("Vegana");
        sessionFactory.getCurrentSession().save(nuevaEtiqueta1);

        Etiqueta nuevaEtiqueta2 = new Etiqueta();
        nuevaEtiqueta2.setNombre("Baja en sodio");
        sessionFactory.getCurrentSession().save(nuevaEtiqueta2);

        Plato platoActualizado = new Plato();
        platoActualizado.setId(plato.getId());
        platoActualizado.setEtiquetas(List.of(nuevaEtiqueta1, nuevaEtiqueta2));

        Boolean resultado = ((RepositorioPlatoImpl) repositorio).editarEtiquetas(platoActualizado);

        Plato platoModificado = repositorio.buscarPlatoPorId(platoActualizado.getId());

        assertTrue(resultado);
        assertEquals(2, platoModificado.getEtiquetas().size());
    }

    @Test
    public void dadoQueTengoPlatosConEtiquetasQuieroPoderBuscarPlatoPorLaEtiquetaDelCiente(){
        Etiqueta etiqueta1 = new Etiqueta();
        etiqueta1.setNombre("Proteica");
        sessionFactory.getCurrentSession().save(etiqueta1);

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setEtiquetas(List.of(etiqueta1));
        sessionFactory.getCurrentSession().save(cliente);

        Plato plato1 = new Plato();
        plato1.setNombre("Pollo con arroz");
        plato1.setPrecio(1200.0);
        plato1.setEtiquetas(List.of(etiqueta1));
        repositorio.crearPlato(plato1);

        Plato plato2 = new Plato();
        plato2.setNombre("Pollo con carne");
        plato2.setPrecio(1500.0);
        plato2.setEtiquetas(List.of(etiqueta1));
        repositorio.crearPlato(plato2);

        List<Plato> platosObtenidos = repositorio.buscarPlatosPorEtiquetasDelCliente(cliente.getId());

        assertEquals(2, platosObtenidos.size());
    }

    @Test
    public void dadoQueQuieroHacerUnDescuentoAUnPlatoLeQuieroActualizarElPrecioConDescuento(){
        Plato plato = new Plato();
        plato.setNombre("Milanesa");
        plato.setPrecio(80.0);
        repositorio.crearPlato(plato);

        repositorio.actualizarPrecioConDescuento(plato.getId(), 20.0);

        //Limpio la sesión para asegurar que no esté cacheado el objeto viejo
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();

        Plato platoRecuperado = repositorio.buscarPlatoPorId(plato.getId());

        assertEquals(20.0, platoRecuperado.getPrecioConDescuento());
    }

    @Test
    public void dadoQueTengoUnPlatoConDescuentoLeQuieroSacarElDescuento(){
        Plato plato = new Plato();
        plato.setNombre("Milanesa");
        plato.setPrecio(80.0);
        plato.setPrecioConDescuento(20.0);
        repositorio.crearPlato(plato);

        repositorio.quitarDescuento(plato.getId());

        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();

        Plato platoRecuperado = repositorio.buscarPlatoPorId(plato.getId());

        assertNull(platoRecuperado.getPrecioConDescuento());
    }

    @Test
    public void dadoQueTengoPlatosEnPromocionLosQuieroBuscar(){
        Plato plato = new Plato();
        plato.setNombre("Milanesa");
        plato.setPrecio(80.0);
        plato.setPrecioConDescuento(20.0);
        repositorio.crearPlato(plato);

        Plato plato2 = new Plato();
        plato2.setNombre("Pizza");
        plato2.setPrecio(810.0);
        plato2.setPrecioConDescuento(42.0);
        repositorio.crearPlato(plato2);

        List<Plato> platosEnPromocion = repositorio.buscarPlatosEnPromocion();

        assertEquals(2, platosEnPromocion.size());
    }


}
