package org.example.ttps2024grupo15.dao.carrito;

import jakarta.persistence.EntityManager;
import org.example.ttps2024grupo15.dao.GenericDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.entitiManager.EMF;
import org.example.ttps2024grupo15.model.carrito.Compra;


import java.util.List;
import java.util.logging.Logger;

public class CompraDAOHibernateJPA extends GenericDAOHibernateJPA<Compra> implements CompraDAO {
    private static final Logger LOGGER = Logger.getLogger(CompraDAOHibernateJPA.class.getName());
    public CompraDAOHibernateJPA() {
        super(Compra.class);
    }

    @Override
    public List<Compra> getComprasByAlumno(Long idAlumno) {
        EntityManager entityManager = EMF.getEMF().createEntityManager();

        try {
            return entityManager.createQuery(
                "SELECT DISTINCT c FROM Compra c JOIN FETCH c.usuario WHERE c.usuario.id = :idAlumno",
                this.clasePersistente
            ).setParameter("idAlumno", idAlumno).getResultList();


        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Compra> getByPrecioMayorQue(Double precio) {
        EntityManager entityManager = EMF.getEMF().createEntityManager();
        try {
            return entityManager.createQuery(
                "SELECT c FROM Compra c JOIN FETCH c.usuario WHERE c.importe >= :precio", this.clasePersistente).setParameter("precio", precio).getResultList();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Compra> getByPrecioMenorQue(Double precio) {
        EntityManager entityManager = EMF.getEMF().createEntityManager();
        try {
            return entityManager.createQuery(
                    "SELECT c FROM Compra c JOIN FETCH c.usuario WHERE c.importe <= :precio", this.clasePersistente).setParameter("precio", precio).getResultList();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Compra> getAll(){
        EntityManager entityManager = EMF.getEMF().createEntityManager();
        try{
            return entityManager.createQuery("SELECT c FROM Compra c JOIN FETCH c.usuario", this.clasePersistente).getResultList();
        }catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw e;
        } finally {
            entityManager.close();
        }
    }

}
