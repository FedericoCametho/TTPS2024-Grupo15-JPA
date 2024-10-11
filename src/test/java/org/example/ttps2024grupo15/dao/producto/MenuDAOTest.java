package org.example.ttps2024grupo15.dao.producto;

import org.example.ttps2024grupo15.dao.menu.impl.ComidaDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.menu.impl.MenuDAOHibernateJPA;
import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.carta.producto.Menu;
import org.example.ttps2024grupo15.model.carta.producto.TipoComida;
import org.example.ttps2024grupo15.model.request.menu.ComidaRequest;
import org.example.ttps2024grupo15.model.request.menu.MenuRequest;
import org.example.ttps2024grupo15.service.menu.ComidaService;
import org.example.ttps2024grupo15.service.menu.MenuService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MenuDAOTest {
    private MenuDAOHibernateJPA menuDAO;
    private MenuService menuService;
    private ComidaDAOHibernateJPA comidaDAO;
    private ComidaService comidaService;

    @BeforeAll
    public void setUp(){
        this.menuDAO = new MenuDAOHibernateJPA();

        this.comidaDAO = new ComidaDAOHibernateJPA();
        this.comidaService = new ComidaService(comidaDAO);

        this.menuService = new MenuService(menuDAO, comidaService);
    }

    @ParameterizedTest
    @MethodSource("createMenuRequestWithData")
    @Order(2)
    public void testCreateMenu(MenuRequest menuRequest) {
        Menu menu = this.menuService.saveMenu(menuRequest);
        this.testQueryAndValidateMenuById(menu.getId(), menuRequest);
    }
    private void testQueryAndValidateMenuById(Long id, MenuRequest menuRequest) {
        Menu menu = this.menuService.getMenuById(id);
        assertNotNull(menu);
        assertEquals(menuRequest.getNombre(), menu.getNombre());
        assertEquals(menuRequest.getPrecio(), menu.getPrecio());
        assertEquals(menuRequest.getComidas().size(), menu.getComidas().size());
        assertEquals(menuRequest.getImagen(), menu.getFoto());
    }

    @Test
    @Order(3)
    public void testGetAllMenus() {
        List<Menu> menus = this.menuService.getAllMenues();
        assertNotNull(menus);
        assertEquals(2, menus.size());
    }

    @Test
    @Order(4)
    public void testGetByNombre() {
        List<Menu> menus = this.menuService.getMenuesByNombre("No existe");
        assertNotNull(menus);
        assertEquals(0, menus.size());

        menus = this.menuService.getMenuesByNombre("Menu Lunes Comun");
        assertNotNull(menus);
        assertEquals(1, menus.size());
        Menu menu = menus.get(0);
        assertEquals("Menu Lunes Comun", menu.getNombre());
        assertEquals(4500.0, menu.getPrecio());
        assertEquals(3, menu.getComidas().size());
    }

    @Test
    @Order(5)
    public void testUpdateMenu(){
        List<Menu> menus = this.menuService.getMenuesByNombre("Menu Lunes Comun");
        assertNotNull(menus);
        assertEquals(1, menus.size());
        Menu menu = menus.get(0);
        MenuRequest menuRequestUpdate = this.createMenuRequest(menu.getNombre(), 3500.0,menu.getComidas());
        this.menuService.update(menu.getId(), menuRequestUpdate);
        this.testQueryAndValidateMenuById(menu.getId(), menuRequestUpdate);
    }

    @Test
    @Order(6)
    public void testGetByPrecio() {
        List<Menu> menus = this.menuService.getMenuesByPrecio(Double.valueOf("0.00"));
        assertNotNull(menus);
        assertEquals(0, menus.size());

        menus = this.menuService.getMenuesByPrecio(Double.valueOf("3500.00"));
        assertNotNull(menus);
        assertEquals(2, menus.size());
    }
    @Test
    @Order(7)
    public void testDeleteAllMenuOk(){
        List<Menu> menues = this.menuService.getAllMenues();
        List<Comida> comidas = menues.stream().flatMap(menu -> menu.getComidas().stream()).collect(Collectors.toList());
        for(Comida comida : comidas){
            this.comidaService.deleteComida(comida);
        }
        for(Menu menu : menues){
            this.menuService.deleteMenu(menu.getId());
        }
        assertEquals(0, this.menuService.getAllMenues().size());
    }


    private Stream<Arguments> createMenuRequestWithData() {
        return Stream.of(
                Arguments.of(this.createMenuRequest("Menu Lunes Comun", 4500.0, List.of(
                                this.comidaService.getComidasByNombre("Milanesa").get(0),
                                this.comidaService.getComidasByNombre("Ensalada").get(0),
                                this.comidaService.getComidasByNombre("Helado").get(0))
                        )),
                Arguments.of(this.createMenuRequest("Menu Lunes Vegano", 3500.0, List.of(
                        this.comidaService.getComidasByNombre("Pasta").get(0),
                        this.comidaService.getComidasByNombre("Ensalada").get(0),
                        this.comidaService.getComidasByNombre("Manzana").get(0))
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
    @MethodSource("createComidaRequestWithData")
    @Order(1)
    public void createComidas(ComidaRequest comidaRequest) {
        this.comidaService.saveComida(comidaRequest);
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

}
