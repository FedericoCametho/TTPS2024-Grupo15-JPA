package org.example.ttps2024grupo15.dao.usuario;

import org.example.ttps2024grupo15.model.usuario.ResponsableDeTurno;
import org.example.ttps2024grupo15.model.usuario.Turno;

import java.util.List;

public interface ResponsableDeTurnoDAO extends UsuarioDAO<ResponsableDeTurno> {
    List<ResponsableDeTurno> getByTurno(Turno turno);
}
