package org.example.ttps2024grupo15.service.carta.menu;

import org.example.ttps2024grupo15.dao.menu.MenuDAO;
import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.carta.producto.Menu;
import org.example.ttps2024grupo15.model.request.carta.menu.MenuRequest;

import java.util.List;
import java.util.stream.Collectors;

public class MenuService extends ProductoComercializableService<Menu, MenuDAO, MenuRequest> {
    private ComidaService comidaService;
    public MenuService(MenuDAO menuDAO, ComidaService comidaService) {
        super(menuDAO);
        this.comidaService = comidaService;
    }

    @Override
    public void sanitizeRequestSpecificFields(MenuRequest menuRequest){
        if(menuRequest.getComidas() == null || menuRequest.getComidas().isEmpty()){
            throw new IllegalArgumentException("Menu debe tener al menos una comida");
        }
    }

    @Override
    public void updateComidasEnMenuRelation(Menu menuOriginal, Menu menuUpdated){
        menuOriginal.getComidas().forEach(
                comida -> {
                    this.comidaService.updateComidaMenuRelation(menuUpdated,comida.getId());
                }
        );
    }

    @Override
    protected void setUpdateSpecificFields(Menu product, MenuRequest request) {
        // no aplicable a este caso, solo para comidas
    }

    @Override
    public void updateSpecificRelations(Menu originalMenu, Menu updatedMenu, MenuRequest menuRequest){
        List<Long> idComidasMenu = originalMenu.getComidas().stream().mapToLong(Comida::getId).boxed().collect(Collectors.toList());
        List<Comida> comidasToUpdate = menuRequest.getComidas().stream().filter(comida -> !idComidasMenu.contains(comida.getId())).collect(Collectors.toList());
        if(!comidasToUpdate.isEmpty()){
            this.updateComidasEnMenuRelation(originalMenu, updatedMenu);
        }
    }

    @Override
    protected Menu createProductoComercializable(MenuRequest request) {
        return new Menu(request.getNombre(), request.getPrecio(), request.getComidas(), request.getImagen());
    }

}
