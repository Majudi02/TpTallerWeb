package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioPlato;
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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;



@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
public class RepositorioRestauranteImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioPlato repositorioPlato;

    @BeforeEach
    public void setUp() {
        repositorioPlato = new RepositorioPlatoImpl(sessionFactory);
    }



    @Test
    public void dadoQueCreoUnPlatoLoQuieroGuardarEnLaBaseDeDatos(){
        Etiqueta etiqueta1 = sessionFactory.getCurrentSession().get(Etiqueta.class, 1);
        Etiqueta etiqueta2 = sessionFactory.getCurrentSession().get(Etiqueta.class, 2);

        List<Etiqueta> nuevasEtiquetas = new ArrayList<>();
        nuevasEtiquetas.add(etiqueta1);
        nuevasEtiquetas.add(etiqueta2);

        Plato platoOriginal = new Plato();
        platoOriginal.setNombre("Milanesa");
        platoOriginal.setPrecio(80.0);
        platoOriginal.setEtiquetas(new ArrayList<>());
        sessionFactory.getCurrentSession().save(platoOriginal);

        Boolean platoCreado = repositorioPlato.crearPlato(platoOriginal);

        assertThat(platoCreado, is(true));
    }

    @Test
    public void dadoQueTengoUnPlatoLoQuieroActualizar(){
        Etiqueta etiqueta1 = sessionFactory.getCurrentSession().get(Etiqueta.class, 1);
        Etiqueta etiqueta2 = sessionFactory.getCurrentSession().get(Etiqueta.class, 2);

        List<Etiqueta> nuevasEtiquetas = new ArrayList<>();
        nuevasEtiquetas.add(etiqueta1);
        nuevasEtiquetas.add(etiqueta2);

        Plato platoOriginal = new Plato();
        platoOriginal.setNombre("Milanesa");
        platoOriginal.setPrecio(80.0);
        platoOriginal.setEtiquetas(new ArrayList<>());
        sessionFactory.getCurrentSession().save(platoOriginal);



       Plato platoGuardado= repositorioPlato.buscarPlatoPorId(platoOriginal.getId());
       platoGuardado.setNombre("Empanadas");
       Boolean actualizado = repositorioPlato.actualizarPlato(platoGuardado);

        assertTrue(actualizado);
        assertThat(platoGuardado.getNombre(), is("Empanadas"));

    }

    @Test
    public void dadoQueTengoUnPlatoEntoncesLeQuieroModificarSusEtiquetas() {
        Etiqueta etiqueta1 = sessionFactory.getCurrentSession().get(Etiqueta.class, 1);
        Etiqueta etiqueta2 = sessionFactory.getCurrentSession().get(Etiqueta.class, 2);

        List<Etiqueta> nuevasEtiquetas = new ArrayList<>();
        nuevasEtiquetas.add(etiqueta1);
        nuevasEtiquetas.add(etiqueta2);

        Plato platoOriginal = new Plato();
        platoOriginal.setNombre("Milanesa");
        platoOriginal.setPrecio(80.0);
        platoOriginal.setEtiquetas(new ArrayList<>());
        sessionFactory.getCurrentSession().save(platoOriginal);
        Integer idGenerado = platoOriginal.getId();

        Plato platoParaActualizar = new Plato();
        platoParaActualizar.setId(idGenerado);
        platoParaActualizar.setEtiquetas(nuevasEtiquetas);

        Boolean modificado = repositorioPlato.editarEtiquetas(platoParaActualizar);

        assertThat(modificado, is(true));

        Plato platoVerificado = sessionFactory.getCurrentSession().get(Plato.class, idGenerado);
        assertThat(platoVerificado.getEtiquetas().size(), is(2));
    }




}