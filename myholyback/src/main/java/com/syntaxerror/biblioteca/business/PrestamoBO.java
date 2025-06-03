package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.EjemplarDTO;
import com.syntaxerror.biblioteca.model.PersonaDTO;
import com.syntaxerror.biblioteca.model.PrestamoDTO;
import com.syntaxerror.biblioteca.model.PrestamoEjemplarDTO;
import com.syntaxerror.biblioteca.model.enums.EstadoPrestamoEjemplar;
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

    public int contarPrestamosActivosPorUsuario(int idUsuario) throws BusinessException {
        BusinessValidator.validarId(idUsuario, "usuario");

        ArrayList<PrestamoDTO> prestamos = this.listarTodos();
        ArrayList<PrestamoEjemplarDTO> prestamosEjemplares = new PrestamoEjemplarBO().listarTodos();

        int contador = 0;
        for (PrestamoDTO prestamo : prestamos) {
            if (prestamo.getPersona() != null && prestamo.getPersona().getIdPersona() == idUsuario) {
                for (PrestamoEjemplarDTO pe : prestamosEjemplares) {
                    if (pe.getIdPrestamo().equals(prestamo.getIdPrestamo())
                            && (pe.getEstado() == EstadoPrestamoEjemplar.PRESTADO || pe.getEstado() == EstadoPrestamoEjemplar.ATRASADO)) {
                        contador++;
                    }
                }
            }
        }
        return contador;
    }

    public int contarPrestamosAtrasadosPorUsuario(int idUsuario) throws BusinessException {
        BusinessValidator.validarId(idUsuario, "usuario");

        ArrayList<PrestamoDTO> prestamos = this.listarTodos();
        ArrayList<PrestamoEjemplarDTO> prestamosEjemplares = new PrestamoEjemplarBO().listarTodos();

        int contador = 0;
        for (PrestamoDTO prestamo : prestamos) {
            if (prestamo.getPersona() != null && prestamo.getPersona().getIdPersona() == idUsuario) {
                for (PrestamoEjemplarDTO pe : prestamosEjemplares) {
                    if (pe.getIdPrestamo().equals(prestamo.getIdPrestamo())
                            && pe.getEstado() == EstadoPrestamoEjemplar.ATRASADO) {
                        contador++;
                    }
                }
            }
        }
        return contador;
    }

    public int contarPrestamosPorMaterial(int idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");

        ArrayList<PrestamoEjemplarDTO> prestamosEjemplares = new PrestamoEjemplarBO().listarTodos();
        ArrayList<EjemplarDTO> ejemplares = new EjemplarBO().listarTodos();

        int contador = 0;
        for (PrestamoEjemplarDTO pe : prestamosEjemplares) {
            for (EjemplarDTO ejem : ejemplares) {
                if (ejem.getIdEjemplar().equals(pe.getIdEjemplar())
                        && ejem.getMaterial() != null
                        && ejem.getMaterial().getIdMaterial() == idMaterial) {
                    contador++;
                }
            }
        }
        return contador;
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
