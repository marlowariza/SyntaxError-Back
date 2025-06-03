/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package com.syntaxerror.biblioteca.bibliows;

import com.syntaxerror.biblioteca.business.MaterialCreadorBO;
import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.CreadorDTO;
import com.syntaxerror.biblioteca.model.MaterialDTO;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;

import java.util.ArrayList;

@WebService(serviceName = "CreadorMaterialWS")
public class CreadorMaterialWS {

    private final MaterialCreadorBO materialCreadorBO;

    public CreadorMaterialWS() {
        this.materialCreadorBO = new MaterialCreadorBO();
    }

    @WebMethod(operationName = "asociarMaterialCreador")
    public int asociar(
        @WebParam(name = "idMaterial") Integer idMaterial,
        @WebParam(name = "idCreador") Integer idCreador
    ) {
        try {
            return materialCreadorBO.asociar(idMaterial, idCreador);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al asociar material y creador: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "desasociarMaterialCreador")
    public int desasociar(
        @WebParam(name = "idMaterial") Integer idMaterial,
        @WebParam(name = "idCreador") Integer idCreador
    ) {
        try {
            return materialCreadorBO.desasociar(idMaterial, idCreador);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al desasociar material y creador: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarCreadoresPorMaterial")
    public ArrayList<CreadorDTO> listarCreadoresPorMaterial(
        @WebParam(name = "idMaterial") Integer idMaterial
    ) {
        try {
            return materialCreadorBO.listarCreadoresPorMaterial(idMaterial);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar creadores por material: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarMaterialesPorCreador")
    public ArrayList<MaterialDTO> listarMaterialesPorCreador(
        @WebParam(name = "idCreador") Integer idCreador
    ) {
        try {
            return materialCreadorBO.listarMaterialesPorCreador(idCreador);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar materiales por creador: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "existeRelacion")
    public boolean existeRelacion(
        @WebParam(name = "idMaterial") Integer idMaterial,
        @WebParam(name = "idCreador") Integer idCreador
    ) {
        try {
            return materialCreadorBO.existeRelacion(idMaterial, idCreador);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al verificar la relación: " + e.getMessage());
        }
    }
}
