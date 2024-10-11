package org.example.ttps2024grupo15.dao.usuario;

import org.example.ttps2024grupo15.model.usuario.Alumno;

import java.util.List;

public interface AlumnoDAO extends UsuarioDAO<Alumno> {
    public List<Alumno> getByHabilitado();
}
