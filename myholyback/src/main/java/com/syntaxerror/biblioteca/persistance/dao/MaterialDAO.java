package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.MaterialDTO;
import java.util.ArrayList;
import java.util.List;

public interface MaterialDAO {

    public Integer insertar(MaterialDTO material);

    public MaterialDTO obtenerPorId(Integer idMaterial);

    public ArrayList<MaterialDTO> listarTodos();

    public Integer modificar(MaterialDTO material);

    public Integer eliminar(MaterialDTO material);

    public List<MaterialDTO> listarMasSolicitados(int limite, int offset);

    public List<MaterialDTO> listarMasRecientes(int limite, int offset);

    public List<MaterialDTO> listarPorSedeYFiltro(Integer idSede, String filtro, boolean porTitulo);
}
