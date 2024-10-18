package org.example.ttps2024grupo15.dao.usuario;

import org.example.ttps2024grupo15.dao.usuario.impl.ResponsableDeTurnoDAOHibernateJPA;
import org.example.ttps2024grupo15.controller.request.usuario.ResponsableDeTurnoRequest;
import org.example.ttps2024grupo15.model.usuario.ResponsableDeTurno;
import org.example.ttps2024grupo15.model.usuario.Turno;
import org.example.ttps2024grupo15.service.usuario.ResponsableDeTurnoService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResponsableDeTurnoServiceTest {
    private ResponsableDeTurnoDAOHibernateJPA responsableDeTurnoDAO;
    private ResponsableDeTurnoService responsableDeTurnoService;

    @BeforeAll
    public void setUp(){
        this.responsableDeTurnoDAO = new ResponsableDeTurnoDAOHibernateJPA();
        this.responsableDeTurnoService = new ResponsableDeTurnoService(responsableDeTurnoDAO);
    }

    @ParameterizedTest
    @MethodSource("createResponsableDeTurnoRequestWithData")
    @Order(1)
    public void testCreateResponsableDeTurnoTest(ResponsableDeTurnoRequest responsableDeTurnoRequest) {
        ResponsableDeTurno responsableDeTurno = this.responsableDeTurnoService.save(responsableDeTurnoRequest);
        this.testQueryAndValidateResponsableDeTurnoById(responsableDeTurno.getId(), responsableDeTurnoRequest);
    }

    private void testQueryAndValidateResponsableDeTurnoById(Long id, ResponsableDeTurnoRequest responsableDeTurnoRequest){
        ResponsableDeTurno responsableDeTurno = this.responsableDeTurnoService.getUserById(id);
        assertNotNull(responsableDeTurno);
        assertEquals(responsableDeTurnoRequest.getNombre(), responsableDeTurno.getNombre());
        assertEquals(responsableDeTurnoRequest.getApellido(), responsableDeTurno.getApellido());
        assertEquals(responsableDeTurnoRequest.getEmail(), responsableDeTurno.getEmail());
        assertEquals(responsableDeTurnoRequest.getDni(), responsableDeTurno.getDni());
    }

    @Test
    @Order(2)
    public void testMassiveQuery(){
        List<ResponsableDeTurno> responsableDeTurnoList = this.responsableDeTurnoService.getAll();
        assertNotNull(responsableDeTurnoList);
        assertEquals(3, responsableDeTurnoList.size());
    }

    @Test
    @Order(3)
    public void testQueryByRol(){
        List<ResponsableDeTurno> responsableDeTurnoList = this.responsableDeTurnoService.getResponsablesDeTurnoByTurno(Turno.TARDE);
        assertNotNull(responsableDeTurnoList);
        assertEquals(2, responsableDeTurnoList.size());
    }



    @Test
    @Order(4)
    public void testUpdateResponsableDeTurno(){
        ResponsableDeTurno responsableDeTurnoToUpdate = this.responsableDeTurnoService.getUserByEmail("roman@gmail.com");
        assertNotNull(responsableDeTurnoToUpdate);
        ResponsableDeTurnoRequest responsableDeTurnoModificationRequest = this.createResponsableDeTurnoRequest("Juan", responsableDeTurnoToUpdate.getApellido(), responsableDeTurnoToUpdate.getEmail(), responsableDeTurnoToUpdate.getDni(), Turno.MANANA);
        ResponsableDeTurno responsableDeTurnoUpdated = this.responsableDeTurnoService.update(responsableDeTurnoToUpdate.getId(), responsableDeTurnoModificationRequest);
        this.testQueryAndValidateResponsableDeTurnoById(responsableDeTurnoUpdated.getId(), responsableDeTurnoModificationRequest);
    }

    @Test
    @Order(5)
    public void testQueryByName(){
        List<ResponsableDeTurno> responsableDeTurnoList = this.responsableDeTurnoService.getUsersByName("No Existe");
        assertNotNull(responsableDeTurnoList);
        assertEquals(0, responsableDeTurnoList.size());
        responsableDeTurnoList = this.responsableDeTurnoService.getUsersByName("Juan");
        assertNotNull(responsableDeTurnoList);
        assertEquals(2, responsableDeTurnoList.size());
    }

    @Test
    @Order(6)
    public void testQueryByLastName(){
        List<ResponsableDeTurno> responsableDeTurnoList = this.responsableDeTurnoService.getUsersByLastName("No Existe");
        assertNotNull(responsableDeTurnoList);
        assertEquals(0, responsableDeTurnoList.size());
        responsableDeTurnoList = this.responsableDeTurnoService.getUsersByLastName("Gomez");
        assertNotNull(responsableDeTurnoList);
        assertEquals(1, responsableDeTurnoList.size());
    }

    @Test
    @Order(7)
    public void testQueryByEmail(){
        ResponsableDeTurno responsableDeTurno = this.responsableDeTurnoService.getUserByEmail("noexiste@email.com");
        assertNull(responsableDeTurno);
        responsableDeTurno  = this.responsableDeTurnoService.getUserByEmail("roman@gmail.com");
        assertNotNull(responsableDeTurno);
    }

    @Test
    @Order(8)
    public void testQueryByDni(){
        ResponsableDeTurno responsableDeTurno = this.responsableDeTurnoService.getUserByDni(333);
        assertNotNull(responsableDeTurno);
        assertEquals("Pedro", responsableDeTurno.getNombre());
        assertEquals("Gonzalez", responsableDeTurno.getApellido());
        assertEquals(Turno.TARDE, responsableDeTurno.getTurno());
    }


    @AfterAll
    public void deletaAll(){
        List<ResponsableDeTurno> responsableDeTurnoList = this.responsableDeTurnoService.getAll();
        responsableDeTurnoList.forEach(responsableDeTurno -> this.responsableDeTurnoService.delete(responsableDeTurno.getId()));
        responsableDeTurnoList = this.responsableDeTurnoService.getAll();
        assertNotNull(responsableDeTurnoList);
        assertEquals(0, responsableDeTurnoList.size());
    }

    private Stream<Arguments> createResponsableDeTurnoRequestWithData() {
        return Stream.of(
                Arguments.of(this.createResponsableDeTurnoRequest("Roman", "Gomez", "roman@gmail.com",11111,Turno.TARDE)),
                Arguments.of(this.createResponsableDeTurnoRequest("Juan", "Perez", "juan@gmail.com", 2222, Turno.MANANA)),
                Arguments.of(this.createResponsableDeTurnoRequest("Pedro", "Gonzalez", "pedro@gmail.com", 333, Turno.TARDE))
        );
    }
    private ResponsableDeTurnoRequest createResponsableDeTurnoRequest(String nombre, String apellido, String email, int dni, Turno turno){
        ResponsableDeTurnoRequest responsableDeTurnoRequest = new ResponsableDeTurnoRequest();
        responsableDeTurnoRequest.setNombre(nombre);
        responsableDeTurnoRequest.setApellido(apellido);
        responsableDeTurnoRequest.setEmail(email);
        responsableDeTurnoRequest.setDni(dni);
        responsableDeTurnoRequest.setTurno(turno);
        return responsableDeTurnoRequest;
    }
}
