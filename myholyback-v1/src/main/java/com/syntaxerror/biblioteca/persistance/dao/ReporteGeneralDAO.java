package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.ReporteGeneralDTO;
import java.util.ArrayList;

public interface ReporteGeneralDAO {

    ArrayList<ReporteGeneralDTO> listarPorPeriodo(Integer anio, Integer mes, Integer idPrestamo, Integer idPersona);

    public void insertarDatosDePrueba();

    public void eliminarDatosDePrueba();

    void generarReporteGeneral(Integer anio, Integer mes);
}
