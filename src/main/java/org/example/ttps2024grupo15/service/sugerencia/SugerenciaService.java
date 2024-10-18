package org.example.ttps2024grupo15.service.sugerencia;

import org.example.ttps2024grupo15.dao.sugerencia.SugerenciaDAO;
import org.example.ttps2024grupo15.controller.request.sugerencia.SugerenciaRequest;
import org.example.ttps2024grupo15.model.sugerencia.Sugerencia;
import org.example.ttps2024grupo15.model.sugerencia.TipoSugerencia;
import org.example.ttps2024grupo15.model.usuario.Alumno;
import org.example.ttps2024grupo15.service.helper.RequestValidatorHelper;
import org.example.ttps2024grupo15.service.usuario.AlumnoService;

import java.util.List;

public class SugerenciaService {
    private SugerenciaDAO sugerenciaDAO;
    private AlumnoService alumnoService;
    public SugerenciaService(SugerenciaDAO sugerenciaDAO, AlumnoService alumnoService) {
        this.sugerenciaDAO = sugerenciaDAO;
        this.alumnoService = alumnoService;
    }

    public Sugerencia save(SugerenciaRequest sugerenciaRequest){
        this.sanitize(sugerenciaRequest);
        Sugerencia sugerencia = this.createSugerencia(sugerenciaRequest);
        try{
            return this.sugerenciaDAO.save(sugerencia);
        } catch (Exception e){
            throw new IllegalArgumentException("Error al guardar sugerencia");
        }
    }

    public List<Sugerencia> getSugerenciaByTipo(TipoSugerencia tipo){
        if (tipo == null){
            throw new IllegalArgumentException("Tipo de sugerencia no puede ser nulo");
        }

        try{
            return this.sugerenciaDAO.getSugerenciasByTipo(tipo);
        } catch (Exception e){
            throw new IllegalArgumentException("No se encontro sugerencia con tipo: " + tipo);
        }
    }


    public Sugerencia getById(Long id){
        RequestValidatorHelper.validateID(id);
        try{
            return this.sugerenciaDAO.getById(id);
        } catch (Exception e){
            throw new IllegalArgumentException("No se encontro sugerencia con id: " + id);
        }
    }

    public List<Sugerencia> getAll(){
        return this.sugerenciaDAO.getAll();
    }

    public void delete(Long id){
        RequestValidatorHelper.validateID(id);
        try{
            this.sugerenciaDAO.delete(id);
        } catch (Exception e){
            throw new IllegalArgumentException("Error al eliminar sugerencia");
        }
    }

    private void sanitize(SugerenciaRequest sugerenciaRequest) {
        if(sugerenciaRequest.getTitulo() == null || sugerenciaRequest.getTitulo().isEmpty()){
            throw new IllegalArgumentException("Titulo no puede ser nulo o vacio");
        }
        if(sugerenciaRequest.getTipoSugerencia() == null){
            throw new IllegalArgumentException("Tipo de sugerencia no puede ser nulo");
        }
        if(sugerenciaRequest.getMensajeOriginal() == null || sugerenciaRequest.getMensajeOriginal().isEmpty()){
            throw new IllegalArgumentException("Mensaje no puede ser nulo o vacio");
        }
        if(sugerenciaRequest.getAlumnoId() == null || sugerenciaRequest.getAlumnoId() <= 0){
            throw new IllegalArgumentException("Id de alumno no puede ser nulo o menor o igual a 0");
        }
    }

    private Sugerencia createSugerencia(SugerenciaRequest sugerenciaRequest){
        Sugerencia sugerencia = new Sugerencia();
        sugerencia.setTitulo(sugerenciaRequest.getTitulo());
        sugerencia.setTipo(sugerenciaRequest.getTipoSugerencia());
        sugerencia.setMensajeOriginal(sugerenciaRequest.getMensajeOriginal());
        Alumno alumno ;
        try{
            alumno = this.alumnoService.getUserById(sugerenciaRequest.getAlumnoId());
        } catch (Exception e){
            throw new IllegalArgumentException("No se encontro el alumno con id: " + sugerenciaRequest.getAlumnoId());
        }
        sugerencia.setUsuario(alumno);
        return sugerencia;
    }


}
