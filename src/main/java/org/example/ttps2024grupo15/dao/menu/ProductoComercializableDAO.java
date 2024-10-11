package org.example.ttps2024grupo15.dao.menu;

import org.example.ttps2024grupo15.dao.GenericDAO;
import org.example.ttps2024grupo15.model.carta.producto.ProductoComercializable;


import java.util.List;


public interface ProductoComercializableDAO<T extends ProductoComercializable> extends GenericDAO<T> {

    List<T> findByNombre(String nombre);
    List<T> findByPrecio(Double precio);

}
