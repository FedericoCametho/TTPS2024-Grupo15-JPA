package org.example.ttps2024grupo15.model.carta.producto;

import org.example.ttps2024grupo15.model.carrito.Carrito;
import org.example.ttps2024grupo15.model.carrito.Compra;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Menu extends ProductoComercializable {
    @Lob
    private byte[] foto;
    @ManyToMany(mappedBy = "menues", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comida> comidas;
    @ManyToMany(mappedBy = "menues", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Compra> compras;

    public Menu(String titulo, List<Comida> comidas, Double precio) {
        this.nombre = titulo;
        this.precio = precio;
        this.comidas = comidas;
    }

    public Menu() {
        super();
    }


    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public List<Comida> getComidas() {
        return comidas;
    }

    public void setComidas(List<Comida> comidas) {
        this.comidas = comidas;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }
}
