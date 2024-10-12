package org.example.ttps2024grupo15.dao.menu.impl;

import jakarta.persistence.EntityManager;
import org.example.ttps2024grupo15.dao.GenericDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.entitiManager.EMF;
import org.example.ttps2024grupo15.dao.menu.ComidaDAO;

import org.example.ttps2024grupo15.model.carta.producto.Comida;

import java.util.List;


public class ComidaDAOHibernateJPA extends GenericDAOHibernateJPA<Comida> implements ComidaDAO {
    public ComidaDAOHibernateJPA() {
        super(Comida.class);
    }

    @Override
    public List<Comida> findByNombre(String nombre) {
        EntityManager em = EMF.getEMF().createEntityManager();
        try{
            return em.createQuery("SELECT c FROM Comida c WHERE c.nombre LIKE :nombre").setParameter("nombre","%"+nombre+"%").getResultList();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            em.close();
        }
    }

    @Override
    public List<Comida> findByPrecio(Double precio) {
        EntityManager em = EMF.getEMF().createEntityManager();
        try{
            return em.createQuery("SELECT c FROM Comida c WHERE c.precio = :precio").setParameter("precio",precio).getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }
}
