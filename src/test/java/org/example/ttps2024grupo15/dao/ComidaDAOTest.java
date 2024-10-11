package org.example.ttps2024grupo15.dao;

import org.example.ttps2024grupo15.dao.menu.impl.ComidaDAOHibernateJPA;
import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.carta.producto.TipoComida;
import org.example.ttps2024grupo15.model.request.menu.ComidaRequest;
import org.example.ttps2024grupo15.service.menu.ComidaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ComidaDAOTest {
    private ComidaDAOHibernateJPA comidaDAO;
    private ComidaService comidaService;

    @BeforeAll
    public void setUp(){
        this.comidaDAO = new ComidaDAOHibernateJPA();
        this.comidaService = new ComidaService(comidaDAO);
    }


    @ParameterizedTest
    @MethodSource("createComidaRequestWithMoreData")
    public void testCreateComida(ComidaRequest comidaRequest) {

        Comida comida = this.comidaService.saveComida(comidaRequest);

        assertNotNull(comida);
        assertEquals(comidaRequest.getTipoComida(), comida.getTipoComida());
        assertEquals(comidaRequest.getNombre(), comida.getNombre());
        assertEquals(comidaRequest.getPrecio(), comida.getPrecio());
    }

    private Stream<Arguments> createComidaRequestWithMoreData() {
        return Stream.of(
                Arguments.of(this.createComidaRequest("Milanesa", 2500.0, TipoComida.PLATO_PRINCIPAL)),
                Arguments.of(this.createComidaRequest("Ensalada", 1500.0, TipoComida.ENTRADA)),
                Arguments.of(this.createComidaRequest("Pizza", 3000.0, TipoComida.PLATO_PRINCIPAL))
        );
    }
    private ComidaRequest createComidaRequest(String nombre, Double precio, TipoComida tipoComida) {
        ComidaRequest comidaRequest = new ComidaRequest();
        comidaRequest.setNombre(nombre);
        comidaRequest.setPrecio(precio);
        comidaRequest.setTipoComida(tipoComida);
        return comidaRequest;
    }

}
