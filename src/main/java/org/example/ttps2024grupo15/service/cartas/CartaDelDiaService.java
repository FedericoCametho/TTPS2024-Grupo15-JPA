package org.example.ttps2024grupo15.service.cartas;

import org.example.ttps2024grupo15.controller.request.carta.menu.CartaDelDiaRequest;
import org.example.ttps2024grupo15.dao.cartaDelDia.CartaDelDiaDAO;
import org.example.ttps2024grupo15.model.carta.CartaDelDia;
import org.example.ttps2024grupo15.service.helper.RequestValidatorHelper;

import java.util.List;

public class CartaDelDiaService  {
    private CartaDelDiaDAO cartaDelDiaDAO;

    public CartaDelDiaService(CartaDelDiaDAO cartaDelDiaDAO) {
        this.cartaDelDiaDAO = cartaDelDiaDAO;
    }

    public CartaDelDia save(CartaDelDiaRequest cartaDelDiaRequest) {
        this.sanitize(cartaDelDiaRequest);
        CartaDelDia cartaDelDia = this.createCartaDelDia(cartaDelDiaRequest);
        try {
            return this.cartaDelDiaDAO.save(cartaDelDia);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al guardar la carta del dia");
        }

    }

    public CartaDelDia getById(Long id) {
        RequestValidatorHelper.validateID(id);
        try {
            return this.cartaDelDiaDAO.getById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se encontro carta del dia con id: " + id);
        }
    }

    private void sanitize(CartaDelDiaRequest cartaDelDiaRequest) {
        if (cartaDelDiaRequest.getMenuComun() == null) {
            throw new IllegalArgumentException("Menu comun es requerido");
        }
        if (cartaDelDiaRequest.getMenuVegetariano() == null) {
            throw new IllegalArgumentException("Menu vegetariano es requerido");
        }
        if (cartaDelDiaRequest.getDiaSemana() == null) {
            throw new IllegalArgumentException("Dia de la semana es requerido");
        }
        if (cartaDelDiaRequest.getFechaInicio() == null) {
            throw new IllegalArgumentException("Fecha de inicio es requerida");
        }
        if (cartaDelDiaRequest.getFechaFin() == null) {
            throw new IllegalArgumentException("Fecha de fin es requerida");
        }
        if (cartaDelDiaRequest.getCartaSemanal() == null) {
            throw new IllegalArgumentException("Carta semanal es requerida");
        }


    }

    public CartaDelDia createCartaDelDia(CartaDelDiaRequest cartaDelDiaRequest) {
        CartaDelDia cartaDelDia = new CartaDelDia();
        cartaDelDia.setCartaSemanal(cartaDelDiaRequest.getCartaSemanal());
        cartaDelDia.setDiaSemana(cartaDelDiaRequest.getDiaSemana());
        cartaDelDia.setFechaFin(cartaDelDiaRequest.getFechaFin());
        cartaDelDia.setFechaInicio(cartaDelDiaRequest.getFechaInicio());
        cartaDelDia.setMenuComun(cartaDelDiaRequest.getMenuComun());
        cartaDelDia.setMenuVegetariano(cartaDelDiaRequest.getMenuVegetariano());
        return cartaDelDia;
    }

    public CartaDelDia update(CartaDelDia cartaDelDia) {
        return cartaDelDiaDAO.update(cartaDelDia);
    }

    public void delete(Long id) {
        cartaDelDiaDAO.delete(id);
    }

    public List<CartaDelDia> getAll() {
        return cartaDelDiaDAO.getAll();
    }

}
