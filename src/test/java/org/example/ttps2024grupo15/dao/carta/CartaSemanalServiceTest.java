package org.example.ttps2024grupo15.dao.carta;

import org.example.ttps2024grupo15.controller.request.carta.CartaDelDiaRequest;
import org.example.ttps2024grupo15.controller.request.carta.CartaSemanalRequest;
import org.example.ttps2024grupo15.controller.request.carta.producto.ComidaRequest;
import org.example.ttps2024grupo15.controller.request.carta.producto.MenuRequest;
import org.example.ttps2024grupo15.dao.carta.cartaDelDia.CartaDelDiaDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.carta.cartaSemanal.CartaSemanalDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.carta.producto.impl.ComidaDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.carta.producto.impl.MenuDAOHibernateJPA;
import org.example.ttps2024grupo15.model.carta.CartaDelDia;
import org.example.ttps2024grupo15.model.carta.CartaSemanal;
import org.example.ttps2024grupo15.model.carta.DiaSemana;
import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.carta.producto.Menu;
import org.example.ttps2024grupo15.model.carta.producto.TipoComida;
import org.example.ttps2024grupo15.service.carta.CartaDelDiaService;
import org.example.ttps2024grupo15.service.carta.CartaSemanalService;
import org.example.ttps2024grupo15.service.carta.producto.ComidaService;
import org.example.ttps2024grupo15.service.carta.producto.MenuService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartaSemanalServiceTest {
    private CartaDelDiaDAOHibernateJPA cartaDelDiaDAO;
    private CartaDelDiaService cartaDelDiaService;

    private MenuDAOHibernateJPA menuDAO;
    private MenuService menuService;

    private ComidaDAOHibernateJPA comidaDAO;
    private ComidaService comidaService;

    private CartaSemanalDAOHibernateJPA cartaSemanalDAO;
    private CartaSemanalService cartaSemanalService;

    @BeforeAll
    public void setUp() {
        this.comidaDAO = new ComidaDAOHibernateJPA();
        this.comidaService = new ComidaService(comidaDAO);

        this.menuDAO = new MenuDAOHibernateJPA();
        this.menuService = new MenuService(menuDAO, comidaService);

        this.cartaDelDiaDAO = new CartaDelDiaDAOHibernateJPA();
        this.cartaDelDiaService = new CartaDelDiaService(cartaDelDiaDAO);

        this.cartaSemanalDAO = new CartaSemanalDAOHibernateJPA();
        this.cartaSemanalService = new CartaSemanalService(cartaSemanalDAO, cartaDelDiaService);
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
                )),
                Arguments.of(this.createMenuRequest("Menu Miercoles Comun", 4500.0, List.of(
                        this.comidaService.getProductsByName("Milanesa").get(0),
                        this.comidaService.getProductsByName("Ensalada").get(0),
                        this.comidaService.getProductsByName("Helado").get(0))
                )),
                Arguments.of(this.createMenuRequest("Menu Miercoles Vegano", 3500.0, List.of(
                        this.comidaService.getProductsByName("Pasta").get(0),
                        this.comidaService.getProductsByName("Ensalada").get(0),
                        this.comidaService.getProductsByName("Manzana").get(0))
                )),
                Arguments.of(this.createMenuRequest("Menu Jueves Comun", 4500.0, List.of(
                        this.comidaService.getProductsByName("Milanesa").get(0),
                        this.comidaService.getProductsByName("Ensalada").get(0),
                        this.comidaService.getProductsByName("Helado").get(0))
                )),
                Arguments.of(this.createMenuRequest("Menu Jueves Vegano", 3500.0, List.of(
                        this.comidaService.getProductsByName("Pasta").get(0),
                        this.comidaService.getProductsByName("Ensalada").get(0),
                        this.comidaService.getProductsByName("Manzana").get(0))
                )),
                Arguments.of(this.createMenuRequest("Menu Viernes Comun", 4500.0, List.of(
                        this.comidaService.getProductsByName("Milanesa").get(0),
                        this.comidaService.getProductsByName("Ensalada").get(0),
                        this.comidaService.getProductsByName("Helado").get(0))
                )),
                Arguments.of(this.createMenuRequest("Menu Viernes Vegano", 3500.0, List.of(
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

    private CartaDelDiaRequest createCartaDelDiaRequest(String menuComunP, String menuVegetarianoP, DiaSemana diaSemana) {
        CartaDelDiaRequest cartaDelDiaRequest = new CartaDelDiaRequest();

        cartaDelDiaRequest.setMenuComun(this.menuService.getProductsByName(menuComunP).get(0));
        cartaDelDiaRequest.setMenuVegetariano(this.menuService.getProductsByName(menuVegetarianoP).get(0));
        cartaDelDiaRequest.setDiaSemana(diaSemana);
        cartaDelDiaRequest.setCartaSemanal(null);

        return cartaDelDiaRequest;
    }

    @ParameterizedTest
    @MethodSource("createCartaDelDiaRequestWithData")
    @Order(3)
    public void testCreateCartaDelDia(CartaDelDiaRequest cartaDelDiaRequest) {
        CartaDelDia cartaDelDia = this.cartaDelDiaService.save(cartaDelDiaRequest);
        assertNotNull(this.cartaDelDiaService.getById(cartaDelDia.getId()));
    }

    private Stream<Arguments> createCartaDelDiaRequestWithData() {

        return Stream.of(
                Arguments.of(this.createCartaDelDiaRequest("Menu Lunes Comun", "Menu Lunes Vegano", DiaSemana.LUNES)),
                Arguments.of(this.createCartaDelDiaRequest("Menu Martes Comun", "Menu Martes Vegano", DiaSemana.MARTES)),
                Arguments.of(this.createCartaDelDiaRequest("Menu Miercoles Comun", "Menu Miercoles Vegano", DiaSemana.MIERCOLES)),
                Arguments.of(this.createCartaDelDiaRequest("Menu Jueves Comun", "Menu Jueves Vegano", DiaSemana.JUEVES)),
                Arguments.of(this.createCartaDelDiaRequest("Menu Viernes Comun", "Menu Viernes Vegano", DiaSemana.VIERNES))
        );

    }

    @ParameterizedTest
    @MethodSource("createCartaSemanalRequestWithData")
    @Order(4)
    public void testCreateCartaSemanal(CartaSemanalRequest cartaSemanalRequest) {
        CartaSemanal cartaSemanal = this.cartaSemanalService.save(cartaSemanalRequest);
        this.testQueryAndValidateCartaSemanalById(cartaSemanal.getId(), cartaSemanalRequest);
    }

    private Stream<Arguments> createCartaSemanalRequestWithData() {
        return Stream.of(
                Arguments.of(this.createCartaSemanalRequest("Carta de Navidad",List.of(
                                this.cartaDelDiaService.getCartaDelDiaByDiaSemana(DiaSemana.LUNES).get(0),
                                this.cartaDelDiaService.getCartaDelDiaByDiaSemana(DiaSemana.MARTES).get(0),
                                this.cartaDelDiaService.getCartaDelDiaByDiaSemana(DiaSemana.MIERCOLES).get(0),
                                this.cartaDelDiaService.getCartaDelDiaByDiaSemana(DiaSemana.JUEVES).get(0),
                                this.cartaDelDiaService.getCartaDelDiaByDiaSemana(DiaSemana.VIERNES).get(0)
                        ))
                ));
    }

    private CartaSemanalRequest createCartaSemanalRequest(String nombre, List<CartaDelDia> cartasDelDia) {
        CartaSemanalRequest cartaSemanalRequest = new CartaSemanalRequest();
        cartaSemanalRequest.setNombre(nombre);
        cartaSemanalRequest.setCartasDelDia(cartasDelDia);
        return cartaSemanalRequest;
    }

    private void testQueryAndValidateCartaSemanalById(Long id, CartaSemanalRequest cartaSemanalRequest) {
        CartaSemanal cartaSemanal = this.cartaSemanalService.getById(id);

        assertNotNull(cartaSemanal);
        assertEquals(5, cartaSemanal.getCartas().size());

        Set<Long> requestCartasDelDiaIds = cartaSemanalRequest.getCartasDelDia().stream().map(CartaDelDia::getId).collect(Collectors.toSet());
        Set<Long> cartasDelDiaIds = cartaSemanal.getCartas().stream().map(CartaDelDia::getId).collect(Collectors.toSet());
        assertEquals(requestCartasDelDiaIds, cartasDelDiaIds);
    }

    @Test
    @Order(5)
    public void testUpdateCartaSemanal() {
        CartaDelDia cartaDelDia = this.cartaDelDiaService.save(createCartaDelDiaRequest("Menu Miercoles Comun", "Menu Miercoles Vegano", DiaSemana.LUNES));
        CartaSemanal cartaSemanal = this.cartaSemanalService.getAll().get(0);
        CartaSemanalRequest cartaSemanalRequest = new CartaSemanalRequest();
        cartaSemanalRequest.setNombre(cartaSemanal.getNombre());
        cartaSemanalRequest.setCartasDelDia(List.of(
                this.cartaDelDiaService.getById(cartaDelDia.getId()),
                this.cartaDelDiaService.getCartaDelDiaByDiaSemana(DiaSemana.MARTES).get(0),
                this.cartaDelDiaService.getCartaDelDiaByDiaSemana(DiaSemana.MIERCOLES).get(0),
                this.cartaDelDiaService.getCartaDelDiaByDiaSemana(DiaSemana.JUEVES).get(0),
                this.cartaDelDiaService.getCartaDelDiaByDiaSemana(DiaSemana.VIERNES).get(0)
        ));


        CartaSemanal cartaSemanalUpdate = this.cartaSemanalService.update(cartaSemanal.getId(), cartaSemanalRequest);
        this.testQueryAndValidateCartaSemanalById(cartaSemanalUpdate.getId(), cartaSemanalRequest);
    }


    @AfterAll
    public void deleteAll(){
        List<CartaDelDia> cartasDelDia = this.cartaDelDiaService.getAll();
        cartasDelDia.forEach(cartaDelDia -> this.cartaDelDiaService.delete(cartaDelDia.getId()));

        List<CartaSemanal> cartasSemanal = this.cartaSemanalService.getAll();
        cartasSemanal.forEach(cartaSemanal -> this.cartaSemanalService.delete(cartaSemanal.getId()));

        List<Comida> comidas = this.comidaService.getAll();
        comidas.forEach(comida -> this.comidaService.delete(comida.getId()));

        List<Menu> menus = this.menuService.getAll();
        menus.forEach(menu -> this.menuService.delete(menu.getId()));
    }



}
