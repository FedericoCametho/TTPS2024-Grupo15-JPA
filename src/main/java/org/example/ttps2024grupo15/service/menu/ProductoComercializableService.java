package org.example.ttps2024grupo15.service.menu;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.example.ttps2024grupo15.dao.menu.ProductoComercializableDAO;
import org.example.ttps2024grupo15.model.carta.producto.ProductoComercializable;
import org.example.ttps2024grupo15.model.request.menu.ProductoComercializableRequest;

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
        this.validateID(id);
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
        try{
            this.validateID(id);
            this.dao.delete(id);
        } catch (NoResultException e){
            e.printStackTrace();
            throw new IllegalArgumentException("El producto no existe");
        }
    }
    @Transactional
    public void delete(T product) {
        try{
            if(product == null){
                throw new IllegalArgumentException("El producto comercializable no puede ser nulo");
            }
            this.dao.delete(product);
        } catch (NoResultException e){
            e.printStackTrace();
            throw new IllegalArgumentException("El producto comercializable no existe");
        }
    }

    public T getProductById(Long id) {
        try{
            this.validateID(id);
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
        try{
            this.validateStringInputParameter(name, "El nombre del producto no puede ser nulo o vacío");
            return dao.findByNombre(name);
        } catch (NoResultException e){
            e.printStackTrace();
            throw new IllegalArgumentException("El producto no existe");
        }
    }
    public List<T> getProductsByPrice(Double price) {
        try{
            this.validateDoubleInputParameter(price, "El precio del producto no puede ser nulo o negativo");
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
    private void validateID(Long id) {
        if(id == null){
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
    }
    private void validateDoubleInputParameter(Double param, String message){
        if(param == null || param < 0){
            throw new IllegalArgumentException(message);
        }
    }
    private void validateStringInputParameter(String param, String message){
        if(param == null || param.isEmpty()){
            throw new IllegalArgumentException(message);
        }
    }
    protected abstract void sanitizeRequestSpecificFields(R requestequest);
    protected abstract void updateSpecificRelations(T originalProduc, T updatedProduct, R request) ;
    protected abstract T createProductoComercializable(R request);

    protected abstract void updateComidasEnMenuRelation(T originalProduct, T result);

    protected abstract void setUpdateSpecificFields(T product, R request) ;

}
