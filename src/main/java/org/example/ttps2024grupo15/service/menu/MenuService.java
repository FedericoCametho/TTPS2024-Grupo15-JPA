package org.example.ttps2024grupo15.service.menu;

import jakarta.transaction.Transactional;
import org.example.ttps2024grupo15.dao.menu.MenuDAO;
import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.carta.producto.Menu;
import org.example.ttps2024grupo15.model.request.menu.MenuRequest;

import java.util.List;

public class MenuService {
    private MenuDAO menuDAO;
    private ComidaService comidaService;
    public MenuService(MenuDAO menuDAO, ComidaService comidaService) {
        this.menuDAO = menuDAO;
        this.comidaService = comidaService;
    }

    public Menu getMenuById(Long id) {
        return menuDAO.getById(id);
    }
    public List<Menu> getAllMenues() {
        return menuDAO.getAll();
    }

    public List<Menu> getMenusByNombre(String nombre) {
        return menuDAO.findByNombre(nombre);
    }
    public List<Menu> getMenusByPrecio(Double precio) {
        return menuDAO.findByPrecio(precio);
    }
    @Transactional
    public Menu saveMenu(MenuRequest menuRequest) {
        this.sanitizeMenuRequest(menuRequest);
        Menu menu = new Menu(menuRequest.getNombre(), menuRequest.getPrecio(), menuRequest.getComidas(), menuRequest.getImagen());
        try{
            Menu result = menuDAO.save(menu);
            this.updateComidasEnMenuRelation(menu.getComidas(), result);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    @Transactional
    public void deleteMenu(Long id) {
        menuDAO.delete(id);
    }

    private void sanitizeMenuRequest(MenuRequest menuRequest){
        if(menuRequest.getNombre() == null || menuRequest.getNombre().isEmpty()){
            throw new IllegalArgumentException("Nombre de menu no puede ser nulo o vacio");
        }
        if(menuRequest.getPrecio() == null || menuRequest.getPrecio() < 0){
            throw new IllegalArgumentException("Precio de menu no puede ser nulo o negativo");
        }
        if(menuRequest.getComidas() == null || menuRequest.getComidas().isEmpty()){
            throw new IllegalArgumentException("Menu debe tener al menos una comida");
        }
    }

    private void updateComidasEnMenuRelation(List<Comida> comidas, Menu menu){
        comidas.forEach(
                comida -> {
                    this.comidaService.updateComidaMenuRelation(menu,comida.getId());
                }
        );
    }

}
