package org.example.ttps2024grupo15.service.usuario;

import org.example.ttps2024grupo15.dao.usuario.ResponsableDeTurnoDAO;
import org.example.ttps2024grupo15.model.request.usuario.ResponsableDeTurnoRequest;
import org.example.ttps2024grupo15.model.usuario.ResponsableDeTurno;
import org.example.ttps2024grupo15.model.usuario.Turno;

import java.util.List;

public class ResponsableDeTurnoService extends UsuarioService<ResponsableDeTurno, ResponsableDeTurnoDAO, ResponsableDeTurnoRequest> {


    public ResponsableDeTurnoService(ResponsableDeTurnoDAO responsableDeTurnoDAO) {
        super(responsableDeTurnoDAO);
    }

    public List<ResponsableDeTurno> getResponsablesDeTurnoByTurno(Turno turno) {
        return this.dao.getByTurno(turno);
    }

    @Override
    protected void sanitizeRequestSpecificFields(ResponsableDeTurnoRequest usuarioRequest) {
        if(usuarioRequest.getTurno() == null){
            throw new IllegalArgumentException("El turno es requerido y solo pueden ser MANANA o TARDE");
        }
    }

    @Override
    protected void setUpdateSpecificFields(ResponsableDeTurno responsableDeTurno, ResponsableDeTurnoRequest responsableDeTurnoRequest) {
        responsableDeTurno.setTurno(responsableDeTurnoRequest.getTurno());
    }
    @Override
    protected ResponsableDeTurno createUsuario(ResponsableDeTurnoRequest usuarioRequest) {
        return new ResponsableDeTurno(usuarioRequest.getDni(), usuarioRequest.getEmail(),usuarioRequest.getNombre(), usuarioRequest.getApellido(), usuarioRequest.getTurno());
    }






}
