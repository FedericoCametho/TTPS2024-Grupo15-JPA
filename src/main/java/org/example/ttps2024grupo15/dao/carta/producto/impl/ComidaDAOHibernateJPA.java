package org.example.ttps2024grupo15.dao.carta.producto.impl;

import org.example.ttps2024grupo15.dao.carta.producto.ComidaDAO;

import org.example.ttps2024grupo15.model.carta.producto.Comida;


public class ComidaDAOHibernateJPA extends ProductoComercializableDAOHibernateJPA<Comida> implements ComidaDAO {
    public ComidaDAOHibernateJPA() {
        super(Comida.class);
    }

}
