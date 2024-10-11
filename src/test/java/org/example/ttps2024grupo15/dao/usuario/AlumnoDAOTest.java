package org.example.ttps2024grupo15.dao.usuario;

import org.example.ttps2024grupo15.dao.usuario.impl.AlumnoDAOHibernateJPA;
import org.example.ttps2024grupo15.model.request.usuario.AlumnoRequest;
import org.example.ttps2024grupo15.model.usuario.Alumno;
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
public class AlumnoDAOTest {
    private AlumnoDAOHibernateJPA alumnoDAO;
    private AlumnoService alumnoService;

    @BeforeAll
    public void setUp(){
        this.alumnoDAO = new AlumnoDAOHibernateJPA();
        this.alumnoService = new AlumnoService(alumnoDAO);
    }

    @ParameterizedTest
    @MethodSource("createAlumnoRequestWithData")
    @Order(1)
    public void testCreateAlumnoTest(AlumnoRequest alumnoRequest) {
        Alumno alumno = this.alumnoService.save(alumnoRequest);
        this.testQueryAndValidateAlumnoById(alumno.getId(), alumnoRequest);
    }

    private void testQueryAndValidateAlumnoById(Long id, AlumnoRequest alumnoRequest) {
        Alumno alumno = this.alumnoService.getUserById(id);
        assertNotNull(alumno);
        assertEquals(alumnoRequest.getNombre(), alumno.getNombre());
        assertEquals(alumnoRequest.getApellido(), alumno.getApellido());
        assertEquals(alumnoRequest.getEmail(), alumno.getEmail());
        assertEquals(alumnoRequest.getDni(), alumno.getDni());
    }

    @Test
    @Order(2)
    public void updateAlumnoTest(){
        Alumno alumnoToUpdate = this.alumnoService.getUserByEmail("roman@gmail.com");
        assertNotNull(alumnoToUpdate);
        AlumnoRequest alumnoModificationRequest = this.createAlumnoRequest("Juan Roman", alumnoToUpdate.getApellido(), alumnoToUpdate.getEmail(), alumnoToUpdate.getDni());
        Alumno alumnoUpdated = this.alumnoService.update(alumnoToUpdate.getId(), alumnoModificationRequest);
        this.testQueryAndValidateAlumnoById(alumnoUpdated.getId(), alumnoModificationRequest);
    }

    @Test
    @Order(3)
    public void massiveQueryByRol() {
        List<Alumno> alumnos = this.alumnoService.getAll();
        assertNotNull(alumnos);
        assertEquals(3, alumnos.size());

        List<Alumno> alumnosPorRol = this.alumnoService.getAlumnosPorRol();
        assertNotNull(alumnos);
        assertEquals(3, alumnos.size());
    }

    @AfterAll
    public void deleteAllAlumnos() {
        List<Alumno> alumnos = this.alumnoService.getAll();
        alumnos.forEach(alumno -> this.alumnoService.delete(alumno.getId()));
        alumnos = this.alumnoService.getAll();
        assertNotNull(alumnos);
        assertEquals(0, alumnos.size());
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
        alumnoRequest.setFotoDePerfil(null);
        return alumnoRequest;
    }

}
