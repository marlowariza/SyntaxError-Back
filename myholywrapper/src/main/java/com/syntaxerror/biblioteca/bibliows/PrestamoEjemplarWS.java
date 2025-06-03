package com.syntaxerror.biblioteca.bibliows;

import com.syntaxerror.biblioteca.business.PrestamoEjemplarBO;
import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.PrestamoEjemplarDTO;

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
        @WebParam(name = "dto") PrestamoEjemplarDTO dto
    ) {
        try {
            return bo.insertar(idPrestamo, idEjemplar, dto);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al insertar: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "modificarPrestamoEjemplar")
    public Integer modificarPrestamoEjemplar(
        @WebParam(name = "dto") PrestamoEjemplarDTO dto
    ) {
        try {
            return bo.modificar(dto);
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
    public PrestamoEjemplarDTO obtenerPrestamoEjemplarPorIds(
        @WebParam(name = "idPrestamo") Integer idPrestamo,
        @WebParam(name = "idEjemplar") Integer idEjemplar
    ) {
        try {
            return bo.obtenerPorIds(idPrestamo, idEjemplar);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al obtener la relación: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarTodosPrestamoEjemplar")
    public ArrayList<PrestamoEjemplarDTO> listarTodosPrestamoEjemplar() {
        return bo.listarTodos();
    }

    @WebMethod(operationName = "listarPorPrestamo")
    public ArrayList<PrestamoEjemplarDTO> listarPorPrestamo(
        @WebParam(name = "idPrestamo") Integer idPrestamo
    ) {
        try {
            return bo.listarPorPrestamo(idPrestamo);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar por préstamo: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarPorEjemplar")
    public ArrayList<PrestamoEjemplarDTO> listarPorEjemplar(
        @WebParam(name = "idEjemplar") Integer idEjemplar
    ) {
        try {
            return bo.listarPorEjemplar(idEjemplar);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar por ejemplar: " + e.getMessage());
        }
    }
}