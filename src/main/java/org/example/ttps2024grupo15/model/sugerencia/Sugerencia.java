package org.example.ttps2024grupo15.model.sugerencia;

import org.example.ttps2024grupo15.model.usuario.Alumno;
import jakarta.persistence.*;

@Entity
public class Sugerencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private TipoSugerencia tipo;
    private String mensajeOriginal;
    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno usuario;


    public Sugerencia() {

    }
    public Sugerencia(String titulo, TipoSugerencia tipo, String mensaje, Alumno alumno) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.mensajeOriginal = mensaje;
        this.usuario = alumno;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public TipoSugerencia getTipo() {
        return tipo;
    }

    public void setTipo(TipoSugerencia tipo) {
        this.tipo = tipo;
    }

    public String getMensajeOriginal() {
        return mensajeOriginal;
    }

    public void setMensajeOriginal(String mensajeOriginal) {
        this.mensajeOriginal = mensajeOriginal;
    }

    public Alumno getUsuario() {
        return usuario;
    }

    public void setUsuario(Alumno alumno) {
        this.usuario = alumno;
    }
}
