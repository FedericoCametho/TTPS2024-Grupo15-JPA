package org.example.ttps2024grupo15.service.carrito;


import org.example.ttps2024grupo15.model.carrito.Carrito;
import org.example.ttps2024grupo15.model.carrito.Compra;
import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.carta.producto.Menu;

import org.example.ttps2024grupo15.model.usuario.Alumno;
import org.example.ttps2024grupo15.service.AbstractGenericTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompraServiceTest extends AbstractGenericTest {

    @ParameterizedTest
    @MethodSource("createCarritoWithData")
    @Order(1)
    public void testCreateCompra(Carrito carrito) {
        Compra compra = this.compraService.save(carrito);
        this.testQueryAndValidateCompraById(compra.getId(), carrito);
    }
    private void testQueryAndValidateCompraById(Long idCompra, Carrito carrito) {
        Compra compra = this.compraService.getById(idCompra);
        assertNotNull(compra);
        assertEquals(compra.getComidas().size(), carrito.getComidas().size());
        assertEquals(compra.getMenues().size(), carrito.getMenues().size());
        assertEquals(compra.getImporte(), carrito.getPrecioTotal());
        assertEquals(compra.getUsuario().getId(), carrito.getUsuario().getId());
        assertFalse(compra.isPagado());
    }

    @Test
    @Order(2)
    public void testGetAllComprasByAlumno() {
        Long alumnoID = this.alumnoService.getUserByDni(1111111).getId();
        List<Compra> compras = this.compraService.getComprasByAlumnoId(alumnoID);
        assertNotNull(compras);
        assertEquals(compras.size(), 1);
    }

    @Test
    @Order(3)
    public void testGetAllCompras() {
        List<Compra> compras = this.compraService.getAll();
        assertNotNull(compras);
        assertEquals(compras.size(), 2);
    }

    @Test
    @Order(4)
    public void testPagarCompra() {
        Long alumnoID = this.alumnoService.getUserByDni(1111111).getId();
        List<Compra> compras = this.compraService.getComprasByAlumnoId(alumnoID);
        assertNotNull(compras);
        assertEquals(compras.size(), 1);
        Compra compra = compras.get(0);
        this.compraService.pagarCompra(compra.getId());
        compra = this.compraService.getById(compra.getId());
        assertTrue(compra.isPagado());
    }

    @Test
    @Order(5)
    public void testGetByPrecio() {
        List<Compra> compras = this.compraService.getComprasPrecioMayorQue(12000.0);
        assertNotNull(compras);
        assertEquals(compras.size(), 1);

        compras = this.compraService.getComprasPrecioMenorQue(12000.0);
        assertNotNull(compras);
        assertEquals(compras.size(), 1);

    }

    @Test
    @Order(6)
    public void testRevertirCompra() {
        Long alumnoID = this.alumnoService.getUserByDni(1111111).getId();
        List<Compra> compras = this.compraService.getComprasByAlumnoId(alumnoID);
        assertNotNull(compras);
        assertEquals(compras.size(), 1);
        Compra compra = compras.get(0);
        this.compraService.marcarImpagoCompra(compra.getId());
        compra = this.compraService.getById(compra.getId());
        assertFalse(compra.isPagado());
    }

    @AfterAll
    public void deleteAll(){
        this.compraService.getAll().forEach(compra -> {
            this.compraService.delete(compra.getId());
        });
        this.comidaService.getAll().forEach(comida -> {
            this.comidaService.delete(comida.getId());
        });
        this.menuService.getAll().forEach(menu -> {
            this.menuService.delete(menu.getId());
        });
        this.alumnoService.getAll().forEach(alumno -> {
            this.alumnoService.delete(alumno.getId());
        });
    }

    private Carrito createCarrito(Alumno alumno, List<Menu> menues, List<Comida> comidas) {
        Carrito carrito = new Carrito();
        carrito.setUsuario(alumno);
        carrito.setMenues(menues);
        carrito.setComidas(comidas);
        return carrito;
    }

    private Stream<Arguments> createCarritoWithData() {
        return Stream.of(
                Arguments.of(this.createCarrito(this.alumnoService.getUserByDni(1111111), List.of(
                        this.menuService.getProductsByName("Menu Lunes Comun").get(0),
                        this.menuService.getProductsByName("Menu Martes Comun").get(0)
                ), List.of(
                        this.comidaService.getProductsByName("Milanesa").get(0),
                        this.comidaService.getProductsByName("Ensalada").get(0)
                ))),
                Arguments.of(this.createCarrito(this.alumnoService.getUserByDni(22222222), List.of(
                        this.menuService.getProductsByName("Menu Lunes Vegano").get(0),
                        this.menuService.getProductsByName("Menu Martes Vegano").get(0)
                ), List.of(
                        this.comidaService.getProductsByName("Pasta").get(0),
                        this.comidaService.getProductsByName("Ensalada").get(0)
                )))
        );
    }




}
