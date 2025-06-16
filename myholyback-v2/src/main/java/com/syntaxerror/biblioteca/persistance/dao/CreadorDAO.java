package com.syntaxerror.biblioteca.persistance.dao;

import java.util.ArrayList;

import com.syntaxerror.biblioteca.model.CreadoresDTO;

public interface CreadorDAO { 

    public Integer insertar(CreadoresDTO autor);

    public CreadoresDTO obtenerPorId(Integer autorId);

    public ArrayList<CreadoresDTO> listarTodos();

    public Integer modificar(CreadoresDTO autor);

    public Integer eliminar(CreadoresDTO autor);
}
