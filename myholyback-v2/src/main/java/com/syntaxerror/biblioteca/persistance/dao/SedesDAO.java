package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.SedesDTO;
import java.util.ArrayList;

/**
 *
 * @author Fabian
 */
public interface SedesDAO {

    public Integer insertar(SedesDTO sede);

    public SedesDTO obtenerPorId(Integer idSede);

    public ArrayList<SedesDTO> listarTodos();

    public Integer modificar(SedesDTO sede);

    public Integer eliminar(SedesDTO sede);
}
