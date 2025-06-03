package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.PersonaDTO;
import com.syntaxerror.biblioteca.model.PrestamoDTO;
import com.syntaxerror.biblioteca.persistance.dao.PersonaDAO;
import com.syntaxerror.biblioteca.persistance.dao.PrestamoDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.PersonaDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.PrestamoDAOImpl;
import java.util.ArrayList;
import java.util.Date;

public class PrestamoBO {

    private final PrestamoDAO prestamoDAO;
    private final PersonaDAO personaDAO;

    public PrestamoBO() {
        this.prestamoDAO = new PrestamoDAOImpl();
        this.personaDAO = new PersonaDAOImpl();
    }

    public int insertar(Date fechaSolicitud, Date fechaPrestamo, Date fechaDevolucion, Integer idPersona) throws BusinessException {
        validarDatos(fechaSolicitud, fechaPrestamo, fechaDevolucion, idPersona);
        PrestamoDTO prestamo = new PrestamoDTO();
        prestamo.setFechaSolicitud(fechaSolicitud);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucion(fechaDevolucion);

        PersonaDTO persona = personaDAO.obtenerPorId(idPersona);
        if (persona == null) {
            throw new BusinessException("La persona con ID " + idPersona + " no existe.");
        }
        prestamo.setPersona(persona);

        return this.prestamoDAO.insertar(prestamo);
    }

    public int modificar(Integer idPrestamo, Date fechaSolicitud, Date fechaPrestamo, Date fechaDevolucion, Integer idPersona) throws BusinessException {
        BusinessValidator.validarId(idPrestamo, "préstamo");
        validarDatos(fechaSolicitud, fechaPrestamo, fechaDevolucion, idPersona);
        PrestamoDTO prestamo = new PrestamoDTO();
        prestamo.setIdPrestamo(idPrestamo);
        prestamo.setFechaSolicitud(fechaSolicitud);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucion(fechaDevolucion);

        PersonaDTO persona = personaDAO.obtenerPorId(idPersona);
        if (persona == null) {
            throw new BusinessException("La persona con ID " + idPersona + " no existe.");
        }
        prestamo.setPersona(persona);

        return this.prestamoDAO.modificar(prestamo);
    }

    public int eliminar(Integer idPrestamo) throws BusinessException {
        BusinessValidator.validarId(idPrestamo, "préstamo");
        PrestamoDTO prestamo = new PrestamoDTO();
        prestamo.setIdPrestamo(idPrestamo);
        return this.prestamoDAO.eliminar(prestamo);
    }

    public PrestamoDTO obtenerPorId(Integer idPrestamo) throws BusinessException {
        BusinessValidator.validarId(idPrestamo, "préstamo");
        return this.prestamoDAO.obtenerPorId(idPrestamo);
    }

    public ArrayList<PrestamoDTO> listarTodos() {
        return this.prestamoDAO.listarTodos();
    }

    private void validarDatos(Date fechaSolicitud, Date fechaPrestamo, Date fechaDevolucion, Integer idPersona) throws BusinessException {
        if (fechaSolicitud == null || fechaPrestamo == null || fechaDevolucion == null) {
            throw new BusinessException("Las fechas no pueden ser nulas.");
        }

        if (fechaSolicitud.after(fechaPrestamo)) {
            throw new BusinessException("La fecha de solicitud no puede ser posterior a la de préstamo.");
        }

        if (fechaPrestamo.after(fechaDevolucion)) {
            throw new BusinessException("La fecha de préstamo no puede ser posterior a la de devolución.");
        }

        BusinessValidator.validarId(idPersona, "persona");
    }
}
