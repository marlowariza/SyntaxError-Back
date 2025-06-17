package com.syntaxerror.biblioteca.bibliows;

import com.syntaxerror.biblioteca.business.PrestamoEjemplarBO;
import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.PrestamosDeEjemplaresDTO;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceException;

import java.util.ArrayList;

@WebService(serviceName = "PrestamoEjemplarWS")
public class PrestamoEjemplarWS {

    private final PrestamoEjemplarBO bo;

    public PrestamoEjemplarWS() {
        this.bo = new PrestamoEjemplarBO();
    }

    @WebMethod(operationName = "insertarPrestamoEjemplar")
    public Integer insertarPrestamoEjemplar(
        @WebParam(name = "idPestamo") Integer idPrestamo,
        @WebParam(name = "idEjemplar") Integer idEjemplar,
        @WebParam(name = "dto") PrestamosDeEjemplaresDTO dto
    ) {
        try {
            return bo.insertar(idPrestamo, idEjemplar, dto.getEstado(), dto.getFechaRealDevolucion());
        } catch (BusinessException e) {
            throw new WebServiceException("Error al insertar: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "modificarPrestamoEjemplar")
    public Integer modificarPrestamoEjemplar(
        @WebParam(name = "dto") PrestamosDeEjemplaresDTO dto
    ) {
        try {
            return bo.modificar(dto.getIdPrestamo(), dto.getIdEjemplar(), dto.getEstado(), dto.getFechaRealDevolucion());
        } catch (BusinessException e) {
            throw new WebServiceException("Error al modificar: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "eliminarPrestamoEjemplar")
    public Integer eliminarPrestamoEjemplar(
        @WebParam(name = "idPrestamo") Integer idPrestamo,
        @WebParam(name = "idEjemplar") Integer idEjemplar
    ) {
        try {
            return bo.eliminar(idPrestamo, idEjemplar);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al eliminar: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "obtenerPrestamoEjemplar")
    public PrestamosDeEjemplaresDTO obtenerPrestamoEjemplarPorIds(
        @WebParam(name = "idPrestamo") Integer idPrestamo,
        @WebParam(name = "idEjemplar") Integer idEjemplar
    ) {
        try {
            return bo.obtenerPorId(idPrestamo, idEjemplar);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al obtener la relación: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarTodosPrestamoEjemplar")
    public ArrayList<PrestamosDeEjemplaresDTO> listarTodosPrestamoEjemplar() {
        return bo.listarTodos();
    }

//    @WebMethod(operationName = "listarPorPrestamo")
//    public ArrayList<PrestamosDeEjemplaresDTO> listarPorPrestamo(
//        @WebParam(name = "idPrestamo") Integer idPrestamo
//    ) {
//        try {
//            return bo.listarPorPrestamo(idPrestamo);
//        } catch (BusinessException e) {
//            throw new WebServiceException("Error al listar por préstamo: " + e.getMessage());
//        }
//    }
//
//    @WebMethod(operationName = "listarPorEjemplar")
//    public ArrayList<PrestamosDeEjemplaresDTO> listarPorEjemplar(
//        @WebParam(name = "idEjemplar") Integer idEjemplar
//    ) {
//        try {
//            return bo.listarPorEjemplar(idEjemplar);
//        } catch (BusinessException e) {
//            throw new WebServiceException("Error al listar por ejemplar: " + e.getMessage());
//        }
//    }
    @WebMethod(operationName = "listarPrestamosSolicitados")
    public ArrayList<PrestamosDeEjemplaresDTO> listarPrestamosSolicitados() {
        return bo.listarPrestamosSolicitados();
    }
    
    @WebMethod(operationName = "listarPrestamosAtrasados")
    public ArrayList<PrestamosDeEjemplaresDTO> listarPrestamosAtrasados() {
        return bo.listarPrestamosAtrasados();
    }
    
    @WebMethod(operationName = "listarPrestamosDevueltos")
    public ArrayList<PrestamosDeEjemplaresDTO> listarPrestamosDevueltos() {
        return bo.listarPrestamosDevueltos();
    }
    
    @WebMethod(operationName = "listarPrestamosNoCulminados")
    public ArrayList<PrestamosDeEjemplaresDTO> listarPrestamosNoCulminados() {
        return bo.listarPrestamosNoCulminados();
    }

}