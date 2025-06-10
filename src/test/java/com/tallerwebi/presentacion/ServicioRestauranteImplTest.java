package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.dominio.RepositorioPlato;
import com.tallerwebi.dominio.entidades.Etiqueta;
import com.tallerwebi.dominio.entidades.Plato;
import com.tallerwebi.infraestructura.RepositorioPlatoImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Transactional
public class ServicioRestauranteImplTest {


        private SessionFactory sessionFactoryMock;
        private Session sessionMock;

        private RepositorioPlato repositorioPlato;

        @BeforeEach
        public void setUp() {
            sessionFactoryMock = mock(SessionFactory.class);
            sessionMock = mock(Session.class);

            when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);

            repositorioPlato = new RepositorioPlatoImpl(sessionFactoryMock);
        }

        @Test
        public void dadoQueTengoUnIdDeUnPlatoQuieroQueMeLoBusqueYMeLoDevueva(){
            Integer idBuscado=1;
            Plato plato = new Plato();
            plato.setId(1);
            plato.setNombre("Milanesa");
            plato.setDescripcion("Original");
            plato.setPrecio(1000.5);
            plato.setEtiquetas(new ArrayList<>());


            RepositorioPlato repositorioPlatoMock = mock(RepositorioPlato.class);
            when(repositorioPlatoMock.buscarPlatoPorId(idBuscado)).thenReturn(plato);

            Plato platoObtenido = repositorioPlatoMock.buscarPlatoPorId(idBuscado);
            PlatoDto dto=platoObtenido.obtenerDto();


            assertThat(dto.getId(),is(1));
            assertThat(dto.getNombre(),is("Milanesa"));
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


            repositorioPlato = mock(RepositorioPlato.class);
            when(repositorioPlato.crearPlato(platoMock)).thenReturn(true);

            Boolean resultado = repositorioPlato.crearPlato(platoMock);

            assertThat(resultado, is(true));
            verify(repositorioPlato).crearPlato(platoMock);
        }

    @Test
    public void dadoQueTengoUnPlatoLoQuieroActualizar() {
        Plato plato = new Plato();
        plato.setId(1);
        plato.setNombre("Milanesa");
        plato.setDescripcion("Original");
        plato.setPrecio(1000.5);

        RepositorioPlato repositorioPlato = mock(RepositorioPlato.class);
        when(repositorioPlato.actualizarPlato(any(Plato.class))).thenReturn(true);

        plato.setDescripcion("Milanesa con pure");


        Boolean actualizado = repositorioPlato.actualizarPlato(plato);

        assertTrue(actualizado);
        assertThat(plato.getDescripcion(), is("Milanesa con pure"));
    }



    }

