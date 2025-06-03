package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.CreadorDTO;
import com.syntaxerror.biblioteca.model.MaterialDTO;
import com.syntaxerror.biblioteca.model.TemaDTO;
import java.util.ArrayList;

public interface MaterialDAO {

    public Integer insertar(MaterialDTO material);

    public MaterialDTO obtenerPorId(Integer idMaterial);

    public ArrayList<MaterialDTO> listarTodos();

    public Integer modificar(MaterialDTO material);

    public Integer eliminar(MaterialDTO material);
    
    //CRUD de tabla intermedia con Creador
    public Integer asociarCreador(CreadorDTO creador);

    public Integer desasociarCreador(CreadorDTO creador);

    public boolean existeRelacionConCreador(CreadorDTO creador);

    public ArrayList<MaterialDTO> listarPorCreador(CreadorDTO creador);
    
    //CRUD de tabla intermedia con Tema
    public Integer asociarTema(TemaDTO tema);

    public Integer desasociarTema(TemaDTO tema);

    public boolean existeRelacionConTema(TemaDTO tema);

    public ArrayList<MaterialDTO> listarPorTema(TemaDTO tema);
}
