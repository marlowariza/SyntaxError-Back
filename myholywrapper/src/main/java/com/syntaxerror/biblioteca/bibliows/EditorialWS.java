package com.syntaxerror.biblioteca.bibliows;

import com.syntaxerror.biblioteca.business.EditorialBO;
import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.EditorialDTO;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;

import java.util.ArrayList;

@WebService(serviceName = "EditorialWS")
public class EditorialWS {

    private final EditorialBO editorialBO;

    public EditorialWS() {
        this.editorialBO = new EditorialBO();
    }

    @WebMethod(operationName = "listarEditoriales")
    public ArrayList<EditorialDTO> listarEditoriales() {
        try {
            return editorialBO.listarTodos();
        } catch (Exception e) {
            throw new WebServiceException("Error al listar editoriales: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "obtenerEditorial")
    public EditorialDTO obtenerEditorial(@WebParam(name = "idEditorial") Integer idEditorial) {
        try {
            return editorialBO.obtenerPorId(idEditorial);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al obtener la editorial: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "insertarEditorial")
    public int insertarEditorial(
        @WebParam(name = "nombre") String nombre,
        @WebParam(name = "sitioWeb") String sitioWeb,
        @WebParam(name = "pais") String pais
    ) {
        try {
            return editorialBO.insertar(nombre, sitioWeb, pais);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al insertar editorial: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "modificarEditorial")
    public int modificarEditorial(
        @WebParam(name = "idEditorial") Integer idEditorial,
        @WebParam(name = "nombre") String nombre,
        @WebParam(name = "sitioWeb") String sitioWeb,
        @WebParam(name = "pais") String pais
    ) {
        try {
            return editorialBO.modificar(idEditorial, nombre, sitioWeb, pais);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al modificar editorial: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "eliminarEditorial")
    public int eliminarEditorial(@WebParam(name = "idEditorial") Integer idEditorial) {
        try {
            return editorialBO.eliminar(idEditorial);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al eliminar editorial: " + e.getMessage());
        }
    }
}
