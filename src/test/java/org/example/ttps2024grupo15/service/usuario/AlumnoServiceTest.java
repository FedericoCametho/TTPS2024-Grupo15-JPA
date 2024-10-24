package org.example.ttps2024grupo15.service.usuario;

import org.example.ttps2024grupo15.model.permiso.Rol;
import org.example.ttps2024grupo15.controller.request.usuario.AlumnoRequest;
import org.example.ttps2024grupo15.model.usuario.Alumno;
import org.example.ttps2024grupo15.service.AbstractGenericTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.MethodSource;


import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlumnoServiceTest extends AbstractGenericTest {

    @Test
    @Order(1)
    public void testCreateAlumnoTest() {
        AlumnoRequest alumnoRequest = this.createAlumnoRequest("Mateo", "Retegol", "retegol@gmail.com", 777777);
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
        AlumnoRequest alumnoModificationRequest = this.createAlumnoRequest("Enzo", alumnoToUpdate.getApellido(), alumnoToUpdate.getEmail(), alumnoToUpdate.getDni());
        alumnoModificationRequest.setHabilitado(false);
        Alumno alumnoUpdated = this.alumnoService.update(alumnoToUpdate.getId(), alumnoModificationRequest);
        this.testQueryAndValidateAlumnoById(alumnoUpdated.getId(), alumnoModificationRequest);
    }

    @Test
    @Order(3)
    public void queryAlumnoByNameTest(){
        List<Alumno> alumnos = this.alumnoService.getUsersByName("No Existe");
        assertNotNull(alumnos);
        assertEquals(0, alumnos.size());
        alumnos = this.alumnoService.getUsersByName("Enzo");
        assertNotNull(alumnos);
        assertEquals(2, alumnos.size());
    }

    @Test
    @Order(4)
    public void massiveQueryByRol() {
        List<Alumno> alumnos = this.alumnoService.getAll();
        assertNotNull(alumnos);
        assertEquals(4, alumnos.size());

        List<Alumno> alumnosPorRol = this.alumnoService.getUserByRol(Rol.ALUMNO);
        assertNotNull(alumnosPorRol);
        assertEquals(4, alumnosPorRol.size());
    }

    @Test
    @Order(5)
    public void queryAlumnossByLastName(){
        List<Alumno> alumnos = this.alumnoService.getUsersByLastName("No Existe");
        assertNotNull(alumnos);
        alumnos = this.alumnoService.getUsersByLastName("Siesta");
        assertNotNull(alumnos);
        assertEquals(1, alumnos.size());
    }

    @Test
    @Order(6)
    public void queryAlumnosByEnabled(){
        List<Alumno> alumnos = this.alumnoService.getAlumnosByEnabled();
        assertNotNull(alumnos);
        assertEquals(3, alumnos.size());
    }
    @Test
    @Order(7)
    public void testQueryByDni(){
        Alumno alumno = this.alumnoService.getUserByDni(1111111);
        assertNotNull(alumno);
        assertEquals("Chicho", alumno.getNombre());
        assertEquals("Siesta", alumno.getApellido());
        assertEquals(Rol.ALUMNO, alumno.getRol());
    }


    @Test
    @Order(8)
    public void deleteAlumnoTest(){
        List<Alumno> alumnos = this.alumnoService.getAll();
        assertNotNull(alumnos);
        assertEquals(4, alumnos.size());
        Alumno alumnoToDelete = alumnos.get(0);
        this.alumnoService.delete(alumnoToDelete.getId());
        alumnos = this.alumnoService.getAll();
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

}
