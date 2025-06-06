package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.ReportesPorSedeDTO;
import java.util.ArrayList;

public interface ReportesPorSedeDAO {

    ArrayList<ReportesPorSedeDTO> listarPorPeriodoYSede(Integer anio, Integer mes, Integer idSede,Integer idPrestamo,Integer idPersona);

    public void insertarDatosDePrueba();

    public void eliminarDatosDePrueba();

    void generarReportePorSede(Integer anio, Integer mes);
}
