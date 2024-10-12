package org.example.ttps2024grupo15.model.request.usuario;

import org.example.ttps2024grupo15.model.usuario.Turno;

public class ResponsableDeTurnoRequest extends UsuarioRequest{
    private Turno turno;

    public ResponsableDeTurnoRequest() {
        super();
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }
}
