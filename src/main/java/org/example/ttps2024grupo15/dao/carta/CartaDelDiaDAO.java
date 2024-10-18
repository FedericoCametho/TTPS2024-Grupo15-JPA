package org.example.ttps2024grupo15.dao.carta;

import org.example.ttps2024grupo15.dao.GenericDAO;
import org.example.ttps2024grupo15.model.carta.CartaDelDia;
import org.example.ttps2024grupo15.model.carta.DiaSemana;

import java.time.LocalDate;
import java.util.List;

public interface CartaDelDiaDAO extends GenericDAO<CartaDelDia> {
    List<CartaDelDia> getCartasDelDiaByDiaDeSemana(DiaSemana diaSemana);
    List<CartaDelDia> getCartasDelDiaActivas();
    List<CartaDelDia> getCartasDelDiaByDiaDeSemanaAndActivas(DiaSemana diaSemana);
    List<CartaDelDia> getCartasDelDiaByDateRange(LocalDate fechaInicio, LocalDate fechaFin);
}
