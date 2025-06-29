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

    public List<MaterialesDTO> listarPorTituloConteniendoGenerico(String texto, int limite, int offset, boolean soloVigentes);

    public List<MaterialesDTO> listarMaterialesVigentesPorCreadorFiltro(String filtro, int limite, int offset);

    public List<MaterialesDTO> listarPorSedeGenerico(Integer idSede, int limite, int offset, boolean soloVigentes);

    public List<MaterialesDTO> listarMasSolicitados(int limite, int offset);

    public List<MaterialesDTO> listarMasRecientes(int limite, int offset);

    public List<MaterialesDTO> listarPorSedeYFiltro(Integer idSede, String filtro, boolean porTitulo, int limite, int offset);

    int contarTodos();

    public List<CreadoresDTO> listarCreadoresPorMaterial(Integer idMaterial);

    public List<TemasDTO> listarTemasPorMaterial(Integer idMaterial);

    public List<MaterialesDTO> listarPaginadoPorNivel(Nivel nivel, int limite, int offset);

    public List<MaterialesDTO> listarPaginadoPorTema(String descripcionTema, int limite, int offset);

    public List<MaterialesDTO> listarPaginadoPorEditorial(String nombreEditorial, int limite, int offset);

    public String obtenerNombreCreadorRandomPorMaterial(Integer idMaterial);

    public List<MaterialesDTO> listarMaterialesPorTituloParcialPaginado(String textoBusqueda, Integer sedeId, int limite, int offset);

    public int contarMaterialesPorSede(Integer idSede);

    public List<MaterialesDTO> listarTodosSeek(String ultimoTitulo, int ultimoId, int limite);

    public List<MaterialesDTO> buscarMaterialesUsuario(Integer idTema, Integer idAutor, Integer idNivel, String filtro, int limite, int offset);

    int contarMaterialesUsuario(Integer idTema, Integer idAutor, Integer idNivel, String filtro);

}
