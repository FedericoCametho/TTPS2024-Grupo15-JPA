package org.example.ttps2024grupo15.service.usuario;


import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.example.ttps2024grupo15.dao.usuario.UsuarioDAO;
import org.example.ttps2024grupo15.model.permiso.Rol;
import org.example.ttps2024grupo15.model.request.usuario.UsuarioRequest;
import org.example.ttps2024grupo15.model.usuario.Usuario;

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
            return this.dao.save(this.createUsuario(usuarioRequest));
        } catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("El usuario ya existe");
        }

    }

    @Transactional
    public T update(Long id, R usuarioRequest){
        this.validateID(id);
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
        try{
            this.validateID(id);
            this.dao.delete(id);
        } catch (NoResultException e){
            e.printStackTrace();
            throw new IllegalArgumentException("El usuario no existe");
        }
    }
    @Transactional
    public void delete(T user) {
        try{
            if(user == null){
                throw new IllegalArgumentException("El usuario no puede ser nulo");
            }
            this.dao.delete(user);
        } catch (NoResultException e){
            e.printStackTrace();
            throw new IllegalArgumentException("El usuario no existe");
        }
    }
    public T getUserById(Long id) {
        try{
            this.validateID(id);
            return dao.getById(id);
        } catch (NoResultException e){
            e.printStackTrace();
            throw new IllegalArgumentException("El usuario no existe");
        }
    }

    public List<T> getUsersByName(String name) {
        try{
            this.validateStringInputParameter(name, "El nombre no puede ser nulo o vacio");
            return dao.getUsuarioPorNombre(name);
        } catch (NoResultException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<T> getUsersByLastName(String lastName) {
        try{
            this.validateStringInputParameter(lastName, "El apellido no puede ser nulo o vacio");
            return dao.getUsuarioPorApellido(lastName);
        } catch (NoResultException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public T getUserByEmail(String email) {
        try{
            this.validateStringInputParameter(email,  "El email no puede ser nulo o vacio");
            return dao.getByEmail(email);
        } catch (NoResultException e){
            e.printStackTrace();
            return null;
        }
    }

    public T getUserByDni(Integer dni) {
        try{
            this.validateDNI(dni);
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
        if(usuarioRequest.getDni() == null){
            throw new IllegalArgumentException("El dni  no puede ser nulo");
        }
        this.sanitizeRequestSpecificFields(usuarioRequest);
    }
    private void validateID(Long id) {
        if(id == null){
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
    }
    private void validateDNI(Integer dni) {
        if(dni == null){
            throw new IllegalArgumentException("El dni no puede ser nulo");
        }
    }

    private void validateStringInputParameter(String param, String message){
        if(param == null || param.isEmpty()){
            throw new IllegalArgumentException(message);
        }
    }
    protected abstract T createUsuario(R usuarioRequest);
    protected abstract void setUpdateSpecificFields(T user, R usuarioRequest) ;
    protected abstract void sanitizeRequestSpecificFields(R usuarioRequest);

}

