package org.example.ttps2024grupo15.dao.usuario.impl;

import jakarta.persistence.EntityManager;
import org.example.ttps2024grupo15.dao.entitiManager.EMF;
import org.example.ttps2024grupo15.dao.usuario.ResponsableDeTurnoDAO;
import org.example.ttps2024grupo15.model.usuario.ResponsableDeTurno;
import org.example.ttps2024grupo15.model.usuario.Turno;

import java.util.List;

public class ResponsableDeTurnoDAOHibernateJPA  extends UsuarioDAOHibernateJPA<ResponsableDeTurno> implements ResponsableDeTurnoDAO {
    public ResponsableDeTurnoDAOHibernateJPA() {
        super(ResponsableDeTurno.class);
    }


    @Override
    public List<ResponsableDeTurno> getByTurno(Turno turno) {
        EntityManager em = EMF.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT r FROM ResponsableDeTurno r WHERE r.turno = :turno", ResponsableDeTurno.class).setParameter("turno", turno).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }


}
