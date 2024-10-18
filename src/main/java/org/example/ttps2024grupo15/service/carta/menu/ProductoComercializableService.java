package org.example.ttps2024grupo15.service.carta.menu;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.example.ttps2024grupo15.dao.menu.ProductoComercializableDAO;
import org.example.ttps2024grupo15.model.carta.producto.ProductoComercializable;
import org.example.ttps2024grupo15.controller.request.carta.menu.ProductoComercializableRequest;
import org.example.ttps2024grupo15.service.helper.RequestValidatorHelper;

import java.util.List;

public abstract class ProductoComercializableService<T extends ProductoComercializable, S extends ProductoComercializableDAO<T>, R extends ProductoComercializableRequest> {


    protected S dao;

    public ProductoComercializableService(S dao) {
        this.dao = dao;
    }

    @Transactional
    public T save(R request) {
        this.sanitizeRequest(request);
        try{
            T originalProduct = this.createProductoComercializable(request);
            T result =  this.dao.save(originalProduct);
            this.updateComidasEnMenuRelation(originalProduct, result);
            return result;
        } catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("El producto ya existe");
        }
    }

    @Transactional
    public T update(Long id, R request){
        RequestValidatorHelper.validateID(id);
        this.sanitizeRequest(request);
        T originalProduct;
        try{
            originalProduct = this.dao.getById(id);
        } catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("El producto no existe");
        }
        originalProduct.setNombre(request.getNombre());
        originalProduct.setPrecio(request.getPrecio());
        originalProduct.setFoto(request.getImagen());
        this.setUpdateSpecificFields(originalProduct, request);
        T result = this.dao.update(originalProduct);
        this.updateSpecificRelations(originalProduct, result, request);
        return result;
    }
    @Transactional
    public void delete(Long id) {
        RequestValidatorHelper.validateID(id);
        try{
            this.dao.delete(id);
        } catch (NoResultException e){
            e.printStackTrace();
            throw new IllegalArgumentException("El producto no existe");
        }
    }
    @Transactional
    public void delete(T product) {
        if(product == null){
            throw new IllegalArgumentException("El producto comercializable no puede ser nulo");
        }
        try{
            this.dao.delete(product);
        } catch (NoResultException e){
            e.printStackTrace();
            throw new IllegalArgumentException("El producto comercializable no existe");
        }
    }

    public T getProductById(Long id) {
        RequestValidatorHelper.validateID(id);
        try{
            return dao.getById(id);
        } catch (NoResultException e){
            e.printStackTrace();
            throw new IllegalArgumentException("El producto no existe");
        }
    }
    public List<T> getAll() {
        return dao.getAll();
    }
    public List<T> getProductsByName(String name) {
        RequestValidatorHelper.validateStringInputParameter(name, "El nombre del producto no puede ser nulo o vacío");
        try{
            return dao.findByNombre(name);
        } catch (NoResultException e){
            e.printStackTrace();
            throw new IllegalArgumentException("El producto no existe");
        }
    }
    public List<T> getProductsByPrice(Double price) {
        RequestValidatorHelper.validateDoubleInputParameter(price, "El precio del producto no puede ser nulo o negativo");
        try{
            return dao.findByPrecio(price);
        } catch (NoResultException e){
            e.printStackTrace();
            throw new IllegalArgumentException("El producto no existe");
        }
    }

    private void sanitizeRequest(R request) {
        if (request.getNombre() == null || request.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede ser nulo o vacío");
        }
        if (request.getPrecio() == null || request.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio del producto no puede ser nulo o negativo");
        }
        this.sanitizeRequestSpecificFields(request);
    }

    protected abstract void sanitizeRequestSpecificFields(R requestequest);
    protected abstract void updateSpecificRelations(T originalProduc, T updatedProduct, R request) ;
    protected abstract T createProductoComercializable(R request);

    protected abstract void updateComidasEnMenuRelation(T originalProduct, T result);

    protected abstract void setUpdateSpecificFields(T product, R request) ;

}
