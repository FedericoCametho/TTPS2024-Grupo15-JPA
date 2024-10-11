package org.example.ttps2024grupo15.model.request.usuario;

import org.example.ttps2024grupo15.model.carrito.Compra;
import jakarta.persistence.Lob;

import java.util.List;

public class AlumnoRequest extends UsuarioRequest {
    private byte[] foto;

    public AlumnoRequest() {
        super();
    }

    public byte[] getFotoDePerfil() {
        return foto;
    }

    public void setFotoDePerfil(byte[] fotoDePerfil) {
        this.foto = fotoDePerfil;
    }
}
