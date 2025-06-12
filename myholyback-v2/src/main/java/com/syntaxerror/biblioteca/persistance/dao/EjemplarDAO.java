package com.syntaxerror.biblioteca.persistance.dao;

import java.util.ArrayList;

import com.syntaxerror.biblioteca.model.EjemplaresDTO;
import com.syntaxerror.biblioteca.model.enums.TipoEjemplar;

public interface EjemplarDAO {

    public Integer insertar(EjemplaresDTO ejemplar);

    public EjemplaresDTO obtenerPorId(Integer ejemplarId);

    public ArrayList<EjemplaresDTO> listarTodos();

    public Integer modificar(EjemplaresDTO ejemplar);

    public Integer eliminar(EjemplaresDTO ejemplar);

    ArrayList<EjemplaresDTO> listarPorIdMaterial(Integer idMaterial);

    boolean existeEjemplarDigitalPorMaterial(Integer idMaterial);

    public ArrayList<EjemplaresDTO> listarEjemplaresPorFiltros(Integer idMaterial, Integer idSede, Boolean disponible, TipoEjemplar tipo);

    public int contarEjemplaresPorFiltros(Integer idMaterial, Integer idSede, Boolean disponible, TipoEjemplar tipo);

    

}
