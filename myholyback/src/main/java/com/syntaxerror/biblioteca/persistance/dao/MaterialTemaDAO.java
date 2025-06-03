package com.syntaxerror.biblioteca.persistance.dao;

import java.util.ArrayList;

import com.syntaxerror.biblioteca.model.MaterialDTO;
import com.syntaxerror.biblioteca.model.TemaDTO;

public interface MaterialTemaDAO {
    public Integer asociar(Integer idMaterial, Integer idTema);

    public Integer desasociar(Integer idMaterial, Integer idTema);

    public boolean existeRelacion(Integer idMaterial, Integer idTema);

    public ArrayList<TemaDTO> listarTemasPorMaterial(Integer idMaterial);

    public ArrayList<MaterialDTO> listarMaterialesPorTema(Integer idTema);
}
