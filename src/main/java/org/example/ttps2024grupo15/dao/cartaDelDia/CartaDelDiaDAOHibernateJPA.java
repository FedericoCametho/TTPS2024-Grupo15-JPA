package org.example.ttps2024grupo15.dao.cartaDelDia;

import jakarta.persistence.EntityManager;
import org.example.ttps2024grupo15.dao.GenericDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.entitiManager.EMF;
import org.example.ttps2024grupo15.model.carta.CartaDelDia;
import org.example.ttps2024grupo15.model.carta.DiaSemana;

import java.util.List;

public class CartaDelDiaDAOHibernateJPA extends GenericDAOHibernateJPA<CartaDelDia> implements CartaDelDiaDAO {
    public CartaDelDiaDAOHibernateJPA() {
        super(CartaDelDia.class);
    }

    public List<CartaDelDia> getCartaDelDiaByDiaSemana(DiaSemana diaSemana) {
        EntityManager entityManager = EMF.getEMF().createEntityManager();

        try {
            return entityManager.createQuery("SELECT c FROM CartaDelDia c WHERE c.diaSemana = :diaSemana", CartaDelDia.class).setParameter("diaSemana", diaSemana).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            entityManager.close();
        }

    }
}
