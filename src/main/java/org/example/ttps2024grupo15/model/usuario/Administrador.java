package org.example.ttps2024grupo15.model.usuario;

import org.example.ttps2024grupo15.model.permiso.Rol;
import jakarta.persistence.Entity;

@Entity
public class Administrador extends Usuario {
    public Administrador(Integer dni, String email, String nombre, String apellido, Rol rol) {
        super(dni, email, nombre, apellido, rol);
    }


    public Administrador() {

    }
}
