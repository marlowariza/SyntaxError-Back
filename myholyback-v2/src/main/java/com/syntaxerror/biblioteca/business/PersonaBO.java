package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.PersonaDTO;
import com.syntaxerror.biblioteca.model.SedeDTO;
import com.syntaxerror.biblioteca.model.enums.NivelDeIngles;
import com.syntaxerror.biblioteca.model.enums.TipoPersona;
import com.syntaxerror.biblioteca.model.enums.Turnos;
import com.syntaxerror.biblioteca.persistance.dao.PersonaDAO;
import com.syntaxerror.biblioteca.persistance.dao.SedeDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.PersonaDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.SedeDAOImpl;

import java.util.ArrayList;
import java.util.Date;

public class PersonaBO {

    private final PersonaDAO personaDAO;
    private final SedeDAO sedeDAO;

    public PersonaBO() {
        this.personaDAO = new PersonaDAOImpl();
        this.sedeDAO = new SedeDAOImpl();
    }

    public int insertar(String nombre, String paterno, String materno, String direccion,
            String telefono, String correo, String contrasenha,
            TipoPersona tipo, NivelDeIngles nivel, Turnos turno,
            Date fechaContratoInicio, Date fechaContratoFinal,
            Boolean vigente, Integer idSede) throws BusinessException {

        validarDatos(nombre, paterno, materno, direccion, telefono, correo, contrasenha,
                tipo, nivel, turno, fechaContratoInicio, fechaContratoFinal, idSede);

        PersonaDTO persona = new PersonaDTO();
        persona.setNombre(nombre);
        persona.setPaterno(paterno);
        persona.setMaterno(materno);
        persona.setDireccion(direccion);
        persona.setTelefono(telefono);
        persona.setCorreo(correo);
        persona.setContrasenha(contrasenha);
        persona.setTipo(tipo);
        persona.setNivel(nivel);
        persona.setTurno(turno);
        persona.setFechaContratoInicio(fechaContratoInicio);
        persona.setFechaContratoFinal(fechaContratoFinal);
        persona.setVigente(vigente != null ? vigente : true);

        SedeDTO sede = sedeDAO.obtenerPorId(idSede);
        if (sede == null) {
            throw new BusinessException("La sede con ID " + idSede + " no existe.");
        }
        persona.setSede(sede);

        return this.personaDAO.insertar(persona);
    }

    public int modificar(Integer idPersona, String nombre, String paterno, String materno, String direccion,
            String telefono, String correo, String contrasenha,
            TipoPersona tipo, NivelDeIngles nivel, Turnos turno,
            Date fechaContratoInicio, Date fechaContratoFinal,
            Boolean vigente, Integer idSede) throws BusinessException {

        BusinessValidator.validarId(idPersona, "persona");
        validarDatos(nombre, paterno, materno, direccion, telefono, correo, contrasenha,
                tipo, nivel, turno, fechaContratoInicio, fechaContratoFinal, idSede);

        PersonaDTO persona = new PersonaDTO();
        persona.setIdPersona(idPersona);
        persona.setNombre(nombre);
        persona.setPaterno(paterno);
        persona.setMaterno(materno);
        persona.setDireccion(direccion);
        persona.setTelefono(telefono);
        persona.setCorreo(correo);
        persona.setContrasenha(contrasenha);
        persona.setTipo(tipo);
        persona.setNivel(nivel);
        persona.setTurno(turno);
        persona.setFechaContratoInicio(fechaContratoInicio);
        persona.setFechaContratoFinal(fechaContratoFinal);
        persona.setVigente(vigente != null ? vigente : true);

        SedeDTO sede = sedeDAO.obtenerPorId(idSede);
        if (sede == null) {
            throw new BusinessException("La sede con ID " + idSede + " no existe.");
        }
        persona.setSede(sede);
        return this.personaDAO.modificar(persona);
    }

    public int eliminar(Integer idPersona) throws BusinessException {
        BusinessValidator.validarId(idPersona, "persona");
        PersonaDTO persona = new PersonaDTO();
        persona.setIdPersona(idPersona);
        return this.personaDAO.eliminar(persona);
    }

    public PersonaDTO obtenerPorId(Integer idPersona) throws BusinessException {
        BusinessValidator.validarId(idPersona, "persona");
        return this.personaDAO.obtenerPorId(idPersona);
    }

    public ArrayList<PersonaDTO> listarTodos() {
        return this.personaDAO.listarTodos();
    }

    private void validarDatos(String nombre, String paterno, String materno, String direccion,
            String telefono, String correo, String contrasenha,
            TipoPersona tipo, NivelDeIngles nivel, Turnos turno,
            Date fechaIni, Date fechaFin, Integer idSede) throws BusinessException {

        BusinessValidator.validarTexto(nombre, "nombre");
        BusinessValidator.validarTexto(paterno, "apellido paterno");
        BusinessValidator.validarTexto(materno, "apellido materno");
        BusinessValidator.validarTexto(direccion, "dirección");

        if (telefono == null || telefono.length() < 9) {
            throw new BusinessException("El teléfono debe tener al menos 9 dígitos.");
        }
        if (correo == null || !correo.contains("@")) {
            throw new BusinessException("El correo debe tener un formato válido.");
        }
        if (contrasenha == null || contrasenha.length() < 6) {
            throw new BusinessException("La contraseña debe tener al menos 6 caracteres.");
        }

        if (tipo == null) {
            throw new BusinessException("Debe seleccionar un tipo de persona.");
        }

        if (idSede == null || idSede <= 0) {
            throw new BusinessException("Debe asignarse una sede válida.");
        }

        if (tipo == TipoPersona.BIBLIOTECARIO) {
            if (turno == null) {
                throw new BusinessException("Debe asignarse un turno al bibliotecario.");
            }
            if (fechaIni == null || fechaFin == null) {
                throw new BusinessException("Las fechas de contrato no pueden ser nulas.");
            }
            if (fechaIni.after(fechaFin)) {
                throw new BusinessException("La fecha de inicio no puede ser posterior a la fecha de fin.");
            }
        }
        if (tipo == TipoPersona.LECTOR) {
            if (nivel == null) {
                throw new BusinessException("Debe asignarse un nivel de inglés al lector.");
            }
        }

    }
}
