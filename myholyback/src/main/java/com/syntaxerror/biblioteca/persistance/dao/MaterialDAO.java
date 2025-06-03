package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.MaterialDTO;
import java.util.ArrayList;

public interface MaterialDAO {

    public Integer insertar(MaterialDTO material);

    public MaterialDTO obtenerPorId(Integer idMaterial);

    public ArrayList<MaterialDTO> listarTodos();

    public Integer modificar(MaterialDTO material);

    public Integer eliminar(MaterialDTO material);
}
