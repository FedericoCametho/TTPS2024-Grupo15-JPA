package org.example.ttps2024grupo15.dao.usuario.impl;


import jakarta.persistence.EntityManager;
import org.example.ttps2024grupo15.dao.GenericDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.entitiManager.EMF;
import org.example.ttps2024grupo15.dao.usuario.AlumnoDAO;
import org.example.ttps2024grupo15.model.permiso.Rol;
import org.example.ttps2024grupo15.model.usuario.Alumno;

import java.util.List;

public class AlumnoDAOHibernateJPA extends GenericDAOHibernateJPA<Alumno> implements AlumnoDAO {


    public AlumnoDAOHibernateJPA() {
        super(Alumno.class);
    }

    @Override
    public Alumno getByEmail(String email){
        return (Alumno)  EMF.getEMF().createEntityManager()
                .createQuery("SELECT a FROM Alumno a WHERE a.email = :email").setParameter("email", email).getSingleResult();
    }

    @Override
    public List<Alumno> getUsuariosPorRol(Rol rol){
        return EMF.getEMF().createEntityManager()
                .createQuery("SELECT a FROM Alumno a WHERE a.rol = :rol").setParameter("rol", rol).getResultList();
    }

    @Override
    public List<Alumno> getUsuarioPorNombre(String nombre) {
        return EMF.getEMF().createEntityManager()
                .createQuery("SELECT a FROM Alumno a WHERE a.nombre LIKE %:nombre%").setParameter("nombre", nombre).getResultList();
    }

    @Override
    public List<Alumno> getUsuarioPorApellido(String apellido) {
        return EMF.getEMF().createEntityManager()
                .createQuery("SELECT a FROM Alumno a WHERE a.nombre LIKE %:apellido%").setParameter("apellido", apellido).getResultList();
    }

    @Override
    public List<Alumno> getByHabilitado() {
        return EMF.getEMF().createEntityManager()
                .createQuery("SELECT a FROM Alumno a WHERE a.habilitado = :habilitado").setParameter("habilitado", true).getResultList();
    }
}

