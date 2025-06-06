package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.ReportesGeneralesDTO;
import java.util.ArrayList;

public interface ReportesGeneralesDAO {

    ArrayList<ReportesGeneralesDTO> listarPorPeriodo(Integer anio, Integer mes, Integer idPrestamo, Integer idPersona);

    public void insertarDatosDePrueba();

    public void eliminarDatosDePrueba();

    void generarReporteGeneral(Integer anio, Integer mes);
}
