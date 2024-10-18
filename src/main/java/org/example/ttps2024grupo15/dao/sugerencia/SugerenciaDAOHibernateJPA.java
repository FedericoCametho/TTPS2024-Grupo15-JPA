package org.example.ttps2024grupo15.dao.sugerencia;

import jakarta.persistence.EntityManager;
import org.example.ttps2024grupo15.dao.GenericDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.entitiManager.EMF;
import org.example.ttps2024grupo15.model.sugerencia.Sugerencia;
import org.example.ttps2024grupo15.model.sugerencia.TipoSugerencia;

import java.util.List;

public class SugerenciaDAOHibernateJPA extends GenericDAOHibernateJPA<Sugerencia> implements SugerenciaDAO {
    public SugerenciaDAOHibernateJPA() {
        super(Sugerencia.class);
    }


    @Override
    public List<Sugerencia> getSugerenciasByTipo(TipoSugerencia tipo) {
        EntityManager entityManager = EMF.getEMF().createEntityManager();

        try {
            return entityManager.createQuery("SELECT s FROM Sugerencia s WHERE s.tipo = :tipo", Sugerencia.class).setParameter("tipo", tipo).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            entityManager.close();
        }

    }
}
