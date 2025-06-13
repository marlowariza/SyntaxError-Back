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
        @WebParam(name = "nombre") String nombre,
        @WebParam(name = "paterno") String paterno,
        @WebParam(name = "materno") String materno,
        @WebParam(name = "seudonimo") String seudonimo,
        @WebParam(name = "tipo") TipoCreador tipo,
        @WebParam(name = "nacionalidad") String nacionalidad,
        @WebParam(name = "activo") Boolean activo
    ) {
        try {
            return creadorBO.insertar(nombre, paterno, materno, seudonimo, tipo, nacionalidad, activo);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al insertar creador: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "modificarCreador")
    public int modificarCreador(
        @WebParam(name = "idCreador") Integer idCreador,
        @WebParam(name = "nombre") String nombre,
        @WebParam(name = "paterno") String paterno,
        @WebParam(name = "materno") String materno,
        @WebParam(name = "seudonimo") String seudonimo,
        @WebParam(name = "tipo") TipoCreador tipo,
        @WebParam(name = "nacionalidad") String nacionalidad,
        @WebParam(name = "activo") Boolean activo
    ) {
        try {
            return creadorBO.modificar(idCreador, nombre, paterno, materno, seudonimo, tipo, nacionalidad, activo);
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
