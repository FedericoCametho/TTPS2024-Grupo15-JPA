package org.example.ttps2024grupo15.service.usuario;

import jakarta.transaction.Transactional;
import org.example.ttps2024grupo15.dao.usuario.AlumnoDAO;
import org.example.ttps2024grupo15.model.permiso.Rol;
import org.example.ttps2024grupo15.model.request.usuario.AlumnoRequest;
import org.example.ttps2024grupo15.model.usuario.Alumno;

import java.util.List;

public class AlumnoService {
    private AlumnoDAO alumnoDAO;
    public AlumnoService(AlumnoDAO alumnoDAO) {
        this.alumnoDAO = alumnoDAO;
    }

    @Transactional
    public Alumno saveAlumno(AlumnoRequest alumnoRequest) {
        this.sanitizeAlumnoRequest(alumnoRequest);
        Alumno alumno = new Alumno(alumnoRequest.getDni(),alumnoRequest.getNombre(), alumnoRequest.getApellido(), alumnoRequest.getEmail());
        return alumnoDAO.save(alumno);
    }

    public Alumno getAlumnoById(Long id) {
        return alumnoDAO.getById(id);
    }
    public Alumno getAlumnoByEmail(String email) {
        return alumnoDAO.getByEmail(email);
    }
    public Alumno getAlumnoByNombre(String nombre) {
        return alumnoDAO.getUsuarioPorNombre(nombre).get(0);
    }
    public Alumno getAlumnoByApellido(String apellido) {
        return alumnoDAO.getUsuarioPorApellido(apellido).get(0);
    }
    public List<Alumno> getAlumnos() {
        return alumnoDAO.getAll();
    }
    public List<Alumno> getAlumnosPorRol(Rol rol) {
        return alumnoDAO.getUsuariosPorRol(rol);
    }
    private void sanitizeAlumnoRequest(AlumnoRequest alumnoRequest) {
        if(alumnoRequest.getNombre() == null || alumnoRequest.getNombre().isEmpty()){
            throw new IllegalArgumentException("El nombre del alumno no puede ser nulo o vacio");
        }
        if(alumnoRequest.getApellido() == null || alumnoRequest.getApellido().isEmpty()){
            throw new IllegalArgumentException("El apellido del alumno no puede ser nulo o vacio");
        }
        if(alumnoRequest.getEmail() == null || alumnoRequest.getEmail().isEmpty()){
            throw new IllegalArgumentException("El email del alumno no puede ser nulo o vacio");
        }
        if(alumnoRequest.getDni() == null){
            throw new IllegalArgumentException("El dni del alumno no puede ser nulo");
        }
    }
}
