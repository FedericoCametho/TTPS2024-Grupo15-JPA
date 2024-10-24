package org.example.ttps2024grupo15.service.carta.producto;

import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.carta.producto.TipoComida;
import org.example.ttps2024grupo15.controller.request.carta.producto.ComidaRequest;
import org.example.ttps2024grupo15.service.AbstractGenericTest;
import org.junit.jupiter.api.*;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComidaServiceTest extends AbstractGenericTest {



    @Test
    @Order(1)
    public void testMassiveQuery() {
        List<Comida> comidas = this.comidaService.getAll();
        assertNotNull(comidas);
        assertEquals(5, comidas.size());
    }
    @Test
    @Order(2)
    public void testCreateComida() {
        ComidaRequest comidaRequest = this.createComidaRequest("Hamburguesa", 8000.00, TipoComida.PLATO_PRINCIPAL);
        Comida comida = this.comidaService.save(comidaRequest);
        this.testQueryAndValidateComidaById(comida.getId(), comidaRequest);
    }

    @Test
    @Order(3)
    public void testQueryByName() {
        List<Comida> comidas = this.comidaService.getProductsByName("No existe");
        assertNotNull(comidas);
        assertEquals(0, comidas.size());

        comidas = this.comidaService.getProductsByName("Milanesa");
        assertNotNull(comidas);
        assertEquals(1, comidas.size());
        Comida comida = comidas.get(0);
        assertEquals("Milanesa", comida.getNombre());
        assertEquals(TipoComida.PLATO_PRINCIPAL, comida.getTipoComida());
        assertEquals(2500.0, comida.getPrecio());
    }

    @Test
    @Order(4)
    public void testQueryByPrice() {
        List<Comida> comidas = this.comidaService.getProductsByPrice(Double.valueOf("0.00"));
        assertNotNull(comidas);
        assertEquals(0, comidas.size());

        comidas = this.comidaService.getProductsByPrice(1500.0);
        assertNotNull(comidas);
        assertEquals(1, comidas.size());
        Comida comida = comidas.get(0);
        assertEquals("Ensalada", comida.getNombre());
        assertEquals(TipoComida.ENTRADA, comida.getTipoComida());
        assertEquals(1500.0, comida.getPrecio());
    }

    @Test
    @Order(5)
    public void testQueryByPriceMultipleResults() {
        List<Comida> comidas = this.comidaService.getProductsByPrice(Double.valueOf("2500.0"));
        assertNotNull(comidas);
        assertEquals(2, comidas.size());
    }
    @Test
    @Order(6)
    public void updateComida(){
        Comida comidaToUpdate = this.comidaService.getProductsByName("Milanesa").get(0);
        Comida comidaPostUpdate = this.comidaService.update(comidaToUpdate.getId(), this.createComidaRequest("Milanesa", 3000.0, TipoComida.PLATO_PRINCIPAL));
        assertNotNull(comidaPostUpdate);
        assertEquals(comidaToUpdate.getId(), comidaPostUpdate.getId());
        assertEquals(comidaToUpdate.getNombre(), comidaPostUpdate.getNombre());
        assertEquals(3000.0, comidaPostUpdate.getPrecio());
        assertEquals(comidaToUpdate.getTipoComida(), comidaPostUpdate.getTipoComida());
    }

    @Test
    @Order(7)
    public void testDeleteComidaById() {
        List<Comida> comidas = this.comidaService.getAll();
        assertNotNull(comidas);
        assertEquals(6, comidas.size());
        this.comidaService.delete(comidas.get(0).getId());
        comidas = this.comidaService.getAll();
        assertNotNull(comidas);
        assertEquals(5, comidas.size());
    }

    @Test
    @Order(8)
    public void testDeleteComida(){
        List<Comida> comidas = this.comidaService.getAll();
        assertNotNull(comidas);
        assertEquals(5, comidas.size());
        Comida comidaToDelete = comidas.get(0);
        this.comidaService.delete(comidaToDelete.getId());
        comidas = this.comidaService.getAll();
        assertNotNull(comidas);
        assertEquals(4, comidas.size());
    }
    @AfterAll
    public void deleteAllComidas(){
        List<Comida> comidas = this.comidaService.getAll();
        comidas.forEach(comida -> this.comidaService.delete(comida.getId()));
        comidas = this.comidaService.getAll();
        assertNotNull(comidas);
        assertEquals(0, comidas.size());
    }


    private void testQueryAndValidateComidaById(Long id, ComidaRequest comidaRequest) {
        Comida comida = this.comidaService.getProductById(id);
        assertNotNull(comida);
        assertEquals(comidaRequest.getTipoComida(), comida.getTipoComida());
        assertEquals(comidaRequest.getNombre(), comida.getNombre());
        assertEquals(comidaRequest.getPrecio(), comida.getPrecio());
    }





}
