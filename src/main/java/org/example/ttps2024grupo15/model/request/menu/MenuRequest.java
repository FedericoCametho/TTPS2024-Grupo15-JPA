package org.example.ttps2024grupo15.model.request.menu;

import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.request.carta.menu.ProductoComercializableRequest;

import java.util.List;

public class MenuRequest extends ProductoComercializableRequest {

    private List<Comida> comidas;


    public List<Comida> getComidas() {
        return comidas;
    }

    public void setComidas(List<Comida> comidas) {
        this.comidas = comidas;
    }
}
