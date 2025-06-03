package com.syntaxerror.biblioteca.bibliows;

import com.syntaxerror.biblioteca.business.PersonaBO;
import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.PersonaDTO;
import com.syntaxerror.biblioteca.model.enums.NivelDeIngles;
import com.syntaxerror.biblioteca.model.enums.TipoPersona;
import com.syntaxerror.biblioteca.model.enums.Turnos;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceException;

import java.util.ArrayList;
import java.util.Date;

@WebService(serviceName = "PersonaWS")
public class PersonaWS {

    private final PersonaBO personaBO;

    public PersonaWS() {
        personaBO = new PersonaBO();
    }

    @WebMethod(operationName = "listarPersonas")
    public ArrayList<PersonaDTO> listarPersonas() {
        try {
            return personaBO.listarTodos();
        } catch (Exception e) {
            throw new WebServiceException("Error al listar personas: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "obtenerPersona")
    public PersonaDTO obtenerPersona(@WebParam(name = "idPersona") Integer idPersona) {
        try {
            return personaBO.obtenerPorId(idPersona);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al obtener persona: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "insertarPersona")
    public int insertarPersona(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "paterno") String paterno,
            @WebParam(name = "materno") String materno,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "telefono") String telefono,
            @WebParam(name = "correo") String correo,
            @WebParam(name = "contrasenha") String contrasenha,
            @WebParam(name = "tipo") TipoPersona tipo,
            @WebParam(name = "nivel") NivelDeIngles nivel,
            @WebParam(name = "turno") Turnos turno,
            @WebParam(name = "fechaContratoInicio") Date fechaContratoInicio,
            @WebParam(name = "fechaContratoFinal") Date fechaContratoFinal,
            @WebParam(name = "vigente") Boolean vigente,
            @WebParam(name = "idSede") Integer idSede
    ) {
        try {
            return personaBO.insertar(nombre, paterno, materno, direccion, telefono, correo, contrasenha,
                    tipo, nivel, turno, fechaContratoInicio, fechaContratoFinal, vigente, idSede);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al insertar persona: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "modificarPersona")
    public int modificarPersona(
            @WebParam(name = "idPersona") Integer idPersona,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "paterno") String paterno,
            @WebParam(name = "materno") String materno,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "telefono") String telefono,
            @WebParam(name = "correo") String correo,
            @WebParam(name = "contrasenha") String contrasenha,
            @WebParam(name = "tipo") TipoPersona tipo,
            @WebParam(name = "nivel") NivelDeIngles nivel,
            @WebParam(name = "turno") Turnos turno,
            @WebParam(name = "fechaContratoInicio") Date fechaContratoInicio,
            @WebParam(name = "fechaContratoFinal") Date fechaContratoFinal,
            @WebParam(name = "vigente") Boolean vigente,
            @WebParam(name = "idSede") Integer idSede
    ) {
        try {
            return personaBO.modificar(idPersona, nombre, paterno, materno, direccion, telefono, correo,
                    contrasenha, tipo, nivel, turno, fechaContratoInicio, fechaContratoFinal, vigente, idSede);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al modificar persona: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "eliminarPersona")
    public int eliminarPersona(@WebParam(name = "idPersona") Integer idPersona) {
        try {
            return personaBO.eliminar(idPersona);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al eliminar persona: " + e.getMessage());
        }
    }
}
