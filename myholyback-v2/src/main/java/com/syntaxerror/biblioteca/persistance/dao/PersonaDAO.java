package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.PersonasDTO;
import java.util.ArrayList;

public interface PersonaDAO {

    public Integer insertar(PersonasDTO persona);

    public PersonasDTO obtenerPorId(Integer idPersona);

    public ArrayList<PersonasDTO> listarTodos();

    public Integer modificar(PersonasDTO persona);

    public Integer eliminar(PersonasDTO persona);

    PersonasDTO obtenerPorCredenciales(String identificador, String contrasenha);

}
