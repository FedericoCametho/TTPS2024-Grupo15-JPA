package org.example.ttps2024grupo15.dao.carta.producto.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.ttps2024grupo15.dao.entitiManager.EMF;
import org.example.ttps2024grupo15.dao.carta.producto.MenuDAO;
import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.carta.producto.Menu;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class MenuDAOHibernateJPA  extends ProductoComercializableDAOHibernateJPA<Menu> implements MenuDAO {
    private static final Logger LOGGER = Logger.getLogger(MenuDAOHibernateJPA.class.getName());
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

    @Override
    public Menu getById(Long id) {
        EntityManager em = EMF.getEMF().createEntityManager();
        try{
            return em.createQuery(
                            "SELECT m FROM Menu m JOIN FETCH m.comidas WHERE m.id = :id", this.clasePersistente)
                    .setParameter("id", id)
                    .getSingleResult();
        }catch (Exception e){
            LOGGER.severe(e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Menu> findByNombre(String nombre) {
        EntityManager em = EMF.getEMF().createEntityManager();
        try{
            return em.createQuery("SELECT m FROM Menu m JOIN FETCH m.comidas WHERE m.nombre LIKE :nombre", this.clasePersistente).setParameter("nombre","%"+nombre+"%").getResultList();
        }catch (Exception e){
            LOGGER.severe(e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }



}
