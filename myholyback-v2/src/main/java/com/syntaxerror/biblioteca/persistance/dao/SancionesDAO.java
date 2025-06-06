package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.SancionesDTO;
import java.util.ArrayList;

public interface SancionesDAO {

    public Integer insertar(SancionesDTO sancion);

    public SancionesDTO obtenerPorId(Integer idSancion);

    public ArrayList<SancionesDTO> listarTodos();

    public Integer modificar(SancionesDTO sancion);

    public Integer eliminar(SancionesDTO sancion);
}
