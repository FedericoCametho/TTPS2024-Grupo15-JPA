package org.example.ttps2024grupo15.dao.menu.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.ttps2024grupo15.dao.entitiManager.EMF;
import org.example.ttps2024grupo15.dao.menu.MenuDAO;
import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.carta.producto.Menu;


import java.util.ArrayList;
import java.util.List;


public class MenuDAOHibernateJPA  extends ProductoComercializableDAOHibernateJPA<Menu> implements MenuDAO {
    public MenuDAOHibernateJPA() {
        super(Menu.class);
    }

    @Override
    public Menu save(Menu menu) {
        EntityManager em = EMF.getEMF().createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            List<Comida> comidasActualizadas = new ArrayList<>();
                for (Comida comida : menu.getComidas()) {

                    Comida comidaExistente = em.find(Comida.class, comida.getId());

                    if (comidaExistente == null) {
                        throw new IllegalArgumentException("La comida con ID " + comida.getId() + " no existe en la base de datos.");
                    }

                    comidasActualizadas.add(comidaExistente);
                }

            menu.setComidas(comidasActualizadas);
            em.persist(menu);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
        return menu;
    }

}
