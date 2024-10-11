package org.example.ttps2024grupo15.dao.usuario;

import org.example.ttps2024grupo15.dao.usuario.impl.AlumnoDAOHibernateJPA;
import org.example.ttps2024grupo15.service.usuario.AlumnoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlumnoDAO {
    private AlumnoDAOHibernateJPA alumnoDAO;
    private AlumnoService alumnoService;

    @BeforeAll
    public void setUp(){
        this.alumnoDAO = new AlumnoDAOHibernateJPA();
        this.alumnoService = new AlumnoService(alumnoDAO);
    }



}
