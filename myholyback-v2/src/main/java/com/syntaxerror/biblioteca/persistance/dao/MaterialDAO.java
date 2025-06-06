package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.CreadoresDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.TemasDTO;
import java.util.ArrayList;

public interface MaterialDAO {

    public Integer insertar(MaterialesDTO material);

    public MaterialesDTO obtenerPorId(Integer idMaterial);

    public ArrayList<MaterialesDTO> listarTodos();

    public Integer modificar(MaterialesDTO material);

    public Integer eliminar(MaterialesDTO material);
    
    //CRUD de tabla intermedia con Creador
    public Integer asociarCreador(CreadoresDTO creador);

    public Integer desasociarCreador(CreadoresDTO creador);

    public boolean existeRelacionConCreador(CreadoresDTO creador);

    public ArrayList<MaterialesDTO> listarPorCreador(CreadoresDTO creador);
    
    //CRUD de tabla intermedia con Tema
    public Integer asociarTema(TemasDTO tema);

    public Integer desasociarTema(TemasDTO tema);

    public boolean existeRelacionConTema(TemasDTO tema);

    public ArrayList<MaterialesDTO> listarPorTema(TemasDTO tema);
}
