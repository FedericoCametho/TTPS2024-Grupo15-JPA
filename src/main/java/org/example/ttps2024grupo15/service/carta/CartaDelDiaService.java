package org.example.ttps2024grupo15.service.carta;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.example.ttps2024grupo15.controller.request.carta.CartaDelDiaRequest;
import org.example.ttps2024grupo15.dao.carta.cartaDelDia.CartaDelDiaDAO;
import org.example.ttps2024grupo15.model.carta.CartaDelDia;
import org.example.ttps2024grupo15.service.helper.RequestValidatorHelper;

import java.time.LocalDate;
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
    }

    public CartaDelDia createCartaDelDia(CartaDelDiaRequest cartaDelDiaRequest) {
        CartaDelDia cartaDelDia = new CartaDelDia();
        cartaDelDia.setDiaSemana(cartaDelDiaRequest.getDiaSemana());
        cartaDelDia.setFechaFin(cartaDelDiaRequest.getFechaFin());
        cartaDelDia.setFechaInicio(cartaDelDiaRequest.getFechaInicio());
        cartaDelDia.setMenuComun(cartaDelDiaRequest.getMenuComun());
        cartaDelDia.setMenuVegetariano(cartaDelDiaRequest.getMenuVegetariano());
        return cartaDelDia;
    }

    @Transactional
    public void delete(Long id) {
        RequestValidatorHelper.validateID(id);
        try{
            this.cartaDelDiaDAO.delete(id);
        } catch (NoResultException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("La carta del dia no existe");
        }
    }

    @Transactional
    public void update(Long id, CartaDelDiaRequest cartaDelDiaRequest) {
        RequestValidatorHelper.validateID(id);
        this.sanitize(cartaDelDiaRequest);
        CartaDelDia cartaDelDia = this.getById(id);
        cartaDelDia.setDiaSemana(cartaDelDiaRequest.getDiaSemana());
        cartaDelDia.setFechaFin(cartaDelDiaRequest.getFechaFin());
        cartaDelDia.setFechaInicio(cartaDelDiaRequest.getFechaInicio());
        cartaDelDia.setMenuComun(cartaDelDiaRequest.getMenuComun());
        cartaDelDia.setMenuVegetariano(cartaDelDiaRequest.getMenuVegetariano());
        this.cartaDelDiaDAO.update(cartaDelDia);
    }

    public CartaDelDia update(CartaDelDia cartaDelDia) {
        return cartaDelDiaDAO.update(cartaDelDia);
    }

    public List<CartaDelDia> getAll() {
        return cartaDelDiaDAO.getAll();
    }

}
