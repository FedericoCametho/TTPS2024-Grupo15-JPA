package org.example.ttps2024grupo15.service.carta.producto;

import jakarta.persistence.NoResultException;
import org.example.ttps2024grupo15.dao.menu.MenuDAO;
import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.carta.producto.Menu;
import org.example.ttps2024grupo15.controller.request.carta.producto.MenuRequest;
import org.example.ttps2024grupo15.service.helper.RequestValidatorHelper;

import java.util.List;
import java.util.stream.Collectors;

public class MenuService extends ProductoComercializableService<Menu, MenuDAO, MenuRequest> {
    private ComidaService comidaService;
    public MenuService(MenuDAO menuDAO, ComidaService comidaService) {
        super(menuDAO);
        this.comidaService = comidaService;
    }

    @Override
    public Menu getProductById(Long id){
        RequestValidatorHelper.validateID(id);
        try{
            return dao.getById(id);
        } catch (NoResultException e){
            e.printStackTrace();
            throw new IllegalArgumentException("El producto no existe");
        }
    }

    @Override
    public List<Menu> getProductsByName(String name){
        RequestValidatorHelper.validateStringInputParameter(name, "El nombre del producto no puede ser nulo o vacío");
        try{
            return this.dao.findByNombre(name);
        } catch (NoResultException e){
            e.printStackTrace();
            throw new IllegalArgumentException("El producto no existe");
        }
    }

    @Override
    public List<Menu> getProductsByPrice(Double price){
        RequestValidatorHelper.validateDoubleInputParameter(price, "El precio del producto no puede ser nulo o negativo");
        try{
            return this.dao.findByPrecio(price);
        } catch (NoResultException e){
            e.printStackTrace();
            throw new IllegalArgumentException("El producto no existe");
        }
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
