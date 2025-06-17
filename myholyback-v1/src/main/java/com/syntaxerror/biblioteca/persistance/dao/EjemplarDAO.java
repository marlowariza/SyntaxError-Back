package com.syntaxerror.biblioteca.persistance.dao;

import java.util.ArrayList;

import com.syntaxerror.biblioteca.model.EjemplarDTO;

public interface EjemplarDAO {
    public Integer insertar(EjemplarDTO ejemplar);

    public EjemplarDTO obtenerPorId(Integer ejemplarId);

    public ArrayList<EjemplarDTO> listarTodos();

    public Integer modificar(EjemplarDTO ejemplar);

    public Integer eliminar(EjemplarDTO ejemplar);
}