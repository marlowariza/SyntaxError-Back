package com.syntaxerror.biblioteca.persistance.dao;

import java.util.ArrayList;

import com.syntaxerror.biblioteca.model.PrestamosDeEjemplaresDTO;

public interface PrestamoEjemplarDAO {
    public Integer insertar(PrestamosDeEjemplaresDTO prestamoEjemplar);

    public PrestamosDeEjemplaresDTO obtenerPorId(Integer idPrestamo, Integer idEjemplar);

    public ArrayList<PrestamosDeEjemplaresDTO> listarTodos();
    
    public Integer modificar(PrestamosDeEjemplaresDTO prestamoEjemplar);

    public Integer eliminar(PrestamosDeEjemplaresDTO prestamoEjemplar);
    
}
