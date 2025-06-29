package com.syntaxerror.biblioteca.persistance.dao;

import java.util.ArrayList;

import com.syntaxerror.biblioteca.model.TemasDTO;
import java.util.List;

public interface TemaDAO { 
    
    public Integer insertar(TemasDTO tema);

    public TemasDTO obtenerPorId(Integer idTema);

    public ArrayList<TemasDTO> listarTodos();

    public Integer modificar(TemasDTO tema);

    public Integer eliminar(TemasDTO tema);
    
    public List<TemasDTO> listarNombresTemas();
}
