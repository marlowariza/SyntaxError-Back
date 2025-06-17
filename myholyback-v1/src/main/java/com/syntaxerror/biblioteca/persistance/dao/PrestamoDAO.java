package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.PrestamoDTO;
import java.util.ArrayList;

public interface PrestamoDAO {

    public Integer insertar(PrestamoDTO prestamo);

    public PrestamoDTO obtenerPorId(Integer idPrestamo);

    public ArrayList<PrestamoDTO> listarTodos();

    public Integer modificar(PrestamoDTO prestamo);

    public Integer eliminar(PrestamoDTO prestamo);
}