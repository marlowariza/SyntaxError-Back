package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.EjemplaresDTO;
import com.syntaxerror.biblioteca.model.PersonasDTO;
import com.syntaxerror.biblioteca.model.PrestamosDTO;
import com.syntaxerror.biblioteca.model.PrestamosDeEjemplaresDTO;
import com.syntaxerror.biblioteca.model.enums.EstadoPrestamoEjemplar;
import com.syntaxerror.biblioteca.persistance.dao.impl.PersonaDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.PrestamoDAOImpl;
import java.util.ArrayList;
import java.util.Date;
import com.syntaxerror.biblioteca.persistance.dao.PersonaDAO;
import com.syntaxerror.biblioteca.persistance.dao.PrestamoDAO;
import com.syntaxerror.biblioteca.persistance.dao.PrestamoEjemplarDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.PrestamoEjemplarDAOImpl;
import java.text.ParseException;

public class PrestamoBO {

    private final PrestamoDAO prestamoDAO;
    private final PersonaDAO personaDAO;
    private final PrestamoEjemplarDAO prestamoEjemplarDAO;

    public PrestamoBO() {
        this.prestamoDAO = new PrestamoDAOImpl();
        this.personaDAO = new PersonaDAOImpl();
        this.prestamoEjemplarDAO = new PrestamoEjemplarDAOImpl();
    }

    public int insertar(Date fechaSolicitud, Date fechaPrestamo, Date fechaDevolucion, Integer idPersona) throws BusinessException {
        validarDatos(fechaSolicitud, fechaPrestamo, fechaDevolucion, idPersona);
        PrestamosDTO prestamo = new PrestamosDTO();
        prestamo.setFechaSolicitud(fechaSolicitud);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucion(fechaDevolucion);

        PersonasDTO persona = personaDAO.obtenerPorId(idPersona);
        if (persona == null) {
            throw new BusinessException("La persona con ID " + idPersona + " no existe.");
        }
        prestamo.setPersona(persona);

        return this.prestamoDAO.insertar(prestamo);
    }

    public int modificar(Integer idPrestamo, Date fechaSolicitud, Date fechaPrestamo, Date fechaDevolucion, Integer idPersona) throws BusinessException {
        BusinessValidator.validarId(idPrestamo, "préstamo");
        validarDatos(fechaSolicitud, fechaPrestamo, fechaDevolucion, idPersona);
        PrestamosDTO prestamo = new PrestamosDTO();
        prestamo.setIdPrestamo(idPrestamo);
        prestamo.setFechaSolicitud(fechaSolicitud);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucion(fechaDevolucion);

        PersonasDTO persona = personaDAO.obtenerPorId(idPersona);
        if (persona == null) {
            throw new BusinessException("La persona con ID " + idPersona + " no existe.");
        }
        prestamo.setPersona(persona);

        return this.prestamoDAO.modificar(prestamo);
    }

    public int eliminar(Integer idPrestamo) throws BusinessException {
        BusinessValidator.validarId(idPrestamo, "préstamo");
        PrestamosDTO prestamo = new PrestamosDTO();
        prestamo.setIdPrestamo(idPrestamo);
        return this.prestamoDAO.eliminar(prestamo);
    }

    public PrestamosDTO obtenerPorId(Integer idPrestamo) throws BusinessException {
        BusinessValidator.validarId(idPrestamo, "préstamo");
        return this.prestamoDAO.obtenerPorId(idPrestamo);
    }

    public ArrayList<PrestamosDTO> listarTodos() {
        return this.prestamoDAO.listarTodos();
    }

    private void validarDatos(Date fechaSolicitud, Date fechaPrestamo, Date fechaDevolucion, Integer idPersona) throws BusinessException {
        if (fechaSolicitud == null) {
            throw new BusinessException("Las fechas de solicitud no puede ser nula.");
        }

        //revisar
        if (fechaPrestamo.before(fechaSolicitud)) {
            throw new BusinessException("La fecha de préstamo no puede ser antes  a la de solicitud.");
        }
        if (fechaPrestamo.after(fechaDevolucion)) {
            throw new BusinessException("La fecha de préstamo no puede ser después  a la de devolución.");
        }

        BusinessValidator.validarId(idPersona, "persona");
    }

