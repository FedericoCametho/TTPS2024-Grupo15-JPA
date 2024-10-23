package org.example.ttps2024grupo15.service.carta;

import jakarta.transaction.Transactional;
import org.example.ttps2024grupo15.controller.request.carta.CartaSemanalRequest;
import org.example.ttps2024grupo15.dao.carta.cartaSemanal.CartaSemanalDAO;
import org.example.ttps2024grupo15.model.carta.CartaDelDia;
import org.example.ttps2024grupo15.model.carta.CartaSemanal;
import org.example.ttps2024grupo15.service.helper.RequestValidatorHelper;
import java.util.stream.Collectors;

import java.util.List;

public class CartaSemanalService {
    private CartaSemanalDAO cartaSemanalDAO;
    private CartaDelDiaService cartaDelDiaService;

    public CartaSemanalService(CartaSemanalDAO cartaSemanalDAO, CartaDelDiaService cartaDelDiaService) {
        this.cartaSemanalDAO = cartaSemanalDAO;
        this.cartaDelDiaService = cartaDelDiaService;
    }

    public CartaSemanal save(CartaSemanalRequest cartaSemanalRequest) {
        this.sanitize(cartaSemanalRequest);
        try {
            CartaSemanal cartaSemanal = this.createCartaSemanal(cartaSemanalRequest);
            CartaSemanal cartaSemanalResult = this.cartaSemanalDAO.save(cartaSemanal);
            this.updateCartasDelDiaRelation(cartaSemanalResult);
            return cartaSemanalResult;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al guardar la carta semanal");
        }

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
        if (cartaSemanalRequest.getNombre() == null || cartaSemanalRequest.getNombre().isEmpty()){
            throw new IllegalArgumentException("El nombre de la carta no puede ser nulo o vacio");
        }
        if (cartaSemanalRequest.getCartasDelDia() == null || cartaSemanalRequest.getCartasDelDia().isEmpty()) {
            throw new IllegalArgumentException("Cartas del dia son requeridas");
        }

        if (cartaSemanalRequest.getCartasDelDia().size() != 5) {
            throw new IllegalArgumentException("Se deben cargar las 5 cartas del dia");
        }

        checkUnaPorDia(cartaSemanalRequest.getCartasDelDia());

    }

    private void checkUnaPorDia(List<CartaDelDia> cartasDelDia) {
        cartasDelDia.stream().map(CartaDelDia::getDiaSemana).forEach(diaSemana -> {
            if (cartasDelDia.stream().filter(cartaDelDia -> cartaDelDia.getDiaSemana().equals(diaSemana)).count() > 1) {
                throw new IllegalArgumentException("No se puede repetir carta del dia para el mismo dia");
            }
        });
    }


    private CartaSemanal createCartaSemanal(CartaSemanalRequest cartaSemanalRequest) {
        CartaSemanal cartaSemanal = new CartaSemanal();
        cartaSemanal.setNombre(cartaSemanalRequest.getNombre());
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
    public CartaSemanal update(Long id, CartaSemanalRequest cartaSemanalRequest){
        RequestValidatorHelper.validateID(id);
        this.sanitize(cartaSemanalRequest);
        CartaSemanal cartaSemanalOriginal;
        try {
            cartaSemanalOriginal = this.getById(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("La carta semanal no existe");
        }
        try{
            cartaSemanalOriginal.setNombre(cartaSemanalRequest.getNombre());
            CartaSemanal updateCartaSemanal = this.cartaSemanalDAO.update(cartaSemanalOriginal);
            updateCartaSemanal.setCartas(cartaSemanalRequest.getCartasDelDia());
            this.updateSpecificRelations(cartaSemanalOriginal, updateCartaSemanal, cartaSemanalRequest);
            return updateCartaSemanal;
        } catch (Exception e){
            throw new IllegalArgumentException("No se encontro carta semanal con id: " + id);
        }

    }

    private void updateCartasDelDiaRelation(CartaSemanal updatedCartaSemanal) {
        updatedCartaSemanal.getCartas().forEach(cartaDelDia -> {
            this.cartaDelDiaService.updateCartaSemanalRelation(updatedCartaSemanal, cartaDelDia.getId());
        });
    }

    private void updateSpecificRelations(CartaSemanal originalCartaSemanal, CartaSemanal updatedCartaSemanal, CartaSemanalRequest cartaSemanalRequest) {
        List<Long> idCartasDelDiaOriginal = originalCartaSemanal.getCartas().stream().mapToLong(CartaDelDia::getId).boxed().collect(Collectors.toList());
        List<Long> idCartasDelDiaRequest = cartaSemanalRequest.getCartasDelDia().stream().mapToLong(CartaDelDia::getId).boxed().collect(Collectors.toList());

        List<CartaDelDia> cartasDelDiaToUpdate = cartaSemanalRequest.getCartasDelDia().stream().filter(cartaDelDiaRequest -> !idCartasDelDiaOriginal.contains(cartaDelDiaRequest.getId())).collect(Collectors.toList());

        if(!cartasDelDiaToUpdate.isEmpty()){
            this.updateCartasDelDiaRelation(updatedCartaSemanal);
            this.cartaDelDiaService.eliminarRelacionConCartaSemanal(idCartasDelDiaOriginal.stream().filter(id -> !idCartasDelDiaRequest.contains(id)).collect(Collectors.toSet()));
        }
    }



}
