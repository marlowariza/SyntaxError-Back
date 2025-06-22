package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.CreadoresDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.TemasDTO;
import com.syntaxerror.biblioteca.model.enums.Nivel;
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

    public ArrayList<MaterialesDTO> listarPorSede(Integer idSede, int limite, int offset);

    public List<MaterialesDTO> listarVigentesPorSede(Integer idSede, int limite, int offset);

    List<MaterialesDTO> listarMasSolicitados(int limite, int offset);

    List<MaterialesDTO> listarMasRecientes(int limite, int offset);

    public List<MaterialesDTO> listarPorSedeYFiltro(Integer idSede, String filtro, boolean porTitulo, int limite, int offset);

    int contarTodos();

    List<CreadoresDTO> listarCreadoresPorMaterial(Integer idMaterial);

    List<TemasDTO> listarTemasPorMaterial(Integer idMaterial);

    List<MaterialesDTO> listarPaginadoPorNivel(Nivel nivel, int limite, int offset);

    List<MaterialesDTO> listarPaginadoPorTema(String descripcionTema, int limite, int offset);

    public List<MaterialesDTO> listarPaginadoPorEditorial(String nombreEditorial, int limite, int offset);

    String obtenerNombreCreadorRandomPorMaterial(Integer idMaterial);
    
    public List<MaterialesDTO> listarMaterialesPorTituloParcialPaginado(String textoBusqueda, Integer sedeId, int limite, int offset);
}
