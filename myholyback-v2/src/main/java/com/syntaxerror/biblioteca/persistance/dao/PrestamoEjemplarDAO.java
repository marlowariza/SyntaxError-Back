package com.syntaxerror.biblioteca.persistance.dao;

import java.util.ArrayList;

import com.syntaxerror.biblioteca.model.PrestamosDeEjemplaresDTO;

public interface PrestamoEjemplarDAO {

    public Integer insertar(PrestamosDeEjemplaresDTO prestamoEjemplar);

    public PrestamosDeEjemplaresDTO obtenerPorId(Integer idPrestamo, Integer idEjemplar);

    public ArrayList<PrestamosDeEjemplaresDTO> listarTodos();

    public Integer modificar(PrestamosDeEjemplaresDTO prestamoEjemplar);

    public Integer eliminar(PrestamosDeEjemplaresDTO prestamoEjemplar);

    ArrayList<PrestamosDeEjemplaresDTO> listarPorIdPrestamo(int idPrestamo);

    ArrayList<Integer> obtenerIdEjemplaresPrestadosPorIdPersona(int idPersona);

    ArrayList<Integer> obtenerIdEjemplaresSolicitadosPorPersona(int idPersona);

    int contarPrestamosAtrasadosPorIdPersona(int idPersona);

    int contarPrestamosPorIdMaterial(int idMaterial);

    public ArrayList<PrestamosDeEjemplaresDTO> listarPrestamosDevueltos();

    public ArrayList<PrestamosDeEjemplaresDTO> listarPrestamosAtrasados();

    public ArrayList<PrestamosDeEjemplaresDTO> listarPrestamosSolicitados();

    public ArrayList<PrestamosDeEjemplaresDTO> listarPrestamosNoCulminados();

}
