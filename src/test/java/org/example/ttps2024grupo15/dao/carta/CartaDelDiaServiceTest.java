package org.example.ttps2024grupo15.dao.carta;

import org.example.ttps2024grupo15.controller.request.carta.CartaDelDiaRequest;
import org.example.ttps2024grupo15.controller.request.carta.producto.ComidaRequest;
import org.example.ttps2024grupo15.controller.request.carta.producto.MenuRequest;

import org.example.ttps2024grupo15.dao.carta.cartaDelDia.CartaDelDiaDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.carta.producto.impl.ComidaDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.carta.producto.impl.MenuDAOHibernateJPA;
import org.example.ttps2024grupo15.model.carta.CartaDelDia;
import org.example.ttps2024grupo15.model.carta.DiaSemana;
import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.carta.producto.Menu;
import org.example.ttps2024grupo15.model.carta.producto.TipoComida;
import org.example.ttps2024grupo15.service.carta.CartaDelDiaService;
import org.example.ttps2024grupo15.service.carta.producto.ComidaService;
import org.example.ttps2024grupo15.service.carta.producto.MenuService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartaDelDiaServiceTest {
    private CartaDelDiaDAOHibernateJPA cartaDelDiaDAO;
    private CartaDelDiaService cartaDelDiaService;

    private MenuDAOHibernateJPA menuDAO;
    private MenuService menuService;
    private ComidaDAOHibernateJPA comidaDAO;
    private ComidaService comidaService;

    @BeforeAll
    public void setUp(){
        this.comidaDAO = new ComidaDAOHibernateJPA();
        this.comidaService = new ComidaService(comidaDAO);

        this.menuDAO = new MenuDAOHibernateJPA();
        this.menuService = new MenuService(menuDAO, comidaService);

        this.cartaDelDiaDAO = new CartaDelDiaDAOHibernateJPA();
        this.cartaDelDiaService = new CartaDelDiaService(cartaDelDiaDAO);
    }

    @ParameterizedTest
    @MethodSource("createComidaRequestWithData")
    @Order(1)
    public void createComidas(ComidaRequest comidaRequest) {
        Comida comida = this.comidaService.save(comidaRequest);
        assertNotNull(this.comidaService.getProductById(comida.getId()));
    }
    private Stream<Arguments> createComidaRequestWithData() {
        return Stream.of(
                Arguments.of(this.createComidaRequest("Milanesa", 2500.0, TipoComida.PLATO_PRINCIPAL)),
                Arguments.of(this.createComidaRequest("Ensalada", 1500.0, TipoComida.ENTRADA)),
                Arguments.of(this.createComidaRequest("Pasta", 2500.0, TipoComida.PLATO_PRINCIPAL)),
                Arguments.of(this.createComidaRequest("Manzana", 1000.0, TipoComida.POSTRE)),
                Arguments.of(this.createComidaRequest("Helado", 1000.0, TipoComida.POSTRE))
        );
    }

    private ComidaRequest createComidaRequest(String nombre, Double precio, TipoComida tipoComida) {
        ComidaRequest comidaRequest = new ComidaRequest();
        comidaRequest.setNombre(nombre);
        comidaRequest.setPrecio(precio);
        comidaRequest.setTipoComida(tipoComida);
        return comidaRequest;
    }

    @ParameterizedTest
    @MethodSource("createMenuRequestWithData")
    @Order(2)
    public void testCreateMenu(MenuRequest menuRequest) {
        Menu menu = this.menuService.save(menuRequest);
        assertNotNull(this.menuService.getProductById(menu.getId()));
    }


    private Stream<Arguments> createMenuRequestWithData() {
        return Stream.of(
                Arguments.of(this.createMenuRequest("Menu Lunes Comun", 4500.0, List.of(
                        this.comidaService.getProductsByName("Milanesa").get(0),
                        this.comidaService.getProductsByName("Ensalada").get(0),
                        this.comidaService.getProductsByName("Helado").get(0))
                )),
                Arguments.of(this.createMenuRequest("Menu Lunes Vegano", 3500.0, List.of(
                        this.comidaService.getProductsByName("Pasta").get(0),
                        this.comidaService.getProductsByName("Ensalada").get(0),
                        this.comidaService.getProductsByName("Manzana").get(0))
                )),
                Arguments.of(this.createMenuRequest("Menu Martes Comun", 4500.0, List.of(
                        this.comidaService.getProductsByName("Milanesa").get(0),
                        this.comidaService.getProductsByName("Ensalada").get(0),
                        this.comidaService.getProductsByName("Helado").get(0))
                )),
                Arguments.of(this.createMenuRequest("Menu Martes Vegano", 3500.0, List.of(
                        this.comidaService.getProductsByName("Pasta").get(0),
                        this.comidaService.getProductsByName("Ensalada").get(0),
                        this.comidaService.getProductsByName("Manzana").get(0))
                ))
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

    @ParameterizedTest
    @MethodSource("createCartaDelDiaRequestWithData")
    @Order(3)
    public void testCreateCartaDelDia(CartaDelDiaRequest cartaDelDiaRequest) {
        CartaDelDia cartaDelDia = this.cartaDelDiaService.save(cartaDelDiaRequest);
        this.testQueryAndValidateCartaDelDiaById(cartaDelDia.getId(), cartaDelDiaRequest);
    }

    private Stream<Arguments> createCartaDelDiaRequestWithData() {
        return Stream.of(
                Arguments.of(this.createCartaDelDiaRequest("Menu Lunes Comun", "Menu Lunes Vegano", DiaSemana.LUNES))
        );

    }

    private void testQueryAndValidateCartaDelDiaById(Long id, CartaDelDiaRequest cartaDelDiaRequest) {
        CartaDelDia cartaDelDia = this.cartaDelDiaService.getById(id);
        assertNotNull(cartaDelDia);
        Menu menuComunRequest = this.menuService.getProductById(cartaDelDiaRequest.getMenuComun().getId());
        Menu menuVegetarianoRequest = this.menuService.getProductById(cartaDelDiaRequest.getMenuVegetariano().getId());
        assertEquals(menuComunRequest.getId(), cartaDelDia.getMenuComun().getId());
        assertEquals(menuVegetarianoRequest.getId(), cartaDelDia.getMenuVegetariano().getId());
        assertEquals(cartaDelDiaRequest.getDiaSemana(), cartaDelDia.getDiaSemana());
        assertNull(cartaDelDia.getCartaSemanal());
    }


    private CartaDelDiaRequest createCartaDelDiaRequest (String menuComunP, String menuVegetarianoP, DiaSemana diaSemana) {
        CartaDelDiaRequest cartaDelDiaRequest = new CartaDelDiaRequest();

        cartaDelDiaRequest.setMenuComun(this.menuService.getProductsByName(menuComunP).get(0));
        cartaDelDiaRequest.setMenuVegetariano(this.menuService.getProductsByName(menuVegetarianoP).get(0));
        cartaDelDiaRequest.setDiaSemana(diaSemana);
        cartaDelDiaRequest.setCartaSemanal(null);

        return cartaDelDiaRequest;
    }


    @Test
    @Order(4)
    public void testGetAllCartasDelDia() {
        List<CartaDelDia> cartasDelDia = this.cartaDelDiaService.getAll();
        assertNotNull(cartasDelDia);
        assertEquals(1, cartasDelDia.size());
    }

    @Test
    @Order(5)
    public void testUpdateCartaDelDia() {
        CartaDelDia cartaDelDia = this.cartaDelDiaService.getAll().get(0);
        CartaDelDiaRequest cartaDelDiaRequest = new CartaDelDiaRequest();
        cartaDelDiaRequest.setMenuComun(this.menuService.getProductsByName("Menu Martes Comun").get(0));
        cartaDelDiaRequest.setMenuVegetariano(this.menuService.getProductsByName("Menu Martes Vegano").get(0));
        cartaDelDiaRequest.setDiaSemana(DiaSemana.MARTES);
        cartaDelDiaRequest.setCartaSemanal(null);
        this.cartaDelDiaService.update(cartaDelDia.getId(), cartaDelDiaRequest);
        this.testQueryAndValidateCartaDelDiaById(cartaDelDia.getId(), cartaDelDiaRequest);
    }

    @Test
    @Order(6)
    public void testDeleteCartaDelDiaById() {
        List<CartaDelDia> cartasDelDia = this.cartaDelDiaService.getAll();
        assertNotNull(cartasDelDia);
        assertEquals(1, cartasDelDia.size());
        this.cartaDelDiaService.delete(cartasDelDia.get(0).getId());
        cartasDelDia = this.cartaDelDiaService.getAll();
        assertNotNull(cartasDelDia);
        assertEquals(0, cartasDelDia.size());
    }


    @AfterAll
    public void deleteAllTest(){
        List<CartaDelDia> cartasDelDia = this.cartaDelDiaService.getAll();
        cartasDelDia.forEach(cartaDelDia -> this.cartaDelDiaService.delete(cartaDelDia.getId()));
        cartasDelDia = this.cartaDelDiaService.getAll();
        assertNotNull(cartasDelDia);
        assertEquals(0,cartasDelDia.size());


        List<Comida> comidas = this.comidaService.getAll();
        comidas.forEach(comida -> this.comidaService.delete(comida.getId()));
        comidas = this.comidaService.getAll();
        assertNotNull(comidas);
        assertEquals(0,comidas.size());


        List<Menu> menus = this.menuService.getAll();
        menus.forEach(menu -> this.menuService.delete(menu.getId()));
        menus = this.menuService.getAll();
        assertNotNull(menus);
        assertEquals(0,menus.size());


    }



}