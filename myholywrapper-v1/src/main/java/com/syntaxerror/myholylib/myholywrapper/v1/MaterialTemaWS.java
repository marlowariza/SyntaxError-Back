package com.syntaxerror.myholylib.myholywrapper.v1;

import com.syntaxerror.biblioteca.business.MaterialTemaBO;
import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.MaterialDTO;
import com.syntaxerror.biblioteca.model.TemaDTO;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceException;

import java.util.ArrayList;

@WebService(serviceName = "MaterialTemaWS")
public class MaterialTemaWS {

    private final MaterialTemaBO bo;

    public MaterialTemaWS() {
        this.bo = new MaterialTemaBO();
    }

    @WebMethod(operationName = "asociarMaterialATema")
    public Integer asociarMaterialATema(
        @WebParam(name = "idMaterial") Integer idMaterial,
        @WebParam(name = "idTema") Integer idTema
    ) {
        try {
            return bo.asociar(idMaterial, idTema);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al asociar material con tema: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "desasociarMaterialDeTema")
    public Integer desasociarMaterialDeTema(
        @WebParam(name = "idMaterial") Integer idMaterial,
        @WebParam(name = "idTema") Integer idTema
    ) {
        try {
            return bo.desasociar(idMaterial, idTema);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al desasociar material de tema: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarTemasPorMaterial")
    public ArrayList<TemaDTO> listarTemasPorMaterial(
        @WebParam(name = "idMaterial") Integer idMaterial
    ) {
        try {
            return bo.listarTemasPorMaterial(idMaterial);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar temas por material: " + e.getMessage());
        }
    }

    
    @WebMethod(operationName = "listarMaterialesPorTema")
    public ArrayList<MaterialDTO> listarMaterialesPorTema(
        @WebParam(name = "idTema") Integer idTema
    ) {
        try {
            return bo.listarMaterialesPorTema(idTema);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar materiales por tema: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "existeRelacionMaterialTema")
    public boolean existeRelacionMaterialTema(
        @WebParam(name = "idMaterial") Integer idMaterial,
        @WebParam(name = "idTema") Integer idTema
    ) {
        try {
            return bo.existeRelacion(idMaterial, idTema);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al verificar relación material-tema: " + e.getMessage());
        }
    }
}
