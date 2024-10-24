package org.example.ttps2024grupo15.service;

import jakarta.persistence.NoResultException;
import org.example.ttps2024grupo15.dao.GenericDAO;
import org.example.ttps2024grupo15.service.helper.RequestValidatorHelper;

import java.util.List;
import java.util.logging.Logger;

public abstract class GenericService<T, S extends GenericDAO<T>>{
    private static final Logger LOGGER = Logger.getLogger(GenericService.class.getName());

    protected S dao;
    public GenericService(S dao) {
        this.dao = dao;
    }
    public T getById(Long id) {
        RequestValidatorHelper.validateID(id);
        try{
            return this.dao.getById(id);
        } catch (NoResultException e){
            LOGGER.info("El Id no corresponde a una entidad existente");
            throw new IllegalArgumentException("El Id no corresponde a una entidad existente");
        }
    }

    public List<T> getAll() {
        return this.dao.getAll();
    }
}
