package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.db.util.Cifrado;
import com.syntaxerror.biblioteca.model.NivelInglesDTO;
import com.syntaxerror.biblioteca.model.PersonaDTO;
import com.syntaxerror.biblioteca.model.SedeDTO;
import com.syntaxerror.biblioteca.model.enums.TipoPersona;
import com.syntaxerror.biblioteca.model.enums.Turnos;
import com.syntaxerror.biblioteca.persistance.dao.NivelInglesDAO;
import com.syntaxerror.biblioteca.persistance.dao.PersonaDAO;
import com.syntaxerror.biblioteca.persistance.dao.SedeDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.NivelInglesDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.PersonaDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.SedeDAOImpl;

import java.util.ArrayList;
import java.util.Date;

public class PersonaBO {

    private final PersonaDAO personaDAO;
    private final SedeDAO sedeDAO;
    private final NivelInglesDAO nivelDAO;

    public PersonaBO() {
        this.personaDAO = new PersonaDAOImpl();
        this.sedeDAO = new SedeDAOImpl();
        this.nivelDAO = new NivelInglesDAOImpl();
    }

    public int insertar(String codigo, String nombre, String paterno, String materno, String direccion,
            String telefono, String correo, String contrasenha,
            TipoPersona tipo, Turnos turno,
            Date fechaContratoInicio, Date fechaContratoFinal, Double deuda, Date fechaSancionFinal,
            Boolean vigente, Integer idNivel, Integer idSede) throws BusinessException {

        validarDatos(nombre, paterno, materno, direccion, telefono, correo, contrasenha,
                tipo, turno, fechaContratoInicio, fechaContratoFinal, idSede, idNivel);

        PersonaDTO persona = new PersonaDTO();
        persona.setCodigo(codigo);
        persona.setNombre(nombre);
        persona.setPaterno(paterno);
        persona.setMaterno(materno);
        persona.setDireccion(direccion);
        persona.setTelefono(telefono);
        persona.setCorreo(correo);
        persona.setContrasenha(Cifrado.cifrarMD5(contrasenha));
        persona.setTipo(tipo);
        persona.setTurno(turno);
        persona.setFechaContratoInicio(fechaContratoInicio);
        persona.setFechaContratoFinal(fechaContratoFinal);
        persona.setDeuda(deuda);
        persona.setFechaSancionFinal(fechaSancionFinal);
        persona.setVigente(vigente != null ? vigente : true);

        if (idNivel != null) {
            NivelInglesDTO nivel = nivelDAO.obtenerPorId(idNivel);
            if (nivel == null) {
                throw new BusinessException("El nivel con ID " + idNivel + " no existe.");
            }
            persona.setNivel(nivel);
        }

        SedeDTO sede = sedeDAO.obtenerPorId(idSede);
        if (sede == null) {
            throw new BusinessException("La sede con ID " + idSede + " no existe.");
        }
        persona.setSede(sede);

        return this.personaDAO.insertar(persona);
    }

