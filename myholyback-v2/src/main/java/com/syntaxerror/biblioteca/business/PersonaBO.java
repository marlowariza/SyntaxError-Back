package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.db.util.Cifrado;
import com.syntaxerror.biblioteca.model.NivelesInglesDTO;
import com.syntaxerror.biblioteca.model.PersonasDTO;
import com.syntaxerror.biblioteca.model.SedesDTO;
import com.syntaxerror.biblioteca.model.enums.TipoPersona;
import com.syntaxerror.biblioteca.model.enums.Turnos;
import com.syntaxerror.biblioteca.persistance.dao.impl.PersonaDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.SedeDAOImpl;

import java.util.ArrayList;
import java.util.Date;
import com.syntaxerror.biblioteca.persistance.dao.PersonaDAO;
import com.syntaxerror.biblioteca.persistance.dao.SedeDAO;

public class PersonaBO {

    private final PersonaDAO personaDAO;
    private final SedeDAO sedeDAO;

    public PersonaBO() {
        this.personaDAO = new PersonaDAOImpl();
        this.sedeDAO = new SedeDAOImpl();
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
        persona.setContrasenha(Cifrado.cifrarMD5(contrasenha));
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
        persona.setContrasenha(Cifrado.cifrarMD5(contrasenha));
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

        //validarCodigoPorTipo(codigo, tipo);
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

    //POR CONFIRMAR, HAY QUE TENER CUIDADO SI EL CODIGO NO ES UNIQ
    private void validarCodigoPorTipo(String codigo, TipoPersona tipo) throws BusinessException {
        if (codigo == null || codigo.length() != 6) {
            throw new BusinessException("El código debe tener exactamente 6 caracteres.");
        }
        //POR CAMBIAR A,P,E
        String prefijoEsperado;
        switch (tipo) {
            case ADMINISTRADOR:
                prefijoEsperado = "AD";
                break;
            case PROFESOR:
                prefijoEsperado = "PR";
                break;
            case ESTUDIANTE:
                prefijoEsperado = "ES";
                break;
            default:
                throw new BusinessException("Tipo de persona no válido para validar código.");
        }

        if (!codigo.startsWith(prefijoEsperado)) {
            throw new BusinessException("El código debe comenzar con '" + prefijoEsperado + "' para tipo " + tipo.name());
        }

        String numeros = codigo.substring(2);
        if (!numeros.matches("\\d{4}")) {
            throw new BusinessException("El código debe terminar en 4 dígitos numéricos.");
        }
    }

    public PersonasDTO obtenerPorCredenciales(String identificador, String contrasenha) throws BusinessException {
        validarCredenciales(identificador, contrasenha);

        String contraCifrada = Cifrado.cifrarMD5(contrasenha);

        PersonasDTO persona = personaDAO.obtenerPorCredenciales(identificador, contraCifrada);

        if (persona == null) {
            throw new BusinessException("Credenciales inválidas. Verifica correo/código y contraseña.");
        }

        if (Boolean.FALSE.equals(persona.getVigente())) {
            throw new BusinessException("El usuario no está vigente.");
        }

        return persona;

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

        String prefijo = codigo.substring(0, 2).toUpperCase();
        //POR CAMBIAR E,P,A
        switch (prefijo) {
            case "ES":
                return 3; // Estudiante
            case "PR":
                return 5; // Profesor
            case "AD":
                return 2; // Administrador
            default:
                throw new BusinessException("Prefijo de código no reconocido para cálculo de préstamos.");
        }
        //por correo
//        if (correo == null) {
//            throw new BusinessException("Correo no puede ser nulo.");
//        }
//
//        correo = correo.toLowerCase();
//
//        if (correo.endsWith(".student@myholylib.edu.pe")) {
//            return 3;
//        } else if (correo.endsWith(".teacher@myholylib.edu.pe")) {
//            return 5;
//        } else if (correo.endsWith(".admin@myholylib.edu.pe")) {
//            return 2;
//        } else {
//            throw new BusinessException("Dominio de correo no reconocido para cálculo de préstamos.");
//        }
    }

}
