package com.syntaxerror.biblioteca.bibliows;

import com.syntaxerror.biblioteca.business.TemaBO;
import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.TemasDTO;
import com.syntaxerror.biblioteca.model.enums.Categoria;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;

import java.util.ArrayList;

@WebService(serviceName = "TemaWS")
public class TemaWS {

    private final TemaBO temaBO;

    public TemaWS() {
        this.temaBO = new TemaBO();
    }

    @WebMethod(operationName = "insertarTema")
    public int insertarTema(
        @WebParam(name = "tema") TemasDTO tema
    ) {
        try {
            return temaBO.insertar(tema);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al insertar tema: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "modificarTema")
    public int modificarTema(
        @WebParam(name = "tema") TemasDTO tema
    ) {
        try {
            return temaBO.modificar(tema);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al modificar tema: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "eliminarTema")
    public int eliminarTema(@WebParam(name = "idTema") Integer idTema) {
        try {
            return temaBO.eliminar(idTema);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al eliminar tema: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "obtenerTemaPorId")
    public TemasDTO obtenerTemaPorId(@WebParam(name = "idTema") Integer idTema) {
        try {
            return temaBO.obtenerPorId(idTema);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al obtener tema: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarTemas")
    public ArrayList<TemasDTO> listarTemas() {
        try {
            return temaBO.listarTodos();
        } catch (Exception e) {
            throw new WebServiceException("Error al listar temas: " + e.getMessage());
        }
    }
}
