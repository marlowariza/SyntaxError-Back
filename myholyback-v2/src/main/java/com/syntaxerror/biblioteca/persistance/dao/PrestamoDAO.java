package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.PrestamosDTO;
import com.syntaxerror.biblioteca.model.enums.EstadoPrestamoEjemplar;
import java.util.ArrayList;
import java.util.List;

public interface PrestamoDAO {

    public Integer insertar(PrestamosDTO prestamo);

    public PrestamosDTO obtenerPorId(Integer idPrestamo);

    public ArrayList<PrestamosDTO> listarTodos();

    public Integer modificar(PrestamosDTO prestamo);

    public Integer eliminar(PrestamosDTO prestamo);

    ArrayList<PrestamosDTO> listarPorIdPersona(int idPersona);
    
    public List<PrestamosDTO> listarTodosPaginado(int limite, int offset);
    
    public List<PrestamosDTO> listarPorSedePaginado(int limite, int offset, int sedeId);
    
    public List<PrestamosDTO> listarPrestamosPorEstadoPaginado(EstadoPrestamoEjemplar estado, int limite, int offset);
    
    public String obtenerEstadoPrestamo(int idPrestamo);
}
