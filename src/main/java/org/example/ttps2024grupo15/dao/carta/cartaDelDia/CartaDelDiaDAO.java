package org.example.ttps2024grupo15.dao.carta.cartaDelDia;

import org.example.ttps2024grupo15.dao.GenericDAO;
import org.example.ttps2024grupo15.model.carta.CartaDelDia;
import org.example.ttps2024grupo15.model.carta.DiaSemana;

import java.util.List;

public interface CartaDelDiaDAO extends GenericDAO<CartaDelDia> {
    List<CartaDelDia> getCartaDelDiaByDiaSemana(DiaSemana diaSemana);
}
