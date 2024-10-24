package org.example.ttps2024grupo15.dao.carrito;

import org.example.ttps2024grupo15.dao.GenericDAO;
import org.example.ttps2024grupo15.model.carrito.Compra;

import java.util.List;

public interface CompraDAO extends GenericDAO<Compra> {
    List<Compra> getComprasByAlumno(Long idAlumno);
    List<Compra> getByPrecioMayorQue(Double precio);
    List<Compra> getByPrecioMenorQue(Double precio);
}
