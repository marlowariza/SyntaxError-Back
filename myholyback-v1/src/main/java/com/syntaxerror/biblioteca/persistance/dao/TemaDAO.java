
package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.TemaDTO;
import java.util.ArrayList;

public interface TemaDAO {
    
    public Integer insertar(TemaDTO tema);

    public TemaDTO obtenerPorId(Integer idTema);

    public ArrayList<TemaDTO> listarTodos();

    public Integer modificar(TemaDTO tema);

    public Integer eliminar(TemaDTO tema);
}
