package org.example.ttps2024grupo15.dao.carta.menu;

import org.example.ttps2024grupo15.controller.request.carta.menu.CartaDelDiaRequest;
import org.example.ttps2024grupo15.controller.request.carta.menu.CartaSemanalRequest;
import org.example.ttps2024grupo15.controller.request.carta.menu.producto.ComidaRequest;
import org.example.ttps2024grupo15.controller.request.carta.menu.producto.MenuRequest;
import org.example.ttps2024grupo15.dao.cartaDelDia.CartaDelDiaDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.cartaSemanal.CartaSemanalDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.menu.impl.ComidaDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.menu.impl.MenuDAOHibernateJPA;
import org.example.ttps2024grupo15.model.carta.CartaDelDia;
import org.example.ttps2024grupo15.model.carta.CartaSemanal;
import org.example.ttps2024grupo15.model.carta.DiaSemana;
import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.carta.producto.Menu;
import org.example.ttps2024grupo15.model.carta.producto.TipoComida;
import org.example.ttps2024grupo15.service.cartas.CartaSemanalService;
import org.example.ttps2024grupo15.service.menu.ComidaService;
import org.example.ttps2024grupo15.service.cartas.CartaDelDiaService;
import org.example.ttps2024grupo15.service.menu.MenuService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartaDelDiaTest {
    private CartaDelDiaDAOHibernateJPA cartaDelDiaDAO;
    private CartaDelDiaService cartaDelDiaService;

    private MenuDAOHibernateJPA menuDAO;
    private MenuService menuService;
    private ComidaDAOHibernateJPA comidaDAO;
    private ComidaService comidaService;

    private CartaSemanalDAOHibernateJPA cartaSemanalDAO;
    private CartaSemanalService cartaSemanalService;

    @BeforeAll
    public void setUp(){
        this.comidaDAO = new ComidaDAOHibernateJPA();
        this.comidaService = new ComidaService(comidaDAO);

        this.menuDAO = new MenuDAOHibernateJPA();
        this.menuService = new MenuService(menuDAO, comidaService);

        this.cartaSemanalDAO = new CartaSemanalDAOHibernateJPA();
        this.cartaSemanalService = new CartaSemanalService(cartaSemanalDAO);

        this.cartaDelDiaDAO = new CartaDelDiaDAOHibernateJPA();
        this.cartaDelDiaService = new CartaDelDiaService(cartaDelDiaDAO);
    }

    @ParameterizedTest
    @MethodSource("createComidaRequestWithData")
    @Order(1)
    public void createComidas(ComidaRequest comidaRequest) {
        this.comidaService.save(comidaRequest);
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
        this.testQueryAndValidateMenuById(menu.getId(), menuRequest);
    }
    private void testQueryAndValidateMenuById(Long id, MenuRequest menuRequest) {
        Menu menu = this.menuService.getProductById(id);
        assertNotNull(menu);
        assertEquals(menuRequest.getNombre(), menu.getNombre());
        assertEquals(menuRequest.getPrecio(), menu.getPrecio());
        assertEquals(menuRequest.getComidas().size(), menu.getComidas().size());
        assertEquals(menuRequest.getImagen(), menu.getFoto());
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
    @MethodSource("createCartaSemanalRequestWithData")
    @Order(3)
    public void testCreateCartaSemanal(CartaSemanalRequest cartaSemanalRequest) {
        CartaSemanal cartaSemanal = this.cartaSemanalService.save(cartaSemanalRequest);
        this.testQueryAndValidateCartaSemanalById(cartaSemanal.getId());
    }

    private void testQueryAndValidateCartaSemanalById(Long id) {
        CartaSemanal cartaSemanal = this.cartaSemanalService.getById(id);
        assertNotNull(cartaSemanal);
    }

    private Stream<Arguments> createCartaSemanalRequestWithData() {
        return Stream.of(
                Arguments.of(this.createCartaSemanalRequest(List.of(new CartaDelDia())))
        );
    }

    private CartaSemanalRequest createCartaSemanalRequest(List<CartaDelDia> cartas) {
        CartaSemanalRequest cartaSemanalRequest = new CartaSemanalRequest();
        cartaSemanalRequest.setCartasDelDia(cartas);
        return cartaSemanalRequest;
    }

    @ParameterizedTest
    @MethodSource("createCartaDelDiaRequestWithData")
    @Order(4)
    public void testCreateCartaDelDia(CartaDelDiaRequest cartaDelDiaRequest) {
        CartaDelDia cartaDelDia = this.cartaDelDiaService.save(cartaDelDiaRequest);
        this.testQueryAndValidateCartaDelDiaById(cartaDelDia.getId(), cartaDelDiaRequest);
    }

    private Stream<Arguments> createCartaDelDiaRequestWithData() {
        LocalDate fechaInicio = LocalDate.of(2021, 10, 1);
        LocalDate fechaFin = LocalDate.of(2021, 10, 7);

        return Stream.of(
                Arguments.of(createCartaDelDiaRequest("Menu Lunes Comun", "Menu Lunes Vegano", this.cartaSemanalService.getById(1L), DiaSemana.LUNES, fechaInicio, fechaFin))
        );

    }

    private void testQueryAndValidateCartaDelDiaById(Long id, CartaDelDiaRequest cartaDelDiaRequest) {
        CartaDelDia cartaDelDia = this.cartaDelDiaService.getById(id);
        assertNotNull(cartaDelDia);
        assertEquals(cartaDelDiaRequest.getMenuComun(), cartaDelDia.getMenuComun());
        assertEquals(cartaDelDiaRequest.getMenuVegetariano(), cartaDelDia.getMenuVegetariano());
        assertEquals(cartaDelDiaRequest.getDiaSemana(), cartaDelDia.getDiaSemana());
        assertEquals(cartaDelDiaRequest.getFechaInicio(), cartaDelDia.getFechaInicio());
        assertEquals(cartaDelDiaRequest.getFechaFin(), cartaDelDia.getFechaFin());
        assertEquals(cartaDelDiaRequest.getCartaSemanal(), cartaDelDia.getCartaSemanal());
    }


    private CartaDelDiaRequest createCartaDelDiaRequest (String menuComunP, String menuVegetarianoP, CartaSemanal cartaSemanal, DiaSemana diaSemana, LocalDate fechaInicio, LocalDate fechaFin) {
        CartaDelDiaRequest cartaDelDiaRequest = new CartaDelDiaRequest();

        cartaDelDiaRequest.setMenuComun(this.getMenu(menuComunP));
        cartaDelDiaRequest.setMenuVegetariano(this.getMenu(menuVegetarianoP));
        cartaDelDiaRequest.setDiaSemana(diaSemana);
        cartaDelDiaRequest.setFechaInicio(fechaInicio);
        cartaDelDiaRequest.setFechaFin(fechaFin);
        cartaDelDiaRequest.setCartaSemanal(cartaSemanal);

        return cartaDelDiaRequest;
    }

    private Menu getMenu(String menu){
        List<Menu> menus = this.menuService.getProductsByName(menu);
        return menus.get(0);
    }


    @Test
    @Order(5)
    public void testGetAllCartasDelDia() {
        List<CartaDelDia> cartasDelDia = this.cartaDelDiaService.getAll();
        assertNotNull(cartasDelDia);
        assertEquals(1, cartasDelDia.size());
    }

    @AfterAll
    public void deleteAllTest(){
        List<CartaDelDia> cartasDelDia = this.cartaDelDiaService.getAll();
        cartasDelDia.forEach(cartaDelDia -> this.cartaDelDiaService.delete(cartaDelDia.getId()));
        cartasDelDia = this.cartaDelDiaService.getAll();
        assertNotNull(cartasDelDia);
        assertEquals(0,cartasDelDia.size());
        List<Menu> menus = this.menuService.getAll();
        menus.forEach(menu -> this.menuService.delete(menu.getId()));
        menus = this.menuService.getAll();
        assertNotNull(menus);
        assertEquals(0,menus.size());
        List<Comida> comidas = this.comidaService.getAll();
        comidas.forEach(comida -> this.comidaService.delete(comida.getId()));
        comidas = this.comidaService.getAll();
        assertNotNull(comidas);
        assertEquals(0,comidas.size());
    }



}