    public int contarEjemplaresActivosPorUsuario(int idUsuario) throws BusinessException {
        BusinessValidator.validarId(idUsuario, "usuario");

        return this.listarEjemplaresPrestadosPorPersona(idUsuario).size();
    }

    public int contarPrestamosActivosPorUsuario(int idUsuario) throws BusinessException {
        BusinessValidator.validarId(idUsuario, "usuario");
        return this.listarPrestamosActivosPorPersona(idUsuario).size();
    }

    public int contarPrestamosAtrasadosPorUsuario(int idUsuario) throws BusinessException {
        BusinessValidator.validarId(idUsuario, "usuario");
        return new PrestamoEjemplarDAOImpl().contarPrestamosAtrasadosPorIdPersona(idUsuario);
    }

    public int contarPrestamosPorMaterial(int idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return new PrestamoEjemplarDAOImpl().contarPrestamosPorIdMaterial(idMaterial);
    }

    public void solicitarPrestamo(Integer idPersona, Integer idMaterial, Integer idSede) throws BusinessException, ParseException {
        // Validaciones iniciales
        BusinessValidator.validarId(idPersona, "persona");
        BusinessValidator.validarId(idMaterial, "material");
        BusinessValidator.validarId(idSede, "sede");

        PersonasDTO persona = personaDAO.obtenerPorId(idPersona);
        if (persona == null) {
            throw new BusinessException("No se encontró a la persona solicitante.");
        }

        // Validar si tiene sanciones activas
        new SancionBO().verificarSancionesActivas(idPersona);

        // Verificar límite de préstamos activos
        int prestamosActivos = this.contarPrestamosActivosPorUsuario(idPersona);
        int limite = new PersonaBO().calcularLimitePrestamos(persona.getCodigo());

        if (prestamosActivos >= limite) {
            throw new BusinessException("Has alcanzado el límite de préstamos activos permitido.");
        }

        // Buscar ejemplar físico disponible del material en la sede indicada
        EjemplaresDTO ejemplarDisponible = new EjemplarBO()
                .obtenerPrimerEjemplarFisicoDisponiblePorMaterialYSede(idMaterial, idSede);

        if (ejemplarDisponible == null) {
            throw new BusinessException("No hay ejemplares físicos disponibles de este material en la sede seleccionada.");
        }

        // Insertar nuevo préstamo
        PrestamosDTO prestamo = new PrestamosDTO();
        Date fechaActual = new Date();
        prestamo.setFechaSolicitud(fechaActual);
        prestamo.setFechaPrestamo(null);
        prestamo.setFechaDevolucion(null);
        prestamo.setPersona(persona);

        int idPrestamo = this.prestamoDAO.insertar(prestamo);

        // Insertar relación en PrestamoEjemplar (usando parámetros individuales)
        new PrestamoEjemplarBO().insertar(
                idPrestamo,
                ejemplarDisponible.getIdEjemplar(),
                EstadoPrestamoEjemplar.SOLICITADO,
                null
        );

        // Marcar ejemplar como no disponible
        ejemplarDisponible.setDisponible(false);
        new EjemplarBO().modificar(
                ejemplarDisponible.getIdEjemplar(),
                ejemplarDisponible.getFechaAdquisicion(),
                false,
                ejemplarDisponible.getTipo(),
                ejemplarDisponible.getFormatoDigital(),
                ejemplarDisponible.getUbicacion(),
                ejemplarDisponible.getSede().getIdSede(),
                ejemplarDisponible.getMaterial().getIdMaterial()
        );
    }

    public ArrayList<PrestamosDTO> listarPrestamosDevueltos() {
        ArrayList<PrestamosDeEjemplaresDTO> prestamosDevueltos = prestamoEjemplarDAO.listarPrestamosDevueltos();
        ArrayList<PrestamosDTO> prestamos = new ArrayList<>();
        for (PrestamosDeEjemplaresDTO p : prestamosDevueltos) {
            Integer idPrestamoAux = p.getIdPrestamo();
            PrestamosDTO prestamo = prestamoDAO.obtenerPorId(idPrestamoAux);
            if (prestamo != null) {
                prestamos.add(prestamo);
            }
        }

        return prestamos;
    }

