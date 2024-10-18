package org.example.ttps2024grupo15.dao.sugerencia;

import org.example.ttps2024grupo15.dao.GenericDAO;
import org.example.ttps2024grupo15.model.sugerencia.Sugerencia;
import org.example.ttps2024grupo15.model.sugerencia.TipoSugerencia;

import java.util.List;

public interface SugerenciaDAO extends GenericDAO<Sugerencia> {
    List<Sugerencia> getSugerenciasByTipo(TipoSugerencia tipo);
}
