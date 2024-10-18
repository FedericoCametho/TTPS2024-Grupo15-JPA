package org.example.ttps2024grupo15.service.usuario;

import org.example.ttps2024grupo15.dao.usuario.impl.AdministradorDAOHibernateJPA;
import org.example.ttps2024grupo15.controller.request.usuario.AdministradorRequest;
import org.example.ttps2024grupo15.model.usuario.Administrador;

public class AdministradorService extends UsuarioService<Administrador, AdministradorDAOHibernateJPA, AdministradorRequest> {
    public AdministradorService(AdministradorDAOHibernateJPA usuarioDAO) {
        super(usuarioDAO);
    }

    @Override
    protected Administrador createUsuario(AdministradorRequest usuarioRequest) {
        return new Administrador(usuarioRequest.getDni(), usuarioRequest.getEmail(),usuarioRequest.getNombre(), usuarioRequest.getApellido());
    }

    @Override
    protected void setUpdateSpecificFields(Administrador user, AdministradorRequest usuarioRequest) {
    }

    @Override
    protected void sanitizeRequestSpecificFields(AdministradorRequest usuarioRequest) {
    }

}
