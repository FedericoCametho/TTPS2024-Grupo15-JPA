package org.example.ttps2024grupo15.dao.sugerencia;

import org.example.ttps2024grupo15.controller.request.sugerencia.SugerenciaRequest;
import org.example.ttps2024grupo15.controller.request.usuario.AlumnoRequest;
import org.example.ttps2024grupo15.dao.usuario.impl.AlumnoDAOHibernateJPA;
import org.example.ttps2024grupo15.model.sugerencia.Sugerencia;
import org.example.ttps2024grupo15.model.sugerencia.TipoSugerencia;
import org.example.ttps2024grupo15.model.usuario.Alumno;
import org.example.ttps2024grupo15.service.sugerencia.SugerenciaService;
import org.example.ttps2024grupo15.service.usuario.AlumnoService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SugerenciaServiceTest {

    private SugerenciaDAOHibernateJPA sugerenciaDAO;
    private SugerenciaService sugerenciaService;
    private AlumnoService alumnoService;
    private AlumnoDAOHibernateJPA alumnoDAO;

    @BeforeAll
    public void setUp(){
        this.sugerenciaDAO = new SugerenciaDAOHibernateJPA();
        this.alumnoDAO = new AlumnoDAOHibernateJPA();
        this.alumnoService = new AlumnoService(alumnoDAO);
        this.sugerenciaService = new SugerenciaService(sugerenciaDAO, alumnoService);
    }
    @ParameterizedTest
    @MethodSource("createSugerenciaRequestWithData")
    @Order(2)
    public void testCreateSugerencia(SugerenciaRequest sugerenciaRequest) {
        Sugerencia sugerencia = this.sugerenciaService.save(sugerenciaRequest);
        this.testQueryAndValidateSugerenciaById(sugerencia.getId(), sugerenciaRequest);
    }

    private void testQueryAndValidateSugerenciaById(Long id, SugerenciaRequest sugerenciaRequest) {
        Sugerencia sugerencia = this.sugerenciaService.getById(id);
        assertNotNull(sugerencia);
        assertEquals(sugerenciaRequest.getTitulo(), sugerencia.getTitulo());
        assertEquals(sugerenciaRequest.getTipoSugerencia(), sugerencia.getTipo());
        assertEquals(sugerenciaRequest.getMensajeOriginal(), sugerencia.getMensajeOriginal());
        assertEquals(sugerenciaRequest.getAlumnoId(), sugerencia.getUsuario().getId());
    }

    @Test
    @Order(3)
    public void getAllSugerenciasTest(){
        List<Sugerencia> sugerencias = this.sugerenciaService.getAll();
        assertNotNull(sugerencias);
        assertEquals(3, sugerencias.size());
    }

    @Test
    @Order(4)
    public void getSugerenciaByTipoTest(){
        List<Sugerencia> sugerencias = this.sugerenciaService.getSugerenciaByTipo(TipoSugerencia.ALIMENTOS);
        assertNotNull(sugerencias);
        assertEquals(2, sugerencias.size());

    }

    @AfterAll
    public void deleteAllTest(){
        List<Sugerencia> sugerencias = this.sugerenciaService.getAll();
        sugerencias.forEach(sugerencia -> this.sugerenciaService.delete(sugerencia.getId()));
        sugerencias = this.sugerenciaService.getAll();
        assertNotNull(sugerencias);
        assertEquals(0,sugerencias.size());
        List<Alumno> alumnos = this.alumnoService.getAll();
        alumnos.forEach(alumno -> this.alumnoService.delete(alumno.getId()));
    }

    private Stream<Arguments> createSugerenciaRequestWithData() {
        return Stream.of(
                Arguments.of(this.createSugerenciaRequest("titulo1", TipoSugerencia.ALIMENTOS, "mensaje1", 1111111)),
                Arguments.of(this.createSugerenciaRequest("titulo2", TipoSugerencia.ALIMENTOS, "mensaje2", 22222222)),
                Arguments.of(this.createSugerenciaRequest("titulo3", TipoSugerencia.INFRAESTRUCTURA, "mensaje3", 33333333))
        );
    }

    private SugerenciaRequest createSugerenciaRequest(String titulo, TipoSugerencia tipoSugerencia, String mensajeOriginal, int alumnoDni) {
        SugerenciaRequest sugerenciaRequest = new SugerenciaRequest();
        sugerenciaRequest.setTitulo(titulo);
        sugerenciaRequest.setTipoSugerencia(tipoSugerencia);
        sugerenciaRequest.setMensajeOriginal(mensajeOriginal);
        sugerenciaRequest.setAlumnoId(this.alumnoService.getUserByDni(alumnoDni).getId());
        return sugerenciaRequest;
    }
    @ParameterizedTest
    @MethodSource("createAlumnoRequestWithData")
    @Order(1)
    public void createAlumnos(AlumnoRequest alumnoRequest){
        this.alumnoService.save(alumnoRequest);
    }

    private Stream<Arguments> createAlumnoRequestWithData() {
        return Stream.of(
                Arguments.of(this.createAlumnoRequest("Chicho","Siesta","chicho@gmail.com",1111111)),
                Arguments.of(this.createAlumnoRequest("Roman","Riquelme","roman@gmail.com",22222222)),
                Arguments.of(this.createAlumnoRequest("Enzo","Perez","enzo@gmail.com",33333333))
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




}
