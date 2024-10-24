package org.example.ttps2024grupo15.dao.carrito;

import org.example.ttps2024grupo15.controller.request.carta.producto.ComidaRequest;
import org.example.ttps2024grupo15.controller.request.carta.producto.MenuRequest;
import org.example.ttps2024grupo15.controller.request.usuario.AlumnoRequest;
import org.example.ttps2024grupo15.dao.carta.producto.impl.ComidaDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.carta.producto.impl.MenuDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.usuario.impl.AlumnoDAOHibernateJPA;
import org.example.ttps2024grupo15.model.carrito.Carrito;
import org.example.ttps2024grupo15.model.carrito.Compra;
import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.carta.producto.Menu;
import org.example.ttps2024grupo15.model.carta.producto.TipoComida;
import org.example.ttps2024grupo15.model.usuario.Alumno;
import org.example.ttps2024grupo15.service.carrito.CompraService;
import org.example.ttps2024grupo15.service.carta.producto.ComidaService;
import org.example.ttps2024grupo15.service.carta.producto.MenuService;
import org.example.ttps2024grupo15.service.usuario.AlumnoService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompraServiceTest {

    private MenuDAOHibernateJPA menuDAO;
    private MenuService menuService;

    private ComidaDAOHibernateJPA comidaDAO;
    private ComidaService comidaService;

    private AlumnoService alumnoService;
    private AlumnoDAOHibernateJPA alumnoDAO;

    private CompraDAOHibernateJPA compraDAO;
    private CompraService compraService;

    @BeforeAll
    public void setUp(){
        this.alumnoDAO = new AlumnoDAOHibernateJPA();
        this.alumnoService = new AlumnoService(alumnoDAO);

        this.comidaDAO = new ComidaDAOHibernateJPA();
        this.comidaService = new ComidaService(comidaDAO);

        this.menuDAO = new MenuDAOHibernateJPA();
        this.menuService = new MenuService(menuDAO, comidaService);

        this.compraDAO = new CompraDAOHibernateJPA();
        this.compraService = new CompraService(compraDAO);

        this.createAlumnoRequestWithData().forEach(request -> {
            this.alumnoService.save(request);
        });

        this.createComidaRequestWithData().forEach(request -> {
            this.comidaService.save(request);
        });

        this.createMenuRequestWithData().forEach(request -> {
            this.menuService.save(request);
        });

    }

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

    private AlumnoRequest createAlumnoRequest(String nombre, String apellido, String email, Integer dni) {
        AlumnoRequest alumnoRequest = new AlumnoRequest();
        alumnoRequest.setNombre(nombre);
        alumnoRequest.setApellido(apellido);
        alumnoRequest.setEmail(email);
        alumnoRequest.setDni(dni);
        alumnoRequest.setFoto(null);
        return alumnoRequest;
    }

    private List<MenuRequest> createMenuRequestWithData() {
        return List.of(
                this.createMenuRequest("Menu Lunes Comun", 4500.0, List.of(
                        this.comidaService.getProductsByName("Milanesa").get(0),
                        this.comidaService.getProductsByName("Ensalada").get(0),
                        this.comidaService.getProductsByName("Helado").get(0))
                ),
                this.createMenuRequest("Menu Lunes Vegano", 3500.0, List.of(
                        this.comidaService.getProductsByName("Pasta").get(0),
                        this.comidaService.getProductsByName("Ensalada").get(0),
                        this.comidaService.getProductsByName("Manzana").get(0))
                ),
                this.createMenuRequest("Menu Martes Comun", 4500.0, List.of(
                        this.comidaService.getProductsByName("Milanesa").get(0),
                        this.comidaService.getProductsByName("Ensalada").get(0),
                        this.comidaService.getProductsByName("Helado").get(0))
                ),
                this.createMenuRequest("Menu Martes Vegano", 3500.0, List.of(
                        this.comidaService.getProductsByName("Pasta").get(0),
                        this.comidaService.getProductsByName("Ensalada").get(0),
                        this.comidaService.getProductsByName("Manzana").get(0))
                )
        );
    }
    private MenuRequest createMenuRequest(String nombre, Double precio, List<Comida> comidasList) {
        MenuRequest menuRequest = new MenuRequest();
        menuRequest.setNombre(nombre);
        menuRequest.setPrecio(precio);
        menuRequest.setImagen(null);
        menuRequest.setComidas(comidasList);
        return menuRequest;
    }

    private List<AlumnoRequest> createAlumnoRequestWithData() {
        return List.of(
                this.createAlumnoRequest("Chicho","Siesta","chicho@gmail.com",1111111),
                this.createAlumnoRequest("Roman","Riquelme","roman@gmail.com",22222222),
                this.createAlumnoRequest("Enzo","Perez","enzo@gmail.com",33333333)
        );
    }


    private List<ComidaRequest> createComidaRequestWithData() {
        return List.of(
                this.createComidaRequest("Milanesa", 2500.0, TipoComida.PLATO_PRINCIPAL),
                this.createComidaRequest("Ensalada", 1500.0, TipoComida.ENTRADA),
                this.createComidaRequest("Pasta", 2500.0, TipoComida.PLATO_PRINCIPAL),
                this.createComidaRequest("Manzana", 1000.0, TipoComida.POSTRE),
                this.createComidaRequest("Helado", 1000.0, TipoComida.POSTRE)
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
