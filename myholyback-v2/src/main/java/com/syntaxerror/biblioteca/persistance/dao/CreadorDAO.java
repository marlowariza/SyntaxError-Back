package com.syntaxerror.biblioteca.persistance.dao;

import java.util.ArrayList;

import com.syntaxerror.biblioteca.model.CreadorDTO;
import com.syntaxerror.biblioteca.model.MaterialDTO;

public interface CreadorDAO { //manejar relacion desde otra rama

    public Integer insertar(CreadorDTO autor);

    public CreadorDTO obtenerPorId(Integer autorId);

    public ArrayList<CreadorDTO> listarTodos();

    public Integer modificar(CreadorDTO autor);

    public Integer eliminar(CreadorDTO autor);
    
    
    //CRUD de tabla intermedia con Material
    public Integer asociarMaterial(MaterialDTO material);

    public Integer desasociarMaterial(MaterialDTO material);

    public boolean existeRelacionConMaterial(MaterialDTO material);

    public ArrayList<CreadorDTO> listarPorMaterial(MaterialDTO material);
    
}
