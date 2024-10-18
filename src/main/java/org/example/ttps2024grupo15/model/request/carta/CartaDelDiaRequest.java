package org.example.ttps2024grupo15.model.request.carta;

import org.example.ttps2024grupo15.model.carta.DiaSemana;
import org.example.ttps2024grupo15.model.carta.producto.Menu;

import java.time.LocalDate;

public class CartaDelDiaRequest {
    private Menu menuVegetariano;
    private Menu menuComun;
    private DiaSemana diaSemana;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean activa;

    public Menu getMenuVegetariano() {
        return menuVegetariano;
    }

    public void setMenuVegetariano(Menu menuVegetariano) {
        this.menuVegetariano = menuVegetariano;
    }

    public Menu getMenuComun() {
        return menuComun;
    }

    public void setMenuComun(Menu menuComun) {
        this.menuComun = menuComun;
    }

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

    public Boolean isActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }
}
