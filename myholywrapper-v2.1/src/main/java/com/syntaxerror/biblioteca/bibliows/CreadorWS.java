package com.syntaxerror.biblioteca.bibliows;

import com.syntaxerror.biblioteca.business.CreadorBO;
import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.CreadoresDTO;
import com.syntaxerror.biblioteca.model.enums.TipoCreador;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;

import java.util.ArrayList;

@WebService(serviceName = "CreadorWS")
public class CreadorWS {

    private final CreadorBO creadorBO;

    public CreadorWS() {
        this.creadorBO = new CreadorBO();
    }

    @WebMethod(operationName = "insertarCreador")
    public int insertarCreador(
            @WebParam(name = "creador") CreadoresDTO creador
    ) {
        try {
            return creadorBO.insertar(creador);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al insertar creador: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "modificarCreador")
    public int modificarCreador(
            @WebParam(name = "creador") CreadoresDTO creador
    ) {
        try {
            return creadorBO.modificar(creador);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al modificar creador: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "eliminarCreador")
    public int eliminarCreador(@WebParam(name = "idCreador") Integer idCreador) {
        try {
            return creadorBO.eliminar(idCreador);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al eliminar creador: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "obtenerCreadorPorId")
    public CreadoresDTO obtenerCreadorPorId(@WebParam(name = "idCreador") Integer idCreador) {
        try {
            return creadorBO.obtenerPorId(idCreador);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al obtener creador: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarCreadores")
    public ArrayList<CreadoresDTO> listarCreadores() {
        try {
            return creadorBO.listarTodos();
        } catch (Exception e) {
            throw new WebServiceException("Error al listar creadores: " + e.getMessage());
        }
    }
}
