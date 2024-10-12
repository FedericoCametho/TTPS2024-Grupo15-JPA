package org.example.ttps2024grupo15.model.request.usuario;

import org.example.ttps2024grupo15.model.carrito.Compra;
import jakarta.persistence.Lob;

import java.util.List;

public class AlumnoRequest extends UsuarioRequest {
    private byte[] foto;
    private boolean habilitado;

    public AlumnoRequest() {
        super();
    }


    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
}
