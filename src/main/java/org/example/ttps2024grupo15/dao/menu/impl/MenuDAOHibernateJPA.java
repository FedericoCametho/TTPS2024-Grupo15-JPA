package org.example.ttps2024grupo15.dao.menu.impl;

import org.example.ttps2024grupo15.dao.GenericDAOHibernateJPA;
import org.example.ttps2024grupo15.dao.entitiManager.EMF;
import org.example.ttps2024grupo15.dao.menu.MenuDAO;
import org.example.ttps2024grupo15.model.carta.producto.Menu;


import java.util.List;

public class MenuDAOHibernateJPA  extends GenericDAOHibernateJPA<Menu> implements MenuDAO {
    public MenuDAOHibernateJPA() {
        super(Menu.class);
    }

    @Override
    public List<Menu> findByNombre(String nombre) {
        return EMF.getEMF().createEntityManager()
                .createQuery("SELECT m FROM Menu m WHERE m.nombre = :nombre").setParameter("nombre",nombre).getResultList();
    }

    @Override
    public List<Menu> findByPrecio(Double precio) {
        return EMF.getEMF().createEntityManager()
                .createQuery("SELECT m FROM Menu m WHERE m.nombre = :precio").setParameter("precio",precio).getResultList();
    }
}
