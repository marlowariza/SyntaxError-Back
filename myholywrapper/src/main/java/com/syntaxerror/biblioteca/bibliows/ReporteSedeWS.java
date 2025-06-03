package com.syntaxerror.biblioteca.bibliows;

import com.syntaxerror.biblioteca.business.ReporteSedeBO;
import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.ReporteSedeDTO;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;

import java.util.ArrayList;

@WebService(serviceName = "ReporteSedeWS")
public class ReporteSedeWS {

    private final ReporteSedeBO reporteBO;

    public ReporteSedeWS() {
        this.reporteBO = new ReporteSedeBO();
    }

    @WebMethod(operationName = "generarReporte")
    public void generarReporte(
        @WebParam(name = "anio") Integer anio,
        @WebParam(name = "mes") Integer mes
    ) {
        try {
            reporteBO.generarReporte(anio, mes);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al generar reporte: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarPorPeriodoYSede")
    public ArrayList<ReporteSedeDTO> listarPorPeriodoYSede(
        @WebParam(name = "anio") Integer anio,
        @WebParam(name = "mes") Integer mes,
        @WebParam(name = "idSede") Integer idSede,
        @WebParam(name = "idPrestamo") Integer idPrestamo,
        @WebParam(name = "idPersona") Integer idPersona
    ) {
        try {
            return reporteBO.listarPorPeriodoYSede(anio, mes, idSede, idPrestamo, idPersona);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar reporte: " + e.getMessage());
        }
    }
}