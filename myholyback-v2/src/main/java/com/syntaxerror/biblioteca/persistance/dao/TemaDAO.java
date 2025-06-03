package com.syntaxerror.biblioteca.persistance.dao;

import java.util.ArrayList;

import com.syntaxerror.biblioteca.model.MaterialDTO;
import com.syntaxerror.biblioteca.model.TemaDTO;

public interface TemaDAO {
    
    public Integer insertar(TemaDTO tema);

    public TemaDTO obtenerPorId(Integer idTema);

    public ArrayList<TemaDTO> listarTodos();

    public Integer modificar(TemaDTO tema);

    public Integer eliminar(TemaDTO tema);
    
    //CRUD de tabla intermedia con Material
    public Integer asociarMaterial(MaterialDTO material);

    public Integer desasociarMaterial(MaterialDTO material);

    public boolean existeRelacionConMaterial(MaterialDTO material);

    public ArrayList<TemaDTO> listarPorMaterial(MaterialDTO material);
}
