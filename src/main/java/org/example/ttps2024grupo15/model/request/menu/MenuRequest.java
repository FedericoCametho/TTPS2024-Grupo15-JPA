package org.example.ttps2024grupo15.model.request.menu;

import org.example.ttps2024grupo15.model.carta.producto.Comida;

import java.util.List;

public class MenuRequest {
    private String nombre;
    private Double precio;
    private byte[] imagen;
    private List<Comida> comidas;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public List<Comida> getComidas() {
        return comidas;
    }

    public void setComidas(List<Comida> comidas) {
        this.comidas = comidas;
    }
}
