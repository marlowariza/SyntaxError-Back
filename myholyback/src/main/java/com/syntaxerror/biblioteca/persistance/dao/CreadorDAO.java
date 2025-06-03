package com.syntaxerror.biblioteca.persistance.dao;

import java.util.ArrayList;

import com.syntaxerror.biblioteca.model.CreadorDTO;

public interface CreadorDAO { //manejar relacion desde otra rama

    public Integer insertar(CreadorDTO autor);

    public CreadorDTO obtenerPorId(Integer autorId);

    public ArrayList<CreadorDTO> listarTodos();

    public Integer modificar(CreadorDTO autor);

    public Integer eliminar(CreadorDTO autor);
}
