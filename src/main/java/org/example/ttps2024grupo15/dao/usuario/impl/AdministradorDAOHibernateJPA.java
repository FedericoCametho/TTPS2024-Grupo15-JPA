package org.example.ttps2024grupo15.dao.usuario.impl;

import org.example.ttps2024grupo15.dao.usuario.UsuarioDAO;
import org.example.ttps2024grupo15.model.usuario.Administrador;

public class AdministradorDAOHibernateJPA extends UsuarioDAOHibernateJPA<Administrador> implements UsuarioDAO<Administrador> {
    public AdministradorDAOHibernateJPA() {
        super(Administrador.class);
    }
}
