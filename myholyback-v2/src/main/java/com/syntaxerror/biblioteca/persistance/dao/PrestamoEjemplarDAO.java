package com.syntaxerror.biblioteca.persistance.dao;

import java.util.ArrayList;

import com.syntaxerror.biblioteca.model.PrestamoEjemplarDTO;

public interface PrestamoEjemplarDAO {
    public Integer insertar(PrestamoEjemplarDTO prestamoEjemplar);

    public PrestamoEjemplarDTO obtenerPorId(Integer idPrestamo, Integer idEjemplar);

    public ArrayList<PrestamoEjemplarDTO> listarTodos();
    
    public Integer modificar(PrestamoEjemplarDTO prestamoEjemplar);

    public Integer eliminar(PrestamoEjemplarDTO prestamoEjemplar);
    
}
