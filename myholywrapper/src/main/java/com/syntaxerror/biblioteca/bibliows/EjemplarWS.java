package com.syntaxerror.biblioteca.bibliows;

import com.syntaxerror.biblioteca.business.EjemplarBO;
import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.EjemplarDTO;
import com.syntaxerror.biblioteca.model.enums.FormatoDigital;
import com.syntaxerror.biblioteca.model.enums.TipoEjemplar;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;

import java.util.ArrayList;
import java.util.Date;

@WebService(serviceName = "EjemplarWS")
public class EjemplarWS {

    private final EjemplarBO ejemplarBO;

    public EjemplarWS() {
        this.ejemplarBO = new EjemplarBO();
    }

    @WebMethod(operationName = "listarEjemplares")
    public ArrayList<EjemplarDTO> listarEjemplares() {
        try {
            return ejemplarBO.listarTodos();
        } catch (Exception e) {
            throw new WebServiceException("Error al listar ejemplares: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "obtenerEjemplar")
    public EjemplarDTO obtenerEjemplar(@WebParam(name = "idEjemplar") Integer idEjemplar) {
        try {
            return ejemplarBO.obtenerPorId(idEjemplar);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al obtener ejemplar: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "insertarEjemplar")
    public int insertarEjemplar(
        @WebParam(name = "fechaAdquisicion") Date fechaAdquisicion,
        @WebParam(name = "disponible") Boolean disponible,
        @WebParam(name = "tipo") TipoEjemplar tipo,
        @WebParam(name = "formatoDigital") FormatoDigital formatoDigital,
        @WebParam(name = "ubicacion") String ubicacion,
        @WebParam(name = "idSede") Integer idSede,
        @WebParam(name = "idMaterial") Integer idMaterial
    ) {
        try {
            return ejemplarBO.insertar(fechaAdquisicion, disponible, tipo, formatoDigital, ubicacion, idSede, idMaterial);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al insertar ejemplar: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "modificarEjemplar")
    public int modificarEjemplar(
        @WebParam(name = "idEjemplar") Integer idEjemplar,
        @WebParam(name = "fechaAdquisicion") Date fechaAdquisicion,
        @WebParam(name = "disponible") Boolean disponible,
        @WebParam(name = "tipo") TipoEjemplar tipo,
        @WebParam(name = "formatoDigital") FormatoDigital formatoDigital,
        @WebParam(name = "ubicacion") String ubicacion,
        @WebParam(name = "idSede") Integer idSede,
        @WebParam(name = "idMaterial") Integer idMaterial
    ) {
        try {
            return ejemplarBO.modificar(idEjemplar, fechaAdquisicion, disponible, tipo, formatoDigital, ubicacion, idSede, idMaterial);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al modificar ejemplar: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "eliminarEjemplar")
    public int eliminarEjemplar(@WebParam(name = "idEjemplar") Integer idEjemplar) {
        try {
            return ejemplarBO.eliminar(idEjemplar);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al eliminar ejemplar: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "contarTotalPorMaterial")
    public int contarTotalPorMaterial(@WebParam(name = "idMaterial") int idMaterial) {
        try {
            return ejemplarBO.contarTotalEjemplaresPorMaterial(idMaterial);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al contar ejemplares por material: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "contarDisponiblesPorMaterial")
    public int contarDisponiblesPorMaterial(@WebParam(name = "idMaterial") int idMaterial) {
        try {
            return ejemplarBO.contarEjemplaresDisponiblesPorMaterial(idMaterial);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al contar ejemplares disponibles: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "contarTotalPorSede")
    public int contarTotalPorSede(@WebParam(name = "idSede") int idSede) {
        try {
            return ejemplarBO.contarEjemplaresPorSede(idSede);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al contar ejemplares por sede: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "contarDisponiblesPorSede")
    public int contarDisponiblesPorSede(@WebParam(name = "idSede") int idSede) {
        try {
            return ejemplarBO.contarEjemplaresDisponiblesPorSede(idSede);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al contar ejemplares disponibles por sede: " + e.getMessage());
        }
    }
}
