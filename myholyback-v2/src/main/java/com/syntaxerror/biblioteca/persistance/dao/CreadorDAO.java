package com.syntaxerror.biblioteca.persistance.dao;

import java.util.ArrayList;

import com.syntaxerror.biblioteca.model.CreadoresDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;

public interface CreadorDAO { //manejar relacion desde otra rama

    public Integer insertar(CreadoresDTO autor);

    public CreadoresDTO obtenerPorId(Integer autorId);

    public ArrayList<CreadoresDTO> listarTodos();

    public Integer modificar(CreadoresDTO autor);

    public Integer eliminar(CreadoresDTO autor);
    
    
    //CRUD de tabla intermedia con Material
    public Integer asociarMaterial(MaterialesDTO material);

    public Integer desasociarMaterial(MaterialesDTO material);

    public boolean existeRelacionConMaterial(MaterialesDTO material);

    public ArrayList<CreadoresDTO> listarPorMaterial(MaterialesDTO material);
    
}
