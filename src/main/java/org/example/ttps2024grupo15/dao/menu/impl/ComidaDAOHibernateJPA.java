package org.example.ttps2024grupo15.dao.menu.impl;

import jakarta.persistence.EntityManager;
import org.example.ttps2024grupo15.dao.GenericDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.entitiManager.EMF;
import org.example.ttps2024grupo15.dao.menu.ComidaDAO;

import org.example.ttps2024grupo15.model.carta.producto.Comida;

import java.util.List;


public class ComidaDAOHibernateJPA extends ProductoComercializableDAOHibernateJPA<Comida> implements ComidaDAO {
    public ComidaDAOHibernateJPA() {
        super(Comida.class);
    }

}
