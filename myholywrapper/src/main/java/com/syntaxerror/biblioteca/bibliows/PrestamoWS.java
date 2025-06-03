package com.syntaxerror.biblioteca.bibliows;

import com.syntaxerror.biblioteca.business.PrestamoBO;
import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.PrestamoDTO;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;

import java.util.ArrayList;
import java.util.Date;

@WebService(serviceName = "PrestamoWS")
public class PrestamoWS {

    private final PrestamoBO prestamoBO;

    public PrestamoWS() {
        this.prestamoBO = new PrestamoBO();
    }

    @WebMethod(operationName = "insertarPrestamo")
    public int insertarPrestamo(
        @WebParam(name = "fechaSolicitud") Date fechaSolicitud,
        @WebParam(name = "fechaPrestamo") Date fechaPrestamo,
        @WebParam(name = "fechaDevolucion") Date fechaDevolucion,
        @WebParam(name = "idPersona") Integer idPersona
    ) {
        try {
            return prestamoBO.insertar(fechaSolicitud, fechaPrestamo, fechaDevolucion, idPersona);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al insertar préstamo: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "modificarPrestamo")
    public int modificarPrestamo(
        @WebParam(name = "idPrestamo") Integer idPrestamo,
        @WebParam(name = "fechaSolicitud") Date fechaSolicitud,
        @WebParam(name = "fechaPrestamo") Date fechaPrestamo,
        @WebParam(name = "fechaDevolucion") Date fechaDevolucion,
        @WebParam(name = "idPersona") Integer idPersona
    ) {
        try {
            return prestamoBO.modificar(idPrestamo, fechaSolicitud, fechaPrestamo, fechaDevolucion, idPersona);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al modificar préstamo: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "eliminarPrestamo")
    public int eliminarPrestamo(@WebParam(name = "idPrestamo") Integer idPrestamo) {
        try {
            return prestamoBO.eliminar(idPrestamo);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al eliminar préstamo: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "obtenerPrestamo")
    public PrestamoDTO obtenerPrestamo(@WebParam(name = "idPrestamo") Integer idPrestamo) {
        try {
            return prestamoBO.obtenerPorId(idPrestamo);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al obtener préstamo: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarPrestamos")
    public ArrayList<PrestamoDTO> listarPrestamos() {
        try {
            return prestamoBO.listarTodos();
        } catch (Exception e) {
            throw new WebServiceException("Error al listar préstamos: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "contarPrestamosActivos")
    public int contarPrestamosActivos(@WebParam(name = "idUsuario") int idUsuario) {
        try {
            return prestamoBO.contarPrestamosActivosPorUsuario(idUsuario);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al contar préstamos activos: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "contarPrestamosAtrasados")
    public int contarPrestamosAtrasados(@WebParam(name = "idUsuario") int idUsuario) {
        try {
            return prestamoBO.contarPrestamosAtrasadosPorUsuario(idUsuario);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al contar préstamos atrasados: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "contarPrestamosPorMaterial")
    public int contarPrestamosPorMaterial(@WebParam(name = "idMaterial") int idMaterial) {
        try {
            return prestamoBO.contarPrestamosPorMaterial(idMaterial);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al contar préstamos por material: " + e.getMessage());
        }
    }
}
