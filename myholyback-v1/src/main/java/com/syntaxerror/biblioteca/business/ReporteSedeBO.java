package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.ReporteSedeDTO;
import com.syntaxerror.biblioteca.persistance.dao.ReporteSedeDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.ReporteSedeDAOImpl;

import java.util.ArrayList;

public class ReporteSedeBO {

    private final ReporteSedeDAO reporteDAO;

    public ReporteSedeBO() {
        this.reporteDAO = new ReporteSedeDAOImpl();
    }

    public void generarReporte(Integer anio, Integer mes) throws BusinessException {
        validarPeriodo(anio, mes);
        this.reporteDAO.generarReportePorSede(anio, mes);
    }

    public ArrayList<ReporteSedeDTO> listarPorPeriodoYSede(Integer anio, Integer mes, Integer idSede, Integer idPrestamo, Integer idPersona) throws BusinessException {
        validarPeriodo(anio, mes);
        return this.reporteDAO.listarPorPeriodoYSede(anio, mes, idSede, idPrestamo, idPersona);
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
