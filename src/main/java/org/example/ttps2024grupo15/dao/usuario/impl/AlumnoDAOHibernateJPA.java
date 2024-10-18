package org.example.ttps2024grupo15.dao.usuario.impl;


import jakarta.persistence.EntityManager;
import org.example.ttps2024grupo15.dao.entitiManager.EMF;
import org.example.ttps2024grupo15.dao.usuario.AlumnoDAO;

import org.example.ttps2024grupo15.model.usuario.Alumno;

import java.util.List;

public class AlumnoDAOHibernateJPA extends UsuarioDAOHibernateJPA<Alumno> implements AlumnoDAO {


    public AlumnoDAOHibernateJPA() {
        super(Alumno.class);
    }


    @Override
    public List<Alumno> getByHabilitado() {
        EntityManager em = EMF.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT a FROM Alumno a WHERE a.habilitado = :habilitado", Alumno.class).setParameter("habilitado", true).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

}

