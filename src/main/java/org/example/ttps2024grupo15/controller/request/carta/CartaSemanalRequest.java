package org.example.ttps2024grupo15.controller.request.carta;

import org.example.ttps2024grupo15.model.carta.CartaDelDia;

import java.util.List;

public class CartaSemanalRequest {

    private String nombre;
    private List<CartaDelDia> cartasDelDia;


    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public List<CartaDelDia> getCartasDelDia() {
        return cartasDelDia;
    }

    public void setCartasDelDia(List<CartaDelDia> cartasDelDia) {
        this.cartasDelDia = cartasDelDia;
    }


}
