package org.example.ttps2024grupo15.dao.carta;

import org.example.ttps2024grupo15.dao.carta.impl.CartaDelDiaDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.menu.impl.ComidaDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.menu.impl.MenuDAOHibernateJPA;
import org.example.ttps2024grupo15.model.carta.CartaDelDia;
import org.example.ttps2024grupo15.model.carta.DiaSemana;
import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.carta.producto.Menu;
import org.example.ttps2024grupo15.model.carta.producto.TipoComida;
import org.example.ttps2024grupo15.model.request.carta.CartaDelDiaRequest;
import org.example.ttps2024grupo15.model.request.carta.menu.ComidaRequest;
import org.example.ttps2024grupo15.model.request.carta.menu.MenuRequest;
import org.example.ttps2024grupo15.service.carta.CartaDelDiaService;
import org.example.ttps2024grupo15.service.carta.menu.ComidaService;
import org.example.ttps2024grupo15.service.carta.menu.MenuService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartaDelDiaServiceTest {
    private CartaDelDiaDAOHibernateJPA cartaDelDiaDAO;
    private CartaDelDiaService cartaDelDiaService;
    private ComidaService comidaService;
    private ComidaDAOHibernateJPA comidaDAO;
    private MenuService menuService;
    private MenuDAOHibernateJPA menuDAO;

    @BeforeAll
    public void setUp(){
        this.cartaDelDiaDAO = new CartaDelDiaDAOHibernateJPA();
        this.cartaDelDiaService = new CartaDelDiaService(cartaDelDiaDAO);
        this.comidaDAO = new ComidaDAOHibernateJPA();
        this.comidaService = new ComidaService(comidaDAO);
        this.menuDAO = new MenuDAOHibernateJPA();
        this.menuService = new MenuService(menuDAO, comidaService);
    }
    /*

    @ParameterizedTest
    @MethodSource("createCartaDelDiaRequestWithData")
    @Order(3)
    public void testCreateCartaDelDiaTest(CartaDelDiaRequest cartaDelDiaRequest) {
        CartaDelDia cartaDelDia = this.cartaDelDiaService.save(cartaDelDiaRequest);
        this.testQueryAndValidateCartaDelDiaById(cartaDelDia.getId(), cartaDelDiaRequest);
    }

    private void testQueryAndValidateCartaDelDiaById(Long id, CartaDelDiaRequest cartaDelDiaRequest) {
        CartaDelDia cartaDelDia = this.cartaDelDiaService.getById(id);
        assertNotNull(cartaDelDia);
        assertEquals(cartaDelDiaRequest.getDiaSemana(), cartaDelDia.getDiaSemana());
        assertEquals(cartaDelDiaRequest.getMenuVegetariano().getId(), cartaDelDia.getMenuVegetariano().getId());
        assertEquals(cartaDelDiaRequest.getMenuComun().getId(), cartaDelDia.getMenuComun().getId());
        assertEquals(cartaDelDiaRequest.getFechaInicio(), cartaDelDia.getFechaInicio());
        assertEquals(cartaDelDiaRequest.getFechaFin(), cartaDelDia.getFechaFin());
        assertEquals(cartaDelDiaRequest.isActiva(), cartaDelDia.isActiva());
    }

    @Test
    @Order(4)
    public void getAllCartasDelDia() {
        List<CartaDelDia> cartasDelDia = this.cartaDelDiaService.getAll();
        assertNotNull(cartasDelDia);

    }

    @AfterAll
    public void deleteAll(){
        List<Menu> menues = this.menuService.getAll();
        List<Comida> comidas = menues.stream().flatMap(menu -> menu.getComidas().stream()).collect(Collectors.toList());
        for(Comida comida : comidas){
            this.comidaService.delete(comida);
        }
        List<CartaDelDia> cartasDelDia = this.cartaDelDiaService.getAll();
        for(CartaDelDia cartaDelDia : cartasDelDia){
            this.cartaDelDiaService.delete(cartaDelDia.getId());
        }
        cartasDelDia = this.cartaDelDiaService.getAll();
        assertEquals(0, cartasDelDia.size());

        for(Menu menu : menues){
            this.menuService.delete(menu.getId());
        }




    }


    private Stream<Arguments> createCartaDelDiaRequestWithData() {
        return Stream.of(
                Arguments.of(createCartaDelDiaRequest(DiaSemana.LUNES, LocalDate.of(2024,11,11), LocalDate.of(2024,11,18), true)),
                Arguments.of(createCartaDelDiaRequest(DiaSemana.MARTES, LocalDate.of(2024,11,12), LocalDate.of(2024,11,19), true)),
                Arguments.of(createCartaDelDiaRequest(DiaSemana.MIERCOLES, LocalDate.of(2024,11,13), LocalDate.of(2024,11,20), true)),
                Arguments.of(createCartaDelDiaRequest(DiaSemana.JUEVES, LocalDate.of(2024,11,14), LocalDate.of(2024,11,21), true)),
                Arguments.of(createCartaDelDiaRequest(DiaSemana.VIERNES, LocalDate.of(2024,11,15), LocalDate.of(2024,11,22), true)),
                Arguments.of(createCartaDelDiaRequest(DiaSemana.LUNES, LocalDate.of(2024,11,16), LocalDate.of(2024,11,23), true)),
                Arguments.of(createCartaDelDiaRequest(DiaSemana.MARTES, LocalDate.of(2024,11,17), LocalDate.of(2024,11,24), true))
        );
    }

    public CartaDelDiaRequest createCartaDelDiaRequest(DiaSemana diaSemana, LocalDate fechaInicio, LocalDate fechaFin, boolean activa) {
        CartaDelDiaRequest cartaDelDiaRequest = new CartaDelDiaRequest();
        cartaDelDiaRequest.setDiaSemana(diaSemana);
        cartaDelDiaRequest.setFechaInicio(fechaInicio);
        cartaDelDiaRequest.setFechaFin(fechaFin);
        cartaDelDiaRequest.setActiva(activa);
        cartaDelDiaRequest.setMenuComun(this.menuService.getProductsByName("Menu Comun").get(0));
        cartaDelDiaRequest.setMenuVegetariano(this.menuService.getProductsByName("Menu Vegano").get(0));
        return cartaDelDiaRequest;
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
        this.menuService.save(menuRequest);
    }
    private Stream<Arguments> createMenuRequestWithData() {
        return Stream.of(
                Arguments.of(this.createMenuRequest("Menu Comun", 4500.0, List.of(
                        this.comidaService.getProductsByName("Milanesa").get(0),
                        this.comidaService.getProductsByName("Ensalada").get(0),
                        this.comidaService.getProductsByName("Helado").get(0))
                )),
                Arguments.of(this.createMenuRequest("Menu Vegano", 3500.0, List.of(
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

*/
}
