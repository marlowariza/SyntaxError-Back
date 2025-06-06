package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.PersonasDTO;
import com.syntaxerror.biblioteca.model.SedesDTO;
import com.syntaxerror.biblioteca.model.NivelesInglesDTO;
import com.syntaxerror.biblioteca.model.enums.TipoPersona;
import com.syntaxerror.biblioteca.model.enums.Turnos;
import com.syntaxerror.biblioteca.persistance.dao.impl.PersonasDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.SedesDAOImpl;

import java.util.ArrayList;
import java.util.Date;
import com.syntaxerror.biblioteca.persistance.dao.SedesDAO;
import com.syntaxerror.biblioteca.persistance.dao.PersonasDAO;

public class PersonaBO {

    private final PersonasDAO personaDAO;
    private final SedesDAO sedeDAO;

    public PersonaBO() {
        this.personaDAO = new PersonasDAOImpl();
        this.sedeDAO = new SedesDAOImpl();
    }

    public int insertar(String codigo, String nombre, String paterno, String materno, String direccion,
            String telefono, String correo, String contrasenha,
            TipoPersona tipo, NivelesInglesDTO nivel, Turnos turno,
            Date fechaContratoInicio, Date fechaContratoFinal,
            Double deuda, Date fechaSancionFinal, Boolean vigente, Integer idSede) throws BusinessException {

        validarDatos(codigo, nombre, paterno, materno, direccion, telefono, correo, contrasenha,
                tipo, nivel, turno, fechaContratoInicio, fechaContratoFinal, idSede);

        PersonasDTO persona = new PersonasDTO();
        persona.setCodigo(codigo);
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
        persona.setDeuda(deuda != null ? deuda : 0.0);
        persona.setFechaSancionFinal(fechaSancionFinal);
        persona.setVigente(vigente != null ? vigente : true);

        SedesDTO sede = sedeDAO.obtenerPorId(idSede);
        if (sede == null) {
            throw new BusinessException("La sede con ID " + idSede + " no existe.");
        }
        persona.setSede(sede);

        return this.personaDAO.insertar(persona);
    }

    public int modificar(Integer idPersona, String codigo, String nombre, String paterno, String materno, String direccion,
            String telefono, String correo, String contrasenha,
            TipoPersona tipo, NivelesInglesDTO nivel, Turnos turno,
            Date fechaContratoInicio, Date fechaContratoFinal,
            Double deuda, Date fechaSancionFinal, Boolean vigente, Integer idSede) throws BusinessException {

        BusinessValidator.validarId(idPersona, "persona");
        validarDatos(codigo, nombre, paterno, materno, direccion, telefono, correo, contrasenha,
                tipo, nivel, turno, fechaContratoInicio, fechaContratoFinal, idSede);

        PersonasDTO persona = new PersonasDTO();
        persona.setIdPersona(idPersona);
        persona.setCodigo(codigo);
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
        persona.setDeuda(deuda != null ? deuda : 0.0);
        persona.setFechaSancionFinal(fechaSancionFinal);
        persona.setVigente(vigente != null ? vigente : true);

        SedesDTO sede = sedeDAO.obtenerPorId(idSede);
        if (sede == null) {
            throw new BusinessException("La sede con ID " + idSede + " no existe.");
        }
        persona.setSede(sede);
        return this.personaDAO.modificar(persona);
    }

    public int eliminar(Integer idPersona) throws BusinessException {
        BusinessValidator.validarId(idPersona, "persona");
        PersonasDTO persona = new PersonasDTO();
        persona.setIdPersona(idPersona);
        return this.personaDAO.eliminar(persona);
    }

    public PersonasDTO obtenerPorId(Integer idPersona) throws BusinessException {
        BusinessValidator.validarId(idPersona, "persona");
        return this.personaDAO.obtenerPorId(idPersona);
    }

    public ArrayList<PersonasDTO> listarTodos() {
        return this.personaDAO.listarTodos();
    }

    private void validarDatos(String codigo, String nombre, String paterno, String materno, String direccion,
            String telefono, String correo, String contrasenha,
            TipoPersona tipo, NivelesInglesDTO nivel, Turnos turno,
            Date fechaIni, Date fechaFin, Integer idSede) throws BusinessException {

        BusinessValidator.validarTexto(codigo, "código");
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

        if (tipo == TipoPersona.ADMINISTRADOR) {
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
        if (tipo == TipoPersona.PROFESOR || tipo == TipoPersona.ESTUDIANTE) {
            if (nivel == null) {
                throw new BusinessException("Debe asignarse un nivel de inglés al lector.");
            }
        }
    }
}
