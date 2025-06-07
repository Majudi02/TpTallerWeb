package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.entidades.Etiqueta;
import com.tallerwebi.dominio.entidades.Plato;
import com.tallerwebi.dominio.RepositorioPlato;
import com.tallerwebi.infraestructura.RepositorioPlatoImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    private RepositorioPlato repostorioPlato;

    @BeforeEach
    public void setUp() {
        sessionFactoryMock = mock(SessionFactory.class);
        sessionMock = mock(Session.class);

        when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);

        repostorioPlato = new RepositorioPlatoImpl(sessionFactoryMock);
    }


    @Test
    public void dadoQueTengoUnPlatoEntoncesLeQuieroModificarSusEtiquetas() {
        // Arrange
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

        // Mock Query
        Query queryMock = mock(Query.class);
        when(sessionMock.createQuery("UPDATE Plato p SET p.etiquetas = :etiquetas WHERE p.id = :id"))
                .thenReturn(queryMock);
        when(queryMock.setParameter(eq("etiquetas"), eq(etiquetas))).thenReturn(queryMock);
        when(queryMock.setParameter(eq("id"), eq(1))).thenReturn(queryMock);
        when(queryMock.executeUpdate()).thenReturn(1);

        // Act
        Boolean actualizado = repostorioPlato.editarEtiquetas(platoMock);

        // Assert
        assertThat(actualizado, is(true));
        verify(queryMock).setParameter("etiquetas", etiquetas);
        verify(queryMock).setParameter("id", 1);
        verify(queryMock).executeUpdate();
    }
}