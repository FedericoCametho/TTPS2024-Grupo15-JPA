package org.example.ttps2024grupo15.model.request.menu;

import org.example.ttps2024grupo15.model.carta.producto.TipoComida;



public class ComidaRequest {
    private String nombre;
    private TipoComida tipoComida;
    private Double precio;
    private byte[] imagen;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoComida getTipoComida() {
        return tipoComida;
    }

    public void setTipoComida(TipoComida tipoComida) {
        this.tipoComida = tipoComida;
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
}
