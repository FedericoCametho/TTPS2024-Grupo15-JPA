package org.example.ttps2024grupo15.controller.request.carta.menu;

import org.example.ttps2024grupo15.model.carta.CartaSemanal;
import org.example.ttps2024grupo15.model.carta.DiaSemana;
import org.example.ttps2024grupo15.model.carta.producto.Menu;

import java.time.LocalDate;

public class CartaDelDiaRequest {
    private Menu menuVegetariano;
    private Menu menuComun;
    private DiaSemana diaSemana;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private CartaSemanal cartaSemanal;

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public CartaSemanal getCartaSemanal() {
        return cartaSemanal;
    }

    public void setCartaSemanal(CartaSemanal cartaSemanal) {
        this.cartaSemanal = cartaSemanal;
    }

    public Menu getMenuVegetariano() {
        return menuVegetariano;
    }

    public Menu getMenuComun() {
        return menuComun;
    }

    public void setMenuComun(Menu menuComun) {
        this.menuComun = menuComun;
    }

    public void setMenuVegetariano(Menu menuVegetariano) {
        this.menuVegetariano = menuVegetariano;
    }
}
