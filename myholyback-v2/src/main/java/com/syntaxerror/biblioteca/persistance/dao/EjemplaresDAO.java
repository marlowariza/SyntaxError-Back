package com.syntaxerror.biblioteca.persistance.dao;

import java.util.ArrayList;

import com.syntaxerror.biblioteca.model.EjemplaresDTO;

public interface EjemplaresDAO {
    public Integer insertar(EjemplaresDTO ejemplar);

    public EjemplaresDTO obtenerPorId(Integer ejemplarId);

    public ArrayList<EjemplaresDTO> listarTodos();

    public Integer modificar(EjemplaresDTO ejemplar);

    public Integer eliminar(EjemplaresDTO ejemplar);
}