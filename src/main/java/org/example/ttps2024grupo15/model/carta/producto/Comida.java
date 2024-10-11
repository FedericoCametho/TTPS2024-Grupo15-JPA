package org.example.ttps2024grupo15.model.carta.producto;

import org.example.ttps2024grupo15.model.carrito.Carrito;
import org.example.ttps2024grupo15.model.carrito.Compra;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Comida extends ProductoComercializable{

    private TipoComida tipoComida;
    @Lob
    private byte[] foto;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "comida_menu",
            joinColumns = @JoinColumn(name = "comida_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    private List<Menu> menues;
    @ManyToMany(mappedBy = "comidas", fetch = FetchType.EAGER)
    private List<Compra> compras;
    private Boolean enMenu;

    public Comida(String nombre, TipoComida tipoComida, Double precio) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipoComida = tipoComida;
        this.enMenu = false;
    }

    public Comida() {
        super();
    }

    public TipoComida getTipoComida() {
        return tipoComida;
    }

    public void setTipoComida(TipoComida tipoComida) {
        this.tipoComida = tipoComida;
    }

    public List<Menu> getMenues() {
        return menues;
    }

    public void setMenues(List<Menu> menues) {
        this.menues = menues;
    }


    public boolean isEnMenu() {
        return enMenu;
    }

    public void setEnMenu(Boolean enMenu) {
        this.enMenu = enMenu;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    public Boolean getEnMenu() {
        return enMenu;
    }
    public void setComidaInMenu(Menu menu){
        if(!this.isInMenu(menu)){
            this.menues.add(menu);
            this.enMenu = true;
        }
    }
    public void removeComidaFromMenu(Menu menu){
        if(this.isInMenu(menu)){
            this.menues.remove(menu);
            this.enMenu = !this.menues.isEmpty();
        }
    }
    public boolean isInMenu(Menu menu){
        return this.menues.contains(menu);
    }
}
