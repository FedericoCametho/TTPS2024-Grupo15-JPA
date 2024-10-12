package org.example.ttps2024grupo15.dao.usuario.impl;

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
        return  EMF.getEMF().createEntityManager()
                .createQuery("SELECT r FROM ResponsableDeTurno r WHERE r.turno = :turno", this.clasePersistente).setParameter("turno", turno ).getResultList();
    }


}
