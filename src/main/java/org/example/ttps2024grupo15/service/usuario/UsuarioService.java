package org.example.ttps2024grupo15.service.usuario;


import jakarta.transaction.Transactional;
import org.example.ttps2024grupo15.dao.usuario.UsuarioDAO;
import org.example.ttps2024grupo15.model.permiso.Rol;
import org.example.ttps2024grupo15.model.request.usuario.UsuarioRequest;
import org.example.ttps2024grupo15.model.usuario.Usuario;

import java.util.List;

public abstract class UsuarioService<T extends Usuario, S extends UsuarioDAO<T>, R extends UsuarioRequest> {

    protected S dao;

    public UsuarioService(S usuarioDAO) {
        this.dao = usuarioDAO;
    }

    @Transactional
    public T save(R usuarioRequest) {
        this.sanitizeRequest(usuarioRequest);
        return this.dao.save(this.createUsuario(usuarioRequest));
    }

    @Transactional
    public T update(Long id, R usuarioRequest){
        this.sanitizeRequest(usuarioRequest);
        T user = this.getUserById(id);
        user.setNombre(usuarioRequest.getNombre());
        user.setApellido(usuarioRequest.getApellido());
        user.setEmail(usuarioRequest.getEmail());
        user.setDni(usuarioRequest.getDni());
        this.setUpdateSpecificFields(user, usuarioRequest);
        return this.dao.update(user);
    }

    @Transactional
    public void delete(Long id) {
        this.dao.delete(id);
    }
    @Transactional
    public void delete(T user) {
        this.dao.delete(user);
    }
    public T getUserById(Long id) {
        return dao.getById(id);
    }

    public List<T> getUsersByName(String name) {
        return dao.getUsuarioPorNombre(name);
    }

    public List<T> getUsersByLastName(String lastName) {
        return dao.getUsuarioPorApellido(lastName);
    }

    public T getUserByEmail(String email) {
        return dao.getByEmail(email);
    }

    public List<T> getAll() {
        return dao.getAll();
    }

    public List<T> getUserByRol(Rol rol){
        return dao.getUsuariosPorRol(rol);
    }

    protected abstract void sanitizeRequestSpecificFields(R usuarioRequest);

    protected void sanitizeRequest(R usuarioRequest) {
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
    protected abstract T createUsuario(R usuarioRequest);
    protected void setUpdateSpecificFields(T user, R usuarioRequest) {
    }
}

