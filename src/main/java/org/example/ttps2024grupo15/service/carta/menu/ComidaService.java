package org.example.ttps2024grupo15.service.carta.menu;

import jakarta.transaction.Transactional;
import org.example.ttps2024grupo15.dao.menu.ComidaDAO;
import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.carta.producto.Menu;
import org.example.ttps2024grupo15.controller.request.carta.menu.producto.ComidaRequest;

public class ComidaService extends ProductoComercializableService<Comida, ComidaDAO, ComidaRequest> {

    public ComidaService(ComidaDAO comidaDAO) {
        super(comidaDAO);
    }


    @Transactional
    public Comida updateComidaMenuRelation(Menu menu, Long comidaId){
        Comida comida = this.getProductById(comidaId);
        comida.setComidaInMenu(menu);
        return this.dao.update(comida);
    }

    @Override
    protected void sanitizeRequestSpecificFields(ComidaRequest comidaRequest) {
        if(comidaRequest.getTipoComida() == null){
            throw new IllegalArgumentException("Tipo de comida no puede ser nulo o no existir");
        }
    }

    @Override
    protected void setUpdateSpecificFields(Comida originalProduct, ComidaRequest request) {
        originalProduct.setTipoComida(request.getTipoComida());
    }

    @Override
    protected Comida createProductoComercializable(ComidaRequest request) {
        return new Comida(request.getNombre(), request.getTipoComida(), request.getPrecio(), request.getImagen());
    }

    @Override
    protected void updateComidasEnMenuRelation(Comida originalProduct, Comida result) {
        // no aplica a este caso, solo para menues
    }
    @Override
    protected void updateSpecificRelations(Comida originalProduc, Comida updatedProduct, ComidaRequest request) {
        // no aplica a este caso, solo para menues
    }



}
