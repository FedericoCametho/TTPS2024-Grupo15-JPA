package org.example.ttps2024grupo15.dao.usuario.impl;


import jakarta.persistence.EntityManager;
import org.example.ttps2024grupo15.dao.GenericDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.entitiManager.EMF;
import org.example.ttps2024grupo15.dao.usuario.AlumnoDAO;
import org.example.ttps2024grupo15.model.permiso.Rol;
import org.example.ttps2024grupo15.model.usuario.Alumno;

import java.util.List;

public class AlumnoDAOHibernateJPA extends UsuarioDAOHibernateJPA<Alumno> implements AlumnoDAO {


    public AlumnoDAOHibernateJPA() {
        super(Alumno.class);
    }


    @Override
    public List<Alumno> getByHabilitado() {
        return EMF.getEMF().createEntityManager()
                .createQuery("SELECT a FROM Alumno a WHERE a.habilitado = :habilitado", Alumno.class).setParameter("habilitado", true).getResultList();
    }

}

