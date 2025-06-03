package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.PersonaDTO;
import java.util.ArrayList;

public interface PersonaDAO {

    public Integer insertar(PersonaDTO persona);

    public PersonaDTO obtenerPorId(Integer idPersona);

    public ArrayList<PersonaDTO> listarTodos();

    public Integer modificar(PersonaDTO persona);

    public Integer eliminar(PersonaDTO persona);
}
