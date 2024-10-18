package org.example.ttps2024grupo15.dao.usuario;

import org.example.ttps2024grupo15.dao.usuario.impl.AdministradorDAOHibernateJPA;
import org.example.ttps2024grupo15.model.permiso.Rol;
import org.example.ttps2024grupo15.model.request.usuario.AdministradorRequest;
import org.example.ttps2024grupo15.model.usuario.Administrador;
import org.example.ttps2024grupo15.service.usuario.AdministradorService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdministradorServiceTest {
    private AdministradorDAOHibernateJPA administradorDAOHibernateJPA;
    private AdministradorService administradorService;

    @BeforeAll
    public void setUp(){
        administradorDAOHibernateJPA = new AdministradorDAOHibernateJPA();
        administradorService = new AdministradorService(administradorDAOHibernateJPA);
    }


    @ParameterizedTest
    @MethodSource("createAdministradorRequestWithData")
    @Order(1)
    public void createAdministradorTest(AdministradorRequest administradorRequest) {
        Administrador administrador = administradorService.save(administradorRequest);
        queryAndValidateAdministradorById(administrador.getId(), administradorRequest);
    }

    public void queryAndValidateAdministradorById(Long id, AdministradorRequest administradorRequest){
        Administrador administrador = administradorService.getUserById(id);
        assertNotNull(administrador);
        assertEquals(administradorRequest.getNombre(), administrador.getNombre());
        assertEquals(administradorRequest.getApellido(), administrador.getApellido());
        assertEquals(administradorRequest.getEmail(), administrador.getEmail());
        assertEquals(administradorRequest.getDni(), administrador.getDni());
    }

    @Test
    @Order(2)
    public void testMassiveQuery(){
        List<Administrador> administradorList = administradorService.getAll();
        assertNotNull(administradorList);
        assertEquals(3, administradorList.size());
    }

    @Test
    @Order(3)
    public void testQueryByDni(){
        assertThrows(IllegalArgumentException.class, () -> administradorService.getUserByDni(null));
        Administrador administrador = administradorService.getUserByDni(94949949);
        assertNull(administrador);
        administrador = administradorService.getUserByDni(2222222);
        assertNotNull(administrador);
        assertEquals("Juan Roman", administrador.getNombre());
        assertEquals("Riquelme", administrador.getApellido());
        assertEquals(Rol.ADMINISTRADOR, administrador.getRol());
    }
    @Test
    @Order(4)
    public void testQueryByRol(){
        List<Administrador> administradorList = administradorService.getUserByRol(Rol.ALUMNO);
        assertEquals(0, administradorList.size());
        administradorList = administradorService.getUserByRol(Rol.ADMINISTRADOR);
        assertNotNull(administradorList);
        assertEquals(3, administradorList.size());
    }
    @Test
    @Order(5)
    public void testQueryByName(){
        List<Administrador> adminsitradorList = administradorService.getUsersByName("No Existe");
        assertNotNull(adminsitradorList);
        assertEquals(0, adminsitradorList.size());
        adminsitradorList = administradorService.getUsersByName("Juan Sebastian");
        assertNotNull(adminsitradorList);
        assertEquals(1, adminsitradorList.size());
    }

    @Test
    @Order(6)
    public void testQueryByLastName(){
        List<Administrador> administradorList = administradorService.getUsersByLastName("No Existe");
        assertNotNull(administradorList);
        assertEquals(0, administradorList.size());
        administradorList = administradorService.getUsersByLastName("Veron");
        assertNotNull(administradorList);
        assertEquals(1, administradorList.size());
    }

    @Test
    @Order(7)
    public void testQueryByEmail() {
        Administrador administrador = administradorService.getUserByEmail("noexiste@email.com");
        assertNull(administrador);
        administrador = administradorService.getUserByEmail("jsv@gmail.com");
        assertNotNull(administrador);
        assertEquals("Juan Sebastian", administrador.getNombre());
        assertEquals("Veron", administrador.getApellido());
    }

    @Test
    @Order(8)
    public void testUpdateAdministrador(){
        Administrador administradorToUpdate = administradorService.getUserByEmail("jsv@gmail.com");
        assertNotNull(administradorToUpdate);
        AdministradorRequest administradorModificationRequest = this.createAdministradorRequest("La Brujita", administradorToUpdate.getApellido(), administradorToUpdate.getEmail(), administradorToUpdate.getDni());
        Administrador administradorUpdated = administradorService.update(administradorToUpdate.getId(), administradorModificationRequest);
        this.queryAndValidateAdministradorById(administradorUpdated.getId(), administradorModificationRequest);
    }

    @Test
    @Order(9)
    public void testDeleteAdministrador(){
        Administrador administrador = administradorService.getUserByEmail("jsv@gmail.com");
        assertNotNull(administrador);
        administradorService.delete(administrador);
        administrador = administradorService.getUserByEmail("jsv@gmail.com");
        assertNull(administrador);
    }
    @Test
    @Order(10)
    public void testDeleteAdministradorByID(){
        Administrador administrador = administradorService.getUserByEmail("enzo@gmail.com");
        assertNotNull(administrador);
        administradorService.delete(administrador.getId());
        administrador = administradorService.getUserByEmail("enzo@gmail.com");
        assertNull(administrador);
    }


    @AfterAll
    public void deleteAll(){
        List<Administrador> administradorList = administradorService.getAll();
        administradorList.forEach(administrador -> administradorService.delete(administrador));
        administradorList = administradorService.getAll();
        assertNotNull(administradorList);
        assertEquals(0, administradorList.size());
    }


    private Stream<Arguments> createAdministradorRequestWithData() {
        return Stream.of(
                Arguments.of(this.createAdministradorRequest("Juan Sebastian", "Veron", "jsv@gmail.com", 12345678)),
                Arguments.of(this.createAdministradorRequest("Enzo", "Perez", "enzo@gmail.com", 33333333)),
                Arguments.of(this.createAdministradorRequest("Juan Roman", "Riquelme", "jrr@gmail.com", 2222222)));
    }

    private AdministradorRequest createAdministradorRequest(String nombre, String apellido, String email, Integer dni ){
        AdministradorRequest administradorRequest = new AdministradorRequest();
        administradorRequest.setNombre(nombre);
        administradorRequest.setApellido(apellido);
        administradorRequest.setEmail(email);
        administradorRequest.setDni(dni);
        return administradorRequest;
    }

}
