package com.syntaxerror.biblioteca.persistance.dao;

import java.util.ArrayList;

import com.syntaxerror.biblioteca.model.PrestamoEjemplarDTO;

public interface PrestamoEjemplarDAO {
    public Integer insertar(PrestamoEjemplarDTO prestamoEjemplar);

    public PrestamoEjemplarDTO obtenerPorIds(Integer idPrestamo, Integer idEjemplar);

    public ArrayList<PrestamoEjemplarDTO> listarPorPrestamo(Integer idPrestamo);

    public ArrayList<PrestamoEjemplarDTO> listarPorEjemplar(Integer idEjemplar);

    public ArrayList<PrestamoEjemplarDTO> listarTodos();

    public Integer modificar(PrestamoEjemplarDTO prestamoEjemplar);

    public Integer eliminar(Integer idPrestamo, Integer idEjemplar);

    public boolean existeRelacion(Integer idPrestamo, Integer idEjemplar);
}
