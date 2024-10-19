package org.example.ttps2024grupo15.dao.cartaSemanal;

import org.example.ttps2024grupo15.dao.GenericDAOHibernateJPA;
import org.example.ttps2024grupo15.model.carta.CartaSemanal;

public class CartaSemanalDAOHibernateJPA extends GenericDAOHibernateJPA<CartaSemanal> implements CartaSemanalDAO {
    public CartaSemanalDAOHibernateJPA() {
        super(CartaSemanal.class);
    }
}
