package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Entity.Etiqueta;
import com.tallerwebi.dominio.Entity.Plato;
import com.tallerwebi.dominio.RepostitorioPlato;
import com.tallerwebi.infraestructura.RepositorioPlatoImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

//@ExtendWith(SpringExtension.class)
@Transactional
public class RepositorioRestauranteImplTest {

    private SessionFactory sessionFactoryMock;
    private Session sessionMock;

    private RepostitorioPlato repositorioPlato;

    @BeforeEach
    public void setUp() {
        sessionFactoryMock = mock(SessionFactory.class);
        sessionMock = mock(Session.class);

        when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);

        repositorioPlato = new RepositorioPlatoImpl(sessionFactoryMock);
    }



    @Test
    public void dadoQueTengoUnPlatoEntoncesLeQuieroModificarSusEtiquetas() {

        Plato platoMock = mock(Plato.class);
        when(platoMock.getId()).thenReturn(1);

        List<String> nombres = List.of("Saludable", "Vegetariano");
        List<Etiqueta> etiquetas = new ArrayList<>();
        Integer contador=0;
        for (String nombre : nombres) {
            contador+=1;
            Etiqueta etiqueta = new Etiqueta();
            etiqueta.setId(contador);
            etiqueta.setNombre(nombre);
            etiquetas.add(etiqueta);
        }

        when(platoMock.getEtiquetas()).thenReturn(etiquetas);

        Query queryMock = mock(Query.class);
        when(sessionMock.createQuery("UPDATE Plato p SET p.etiquetas = :etiquetas WHERE p.id = :id"))
                .thenReturn(queryMock);
        when(queryMock.setParameter(eq("etiquetas"), eq(etiquetas))).thenReturn(queryMock);
        when(queryMock.setParameter(eq("id"), eq(1))).thenReturn(queryMock);
        when(queryMock.executeUpdate()).thenReturn(1);

        Boolean actualizado = repositorioPlato.editarEtiquetas(platoMock);

        assertThat(actualizado, is(true));
        verify(queryMock).setParameter("etiquetas", etiquetas);
        verify(queryMock).setParameter("id", 1);
        verify(queryMock).executeUpdate();
    }

    @Test
    public void dadoQueCreoUnPlatoLoQuieroGuardarEnLaBaseDeDatos(){
        List<String> nombres = List.of("Saludable", "Vegetariano");
        List<Etiqueta> etiquetas = new ArrayList<>();
        Integer contador=0;
        for (String nombre : nombres) {
            contador+=1;
            Etiqueta etiqueta = new Etiqueta();
            etiqueta.setId(contador);
            etiqueta.setNombre(nombre);
            etiquetas.add(etiqueta);
        }

        Plato platoMock = mock(Plato.class);
        when(platoMock.getId()).thenReturn(1);
        when(platoMock.getNombre()).thenReturn("Milanesa");
        when(platoMock.getDescripcion()).thenReturn("Milanesa con pasa muy ricas");
        when(platoMock.getPrecio()).thenReturn(1000.5);
        when(platoMock.getEtiquetas()).thenReturn(etiquetas);


        repositorioPlato = mock(RepostitorioPlato.class);
        when(repositorioPlato.crearPlato(platoMock)).thenReturn(true);

        Boolean resultado = repositorioPlato.crearPlato(platoMock);

        assertThat(resultado, is(true));
        verify(repositorioPlato).crearPlato(platoMock);
    }

    @Test
    public void dadoQueTengoUnPlatoLoQuieroActualizar(){
        List<String> nombres = List.of("Saludable", "Vegetariano");
        List<Etiqueta> etiquetas = new ArrayList<>();
        Integer contador=0;
        for (String nombre : nombres) {
            contador+=1;
            Etiqueta etiqueta = new Etiqueta();
            etiqueta.setId(contador);
            etiqueta.setNombre(nombre);
            etiquetas.add(etiqueta);
        }


        Plato plato = new Plato();
        plato.setId(1);
        plato.setNombre("Milanesa");
        plato.setDescripcion("Milanesa con pasa muy ricas");
        plato.setPrecio(1000.5);
        plato.setEtiquetas(etiquetas);

        RepostitorioPlato repositorioPlato = mock(RepostitorioPlato.class);
        when(repositorioPlato.actualizarPlato(any(Plato.class))).thenReturn(true);

        plato.setDescripcion("xdasdx");

        // Ejecutar
        Boolean actualizado = repositorioPlato.actualizarPlato(plato);

        assertTrue(actualizado);
        assertThat(plato.getDescripcion(), is("xdasdx"));

    }



}