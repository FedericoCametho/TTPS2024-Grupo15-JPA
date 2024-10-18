package org.example.ttps2024grupo15.service.carta;

import jakarta.transaction.Transactional;
import org.example.ttps2024grupo15.dao.carta.CartaDelDiaDAO;
import org.example.ttps2024grupo15.model.carta.CartaDelDia;
import org.example.ttps2024grupo15.model.carta.DiaSemana;
import org.example.ttps2024grupo15.model.request.carta.CartaDelDiaRequest;
import org.example.ttps2024grupo15.service.GenericService;
import org.example.ttps2024grupo15.service.helper.RequestValidatorHelper;

import java.time.LocalDate;
import java.util.List;

public class CartaDelDiaService extends GenericService<CartaDelDia, CartaDelDiaDAO> {
    private CartaDelDiaDAO dao;
    public CartaDelDiaService(CartaDelDiaDAO cartaDelDiaDAO) {
        super(cartaDelDiaDAO);
    }

    @Transactional
    public CartaDelDia save(CartaDelDiaRequest cartaDelDiaRequest) {
        this.sanitizeRequest(cartaDelDiaRequest);
        try{
            return this.dao.save(this.createCartaDelDia(cartaDelDiaRequest));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al guardar la carta del dia");
        }
    }

    @Transactional
    public CartaDelDia update(Long id, CartaDelDiaRequest cartaDelDiaRequest){
        RequestValidatorHelper.validateID(id);
        this.sanitizeRequest(cartaDelDiaRequest);
        CartaDelDia cartaDelDia;
        try{
            cartaDelDia = this.dao.getById(id);
        } catch (Exception e){
            throw new IllegalArgumentException("La carta del dia no existe");
        }
        cartaDelDia.setMenuVegetariano(cartaDelDiaRequest.getMenuVegetariano());
        cartaDelDia.setMenuComun(cartaDelDiaRequest.getMenuComun());
        cartaDelDia.setDiaSemana(cartaDelDiaRequest.getDiaSemana());
        cartaDelDia.setFechaInicio(cartaDelDiaRequest.getFechaInicio());
        cartaDelDia.setFechaFin(cartaDelDiaRequest.getFechaFin());
        cartaDelDia.setActiva(cartaDelDiaRequest.isActiva());
        return this.dao.update(cartaDelDia);
    }

    @Transactional
    public void delete(Long id) {
        RequestValidatorHelper.validateID(id);
        try{
            this.dao.delete(id);
        } catch (Exception e){
            throw new IllegalArgumentException("La carta del dia no existe");
        }
    }

    @Transactional
    public void delete(CartaDelDia cartaDelDia) {
        if(cartaDelDia == null){
            throw new IllegalArgumentException("La carta del dia no puede ser nula");
        }
        try{
            this.dao.delete(cartaDelDia);
        } catch (Exception e){
            throw new IllegalArgumentException("La carta del dia no existe");
        }
    }

    public List<CartaDelDia> getCartasDelDiaActivas(){
        return this.dao.getCartasDelDiaActivas();
    }

    public List<CartaDelDia> getCartasDelDiaByDiaDeSemana(DiaSemana diaSemana) {
        if(diaSemana == null){
            throw new IllegalArgumentException("El dia de la semana no puede ser nulo");
        }
        try{
            return this.dao.getCartasDelDiaByDiaDeSemana(diaSemana);
        } catch (Exception e){
            throw new IllegalArgumentException("No se encontraron cartas del dia para el dia de la semana indicado");
        }
    }
    public List<CartaDelDia> getCartasDelDiaByDiaDeSemanaAndActivas(DiaSemana diaSemana) {
        if(diaSemana == null){
            throw new IllegalArgumentException("El dia de la semana no puede ser nulo");
        }
        try{
            return this.dao.getCartasDelDiaByDiaDeSemanaAndActivas(diaSemana);
        } catch (Exception e){
            throw new IllegalArgumentException("No se encontraron cartas del dia para el dia de la semana indicado");
        }
    }

    public List<CartaDelDia> getCartasDelDiaByDateRange(LocalDate fechaInicio, LocalDate fechaFin) {
        if(fechaInicio == null || fechaFin == null || fechaInicio.isAfter(fechaFin)){
            throw new IllegalArgumentException("Fechas invalidas");
        }
        try{
            return this.dao.getCartasDelDiaByDateRange(fechaInicio, fechaFin);
        } catch (Exception e){
            throw new IllegalArgumentException("No se encontraron cartas del dia para el rango de fechas indicado");
        }
    }


    private void sanitizeRequest(CartaDelDiaRequest cartaDelDiaRequest) {
        if(cartaDelDiaRequest.getFechaInicio() == null || cartaDelDiaRequest.getFechaFin().isBefore(LocalDate.now()) || cartaDelDiaRequest.getFechaInicio().isAfter(cartaDelDiaRequest.getFechaFin())) {
            throw new IllegalArgumentException("Fecha de inicio Invalida, no puede ser anterior al dia de hoy ni posterior a la fecha de fin");
        }
        if(cartaDelDiaRequest.getFechaFin() == null || cartaDelDiaRequest.getFechaFin().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Fecha de fin Invalida, no puede ser anterior al dia de hoy ni anterior a la fecha de inicio");
        }
        if(cartaDelDiaRequest.getMenuComun() == null || cartaDelDiaRequest.getMenuVegetariano() == null) {
            throw new IllegalArgumentException("Menu comun y menu vegetariano no pueden ser nulos");
        }
        if(cartaDelDiaRequest.getDiaSemana() == null) {
            throw new IllegalArgumentException("Dia de la semana no puede ser nulo");
        }
    }

    private CartaDelDia createCartaDelDia(CartaDelDiaRequest cartaDelDiaRequest) {
        return new CartaDelDia(cartaDelDiaRequest.getMenuVegetariano(), cartaDelDiaRequest.getMenuComun(), cartaDelDiaRequest.getDiaSemana(), cartaDelDiaRequest.getFechaInicio(), cartaDelDiaRequest.getFechaFin());
    }


}
