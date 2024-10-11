package org.example.ttps2024grupo15.dao.menu.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.ttps2024grupo15.dao.GenericDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.entitiManager.EMF;
import org.example.ttps2024grupo15.dao.menu.MenuDAO;
import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.carta.producto.Menu;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.SystemColor.menu;

public class MenuDAOHibernateJPA  extends GenericDAOHibernateJPA<Menu> implements MenuDAO {
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
            throw e; // escribir en un log o mostrar un mensaje
        } finally {
            em.close();
        }
        return menu;
    }
    @Override
    public List<Menu> findByNombre(String nombre) {
        EntityManager em = EMF.getEMF().createEntityManager();
        try{
            return em.createQuery("SELECT m FROM Menu m WHERE m.nombre = :nombre").setParameter("nombre",nombre).getResultList();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Menu> findByPrecio(Double precio) {
        EntityManager em = EMF.getEMF().createEntityManager();
        try{
            return em.createQuery("SELECT m FROM Menu m WHERE m.nombre = :precio").setParameter("precio",precio).getResultList();
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }
}
