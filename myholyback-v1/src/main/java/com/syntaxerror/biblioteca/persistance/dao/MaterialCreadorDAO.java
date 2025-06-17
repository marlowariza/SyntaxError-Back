package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.CreadorDTO;
import com.syntaxerror.biblioteca.model.MaterialDTO;
import java.util.ArrayList;

public interface MaterialCreadorDAO {
    public Integer asociar(Integer idMaterial, Integer idCreador);

    public Integer desasociar(Integer idMaterial, Integer idCreador);

    public boolean existeRelacion(Integer idMaterial, Integer idCreador);

    public ArrayList<CreadorDTO> listarCreadoresPorMaterial(Integer idMaterial);

    public ArrayList<MaterialDTO> listarMaterialesPorCreador(Integer idCreador);
}
