package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.ReportesGeneralesDTO;
import com.syntaxerror.biblioteca.persistance.dao.impl.ReportesGeneralesDAOImpl;

import java.util.ArrayList;
import com.syntaxerror.biblioteca.persistance.dao.ReportesGeneralesDAO;

public class ReporteGeneralBO {

    private final ReportesGeneralesDAO reporteDAO;

    public ReporteGeneralBO() {
        this.reporteDAO = new ReportesGeneralesDAOImpl();
    }

    public void generarReporte(Integer anio, Integer mes) throws BusinessException {
        validarPeriodo(anio, mes);
        this.reporteDAO.generarReporteGeneral(anio, mes);
    }

    public ArrayList<ReportesGeneralesDTO> listarPorPeriodo(Integer anio, Integer mes, Integer idPrestamo, Integer idPersona) throws BusinessException {
        validarPeriodo(anio, mes);
        return this.reporteDAO.listarPorPeriodo(anio, mes, idPrestamo, idPersona);
    }

    private void validarPeriodo(Integer anio, Integer mes) throws BusinessException {
        if (anio == null || anio < 2020 || anio > 2100) {
            throw new BusinessException("El año ingresado no es válido.");
        }
        if (mes == null || mes < 1 || mes > 12) {
            throw new BusinessException("El mes ingresado no es válido.");
        }
    }
}
