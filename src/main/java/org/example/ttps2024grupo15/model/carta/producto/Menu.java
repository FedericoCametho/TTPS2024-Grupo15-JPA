package org.example.ttps2024grupo15.model.carta.producto;

import org.example.ttps2024grupo15.model.carrito.Compra;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "menu")
public class Menu extends ProductoComercializable {

    @ManyToMany(mappedBy = "menues", fetch = FetchType.LAZY)
    private List<Comida> comidas;
    @ManyToMany(mappedBy = "menues", fetch = FetchType.LAZY)
    private List<Compra> compras;

    public Menu(String titulo, Double precio, List<Comida> comidas, byte[] foto) {
        super(titulo, precio, foto);
        this.comidas = comidas;
    }

    public Menu() {
        super();
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

    public boolean hasComida(Comida comida){
        return this.comidas.contains(comida);
    }


}
