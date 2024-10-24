package org.example.ttps2024grupo15.service.carrito;

import jakarta.transaction.Transactional;
import org.example.ttps2024grupo15.dao.carrito.CompraDAO;
import org.example.ttps2024grupo15.model.carrito.Carrito;
import org.example.ttps2024grupo15.model.carrito.Compra;
import org.example.ttps2024grupo15.service.helper.RequestValidatorHelper;

import java.util.List;
import java.util.logging.Logger;

public class CompraService {
    private static final Logger LOGGER = Logger.getLogger(CompraService.class.getName());
    private CompraDAO compraDAO;

    public CompraService(CompraDAO compraDAO) {
        this.compraDAO = compraDAO;
    }

    @Transactional
    public Compra save(Carrito carrito) {
        Compra compra = new Compra(carrito);
        try {
            return this.compraDAO.save(compra);
        } catch (Exception e) {
            LOGGER.info("Error al guardar la compra");
            throw new IllegalArgumentException("Error al guardar la compra");
        }
    }

    @Transactional
    public void pagarCompra(Long id) {
        RequestValidatorHelper.validateID(id);
        try {
            Compra compra = this.getById(id);
            compra.marcarPagado();
            this.compraDAO.update(compra);
        } catch (Exception e) {
            LOGGER.info("Error al pagar la compra");
            throw new IllegalArgumentException("Error al pagar la compra");
        }
    }

    @Transactional
    public void marcarImpagoCompra(Long id) {
        RequestValidatorHelper.validateID(id);
        try {
            Compra compra = this.getById(id);
            compra.marcarImpago();
            this.compraDAO.update(compra);
        } catch (Exception e) {
            LOGGER.info("Error al marcar impago la compra");
            throw new IllegalArgumentException("Error al marcar impago la compra");
        }
    }

    @Transactional
    public void delete(Long id) {
        RequestValidatorHelper.validateID(id);
        try {
            this.compraDAO.delete(id);
        } catch (Exception e) {
            LOGGER.info("Error al eliminar la compra");
            throw new IllegalArgumentException("Error al eliminar la compra");
        }
    }

    public Compra getById(Long id) {
        RequestValidatorHelper.validateID(id);
        try {
            return this.compraDAO.getById(id);
        } catch (Exception e) {
            LOGGER.info("No se encontro compra con id: " + id);
            throw new IllegalArgumentException("Error al recuperar la compra por ID");
        }
    }

    public List<Compra> getComprasByAlumnoId(Long alumnoId) {
        RequestValidatorHelper.validateID(alumnoId);
        try {
            return this.compraDAO.getComprasByAlumno(alumnoId);
        } catch (Exception e) {
            LOGGER.info("Error al recuperar las compras por alumno");
            throw new IllegalArgumentException("Error al recuperar las compras por alumno");
        }
    }

    public List<Compra> getAll() {
        try {
            return this.compraDAO.getAll();
        } catch (Exception e) {
            LOGGER.info("Error al recuperar todas las compras");
            throw new IllegalArgumentException("Error al recuperar todas las compras");
        }
    }
    public List<Compra> getComprasPrecioMayorQue(Double precio){
        RequestValidatorHelper.validateDoubleInputParameter(precio, "El precio para el filtrado no puede ser nulo ni menor que cero");
        try{
            return this.compraDAO.getByPrecioMayorQue(precio);
        } catch(Exception e){
            LOGGER.info("Error al recuperar las compras por filtrado mayor que");
            throw new IllegalArgumentException("Error al recuperar las compras por filtrado mayor que");
        }
    }
    public List<Compra> getComprasPrecioMenorQue(Double precio){
        RequestValidatorHelper.validateDoubleInputParameter(precio, "El precio para el filtrado no puede ser nulo ni menor que cero");
        try{
            return this.compraDAO.getByPrecioMenorQue(precio);
        } catch(Exception e){
            LOGGER.info("Error al recuperar las compras por filtrado menor que");
            throw new IllegalArgumentException("Error al recuperar las compras por filtrado menor que");
        }
    }



}
