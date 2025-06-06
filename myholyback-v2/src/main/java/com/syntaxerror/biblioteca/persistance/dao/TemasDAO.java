package com.syntaxerror.biblioteca.persistance.dao;

import java.util.ArrayList;

import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.TemasDTO;

public interface TemasDAO {
    
    public Integer insertar(TemasDTO tema);

    public TemasDTO obtenerPorId(Integer idTema);

    public ArrayList<TemasDTO> listarTodos();

    public Integer modificar(TemasDTO tema);

    public Integer eliminar(TemasDTO tema);
    
    //CRUD de tabla intermedia con Material
    public Integer asociarMaterial(MaterialesDTO material);

    public Integer desasociarMaterial(MaterialesDTO material);

    public boolean existeRelacionConMaterial(MaterialesDTO material);

    public ArrayList<TemasDTO> listarPorMaterial(MaterialesDTO material);
}
