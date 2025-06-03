package com.syntaxerror.biblioteca.bibliows;

import com.syntaxerror.biblioteca.business.SedeBO;
import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.SedeDTO;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;

import java.util.ArrayList;

@WebService(serviceName = "SedeWS")
public class SedeWS {

    private final SedeBO sedeBO;

    public SedeWS() {
        this.sedeBO = new SedeBO();
    }

    @WebMethod(operationName = "listarSedes")
    public ArrayList<SedeDTO> listarSedes() {
        try {
            return sedeBO.listarTodos();
        } catch (Exception e) {
            throw new WebServiceException("Error al listar sedes: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "obtenerSede")
    public SedeDTO obtenerSede(@WebParam(name = "idSede") Integer idSede) {
        try {
            return sedeBO.obtenerPorId(idSede);
        } catch (Exception e) {
            throw new WebServiceException("Error al obtener la sede: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "insertarSede")
    public int insertarSede(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "distrito") String distrito,
            @WebParam(name = "telefonoContacto") String telefonoContacto,
            @WebParam(name = "correoContacto") String correoContacto,
            @WebParam(name = "activa") Boolean activa
    ) {
        try {
            return sedeBO.insertar(nombre, direccion, distrito, telefonoContacto, correoContacto, activa);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al insertar sede: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "modificarSede")
    public int modificarSede(
            @WebParam(name = "idSede") Integer idSede,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "distrito") String distrito,
            @WebParam(name = "telefonoContacto") String telefonoContacto,
            @WebParam(name = "correoContacto") String correoContacto,
            @WebParam(name = "activa") Boolean activa
    ) {
        try {
            return sedeBO.modificar(idSede, nombre, direccion, distrito, telefonoContacto, correoContacto, activa);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al modificar sede: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "eliminarSede")
    public int eliminarSede(@WebParam(name = "idSede") Integer idSede) {
        try {
            return sedeBO.eliminar(idSede);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al eliminar sede: " + e.getMessage());
        }
    }
}
