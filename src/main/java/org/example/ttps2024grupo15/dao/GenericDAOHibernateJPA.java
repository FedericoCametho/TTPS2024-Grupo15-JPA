package org.example.ttps2024grupo15.dao;



import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.example.ttps2024grupo15.dao.entitiManager.EMF;


import java.util.List;


public class GenericDAOHibernateJPA<T> implements GenericDAO<T> {
    protected Class<T> clasePersistente;
    public GenericDAOHibernateJPA(Class<T> clase) {
        clasePersistente = clase;
    }
    public Class<T> getClasePersistente() {
        return clasePersistente;
    }
    @Override
    public T save(T entity) {
        EntityManager em = EMF.getEMF().createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            tx.commit();
        }
        catch (RuntimeException e) {
            if ( tx != null && tx.isActive() ) tx.rollback();
            throw e; // escribir en un log o mostrar un mensaje
        }
        finally {
            em.close();
        } return entity;
    }

    @Override
    public T getById(Long id) {
        EntityManager em = EMF.getEMF().createEntityManager();
        T entity = em.find(this.getClasePersistente(), id);
        em.close();
        return entity;
    }

    @Override
    public T get(T t) {
        EntityManager em = EMF.getEMF().createEntityManager();
        T entity = em.find(this.getClasePersistente(), t);
        em.close();
        return entity;
    }

    @Override
    public T update(T entity) {
        EntityManager em= EMF.getEMF().createEntityManager();
        EntityTransaction etx= em.getTransaction();
        etx.begin();
        T entityMerged = em.merge(entity);
        etx.commit();
        em.close();
        return entityMerged;
    }

    @Override
    public boolean delete(T entity) {
        EntityManager em = EMF.getEMF().createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.remove(em.merge(entity));
            tx.commit();
            return true;
        }
        catch (RuntimeException e) {
            if ( tx != null && tx.isActive() ) tx.rollback();
            throw e; // escribir en un log o mostrar un mensaje
        } finally {
            em.close();
        }
    }
    @Override
    public boolean delete(Long id) {
        EntityManager em = EMF.getEMF().createEntityManager();
        try{
            em.getTransaction().begin();
            T entity=em.find(this.getClasePersistente(), id);
            if (entity != null) {
                em.remove(entity);
            }
            em.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            if ( em.getTransaction().isActive() ) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    @Override
    public List<T> getAllByOrder(String columnOrder) {
        EntityManager em = EMF.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT e FROM " + clasePersistente.getSimpleName() + " e ORDER BY e." + columnOrder, clasePersistente).getResultList();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<T> getAll(){
        EntityManager em = EMF.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT e FROM " + clasePersistente.getSimpleName() + " e", clasePersistente).getResultList();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }
}
