package org.example.ttps2024grupo15.service.carta;

import jakarta.transaction.Transactional;
import org.example.ttps2024grupo15.controller.request.carta.CartaSemanalRequest;
import org.example.ttps2024grupo15.dao.carta.cartaSemanal.CartaSemanalDAO;
import org.example.ttps2024grupo15.model.carta.CartaDelDia;
import org.example.ttps2024grupo15.model.carta.CartaSemanal;
import org.example.ttps2024grupo15.service.helper.RequestValidatorHelper;

import java.util.List;

public class CartaSemanalService {
    private CartaSemanalDAO cartaSemanalDAO;

    public CartaSemanalService(CartaSemanalDAO cartaSemanalDAO) {
        this.cartaSemanalDAO = cartaSemanalDAO;
    }

    public CartaSemanal save(CartaSemanalRequest cartaSemanalRequest) {
        this.sanitize(cartaSemanalRequest);
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

    public void sanitize(CartaSemanalRequest cartaSemanalRequest) {
        if (cartaSemanalRequest.getCartasDelDia() == null) {
            throw new IllegalArgumentException("Cartas del dia son requeridas");
        }
    }

    public CartaSemanal createCartaSemanal(CartaSemanalRequest cartaSemanalRequest) {
        CartaSemanal cartaSemanal = new CartaSemanal();
        cartaSemanal.setCartas(cartaSemanalRequest.getCartasDelDia());
        return cartaSemanal;
    }

    public List<CartaSemanal> getAll() {
        return this.cartaSemanalDAO.getAll();
    }

    @Transactional
    public void delete(Long id){
        RequestValidatorHelper.validateID(id);
        try{
            this.cartaSemanalDAO.delete(id);
        } catch (Exception e){
            throw new IllegalArgumentException("No se encontro carta semanal con id: " + id);
        }
    }

    @Transactional
    public void update(Long id, CartaSemanalRequest cartaSemanalRequest){
        RequestValidatorHelper.validateID(id);
        this.sanitize(cartaSemanalRequest);
        CartaSemanal cartaSemanal = this.createCartaSemanal(cartaSemanalRequest);
        try{
            this.cartaSemanalDAO.update(cartaSemanal);
        } catch (Exception e){
            throw new IllegalArgumentException("No se encontro carta semanal con id: " + id);
        }
    }



}