    public int modificar(Integer idPersona, String codigo, String nombre, String paterno, String materno, String direccion,
            String telefono, String correo, String contrasenha,
            TipoPersona tipo, Turnos turno,
            Date fechaContratoInicio, Date fechaContratoFinal, Double deuda, Date fechaSancionFinal,
            Boolean vigente, Integer idNivel, Integer idSede) throws BusinessException {

        BusinessValidator.validarId(idPersona, "persona");
        validarDatos(nombre, paterno, materno, direccion, telefono, correo, contrasenha,
                tipo, turno, fechaContratoInicio, fechaContratoFinal, idSede, idNivel);

        PersonaDTO persona = new PersonaDTO();
        persona.setCodigo(codigo);
        persona.setIdPersona(idPersona);
        persona.setNombre(nombre);
        persona.setPaterno(paterno);
        persona.setMaterno(materno);
        persona.setDireccion(direccion);
        persona.setTelefono(telefono);
        persona.setCorreo(correo);
        persona.setContrasenha(Cifrado.cifrarMD5(contrasenha));
        persona.setTipo(tipo);
        persona.setTurno(turno);
        persona.setFechaContratoInicio(fechaContratoInicio);
        persona.setFechaContratoFinal(fechaContratoFinal);
        persona.setDeuda(deuda);
        persona.setFechaSancionFinal(fechaSancionFinal);
        persona.setVigente(vigente != null ? vigente : true);

        if (idNivel != null) {
            NivelInglesDTO nivel = nivelDAO.obtenerPorId(idNivel);
            if (nivel == null) {
                throw new BusinessException("El nivel con ID " + idNivel + " no existe.");
            }
            persona.setNivel(nivel);
        }

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

    public PersonaDTO obtenerPorCredenciales(String identificador, String contrasenha) throws BusinessException {
        validarCredenciales(identificador, contrasenha);
        ArrayList<PersonaDTO> listaPersonas = new PersonaBO().listarTodos();

        for (PersonaDTO p : listaPersonas) {
            boolean coincideIdentificador = identificador.equalsIgnoreCase(p.getCorreo())
                    || identificador.equalsIgnoreCase(p.getCodigo());

            if (coincideIdentificador && p.getContrasenha().equals(contrasenha)) {
                if (Boolean.FALSE.equals(p.getVigente())) {
                    throw new BusinessException("El usuario no está vigente.");
                }
                return p;
            }
        }

        throw new BusinessException("Credenciales inválidas. Verifica correo/código y contraseña.");
    }

    private void validarCredenciales(String identificador, String contrasenha) throws BusinessException {
        if (identificador == null || identificador.isBlank()) {
            throw new BusinessException("Debe ingresar un código o correo.");
        }

        // Si contiene '@', validar como correo
        if (identificador.contains("@")) {
            if (!(identificador.endsWith(".admin@myholylib.edu.pe")
                    || identificador.endsWith(".teacher@myholylib.edu.pe")
                    || identificador.endsWith(".student@myholylib.edu.pe"))) {
                throw new BusinessException("El correo ingresado no tiene un dominio válido.");
            }
        } else {
            // Si es código, debe tener 6 caracteres
            if (identificador.length() != 6) {
                throw new BusinessException("El código debe tener exactamente 6 caracteres.");
            }
        }

        if (contrasenha == null || contrasenha.length() < 6) {
            throw new BusinessException("La contraseña debe tener al menos 6 caracteres.");
        }
    }

    public int calcularLimitePrestamos(String codigo) throws BusinessException {
        if (codigo == null || codigo.length() != 6) {
            throw new BusinessException("El código debe tener exactamente 6 caracteres.");
        }

        String prefijo = codigo.substring(0, 1).toUpperCase();

        switch (prefijo) {
            case "E":
                return 3; // Estudiante
            case "P":
                return 5; // Profesor
            case "A":
                return 2; // Administrador
            default:
                throw new BusinessException("Prefijo de código no reconocido para cálculo de préstamos.");
        }
    }

    private void validarDatos(String nombre, String paterno, String materno, String direccion,
            String telefono, String correo, String contrasenha,
            TipoPersona tipo, Turnos turno,
            Date fechaIni, Date fechaFin, Integer idSede, Integer idNivel) throws BusinessException {

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
            if (idNivel == null || idNivel <= 0) {
                throw new BusinessException("Debe asignarse un nivel de inglés al lector.");
            }
        }
    }

    public int modificarContrasenha(Integer idPersona, String contrasenhaNueva) throws BusinessException {
        BusinessValidator.validarId(idPersona, "persona");

        if (contrasenhaNueva == null || contrasenhaNueva.length() < 6) {
            throw new BusinessException("La nueva contraseña debe tener al menos 6 caracteres.");
        }

        // Obtener la persona para verificar existencia y vigencia.
        PersonaDTO persona = this.personaDAO.obtenerPorId(idPersona);
        if (persona == null) {
            throw new BusinessException("No se encontró una persona con el ID especificado.");
        }

        if (Boolean.FALSE.equals(persona.getVigente())) {
            throw new BusinessException("No se puede modificar la contraseña de un usuario inactivo.");
        }

        // Cifrar y establecer la nueva contraseña.
        persona.setContrasenha(Cifrado.cifrarMD5(contrasenhaNueva));

        return this.personaDAO.modificar(persona);
    }

    public int cambiarContrasenhaPorCodigoYCorreo(String codigo, String correo, String contrasenhaNueva) throws BusinessException {
        if (codigo == null || codigo.isBlank() || codigo.length() != 6) {
            throw new BusinessException("Debe proporcionar un código válido de 6 caracteres.");
        }
        if (correo == null || !correo.contains("@")) {
            throw new BusinessException("Debe proporcionar un correo válido.");
        }
        if (contrasenhaNueva == null || contrasenhaNueva.length() < 6) {
            throw new BusinessException("La nueva contraseña debe tener al menos 6 caracteres.");
        }

        PersonaDTO persona = this.personaDAO.obtenerPorCodigoYCorreo(codigo, correo);

        if (persona == null) {
            throw new BusinessException("No se encontró una persona con el código y correo especificados.");
        }

        if (Boolean.FALSE.equals(persona.getVigente())) {
            throw new BusinessException("No se puede modificar la contraseña de un usuario inactivo.");
        }

        persona.setContrasenha(Cifrado.cifrarMD5(contrasenhaNueva));
        return this.personaDAO.modificar(persona);
    }

}
