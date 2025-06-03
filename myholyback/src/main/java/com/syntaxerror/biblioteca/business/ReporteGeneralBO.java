package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.ReporteGeneralDTO;
import com.syntaxerror.biblioteca.persistance.dao.ReporteGeneralDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.ReporteGeneralDAOImpl;

import java.util.ArrayList;

public class ReporteGeneralBO {

    private final ReporteGeneralDAO reporteDAO;

    public ReporteGeneralBO() {
        this.reporteDAO = new ReporteGeneralDAOImpl();
    }

    public void generarReporte(Integer anio, Integer mes) throws BusinessException {
        validarPeriodo(anio, mes);
        this.reporteDAO.generarReporteGeneral(anio, mes);
    }

    public ArrayList<ReporteGeneralDTO> listarPorPeriodo(Integer anio, Integer mes, Integer idPrestamo, Integer idPersona) throws BusinessException {
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
