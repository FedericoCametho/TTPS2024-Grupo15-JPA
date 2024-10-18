package org.example.ttps2024grupo15.service.usuario;



import org.example.ttps2024grupo15.dao.usuario.AlumnoDAO;
import org.example.ttps2024grupo15.controller.request.usuario.AlumnoRequest;
import org.example.ttps2024grupo15.model.usuario.Alumno;

import java.util.List;

public class AlumnoService extends UsuarioService<Alumno, AlumnoDAO, AlumnoRequest>{

    public AlumnoService(AlumnoDAO alumnoDAO) {
        super(alumnoDAO);
    }

    public List<Alumno> getAlumnosByEnabled() {
        return this.dao.getByHabilitado();
    }
    @Override
    protected Alumno createUsuario(AlumnoRequest alumnoRequest) {
        return new Alumno(alumnoRequest.getDni(), alumnoRequest.getEmail(),alumnoRequest.getNombre(), alumnoRequest.getApellido());
    }

    @Override
    protected void setUpdateSpecificFields(Alumno alumno, AlumnoRequest alumnoRequest) {
        alumno.setFotoDePerfil(alumnoRequest.getFoto());
        this.validarHabilitado(alumno, alumnoRequest.isHabilitado());
    }

    @Override
    protected void sanitizeRequestSpecificFields(AlumnoRequest usuarioRequest) {
        // No se requiere sanitizar campos específicos
    }

    private void validarHabilitado(Alumno alumno, boolean nuevoEstado){
        if(nuevoEstado){
            alumno.habilitar();
        } else {
            alumno.deshabilitar();
        }
    }

}
