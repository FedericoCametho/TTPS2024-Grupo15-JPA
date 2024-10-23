package org.example.ttps2024grupo15.dao.carta.cartaSemanal;

import jakarta.persistence.EntityManager;
import org.example.ttps2024grupo15.dao.GenericDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.entitiManager.EMF;
import org.example.ttps2024grupo15.model.carta.CartaDelDia;
import org.example.ttps2024grupo15.model.carta.CartaSemanal;

import java.util.List;

public class CartaSemanalDAOHibernateJPA extends GenericDAOHibernateJPA<CartaSemanal> implements CartaSemanalDAO {
    public CartaSemanalDAOHibernateJPA() {
        super(CartaSemanal.class);
    }
}
