package org.example.ttps2024grupo15.dao.carta.producto.impl;

import jakarta.persistence.EntityManager;
import org.example.ttps2024grupo15.dao.GenericDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.entitiManager.EMF;
import org.example.ttps2024grupo15.dao.carta.producto.ProductoComercializableDAO;
import org.example.ttps2024grupo15.model.carta.producto.ProductoComercializable;

import java.util.List;
import java.util.logging.Logger;

public abstract class ProductoComercializableDAOHibernateJPA <T extends ProductoComercializable> extends GenericDAOHibernateJPA<T> implements ProductoComercializableDAO<T> {
    private static final Logger LOGGER = Logger.getLogger(ProductoComercializableDAOHibernateJPA.class.getName());
    public ProductoComercializableDAOHibernateJPA(Class<T> persistentClass) {
        super(persistentClass);
    }

    @Override
    public List<T> findByNombre(String nombre) {
        EntityManager em = EMF.getEMF().createEntityManager();
        try{
            return em.createQuery("SELECT p FROM "+ this.clasePersistente.getSimpleName() +" p WHERE p.nombre LIKE :nombre", this.clasePersistente).setParameter("nombre","%"+nombre+"%").getResultList();
        }catch (Exception e){
            LOGGER.severe(e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<T> findByPrecio(Double precio) {
        EntityManager em = EMF.getEMF().createEntityManager();
        try{
            return em.createQuery("SELECT p FROM " + this.clasePersistente.getSimpleName() +" p WHERE p.precio = :precio", this.clasePersistente).setParameter("precio",precio).getResultList();
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }
}
