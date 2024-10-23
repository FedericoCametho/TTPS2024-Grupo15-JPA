package org.example.ttps2024grupo15.service.carta;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.example.ttps2024grupo15.controller.request.carta.CartaDelDiaRequest;
import org.example.ttps2024grupo15.dao.carta.cartaDelDia.CartaDelDiaDAO;
import org.example.ttps2024grupo15.model.carta.CartaDelDia;
import org.example.ttps2024grupo15.model.carta.CartaSemanal;
import org.example.ttps2024grupo15.model.carta.DiaSemana;
import org.example.ttps2024grupo15.service.helper.RequestValidatorHelper;

import java.util.List;
import java.util.Set;

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
    }

    public CartaDelDia createCartaDelDia(CartaDelDiaRequest cartaDelDiaRequest) {
        CartaDelDia cartaDelDia = new CartaDelDia();
        cartaDelDia.setDiaSemana(cartaDelDiaRequest.getDiaSemana());
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

    public List<CartaDelDia> getCartaDelDiaByDiaSemana(DiaSemana diaSemana) {
        return cartaDelDiaDAO.getCartaDelDiaByDiaSemana(diaSemana);
    }

    @Transactional
    public CartaDelDia updateCartaSemanalRelation(CartaSemanal cartaSemanal, Long cartaDelDiaId){
        CartaDelDia cartaDelDia = this.getById(cartaDelDiaId);
        cartaDelDia.setCartaSemanal(cartaSemanal);
        return this.cartaDelDiaDAO.update(cartaDelDia);
    }

    @Transactional
    public void eliminarRelacionConCartaSemanal(Set<Long> idsCartasDelDia){
        try{
            idsCartasDelDia.forEach(id -> {
                    CartaDelDia cartaDelDia = this.getById(id);
                    cartaDelDia.setCartaSemanal(null);
                    this.cartaDelDiaDAO.update(cartaDelDia);
            });
        }catch(Exception e){
            throw new IllegalArgumentException("Error al eliminar la relacion con la carta semanal");
        }
    }

}
