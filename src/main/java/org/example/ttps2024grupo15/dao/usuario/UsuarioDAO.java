package org.example.ttps2024grupo15.dao.usuario;

import org.example.ttps2024grupo15.dao.GenericDAO;
import org.example.ttps2024grupo15.model.permiso.Rol;
import org.example.ttps2024grupo15.model.usuario.Usuario;

import java.util.List;

public interface UsuarioDAO<T extends Usuario> extends GenericDAO<T> {
    public T getByEmail(String email);
    public List<T> getUsuariosPorRol(Rol rol);
    public List<T> getUsuarioPorNombre(String nombre);
    public List<T> getUsuarioPorApellido(String apellido);
    public T getByDni(int dni);

}
