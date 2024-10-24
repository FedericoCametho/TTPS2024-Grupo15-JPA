package org.example.ttps2024grupo15.service.carrito;

import org.example.ttps2024grupo15.model.carrito.Carrito;
import org.example.ttps2024grupo15.model.carrito.Compra;

import java.util.logging.Logger;

public class CarritoService {
    private static final Logger LOGGER = Logger.getLogger(CarritoService.class.getName());
    private CompraService compraService;

    public CarritoService(CompraService compraService){
        this.compraService = compraService;
    }


    public Compra confirmarCompraCarrito(Carrito carrito){
        this.sanitize(carrito);
        return this.compraService.save(carrito);
    }


    private void sanitize(Carrito carrito) {
        if (carrito.getUsuario() == null) {
            throw new IllegalArgumentException("Usuario es requerido");
        }
        if ((carrito.getMenues() == null || carrito.getMenues().isEmpty()) && (carrito.getComidas() == null || carrito.getComidas().isEmpty()) ){
            throw new IllegalArgumentException("Menues o Comidas son requeridos");
        }
        if (carrito.getPrecioTotal() > 0) {
            throw new IllegalArgumentException("Importe Total  no puede ser 0");
        }

    }


}
