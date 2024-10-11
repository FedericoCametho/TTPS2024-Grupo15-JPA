package org.example.ttps2024grupo15.model.request.usuario;

import org.example.ttps2024grupo15.model.carrito.Compra;
import jakarta.persistence.Lob;

import java.util.List;

public class AlumnoRequest {

    private Integer dni;
    private String email;
    private String nombre;
    private String apellido;
    private byte[] fotoDePerfil;

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

    public byte[] getFotoDePerfil() {
        return fotoDePerfil;
    }

    public void setFotoDePerfil(byte[] fotoDePerfil) {
        this.fotoDePerfil = fotoDePerfil;
    }
}
