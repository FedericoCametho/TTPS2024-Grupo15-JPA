package org.example.ttps2024grupo15.service.usuario;


import org.example.ttps2024grupo15.model.usuario.Usuario;

import java.util.List;

public interface UsuarioService<T extends Usuario> {

    List<T> getUserByRol() ;

    T getUserById(Long id) ;
    List<T> getUsersByName(String name) ;
    List<T> getUsersByLastName(String lastName) ;
    List<T> getUsersByEnabled() ;
    T getUserByEmail(String email);
    List<T> getAll();

}
