package org.example.ttps2024grupo15.service.menu;

import jakarta.transaction.Transactional;
import org.example.ttps2024grupo15.dao.menu.MenuDAO;
import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.carta.producto.Menu;
import org.example.ttps2024grupo15.model.request.menu.MenuRequest;

import java.util.List;
import java.util.stream.Collectors;

public class MenuService {
    private MenuDAO menuDAO;
    private ComidaService comidaService;
    public MenuService(MenuDAO menuDAO, ComidaService comidaService) {
        this.menuDAO = menuDAO;
        this.comidaService = comidaService;
    }

    @Transactional
    public Menu save(MenuRequest menuRequest) {
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
    public Menu update(Long idMenu, MenuRequest menuRequest){
        this.sanitizeMenuRequest(menuRequest);
        Menu menu = this.getMenuById(idMenu);
        menu.setNombre(menuRequest.getNombre());
        menu.setPrecio(menuRequest.getPrecio());
        menu.setFoto(menuRequest.getImagen());
        try{
            Menu result = menuDAO.update(menu);
            List<Long> idComidasMenu = menu.getComidas().stream().mapToLong(Comida::getId).boxed().collect(Collectors.toList());
            List<Comida> comidasToUpdate = menuRequest.getComidas().stream().filter(comida -> !idComidasMenu.contains(comida.getId())).collect(Collectors.toList());
            if(!comidasToUpdate.isEmpty()){
                this.updateComidasEnMenuRelation(menu.getComidas(), result);
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    @Transactional
    public void delete(Long id) {
        menuDAO.delete(id);
    }

    public Menu getMenuById(Long id) {
        return menuDAO.getById(id);
    }
    public List<Menu> getAllMenues() {
        return menuDAO.getAll();
    }

    public List<Menu> getMenuesByNombre(String nombre) {
        return menuDAO.findByNombre(nombre);
    }
    public List<Menu> getMenuesByPrecio(Double precio) {
        return menuDAO.findByPrecio(precio);
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
