package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.SancionDTO;
import java.util.ArrayList;

public interface SancionDAO {

    public Integer insertar(SancionDTO sancion);

    public SancionDTO obtenerPorId(Integer idSancion);

    public ArrayList<SancionDTO> listarTodos();

    public Integer modificar(SancionDTO sancion);

    public Integer eliminar(SancionDTO sancion);
}
