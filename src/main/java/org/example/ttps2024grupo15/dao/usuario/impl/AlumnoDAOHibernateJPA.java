package org.example.ttps2024grupo15.dao.usuario.impl;


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
        return EMF.getEMF().createEntityManager()
                .createQuery("SELECT a FROM Alumno a WHERE a.habilitado = :habilitado",this.clasePersistente).setParameter("habilitado", true).getResultList();
    }

}

