package org.example.ttps2024grupo15.dao.usuario.impl;


import jakarta.persistence.EntityManager;
import org.example.ttps2024grupo15.dao.entitiManager.EMF;
import org.example.ttps2024grupo15.dao.usuario.AlumnoDAO;

import org.example.ttps2024grupo15.model.usuario.Alumno;

import java.util.List;
import java.util.logging.Logger;

public class AlumnoDAOHibernateJPA extends UsuarioDAOHibernateJPA<Alumno> implements AlumnoDAO {
    private static final Logger LOGGER = Logger.getLogger(AlumnoDAOHibernateJPA.class.getName());

    public AlumnoDAOHibernateJPA() {
        super(Alumno.class);
    }


    @Override
    public List<Alumno> getByHabilitado() {
        EntityManager em = EMF.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT a FROM Alumno a WHERE a.habilitado = :habilitado", Alumno.class).setParameter("habilitado", true).getResultList();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

}

