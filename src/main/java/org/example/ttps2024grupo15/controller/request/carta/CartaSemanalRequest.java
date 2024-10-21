package org.example.ttps2024grupo15.controller.request.carta;

import org.example.ttps2024grupo15.model.carta.CartaDelDia;

import java.util.List;

public class CartaSemanalRequest {
    List<CartaDelDia> cartasDelDia;

    public List<CartaDelDia> getCartasDelDia() {
        return cartasDelDia;
    }

    public void setCartasDelDia(List<CartaDelDia> cartasDelDia) {
        this.cartasDelDia = cartasDelDia;
    }

}
