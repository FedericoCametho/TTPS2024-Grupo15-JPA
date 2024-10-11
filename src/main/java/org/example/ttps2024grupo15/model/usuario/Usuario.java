package org.example.ttps2024grupo15.model.usuario;

import org.example.ttps2024grupo15.model.permiso.Rol;
import jakarta.persistence.*;


@MappedSuperclass
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer dni;
    private String email;
    private String nombre;
    private String apellido;
    private Rol rol;

    public Usuario() {

    }

    public Usuario(Integer dni, String email, String nombre, String apellido, Rol rol) {
        this.dni = dni;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}