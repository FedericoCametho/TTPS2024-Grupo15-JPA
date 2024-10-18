package org.example.ttps2024grupo15.service.usuario;


import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.example.ttps2024grupo15.dao.usuario.UsuarioDAO;
import org.example.ttps2024grupo15.model.permiso.Rol;
import org.example.ttps2024grupo15.controller.request.usuario.UsuarioRequest;
import org.example.ttps2024grupo15.model.usuario.Usuario;
import org.example.ttps2024grupo15.service.helper.RequestValidatorHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class UsuarioService<T extends Usuario, S extends UsuarioDAO<T>, R extends UsuarioRequest> {

    protected S dao;

    public UsuarioService(S usuarioDAO) {
        this.dao = usuarioDAO;
    }

    @Transactional
    public T save(R usuarioRequest) {
        this.sanitizeRequest(usuarioRequest);
        try{
            this.validarDuplicado(usuarioRequest);
            return this.dao.save(this.createUsuario(usuarioRequest));
        } catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("Error al guardar el usuario");
        }

    }

    @Transactional
    public T update(Long id, R usuarioRequest){
        RequestValidatorHelper.validateID(id);
        this.sanitizeRequest(usuarioRequest);
        T user;
        try{
            user = this.dao.getById(id);
        } catch (NoResultException e){
            e.printStackTrace();
            throw new IllegalArgumentException("El usuario no existe");
        }
        user.setNombre(usuarioRequest.getNombre());
        user.setApellido(usuarioRequest.getApellido());
        user.setEmail(usuarioRequest.getEmail());
        user.setDni(usuarioRequest.getDni());
        this.setUpdateSpecificFields(user, usuarioRequest);
        return this.dao.update(user);
    }

    @Transactional
    public void delete(Long id) {
        RequestValidatorHelper.validateID(id);
        try{
            this.dao.delete(id);
        } catch (NoResultException e){
            e.printStackTrace();
            throw new IllegalArgumentException("El usuario no existe");
        }
    }
    @Transactional
    public void delete(T user) {
        if(user == null){
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        try{
            this.dao.delete(user);
        } catch (NoResultException e){
            e.printStackTrace();
            throw new IllegalArgumentException("El usuario no existe");
        }
    }
    public T getUserById(Long id) {
        RequestValidatorHelper.validateID(id);
        try{
            return dao.getById(id);
        } catch (NoResultException e){
            e.printStackTrace();
            throw new IllegalArgumentException("El usuario no existe");
        }
    }

    public List<T> getUsersByName(String name) {
        RequestValidatorHelper.validateStringInputParameter(name, "El nombre no puede ser nulo o vacio");
        try{
            return dao.getUsuarioPorNombre(name);
        } catch (NoResultException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<T> getUsersByLastName(String lastName) {
        RequestValidatorHelper.validateStringInputParameter(lastName, "El apellido no puede ser nulo o vacio");
        try{
            return dao.getUsuarioPorApellido(lastName);
        } catch (NoResultException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public T getUserByEmail(String email) {
        RequestValidatorHelper.validateStringInputParameter(email,  "El email no puede ser nulo o vacio");
        try{
            return dao.getByEmail(email);
        } catch (NoResultException e){
            e.printStackTrace();
            return null;
        }
    }

    public T getUserByDni(Integer dni) {
        this.validateDNI(dni);
        try{
            return dao.getByDni(dni);
        } catch (NoResultException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<T> getAll() {
        return dao.getAll();
    }

    public List<T> getUserByRol(Rol rol){
        if(rol == null){
            throw new IllegalArgumentException("El rol no puede ser nulo");
        }
        return dao.getUsuariosPorRol(rol);
    }

    private void sanitizeRequest(R usuarioRequest) {
        if(usuarioRequest.getNombre() == null || usuarioRequest.getNombre().isEmpty()){
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacio");
        }
        if(usuarioRequest.getApellido() == null || usuarioRequest.getApellido().isEmpty()){
            throw new IllegalArgumentException("El apellido no puede ser nulo o vacio");
        }
        if(usuarioRequest.getEmail() == null || usuarioRequest.getEmail().isEmpty()){
            throw new IllegalArgumentException("El email  no puede ser nulo o vacio");
        }
        this.validateDNI(usuarioRequest.getDni());
        this.sanitizeRequestSpecificFields(usuarioRequest);
    }

    private void validateDNI(Integer dni) {
        if(dni == null){
            throw new IllegalArgumentException("El dni no puede ser nulo");
        }
    }

    private void validarDuplicado(R usuarioRequest){
        if(this.getUserByEmail(usuarioRequest.getEmail()) != null){
            throw new IllegalArgumentException("El email ya se encuentra registrado");
        }
        if(this.getUserByDni(usuarioRequest.getDni()) != null){
            throw new IllegalArgumentException("El dni ya se encuentra registrado");
        }
    }


    protected abstract T createUsuario(R usuarioRequest);
    protected abstract void setUpdateSpecificFields(T user, R usuarioRequest) ;
    protected abstract void sanitizeRequestSpecificFields(R usuarioRequest);

}

