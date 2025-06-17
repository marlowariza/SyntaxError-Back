package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.EditorialDTO;
import java.util.ArrayList;

public interface EditorialDAO {

    public Integer insertar(EditorialDTO editorial);

    public EditorialDTO obtenerPorId(Integer idEditorial);

    public ArrayList<EditorialDTO> listarTodos();

    public Integer modificar(EditorialDTO editorial);

    public Integer eliminar(EditorialDTO editorial);
}
