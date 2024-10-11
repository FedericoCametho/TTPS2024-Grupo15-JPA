package org.example.ttps2024grupo15.dao.entitiManager;

import org.example.ttps2024grupo15.dao.usuario.AlumnoDAO;
import org.example.ttps2024grupo15.dao.usuario.impl.AlumnoDAOHibernateJPA;

public class DAOFactory {
    public static AlumnoDAO getUsuarioDAO() {
        return new AlumnoDAOHibernateJPA();
    }

}

