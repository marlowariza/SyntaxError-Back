package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.MaterialesDTO;
import java.util.ArrayList;
import java.util.List;

public interface MaterialDAO {

    public Integer insertar(MaterialesDTO material);

    public MaterialesDTO obtenerPorId(Integer idMaterial);

    public ArrayList<MaterialesDTO> listarTodos();

    public List<MaterialesDTO> listarTodosPaginado(int limite, int offset);

    public Integer modificar(MaterialesDTO material);

    public Integer eliminar(MaterialesDTO material);

    public List<MaterialesDTO> listarPorTituloConteniendo(String texto, int limite, int offset);

    public ArrayList<MaterialesDTO> listarVigentesPorTituloConteniendo(String texto, int limite, int offset);

    public List<MaterialesDTO> listarMaterialesVigentesPorCreadorFiltro(String filtro, int limite, int offset);

   public ArrayList<MaterialesDTO> listarPorSede(Integer idSede, int limite, int offset) ;

    public List<MaterialesDTO> listarVigentesPorSede(Integer idSede, int limite, int offset);

    List<MaterialesDTO> listarMasSolicitados(int limite, int offset);

    List<MaterialesDTO> listarMasRecientes(int limite, int offset);

    public List<MaterialesDTO> listarPorSedeYFiltro(Integer idSede, String filtro, boolean porTitulo, int limite, int offset);

}
