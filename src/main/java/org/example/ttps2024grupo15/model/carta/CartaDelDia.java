package org.example.ttps2024grupo15.model.carta;

import org.example.ttps2024grupo15.model.carta.producto.Menu;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class CartaDelDia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    private Menu menuVegetariano;
    @OneToOne(fetch = FetchType.EAGER)
    private Menu menuComun;
    private DiaSemana diaSemana;
    private boolean activa;
    @ManyToOne
    @JoinColumn(name = "carta_semanal_id")
    private CartaSemanal cartaSemanal;

    public CartaDelDia(Menu menuVegetariano, Menu menuComun, DiaSemana diaSemana) {
        this.menuVegetariano = menuVegetariano;
        this.menuComun = menuComun;
        this.diaSemana = diaSemana;
        this.activa = true;
    }

    public CartaDelDia() {
    }

    public Long getId() {
        return id;
    }
    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

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

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public CartaSemanal getCartaSemanal() {
        return cartaSemanal;
    }

    public void setCartaSemanal(CartaSemanal cartaSemanal) {
        this.cartaSemanal = cartaSemanal;
    }
}
