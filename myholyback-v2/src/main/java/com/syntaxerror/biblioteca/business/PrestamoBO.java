package com.syntaxerror.biblioteca.business;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.EjemplaresDTO;
import com.syntaxerror.biblioteca.model.PersonasDTO;
import com.syntaxerror.biblioteca.model.PrestamosDTO;
import com.syntaxerror.biblioteca.model.PrestamosDeEjemplaresDTO;
import com.syntaxerror.biblioteca.model.enums.EstadoPrestamoEjemplar;
import com.syntaxerror.biblioteca.persistance.dao.PersonaDAO;
import com.syntaxerror.biblioteca.persistance.dao.PrestamoDAO;
import com.syntaxerror.biblioteca.persistance.dao.PrestamoEjemplarDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.PersonaDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.PrestamoDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.PrestamoEjemplarDAOImpl;
import java.util.List;

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

//    public void solicitarPrestamo(Integer idPersona, List<Integer> idEjemplares) throws BusinessException, ParseException {
//
//        // Validar entrada
//        BusinessValidator.validarId(idPersona, "persona");
//        if (idEjemplares == null || idEjemplares.isEmpty()) {
//            throw new BusinessException("Debe seleccionar al menos un ejemplar.");
//        }
//
//        PersonasDTO persona = personaDAO.obtenerPorId(idPersona);
//        if (persona == null) {
//            throw new BusinessException("No se encontró a la persona solicitante.");
//        }
//        //Validar sanciones y límites
//        new SancionBO().verificarSancionesActivas(idPersona);
//
//        int ejemplaresEnProceso = this.contarEjemplaresEnProcesoPorUsuario(idPersona);
//        int limite = new PersonaBO().calcularLimitePrestamos(persona.getCodigo());
//
//        if (ejemplaresEnProceso + idEjemplares.size() > limite) {
//            throw new BusinessException("Has alcanzado el límite de ejemplares permitidos en proceso.");
//        }
//
//        // Cargar ejemplares REALES de la BD y validar disponibilidad
//        EjemplarBO ejemplarBO = new EjemplarBO();
//        List<EjemplaresDTO> ejemplares = new ArrayList<>();
//        for (Integer idEjemplar : idEjemplares) {
//            EjemplaresDTO ej = ejemplarBO.obtenerPorId(idEjemplar);
//            if (ej == null) {
//                throw new BusinessException("El ejemplar con ID " + idEjemplar + " no existe.");
//            }
//            if (!ej.getDisponible()) {
//                throw new BusinessException("El ejemplar con ID " + idEjemplar + " no está disponible.");
//            }
//            ejemplares.add(ej);
//        }
//
//        // Crear préstamo principal
//        PrestamosDTO prestamo = new PrestamosDTO();
//        Date fechaActual = new Date();
//        prestamo.setFechaSolicitud(fechaActual);
//        prestamo.setFechaPrestamo(null);
//        prestamo.setFechaDevolucion(null);
//        prestamo.setPersona(persona);
//
//        int idPrestamo = this.prestamoDAO.insertar(prestamo);
//
//        // Insertar cada relación y marcar no disponible
//        PrestamoEjemplarBO prestamoEjemplarBO = new PrestamoEjemplarBO();
//        for (EjemplaresDTO ej : ejemplares) {
//            prestamoEjemplarBO.insertar(
//                    idPrestamo,
//                    ej.getIdEjemplar(),
//                    EstadoPrestamoEjemplar.SOLICITADO,
//                    null
//            );
//            ej.setDisponible(false);
//            ejemplarBO.modificar(
//                    ej.getIdEjemplar(),
//                    ej.getFechaAdquisicion(),
//                    false,
//                    ej.getTipo(),
//                    ej.getFormatoDigital(),
//                    ej.getUbicacion(),
//                    ej.getSede().getIdSede(),
//                    ej.getMaterial().getIdMaterial()
//            );
//        }
//    }
    public void solicitarPrestamo(Integer idPersona, List<Integer> idEjemplares) throws BusinessException, ParseException {

        // ✅ Validar entrada
        BusinessValidator.validarId(idPersona, "persona");
        if (idEjemplares == null || idEjemplares.isEmpty()) {
            throw new BusinessException("Debe seleccionar al menos un ejemplar.");
        }

        PersonasDTO persona = personaDAO.obtenerPorId(idPersona);
        if (persona == null) {
            throw new BusinessException("No se encontró a la persona solicitante.");
        }

        // ✅ Validar sanciones y límite de ejemplares en proceso
        new SancionBO().verificarSancionesActivas(idPersona);
        int ejemplaresEnProceso = this.contarEjemplaresEnProcesoPorUsuario(idPersona);
        int limite = new PersonaBO().calcularLimitePrestamos(persona.getCodigo());

        if (ejemplaresEnProceso + idEjemplares.size() > limite) {
            throw new BusinessException("Has alcanzado el límite de ejemplares permitidos en proceso.");
        }

        // ✅ Cargar ejemplares reales y validar disponibilidad + sede única
        EjemplarBO ejemplarBO = new EjemplarBO();
        List<EjemplaresDTO> ejemplares = new ArrayList<>();
        Integer sedeIdUnica = null;

        for (Integer idEjemplar : idEjemplares) {
            EjemplaresDTO ej = ejemplarBO.obtenerPorId(idEjemplar);
            if (ej == null) {
                throw new BusinessException("El ejemplar con ID " + idEjemplar + " no existe.");
            }
            if (!ej.getDisponible()) {
                throw new BusinessException("El ejemplar con ID " + idEjemplar + " no está disponible.");
            }

            // ✅ Verificar que todos son de la misma sede
            if (sedeIdUnica == null) {
                sedeIdUnica = ej.getSede().getIdSede();
            } else if (!sedeIdUnica.equals(ej.getSede().getIdSede())) {
                throw new BusinessException("Todos los ejemplares del préstamo deben pertenecer a la misma sede.");
            }

            ejemplares.add(ej);
        }

        // ✅ Crear préstamo principal
        PrestamosDTO prestamo = new PrestamosDTO();
        Date fechaActual = new Date();
        prestamo.setFechaSolicitud(fechaActual);
        prestamo.setFechaPrestamo(null);
        prestamo.setFechaDevolucion(null);
        prestamo.setPersona(persona);

        int idPrestamo = this.prestamoDAO.insertar(prestamo);

        // ✅ Insertar relación y marcar ejemplares como no disponibles
        PrestamoEjemplarBO prestamoEjemplarBO = new PrestamoEjemplarBO();
        for (EjemplaresDTO ej : ejemplares) {
            prestamoEjemplarBO.insertar(
                    idPrestamo,
                    ej.getIdEjemplar(),
                    EstadoPrestamoEjemplar.SOLICITADO,
                    null
            );
            ej.setDisponible(false);
            ejemplarBO.modificar(
                    ej.getIdEjemplar(),
                    ej.getFechaAdquisicion(),
                    false,
                    ej.getTipo(),
                    ej.getFormatoDigital(),
                    ej.getUbicacion(),
                    ej.getSede().getIdSede(),
                    ej.getMaterial().getIdMaterial()
            );
        }

    }

    public void recogerPrestamo(Integer idPrestamo) throws BusinessException {
        BusinessValidator.validarId(idPrestamo, "préstamo");

        // Verificar prestamo
        PrestamosDTO prestamo = prestamoDAO.obtenerPorId(idPrestamo);
        if (prestamo == null) {
            throw new BusinessException("No existe el préstamo indicado.");
        }

        PersonasDTO personaCompleta = personaDAO.obtenerPorId(prestamo.getPersona().getIdPersona());
        prestamo.setPersona(personaCompleta);

        // Obtener detalles del prestamo
        ArrayList<PrestamosDeEjemplaresDTO> detalles = prestamoEjemplarDAO.listarPorIdPrestamo(idPrestamo);
        boolean tieneSolicitados = false;

        for (PrestamosDeEjemplaresDTO pe : detalles) {
            if (pe.getEstado() == EstadoPrestamoEjemplar.SOLICITADO) {
                tieneSolicitados = true;
                pe.setEstado(EstadoPrestamoEjemplar.PRESTADO);
                prestamoEjemplarDAO.modificar(pe);
            }
        }

        if (!tieneSolicitados) {
            throw new BusinessException("No hay ejemplares en estado SOLICITADO para este préstamo.");
        }

        // 3️⃣ Registrar fecha de préstamo y calcular fecha devolución según rol
        Date fechaPrestamo = new Date();
        prestamo.setFechaPrestamo(fechaPrestamo);

        int diasPrestamo;
        String tipoPersona = prestamo.getPersona().getTipo().name();
        switch (tipoPersona) {
            case "ESTUDIANTE":
                diasPrestamo = 7;
                break;
            case "PROFESOR":
            case "ADMINISTRADOR":
                diasPrestamo = 14;
                break;
            default:
                diasPrestamo = 7;
        }

        // Calcular fecha devolución
        Date fechaDevolucion = new Date(fechaPrestamo.getTime() + (long) diasPrestamo * 24 * 60 * 60 * 1000);
        prestamo.setFechaDevolucion(fechaDevolucion);

        // Guardar actualización en base de datos
        prestamoDAO.modificar(prestamo);
    }

    /**
     * Registra la devolución de uno o varios ejemplares de un préstamo. Permite
     * devoluciones parciales o totales. Acepta ejemplares en estado PRESTADO o
     * ATRASADO. Si tras la operación todos los ejemplares quedan devueltos, se
     * registra la fecha de devolución del préstamo principal.
     *
     * @param idPrestamo ID del préstamo principal.
     * @param idEjemplaresDevueltos Lista de IDs de ejemplares devueltos.
     * @throws BusinessException Si los parámetros son inválidos o no se
     * encuentra el préstamo.
     */
    public void devolverPrestamo(Integer idPrestamo, List<Integer> idEjemplaresDevueltos) throws BusinessException {
        // Validar entrada
        BusinessValidator.validarId(idPrestamo, "préstamo");
        if (idEjemplaresDevueltos == null || idEjemplaresDevueltos.isEmpty()) {
            throw new BusinessException("Debe seleccionar al menos un ejemplar para devolver.");
        }
        // Cargar préstamo principal
        PrestamosDTO prestamo = prestamoDAO.obtenerPorId(idPrestamo);
        if (prestamo == null) {
            throw new BusinessException("No existe el préstamo indicado.");
        }
        // Cargar detalles de ejemplares asociados
        ArrayList<PrestamosDeEjemplaresDTO> detalles = prestamoEjemplarDAO.listarPorIdPrestamo(idPrestamo);
        EjemplarBO ejemplarBO = new EjemplarBO();
        Date fechaHoy = new Date();
        boolean seDevuelven = false;
        // Procesar cada ejemplar seleccionado para devolución
        for (PrestamosDeEjemplaresDTO pe : detalles) {
            if (idEjemplaresDevueltos.contains(pe.getIdEjemplar())
                    && (pe.getEstado() == EstadoPrestamoEjemplar.PRESTADO
                    || pe.getEstado() == EstadoPrestamoEjemplar.ATRASADO)) {

                // Cambiar estado a DEVUELTO y registrar fecha real
                pe.setEstado(EstadoPrestamoEjemplar.DEVUELTO);
                pe.setFechaRealDevolucion(fechaHoy);
                prestamoEjemplarDAO.modificar(pe);

                // Liberar stock del ejemplar físico
                EjemplaresDTO ejemplar = ejemplarBO.obtenerPorId(pe.getIdEjemplar());
                ejemplar.setDisponible(true);
                ejemplarBO.modificar(
                        ejemplar.getIdEjemplar(),
                        ejemplar.getFechaAdquisicion(),
                        true,
                        ejemplar.getTipo(),
                        ejemplar.getFormatoDigital(),
                        ejemplar.getUbicacion(),
                        ejemplar.getSede().getIdSede(),
                        ejemplar.getMaterial().getIdMaterial()
                );

                seDevuelven = true;
            }
        }
        if (!seDevuelven) {
            throw new BusinessException("Ningún ejemplar válido fue devuelto. Verifique los estados permitidos.");
        }

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

    public ArrayList<PrestamosDTO> listarPrestamosPorEstadoPersona(int idPersona, EstadoPrestamoEjemplar estado) throws BusinessException {
        BusinessValidator.validarId(idPersona, "persona");

        ArrayList<PrestamosDTO> resultado = new ArrayList<>();
        ArrayList<PrestamosDTO> prestamos = prestamoDAO.listarPorIdPersona(idPersona);

        for (PrestamosDTO prestamo : prestamos) {
            ArrayList<PrestamosDeEjemplaresDTO> ejemplares = prestamoEjemplarDAO.listarPorIdPrestamo(prestamo.getIdPrestamo());
            for (PrestamosDeEjemplaresDTO pe : ejemplares) {
                if (pe.getEstado() == estado) {
                    resultado.add(prestamo);
                    break; // Solo se agrega una vez por préstamo
                }
            }
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

    //CUENTA CANTIDAD DE EJEMPLARES (SOLICITADOS,PRESTADOS,ATRASADOS) PARA LA VALIDACION
    //DE LIMITE POR CANTIDAD DE EJEMPLARES 
    public int contarEjemplaresEnProcesoPorUsuario(int idPersona) throws BusinessException {
        BusinessValidator.validarId(idPersona, "persona");
        return new PrestamoEjemplarDAOImpl().contarEjemplaresEnProcesoPorIdPersona(idPersona);
    }

    public List<PrestamosDTO> listarTodosPaginado(int limite, int pagina) throws BusinessException {
        BusinessValidator.validarPaginacion(limite, pagina);
        int offset = (pagina - 1) * limite;
        return this.prestamoDAO.listarTodosPaginado(limite, offset);
    }

    public List<PrestamosDTO> listarPorSedePaginado(int limite, int pagina, int idSede) throws BusinessException {
        BusinessValidator.validarPaginacion(limite, pagina);
        int offset = (pagina - 1) * limite;
        return this.prestamoDAO.listarPorSedePaginado(limite, offset, idSede);
    }

    public List<PrestamosDTO> listarPrestamosPorEstadoPaginado(EstadoPrestamoEjemplar estado, int limite, int pagina) throws BusinessException {
        BusinessValidator.validarPaginacion(limite, pagina);
        int offset = (pagina - 1) * limite;
        return this.prestamoDAO.listarPrestamosPorEstadoPaginado(estado, limite, offset);
    }

    public String obtenerEstadoPrestamo(int idPrestamo) throws BusinessException {
        BusinessValidator.validarId(idPrestamo, "prestamo");
        return this.prestamoDAO.obtenerEstadoPrestamo(idPrestamo);
    }

    public List<PrestamosDTO> listarPrestamosPorEstadoYSedePaginado(EstadoPrestamoEjemplar estado, Integer sedeId, int limite, int pagina) throws BusinessException {
        BusinessValidator.validarPaginacion(limite, pagina);
        int offset = (pagina - 1) * limite;
        return this.prestamoDAO.listarPrestamosPorEstadoYSedePaginado(estado, sedeId, limite, offset);
    }

    public int contarTotalPrestamos() throws BusinessException {
        return this.prestamoDAO.contarTotalPrestamos();
    }

    public int contarTotalPrestamosPorEstado(EstadoPrestamoEjemplar estado) throws BusinessException {
        return this.prestamoDAO.contarTotalPrestamosPorEstado(estado);
    }

    public int contarTotalPrestamosPorEstadoYSede(EstadoPrestamoEjemplar estado, int sedeId) throws BusinessException {
        return this.prestamoDAO.contarTotalPrestamosPorEstadoYSede(estado, sedeId);
    }
}
