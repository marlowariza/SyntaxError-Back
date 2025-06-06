package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.MaterialesDTO;
import java.util.ArrayList;

public interface MaterialesDAO { //falta tabla intermedia

    public Integer insertar(MaterialesDTO material);

    public MaterialesDTO obtenerPorId(Integer idMaterial);

    public ArrayList<MaterialesDTO> listarTodos();

    public Integer modificar(MaterialesDTO material);

    public Integer eliminar(MaterialesDTO material);
    
}
