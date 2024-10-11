package org.example.ttps2024grupo15.service.usuario;

import jakarta.transaction.Transactional;
import org.example.ttps2024grupo15.dao.usuario.AlumnoDAO;
import org.example.ttps2024grupo15.model.permiso.Rol;
import org.example.ttps2024grupo15.model.request.usuario.AlumnoRequest;
import org.example.ttps2024grupo15.model.usuario.Alumno;

import java.util.List;

public class AlumnoService implements UsuarioService<Alumno>{
    private AlumnoDAO alumnoDAO;
    public AlumnoService(AlumnoDAO alumnoDAO) {
        this.alumnoDAO = alumnoDAO;
    }

    @Transactional
    public Alumno save(AlumnoRequest alumnoRequest) {
        this.sanitizeAlumnoRequest(alumnoRequest);
        Alumno alumno = new Alumno(alumnoRequest.getDni(), alumnoRequest.getEmail(),alumnoRequest.getNombre(), alumnoRequest.getApellido());
        return alumnoDAO.save(alumno);
    }

    @Transactional
    public Alumno update(Long idAlumno, AlumnoRequest alumnoRequest){
        this.sanitizeAlumnoRequest(alumnoRequest);
        Alumno alumno = this.getUserById(idAlumno);
        alumno.setNombre(alumnoRequest.getNombre());
        alumno.setApellido(alumnoRequest.getApellido());
        alumno.setEmail(alumnoRequest.getEmail());
        alumno.setDni(alumnoRequest.getDni());
        alumno.setFotoDePerfil(alumnoRequest.getFotoDePerfil());
        return alumnoDAO.update(alumno);
    }

    @Transactional
    public void delete(Long id) {
        alumnoDAO.delete(id);
    }
    @Transactional
    public void delete(Alumno alumno) {
        alumnoDAO.delete(alumno);
    }

    @Override
    public List<Alumno> getUserByRol() {
        return alumnoDAO.getUsuariosPorRol(Rol.ALUMNO);
    }

    @Override
    public Alumno getUserById(Long id) {
        return alumnoDAO.getById(id);
    }

    @Override
    public List<Alumno> getUsersByName(String name) {
        return alumnoDAO.getUsuarioPorNombre(name);
    }

    @Override
    public List<Alumno> getUsersByLastName(String lastName) {
        return alumnoDAO.getUsuarioPorApellido(lastName);
    }

    @Override
    public List<Alumno> getUsersByEnabled() {
        return alumnoDAO.getByHabilitado();
    }

    @Override
    public Alumno getUserByEmail(String email) {
        return alumnoDAO.getByEmail(email);
    }

    public List<Alumno> getAlumnosPorRol() {
        return alumnoDAO.getUsuariosPorRol(Rol.ALUMNO);
    }

    public List<Alumno> getAll(){
        return alumnoDAO.getAll();
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
