package org.example.ttps2024grupo15.model.usuario;

import org.example.ttps2024grupo15.model.permiso.Rol;
import jakarta.persistence.Entity;

@Entity
public class ResponsableDeTurno extends Usuario {
    public Turno turno;
    public ResponsableDeTurno(Integer dni, String email, String nombre, String apellido, Rol rol, Turno turno) {
        super(dni, email, nombre, apellido, rol);
        this.turno = turno;
    }

    public ResponsableDeTurno() {

    }
    public Turno getTurno() {
        return turno;
    }
    public void setTurno(Turno turno) {
        this.turno = turno;
    }
}
