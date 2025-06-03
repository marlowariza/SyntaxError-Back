package com.syntaxerror.biblioteca.bibliows;

import com.syntaxerror.biblioteca.business.SancionBO;
import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.SancionDTO;
import com.syntaxerror.biblioteca.model.enums.TipoSancion;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;

import java.util.ArrayList;
import java.util.Date;

@WebService(serviceName = "SancionWS")
public class SancionWS {

    private final SancionBO sancionBO;

    public SancionWS() {
        this.sancionBO = new SancionBO();
    }

    @WebMethod(operationName = "insertarSancion")
    public int insertarSancion(
        @WebParam(name = "tipo") TipoSancion tipo,
        @WebParam(name = "fecha") Date fecha,
        @WebParam(name = "monto") Double monto,
        @WebParam(name = "duracion") Date duracion,
        @WebParam(name = "descripcion") String descripcion,
        @WebParam(name = "idPrestamo") Integer idPrestamo
    ) {
        try {
            return sancionBO.insertar(tipo, fecha, monto, duracion, descripcion, idPrestamo);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al insertar sanción: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "modificarSancion")
    public int modificarSancion(
        @WebParam(name = "idSancion") Integer idSancion,
        @WebParam(name = "tipo") TipoSancion tipo,
        @WebParam(name = "fecha") Date fecha,
        @WebParam(name = "monto") Double monto,
        @WebParam(name = "duracion") Date duracion,
        @WebParam(name = "descripcion") String descripcion,
        @WebParam(name = "idPrestamo") Integer idPrestamo
    ) {
        try {
            return sancionBO.modificar(idSancion, tipo, fecha, monto, duracion, descripcion, idPrestamo);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al modificar sanción: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "eliminarSancion")
    public int eliminarSancion(@WebParam(name = "idSancion") Integer idSancion) {
        try {
            return sancionBO.eliminar(idSancion);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al eliminar sanción: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "obtenerSancionPorId")
    public SancionDTO obtenerSancionPorId(@WebParam(name = "idSancion") Integer idSancion) {
        try {
            return sancionBO.obtenerPorId(idSancion);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al obtener sanción: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarSanciones")
    public ArrayList<SancionDTO> listarSanciones() {
        try {
            return sancionBO.listarTodos();
        } catch (Exception e) {
            throw new WebServiceException("Error al listar sanciones: " + e.getMessage());
        }
    }
}

