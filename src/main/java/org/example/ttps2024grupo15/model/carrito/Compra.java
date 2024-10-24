package org.example.ttps2024grupo15.model.carrito;

import org.example.ttps2024grupo15.model.carta.producto.Comida;
import org.example.ttps2024grupo15.model.carta.producto.Menu;
import org.example.ttps2024grupo15.model.carta.producto.ProductoComercializable;
import org.example.ttps2024grupo15.model.usuario.Alumno;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fecha;
    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno usuario;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "compra_menu",
            joinColumns = @JoinColumn(name = "compra_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    private List<Menu> menues;
    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(
            name = "compra_comida",
            joinColumns = @JoinColumn(name = "compra_id"),
            inverseJoinColumns = @JoinColumn(name = "comida_id")
    )
    private List<Comida> comidas;
    public Double importe;
    public Boolean pagado;

    public Compra() {

    }
    public Compra(Carrito carrito){
        this.fecha = LocalDateTime.now();
        this.usuario = carrito.getUsuario();
        this.menues = carrito.getMenues();
        this.comidas = carrito.getComidas();
        this.importe = carrito.getPrecioTotal();
        this.pagado = false;
    }

    public List<ProductoComercializable> getProductos(){
        List<ProductoComercializable> productos = new ArrayList<>();
        productos.addAll(this.comidas);
        productos.addAll(this.menues);
        return productos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Alumno getUsuario() {
        return usuario;
    }

    public void setUsuario(Alumno usuario) {
        this.usuario = usuario;
    }

    public List<Menu> getMenues() {
        return menues;
    }

    public void setMenues(List<Menu> menues) {
        this.menues = menues;
    }

    public List<Comida> getComidas() {
        return comidas;
    }

    public void setComidas(List<Comida> comidas) {
        this.comidas = comidas;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Boolean isPagado() {
        return pagado;
    }

    public void marcarPagado() {
        this.pagado = true;
    }
    public void marcarImpago(){
        this.pagado = false;
    }
}
