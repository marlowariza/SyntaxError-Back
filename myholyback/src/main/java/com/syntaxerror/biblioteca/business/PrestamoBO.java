package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.EjemplarDTO;
import com.syntaxerror.biblioteca.model.PersonaDTO;
import com.syntaxerror.biblioteca.model.PrestamoDTO;
import com.syntaxerror.biblioteca.model.PrestamoEjemplarDTO;
import com.syntaxerror.biblioteca.model.SancionDTO;
import com.syntaxerror.biblioteca.model.enums.EstadoPrestamoEjemplar;
import com.syntaxerror.biblioteca.persistance.dao.PersonaDAO;
import com.syntaxerror.biblioteca.persistance.dao.PrestamoDAO;
import com.syntaxerror.biblioteca.persistance.dao.PrestamoEjemplarDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.PersonaDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.PrestamoDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.PrestamoEjemplarDAOImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        if (fechaSolicitud == null ) {
            throw new BusinessException("Las fecha no pueden ser nulas.");
        }

        if (fechaSolicitud.after(fechaPrestamo)) {
            throw new BusinessException("La fecha de solicitud no puede ser posterior a la de préstamo.");
        }

        if (fechaPrestamo.after(fechaDevolucion)) {
            throw new BusinessException("La fecha de préstamo no puede ser posterior a la de devolución.");
        }

        BusinessValidator.validarId(idPersona, "persona");
    }
    

    public int contarEjemplaresActivosPorUsuario(int idUsuario) throws BusinessException {
        BusinessValidator.validarId(idUsuario, "usuario");

        return this.listarEjemplaresPrestadosPorPersona(idUsuario).size();
    }

    public int contarPrestamosActivosPorUsuario(int idUsuario) throws BusinessException {
        BusinessValidator.validarId(idUsuario, "usuario");

        PrestamoEjemplarBO peBO = new PrestamoEjemplarBO();
        ArrayList<PrestamoDTO> prestamos = this.listarPrestamosPorPersona(idUsuario);

        int contador = 0;
        for (PrestamoDTO prestamo : prestamos) {
            for (PrestamoEjemplarDTO pe : peBO.listarPorPrestamo(prestamo.getIdPrestamo())) {
                if (pe.getEstado() == EstadoPrestamoEjemplar.PRESTADO || pe.getEstado() == EstadoPrestamoEjemplar.ATRASADO) {
                    contador++; // cuenta el préstamo si tiene al menos un ejemplar activo
                    break;
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



//    public void solicitarPrestamo(Integer idPersona, Integer idMaterial) throws BusinessException, ParseException {
//        // Validaciones iniciales
//        BusinessValidator.validarId(idPersona, "persona");
//        BusinessValidator.validarId(idMaterial, "material");
//
//        PersonaDTO persona = personaDAO.obtenerPorId(idPersona);
//        if (persona == null) {
//            throw new BusinessException("No se encontró a la persona solicitante.");
//        }
//
//        // Validar si tiene sanciones activas
//        new SancionBO().verificarSancionesActivas(idPersona);
//
//        // Verificar límite de préstamos activos
//        int prestamosActivos = this.contarPrestamosActivosPorUsuario(idPersona);
//        int limite = new PersonaBO().calcularLimitePrestamos(persona.getCorreo());
//
//        if (prestamosActivos >= limite) {
//            throw new BusinessException("Has alcanzado el límite de préstamos activos permitido.");
//        }
//
//        // Buscar ejemplar disponible del material
//        EjemplarDTO ejemplarDisponible = new EjemplarBO().obtenerPrimerEjemplarDisponiblePorMaterial(idMaterial);
//
//        if (ejemplarDisponible == null) {
//            throw new BusinessException("No hay ejemplares disponibles de este material.");
//        }
//
//        // Insertar nuevo préstamo
//        PrestamoDTO prestamo = new PrestamoDTO();
//        Date fechaActual = new Date();
//        prestamo.setFechaSolicitud(fechaActual);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date dummyFecha = sdf.parse("1900-01-01");
//        prestamo.setFechaPrestamo(dummyFecha);
//        prestamo.setFechaDevolucion(dummyFecha);
//
//        prestamo.setPersona(persona);
//
//        int idPrestamo = this.prestamoDAO.insertar(prestamo);
//
//        // Insertar relación en PrestamoEjemplar
//        PrestamoEjemplarDTO pe = new PrestamoEjemplarDTO();
//        pe.setEstado(EstadoPrestamoEjemplar.SOLICITADO);
//        pe.setFechaRealDevolucion(null);
//
//        new PrestamoEjemplarBO().insertar(idPrestamo, ejemplarDisponible.getIdEjemplar(), pe);
//
//        // Marcar ejemplar como no disponible
//        ejemplarDisponible.setDisponible(false);
//        new EjemplarBO().modificar(
//                ejemplarDisponible.getIdEjemplar(),
//                ejemplarDisponible.getFechaAdquisicion(),
//                false,
//                ejemplarDisponible.getTipo(),
//                ejemplarDisponible.getFormatoDigital(),
//                ejemplarDisponible.getUbicacion(),
//                ejemplarDisponible.getSede().getIdSede(),
//                ejemplarDisponible.getMaterial().getIdMaterial()
//        );
//    }
    public void solicitarPrestamo(Integer idPersona, Integer idMaterial, Integer idSede) throws BusinessException, ParseException {
        // Validaciones iniciales
        BusinessValidator.validarId(idPersona, "persona");
        BusinessValidator.validarId(idMaterial, "material");
        BusinessValidator.validarId(idSede, "sede");

        PersonaDTO persona = personaDAO.obtenerPorId(idPersona);
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
        EjemplarDTO ejemplarDisponible = new EjemplarBO().obtenerPrimerEjemplarFisicoDisponiblePorMaterialYSede(idMaterial, idSede);
        if (ejemplarDisponible == null) {
            throw new BusinessException("No hay ejemplares físicos disponibles de este material en la sede seleccionada.");
        }

        // Insertar nuevo préstamo
        PrestamoDTO prestamo = new PrestamoDTO();
        Date fechaActual = new Date();
        prestamo.setFechaSolicitud(fechaActual);

        prestamo.setFechaPrestamo(null);
        prestamo.setFechaDevolucion(null);

        prestamo.setPersona(persona);

        int idPrestamo = this.prestamoDAO.insertar(prestamo);

        // Insertar relación en PrestamoEjemplar
        PrestamoEjemplarDTO pe = new PrestamoEjemplarDTO();
        pe.setEstado(EstadoPrestamoEjemplar.SOLICITADO);
        pe.setFechaRealDevolucion(null);

        new PrestamoEjemplarBO().insertar(idPrestamo, ejemplarDisponible.getIdEjemplar(), pe);

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

    //LISTA EJEMPLARES CON PRESTAMOS ACTIVOS
    public ArrayList<EjemplarDTO> listarEjemplaresPrestadosPorPersona(int idPersona) throws BusinessException {
        BusinessValidator.validarId(idPersona, "persona");

        ArrayList<EjemplarDTO> resultado = new ArrayList<>();
        PrestamoEjemplarBO peBO = new PrestamoEjemplarBO();
        EjemplarBO ejemplarBO = new EjemplarBO();

        // Mapear ejemplares por ID para acceso rápido
        Map<Integer, EjemplarDTO> ejemplaresMap = new HashMap<>();
        for (EjemplarDTO ej : ejemplarBO.listarTodos()) {
            ejemplaresMap.put(ej.getIdEjemplar(), ej);
        }

        // Filtrar préstamos del usuario
        for (PrestamoDTO prestamo : this.listarTodos()) {
            if (prestamo.getPersona() != null && prestamo.getPersona().getIdPersona().equals(idPersona)) {
                ArrayList<PrestamoEjemplarDTO> detalles = peBO.listarPorPrestamo(prestamo.getIdPrestamo());
                for (PrestamoEjemplarDTO pe : detalles) {
                    if (pe.getEstado() == EstadoPrestamoEjemplar.PRESTADO || pe.getEstado() == EstadoPrestamoEjemplar.ATRASADO) {
                        EjemplarDTO ejemplar = ejemplaresMap.get(pe.getIdEjemplar());
                        if (ejemplar != null) {
                            resultado.add(ejemplar);
                        }
                    }
                }
            }
        }

        return resultado;
    }

    public ArrayList<EjemplarDTO> listarEjemplaresSolicitadosPorPersona(int idPersona) throws BusinessException {
        BusinessValidator.validarId(idPersona, "persona");

        ArrayList<EjemplarDTO> resultado = new ArrayList<>();
        PrestamoEjemplarBO peBO = new PrestamoEjemplarBO();
        EjemplarBO ejemplarBO = new EjemplarBO();

        Map<Integer, EjemplarDTO> ejemplaresMap = new HashMap<>();
        for (EjemplarDTO ej : ejemplarBO.listarTodos()) {
            ejemplaresMap.put(ej.getIdEjemplar(), ej);
        }

        for (PrestamoDTO prestamo : this.listarTodos()) {
            if (prestamo.getPersona() != null && prestamo.getPersona().getIdPersona().equals(idPersona)) {
                ArrayList<PrestamoEjemplarDTO> detalles = peBO.listarPorPrestamo(prestamo.getIdPrestamo());
                for (PrestamoEjemplarDTO pe : detalles) {
                    if (pe.getEstado() == EstadoPrestamoEjemplar.SOLICITADO) {
                        EjemplarDTO ejemplar = ejemplaresMap.get(pe.getIdEjemplar());
                        if (ejemplar != null) {
                            resultado.add(ejemplar);
                        }
                    }
                }
            }
        }

        return resultado;
    }

    public ArrayList<PrestamoDTO> listarPrestamosPorPersona(int idPersona) throws BusinessException {
        BusinessValidator.validarId(idPersona, "persona");

        ArrayList<PrestamoDTO> resultado = new ArrayList<>();
        for (PrestamoDTO prestamo : this.listarTodos()) {
            if (prestamo.getPersona() != null && prestamo.getPersona().getIdPersona().equals(idPersona)) {
                resultado.add(prestamo);
            }
        }
        return resultado;
    }

    public ArrayList<PrestamoDTO> listarPrestamosActivosPorPersona(int idPersona) throws BusinessException {
        BusinessValidator.validarId(idPersona, "persona");

        ArrayList<PrestamoDTO> resultado = new ArrayList<>();
        PrestamoEjemplarBO peBO = new PrestamoEjemplarBO();

        for (PrestamoDTO prestamo : this.listarPrestamosPorPersona(idPersona)) {
            for (PrestamoEjemplarDTO pe : peBO.listarPorPrestamo(prestamo.getIdPrestamo())) {
                if (pe.getEstado() == EstadoPrestamoEjemplar.PRESTADO || pe.getEstado() == EstadoPrestamoEjemplar.ATRASADO) {
                    resultado.add(prestamo);
                    break; // Basta con que un ejemplar esté activo
                }
            }
        }

        return resultado;
    }
    public ArrayList<PrestamoDTO> listarPrestamosDevueltos() {
        ArrayList<PrestamoEjemplarDTO> prestamosDevueltos=prestamoEjemplarDAO.listarPrestamosDevueltos();
        ArrayList<PrestamoDTO> prestamos = new ArrayList<>();
        for (PrestamoEjemplarDTO p : prestamosDevueltos) {
            Integer idPrestamoAux = p.getIdPrestamo();
            PrestamoDTO prestamo = prestamoDAO.obtenerPorId(idPrestamoAux);
            if (prestamo != null) {
                prestamos.add(prestamo);
            }
        }
        
        return prestamos;
    }
    
    public ArrayList<PrestamoDTO> listarPrestamosAtrasados() {
        ArrayList<PrestamoEjemplarDTO> prestamosAtrasados=prestamoEjemplarDAO.listarPrestamosAtrasados();
        ArrayList<PrestamoDTO> prestamos = new ArrayList<>();
        for (PrestamoEjemplarDTO p : prestamosAtrasados) {
            Integer idPrestamoAux = p.getIdPrestamo();
            PrestamoDTO prestamo = prestamoDAO.obtenerPorId(idPrestamoAux);
            if (prestamo != null) {
                prestamos.add(prestamo);
            }
        }
        
        return prestamos;
    }
    
    public ArrayList<PrestamoDTO> listarPrestamosSolicitados() {
        ArrayList<PrestamoEjemplarDTO> prestamosSolicitados=prestamoEjemplarDAO.listarPrestamosSolicitados();
        ArrayList<PrestamoDTO> prestamos = new ArrayList<>();
        for (PrestamoEjemplarDTO p : prestamosSolicitados) {
            Integer idPrestamoAux = p.getIdPrestamo();
            PrestamoDTO prestamo = prestamoDAO.obtenerPorId(idPrestamoAux);
            if (prestamo != null) {
                prestamos.add(prestamo);
            }
        }
        
        return prestamos;
    }
    
    public ArrayList<PrestamoDTO> listarPrestamosNoCulminados() {
        ArrayList<PrestamoEjemplarDTO> prestamosNoCulminados=prestamoEjemplarDAO.listarPrestamosNoCulminados();
        ArrayList<PrestamoDTO> prestamos = new ArrayList<>();
        for (PrestamoEjemplarDTO p : prestamosNoCulminados) {
            Integer idPrestamoAux = p.getIdPrestamo();
            PrestamoDTO prestamo = prestamoDAO.obtenerPorId(idPrestamoAux);
            if (prestamo != null) {
                prestamos.add(prestamo);
            }
        }
        
        return prestamos;
    }

}
