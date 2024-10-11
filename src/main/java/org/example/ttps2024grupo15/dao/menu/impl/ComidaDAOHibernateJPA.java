package org.example.ttps2024grupo15.dao.menu.impl;

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
        return EMF.getEMF().createEntityManager()
                .createQuery("SELECT c FROM Comida c WHERE c.nombre = :nombre").setParameter("nombre",nombre).getResultList();
    }

    @Override
    public List<Comida> findByPrecio(Double precio) {
        return EMF.getEMF().createEntityManager()
                .createQuery("SELECT c FROM Comida c WHERE c.nombre = :precio").setParameter("precio",precio).getResultList();
    }
}
