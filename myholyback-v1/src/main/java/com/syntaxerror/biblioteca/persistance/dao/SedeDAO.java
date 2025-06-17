package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.SedeDTO;
import java.util.ArrayList;

/**
 *
 * @author Fabian
 */
public interface SedeDAO {

    public Integer insertar(SedeDTO sede);

    public SedeDTO obtenerPorId(Integer idSede);

    public ArrayList<SedeDTO> listarTodos();

    public Integer modificar(SedeDTO sede);

    public Integer eliminar(SedeDTO sede);
}
