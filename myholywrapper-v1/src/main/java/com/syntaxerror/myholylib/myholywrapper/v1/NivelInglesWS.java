package com.syntaxerror.myholylib.myholywrapper.v1;

import com.syntaxerror.biblioteca.business.NivelInglesBO;
import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.NivelInglesDTO;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;

import java.util.ArrayList;

@WebService(serviceName = "NivelInglesWS")
public class NivelInglesWS {

    private final NivelInglesBO nivelBO;

    public NivelInglesWS() {
        this.nivelBO = new NivelInglesBO();
    }

    @WebMethod(operationName = "insertarNivel")
    public int insertarNivel(
            @WebParam(name = "nombreNivel") String nombreNivel,
            @WebParam(name = "descripcion") String descripcion
    ) {
        try {
            return nivelBO.insertar(nombreNivel, descripcion);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al insertar nivel: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "modificarNivel")
    public int modificarNivel(
            @WebParam(name = "idNivel") Integer idNivel,
            @WebParam(name = "nombreNivel") String nombreNivel,
            @WebParam(name = "descripcion") String descripcion
    ) {
        try {
            return nivelBO.modificar(idNivel, nombreNivel, descripcion);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al modificar nivel: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "eliminarNivel")
    public int eliminarNivel(@WebParam(name = "idNivel") Integer idNivel) {
        try {
            return nivelBO.eliminar(idNivel);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al eliminar nivel: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "obtenerNivelPorId")
    public NivelInglesDTO obtenerNivelPorId(@WebParam(name = "idNivel") Integer idNivel) {
        try {
            return nivelBO.obtenerPorId(idNivel);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al obtener nivel: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarNiveles")
    public ArrayList<NivelInglesDTO> listarNiveles() {
        try {
            return nivelBO.listarTodos();
        } catch (Exception e) {
            throw new WebServiceException("Error al listar niveles: " + e.getMessage());
        }
    }
}
