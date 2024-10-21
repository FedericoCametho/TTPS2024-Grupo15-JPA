package org.example.ttps2024grupo15.controller.request.carta.producto;

import org.example.ttps2024grupo15.model.carta.producto.TipoComida;



public class ComidaRequest extends ProductoComercializableRequest {
    private TipoComida tipoComida;


    public TipoComida getTipoComida() {
        return tipoComida;
    }

    public void setTipoComida(TipoComida tipoComida) {
        this.tipoComida = tipoComida;
    }


}
