package org.example.ttps2024grupo15.model.usuario;


import org.example.ttps2024grupo15.model.carrito.Compra;
import org.example.ttps2024grupo15.model.permiso.Rol;
import org.example.ttps2024grupo15.model.sugerencia.Sugerencia;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Alumno extends Usuario {
    @OneToMany(mappedBy = "usuario")
    private List<Compra> compras;
    private Boolean habilitado;
    @Lob
    private byte[] fotoDePerfil;

    @OneToMany(mappedBy = "usuario")
    private List<Sugerencia> sugerencias;


    public Alumno(Integer dni, String email, String nombre, String apellido) {
        super(dni, email, nombre, apellido, Rol.ALUMNO);
        this.compras = new ArrayList<>();
        this.habilitado = true;
    }

    public Alumno() {

    }

    public List<Compra> getCompras() {
        return compras;
    }
    public void agregarCompra(Compra compra){
        this.compras.add(compra);
    }
    public void habilitar(){
        this.habilitado = true;
    }
    public void deshabilitar(){
        this.habilitado = false;
    }
    public Boolean isHabilitado(){
        return this.habilitado;
    }
    public byte[] getFotoDePerfil() {
        return fotoDePerfil;
    }

    public void setFotoDePerfil(byte[] fotoDePerfil) {
        this.fotoDePerfil = fotoDePerfil;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    public List<Sugerencia> getSugerencias() {
        return sugerencias;
    }

    public void setSugerencias(List<Sugerencia> sugerencias) {
        this.sugerencias = sugerencias;
    }
}
