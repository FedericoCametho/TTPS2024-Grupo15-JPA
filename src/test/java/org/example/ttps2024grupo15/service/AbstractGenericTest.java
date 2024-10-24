package org.example.ttps2024grupo15.service;

import org.example.ttps2024grupo15.controller.request.carta.producto.ComidaRequest;
import org.example.ttps2024grupo15.controller.request.carta.producto.MenuRequest;
import org.example.ttps2024grupo15.controller.request.sugerencia.SugerenciaRequest;
import org.example.ttps2024grupo15.controller.request.usuario.AlumnoRequest;
import org.example.ttps2024grupo15.dao.carrito.CompraDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.carta.producto.impl.ComidaDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.carta.producto.impl.MenuDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.sugerencia.SugerenciaDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.usuario.impl.AlumnoDAOHibernateJPA;
import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.carta.producto.TipoComida;
import org.example.ttps2024grupo15.model.sugerencia.TipoSugerencia;
import org.example.ttps2024grupo15.service.carrito.CompraService;
import org.example.ttps2024grupo15.service.carta.producto.ComidaService;
import org.example.ttps2024grupo15.service.carta.producto.MenuService;
import org.example.ttps2024grupo15.service.sugerencia.SugerenciaService;
import org.example.ttps2024grupo15.service.usuario.AlumnoService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractGenericTest {

    protected MenuDAOHibernateJPA menuDAO;
    protected MenuService menuService;

    protected ComidaDAOHibernateJPA comidaDAO;
    protected ComidaService comidaService;

    protected AlumnoService alumnoService;
    protected AlumnoDAOHibernateJPA alumnoDAO;

    protected CompraDAOHibernateJPA compraDAO;
    protected CompraService compraService;
    protected SugerenciaDAOHibernateJPA sugerenciaDAO;
    protected SugerenciaService sugerenciaService;

    @BeforeAll
    public void setUp(){

        this.alumnoDAO = new AlumnoDAOHibernateJPA();
        this.alumnoService = new AlumnoService(alumnoDAO);

        this.sugerenciaDAO = new SugerenciaDAOHibernateJPA();
        this.sugerenciaService = new SugerenciaService(sugerenciaDAO, this.alumnoService);

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


    @AfterAll
    public void deleteAllTest() {
        this.sugerenciaService.getAll().forEach(sugerencia ->
                this.sugerenciaService.delete(sugerencia.getId()));

        this.compraService.getAll().forEach(compra ->
            this.compraService.delete(compra.getId()));

        this.comidaService.getAll().forEach(comida ->
            this.comidaService.delete(comida.getId()));

        this.menuService.getAll().forEach(menu ->
            this.menuService.delete(menu.getId()));

        this.alumnoService.getAll().forEach(alumno ->
            this.alumnoService.delete(alumno.getId()));
    }




    protected AlumnoRequest createAlumnoRequest(String nombre, String apellido, String email, Integer dni) {
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
    protected MenuRequest createMenuRequest(String nombre, Double precio, List<Comida> comidasList) {
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

    protected ComidaRequest createComidaRequest(String nombre, Double precio, TipoComida tipoComida) {
        ComidaRequest comidaRequest = new ComidaRequest();
        comidaRequest.setNombre(nombre);
        comidaRequest.setPrecio(precio);
        comidaRequest.setTipoComida(tipoComida);
        return comidaRequest;
    }

    protected SugerenciaRequest createSugerenciaRequest(String titulo, TipoSugerencia tipoSugerencia, String mensajeOriginal, int alumnoDni) {
        SugerenciaRequest sugerenciaRequest = new SugerenciaRequest();
        sugerenciaRequest.setTitulo(titulo);
        sugerenciaRequest.setTipoSugerencia(tipoSugerencia);
        sugerenciaRequest.setMensajeOriginal(mensajeOriginal);
        sugerenciaRequest.setAlumnoId(this.alumnoService.getUserByDni(alumnoDni).getId());
        return sugerenciaRequest;
    }

}
