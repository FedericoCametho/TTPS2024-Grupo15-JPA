package org.example.ttps2024grupo15.controller.request.sugerencia;

import org.example.ttps2024grupo15.model.sugerencia.TipoSugerencia;

public class SugerenciaRequest {
    private String titulo;
    private TipoSugerencia tipoSugerencia;
    private String mensajeOriginal;
    private Long alumnoId;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public TipoSugerencia getTipoSugerencia() {
        return tipoSugerencia;
    }

    public void setTipoSugerencia(TipoSugerencia tipoSugerencia) {
        this.tipoSugerencia = tipoSugerencia;
    }

    public String getMensajeOriginal() {
        return mensajeOriginal;
    }

    public void setMensajeOriginal(String mensajeOriginal) {
        this.mensajeOriginal = mensajeOriginal;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Long alumnoId) {
        this.alumnoId = alumnoId;
    }
}
