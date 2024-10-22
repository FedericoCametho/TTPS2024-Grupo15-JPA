package org.example.ttps2024grupo15.service.carta;

import org.example.ttps2024grupo15.controller.request.carta.CartaSemanalRequest;
import org.example.ttps2024grupo15.dao.carta.cartaSemanal.CartaSemanalDAO;
import org.example.ttps2024grupo15.model.carta.CartaSemanal;
import org.example.ttps2024grupo15.service.helper.RequestValidatorHelper;

public class CartaSemanalService {
    private CartaSemanalDAO cartaSemanalDAO;

    public CartaSemanalService(CartaSemanalDAO cartaSemanalDAO) {
        this.cartaSemanalDAO = cartaSemanalDAO;
    }

    public CartaSemanal save(CartaSemanalRequest cartaSemanalRequest) {
//        this.sanitize(cartaSemanalRequest);
        CartaSemanal cartaSemanal = this.createCartaSemanal(cartaSemanalRequest);
        return this.cartaSemanalDAO.save(cartaSemanal);
    }

    public CartaSemanal getById(Long id) {
        RequestValidatorHelper.validateID(id);
        try{
            return this.cartaSemanalDAO.getById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se encontro carta semanal con id: " + id);
        }
    }

//    private void sanitize(CartaSemanalRequest cartaSemanal) {
//        if (cartaSemanal.getCartasDelDia() == null) {
//            throw new IllegalArgumentException("Cartas del dia son requeridas");
//        }
//    }

    public CartaSemanal createCartaSemanal(CartaSemanalRequest cartaSemanalRequest) {
        CartaSemanal cartaSemanal = new CartaSemanal();
        cartaSemanal.setCartas(cartaSemanalRequest.getCartasDelDia());
        return cartaSemanal;
    }

}
