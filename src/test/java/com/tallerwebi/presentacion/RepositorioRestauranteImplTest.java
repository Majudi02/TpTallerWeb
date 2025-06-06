package com.tallerwebi.presentacion;

import com.tallerwebi.config.HibernateConfig;
import com.tallerwebi.dominio.Entity.Plato;
import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.dominio.RepositorioPlato;
import com.tallerwebi.infraestructura.RepositorioPlatoImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class RepositorioRestauranteImplTest {

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
    public void cuandoCreoUnPlatoEntoncesSeLlamaASaveYDevuelveTrue() {
        PlatoDto dto = new PlatoDto("Sándwich de lomito", "Lomito con huevo, jamón, queso, lechuga y tomate en pan tostado.", "/assets/imagen-plato.png", 2800.0, List.of("Proteica"));
        Plato plato = dto.obtenerEntidad();

        Boolean guardado = repositorioPlato.crearPlato(plato);

        ArgumentCaptor<Plato> captor = ArgumentCaptor.forClass(Plato.class);
        verify(sessionMock).save(captor.capture());

        assertThat(captor.getValue(), is(plato));
        assertThat(guardado, is(true));
    }


}