    public ArrayList<EjemplaresDTO> listarEjemplaresPrestadosPorPersona(int idPersona) throws BusinessException {
        BusinessValidator.validarId(idPersona, "persona");

        EjemplarBO ejemplarBO = new EjemplarBO();
        PrestamoEjemplarBO peBO = new PrestamoEjemplarBO();

        ArrayList<EjemplaresDTO> resultado = new ArrayList<>();
        for (Integer idEjemplar : peBO.obtenerIdEjemplaresPrestadosPorPersona(idPersona)) {
            resultado.add(ejemplarBO.obtenerPorId(idEjemplar));
        }

        return resultado;
    }

    public ArrayList<EjemplaresDTO> listarEjemplaresSolicitadosPorPersona(int idPersona) throws BusinessException {
        BusinessValidator.validarId(idPersona, "persona");

        PrestamoEjemplarBO peBO = new PrestamoEjemplarBO();
        EjemplarBO ejemplarBO = new EjemplarBO();

        ArrayList<EjemplaresDTO> resultado = new ArrayList<>();
        for (Integer idEjemplar : peBO.obtenerIdEjemplaresSolicitadosPorPersona(idPersona)) {
            resultado.add(ejemplarBO.obtenerPorId(idEjemplar));
        }

        return resultado;
    }

    public ArrayList<PrestamosDTO> listarPrestamosPorPersona(int idPersona) throws BusinessException {
        BusinessValidator.validarId(idPersona, "persona");

        PrestamoDAO dao = new PrestamoDAOImpl();
        return dao.listarPorIdPersona(idPersona);
    }

    public ArrayList<PrestamosDTO> listarPrestamosActivosPorPersona(int idPersona) throws BusinessException {
        BusinessValidator.validarId(idPersona, "persona");

        ArrayList<PrestamosDTO> prestamos = prestamoDAO.listarPorIdPersona(idPersona);
        ArrayList<PrestamosDTO> resultado = new ArrayList<>();

        for (PrestamosDTO prestamo : prestamos) {
            ArrayList<PrestamosDeEjemplaresDTO> ejemplares = prestamoEjemplarDAO.listarPorIdPrestamo(prestamo.getIdPrestamo());
            for (PrestamosDeEjemplaresDTO pe : ejemplares) {
                if (pe.getEstado() == EstadoPrestamoEjemplar.PRESTADO || pe.getEstado() == EstadoPrestamoEjemplar.ATRASADO) {
                    resultado.add(prestamo);
                    break; // Basta con uno activo
                }
            }
        }

        return resultado;
    }

    public ArrayList<PrestamosDTO> listarPrestamosAtrasados() {
        ArrayList<PrestamosDeEjemplaresDTO> prestamosAtrasados = prestamoEjemplarDAO.listarPrestamosAtrasados();
        ArrayList<PrestamosDTO> prestamos = new ArrayList<>();
        for (PrestamosDeEjemplaresDTO p : prestamosAtrasados) {
            Integer idPrestamoAux = p.getIdPrestamo();
            PrestamosDTO prestamo = prestamoDAO.obtenerPorId(idPrestamoAux);
            if (prestamo != null) {
                prestamos.add(prestamo);
            }
        }

        return prestamos;
    }

    public ArrayList<PrestamosDTO> listarPrestamosSolicitados() {
        ArrayList<PrestamosDeEjemplaresDTO> prestamosSolicitados = prestamoEjemplarDAO.listarPrestamosSolicitados();
        ArrayList<PrestamosDTO> prestamos = new ArrayList<>();
        for (PrestamosDeEjemplaresDTO p : prestamosSolicitados) {
            Integer idPrestamoAux = p.getIdPrestamo();
            PrestamosDTO prestamo = prestamoDAO.obtenerPorId(idPrestamoAux);
            if (prestamo != null) {
                prestamos.add(prestamo);
            }
        }

        return prestamos;
    }

    public ArrayList<PrestamosDTO> listarPrestamosNoCulminados() {
        ArrayList<PrestamosDeEjemplaresDTO> prestamosNoCulminados = prestamoEjemplarDAO.listarPrestamosNoCulminados();
        ArrayList<PrestamosDTO> prestamos = new ArrayList<>();
        for (PrestamosDeEjemplaresDTO p : prestamosNoCulminados) {
            Integer idPrestamoAux = p.getIdPrestamo();
            PrestamosDTO prestamo = prestamoDAO.obtenerPorId(idPrestamoAux);
            if (prestamo != null) {
                prestamos.add(prestamo);
            }
        }

        return prestamos;
    }
}
