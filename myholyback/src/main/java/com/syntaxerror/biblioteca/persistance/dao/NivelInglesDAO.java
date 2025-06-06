package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.NivelInglesDTO;
import java.util.ArrayList;

public interface NivelInglesDAO {

    public Integer insertar(NivelInglesDTO nivelDeIngles);

    public NivelInglesDTO obtenerPorId(Integer idNivel);

    public ArrayList<NivelInglesDTO> listarTodos();

    public Integer modificar(NivelInglesDTO nivelDeIngles);

    public Integer eliminar(NivelInglesDTO nivelDeIngles);
}
