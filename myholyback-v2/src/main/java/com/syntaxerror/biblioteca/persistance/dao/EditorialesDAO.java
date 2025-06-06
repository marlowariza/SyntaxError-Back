package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.EditorialesDTO;
import java.util.ArrayList;

public interface EditorialesDAO {

    public Integer insertar(EditorialesDTO editorial);

    public EditorialesDTO obtenerPorId(Integer idEditorial);

    public ArrayList<EditorialesDTO> listarTodos();

    public Integer modificar(EditorialesDTO editorial);

    public Integer eliminar(EditorialesDTO editorial);
}
