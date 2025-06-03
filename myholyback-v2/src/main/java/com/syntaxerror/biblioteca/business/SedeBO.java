package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.SedeDTO;
import com.syntaxerror.biblioteca.persistance.dao.SedeDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.SedeDAOImpl;
import java.util.ArrayList;

public class SedeBO {

    private final SedeDAO sedeDAO;

    public SedeBO() {
        this.sedeDAO = new SedeDAOImpl();
    }

    public int insertar(String nombre, String direccion, String distrito, String telefonoContacto,
            String correoContacto, Boolean activa) throws BusinessException {

        validarDatos(nombre, direccion, distrito, telefonoContacto, correoContacto);

        SedeDTO sede = new SedeDTO();
        sede.setNombre(nombre);
        sede.setDireccion(direccion);
        sede.setDistrito(distrito);
        sede.setTelefonoContacto(telefonoContacto);
        sede.setCorreoContacto(correoContacto);
        sede.setActiva(activa != null ? activa : true);

        return this.sedeDAO.insertar(sede);
    }

    public int modificar(Integer idSede, String nombre, String direccion, String distrito,
            String telefonoContacto, String correoContacto, Boolean activa) throws BusinessException {

        if (idSede == null || idSede <= 0) {
            throw new BusinessException("Debe proporcionar un ID de sede válido.");
        }

        validarDatos(nombre, direccion, distrito, telefonoContacto, correoContacto);

        SedeDTO sede = new SedeDTO();
        sede.setIdSede(idSede);
        sede.setNombre(nombre);
        sede.setDireccion(direccion);
        sede.setDistrito(distrito);
        sede.setTelefonoContacto(telefonoContacto);
        sede.setCorreoContacto(correoContacto);
        sede.setActiva(activa != null ? activa : true);

        return this.sedeDAO.modificar(sede);
    }

    public int eliminar(Integer idSede) throws BusinessException {
        if (idSede == null || idSede <= 0) {
            throw new BusinessException("Debe proporcionar un ID de sede válido.");
        }
        SedeDTO sede = new SedeDTO();
        sede.setIdSede(idSede);
        return this.sedeDAO.eliminar(sede);
    }

    public SedeDTO obtenerPorId(Integer idSede) {
        return this.sedeDAO.obtenerPorId(idSede);
    }

    public ArrayList<SedeDTO> listarTodos() {
        return this.sedeDAO.listarTodos();
    }

    private void validarDatos(String nombre, String direccion, String distrito,
            String telefono, String correo) throws BusinessException {
        if (nombre == null || nombre.isBlank()) {
            throw new BusinessException("El nombre de la sede no puede estar vacío.");
        }
        if (direccion == null || direccion.isBlank()) {
            throw new BusinessException("La dirección no puede estar vacía.");
        }
        if (distrito == null || distrito.isBlank()) {
            throw new BusinessException("El distrito no puede estar vacío.");
        }
        if (telefono == null || telefono.length() < 9) {
            throw new BusinessException("El teléfono de contacto debe tener al menos 9 dígitos.");
        }
        if (correo == null || !correo.contains("@")) {
            throw new BusinessException("El correo debe tener un formato válido.");
        }
    }
}
