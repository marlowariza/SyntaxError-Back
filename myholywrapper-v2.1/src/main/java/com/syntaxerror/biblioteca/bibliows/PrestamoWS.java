package com.syntaxerror.biblioteca.bibliows;

import com.syntaxerror.biblioteca.business.PrestamoBO;
import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.EjemplaresDTO;
import com.syntaxerror.biblioteca.model.PrestamosDTO;
import com.syntaxerror.biblioteca.model.enums.EstadoPrestamoEjemplar;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebService(serviceName = "PrestamoWS")
public class PrestamoWS {

    private final PrestamoBO prestamoBO;

    public PrestamoWS() {
        this.prestamoBO = new PrestamoBO();
    }

    @WebMethod(operationName = "insertarPrestamo")
    public int insertarPrestamo(
            @WebParam(name = "fechaSolicitud") Date fechaSolicitud,
            @WebParam(name = "fechaPrestamo") Date fechaPrestamo,
            @WebParam(name = "fechaDevolucion") Date fechaDevolucion,
            @WebParam(name = "idPersona") Integer idPersona
    ) {
        try {
            return prestamoBO.insertar(fechaSolicitud, fechaPrestamo, fechaDevolucion, idPersona);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al insertar préstamo: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "modificarPrestamo")
    public int modificarPrestamo(
            @WebParam(name = "idPrestamo") Integer idPrestamo,
            @WebParam(name = "fechaSolicitud") Date fechaSolicitud,
            @WebParam(name = "fechaPrestamo") Date fechaPrestamo,
            @WebParam(name = "fechaDevolucion") Date fechaDevolucion,
            @WebParam(name = "idPersona") Integer idPersona
    ) {
        try {
            return prestamoBO.modificar(idPrestamo, fechaSolicitud, fechaPrestamo, fechaDevolucion, idPersona);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al modificar préstamo: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "eliminarPrestamo")
    public int eliminarPrestamo(@WebParam(name = "idPrestamo") Integer idPrestamo) {
        try {
            return prestamoBO.eliminar(idPrestamo);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al eliminar préstamo: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "obtenerPrestamo")
    public PrestamosDTO obtenerPrestamo(@WebParam(name = "idPrestamo") Integer idPrestamo) {
        try {
            return prestamoBO.obtenerPorId(idPrestamo);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al obtener préstamo: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarPrestamos")
    public ArrayList<PrestamosDTO> listarPrestamos() {
        try {
            return prestamoBO.listarTodos();
        } catch (Exception e) {
            throw new WebServiceException("Error al listar préstamos: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "contarPrestamosActivos")
    public int contarPrestamosActivos(@WebParam(name = "idUsuario") int idUsuario) {
        try {
            return prestamoBO.contarPrestamosActivosPorUsuario(idUsuario);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al contar préstamos activos: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "contarPrestamosAtrasados")
    public int contarPrestamosAtrasados(@WebParam(name = "idUsuario") int idUsuario) {
        try {
            return prestamoBO.contarPrestamosAtrasadosPorUsuario(idUsuario);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al contar préstamos atrasados: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "contarPrestamosPorMaterial")
    public int contarPrestamosPorMaterial(@WebParam(name = "idMaterial") int idMaterial) {
        try {
            return prestamoBO.contarPrestamosPorMaterial(idMaterial);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al contar préstamos por material: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "solicitarPrestamo")
    public void solicitarPrestamo(
            @WebParam(name = "idPersona") Integer idPersona,
            @WebParam(name = "idEjemplares") ArrayList<Integer> idEjemplares
    ) {
        try {
            prestamoBO.solicitarPrestamo(idPersona, idEjemplares);
        } catch (BusinessException | java.text.ParseException e) {
            throw new WebServiceException("Error al solicitar préstamo: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarEjemplaresPrestadosPorPersona")
    public ArrayList<EjemplaresDTO> listarEjemplaresPrestadosPorPersona(@WebParam(name = "idPersona") int idPersona) {
        try {
            return prestamoBO.listarEjemplaresPrestadosPorPersona(idPersona);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar ejemplares prestados: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarEjemplaresSolicitadosPorPersona")
    public ArrayList<EjemplaresDTO> listarEjemplaresSolicitadosPorPersona(@WebParam(name = "idPersona") int idPersona) {
        try {
            return prestamoBO.listarEjemplaresSolicitadosPorPersona(idPersona);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar ejemplares solicitados: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarPrestamosPorPersona")
    public ArrayList<PrestamosDTO> listarPrestamosPorPersona(@WebParam(name = "idPersona") int idPersona) {
        try {
            return prestamoBO.listarPrestamosPorPersona(idPersona);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar préstamos por persona: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarPrestamosActivosPorPersona")
    public ArrayList<PrestamosDTO> listarPrestamosActivosPorPersona(@WebParam(name = "idPersona") int idPersona) {
        try {
            return prestamoBO.listarPrestamosPorEstadoPersona(idPersona, EstadoPrestamoEjemplar.PRESTADO);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar préstamos activos por persona: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarPrestamosSolicitadosPorPersona")
    public ArrayList<PrestamosDTO> listarPrestamosSolicitadosPorPersona(@WebParam(name = "idPersona") int idPersona) {
        try {
            return prestamoBO.listarPrestamosPorEstadoPersona(idPersona, EstadoPrestamoEjemplar.SOLICITADO);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar préstamos activos por persona: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarPrestamosDevueltosPorPersona")
    public ArrayList<PrestamosDTO> listarPrestamosDevueltosPorPersona(@WebParam(name = "idPersona") int idPersona) {
        try {
            return prestamoBO.listarPrestamosPorEstadoPersona(idPersona, EstadoPrestamoEjemplar.DEVUELTO);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar préstamos activos por persona: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarPrestamosAtrasadosPorPersona")
    public ArrayList<PrestamosDTO> listarPrestamosAtrasadosPorPersona(@WebParam(name = "idPersona") int idPersona) {
        try {
            return prestamoBO.listarPrestamosPorEstadoPersona(idPersona, EstadoPrestamoEjemplar.ATRASADO);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar préstamos activos por persona: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarPrestamosSolicitados")
    public ArrayList<PrestamosDTO> listarPrestamosSolicitados() {
        return prestamoBO.listarPrestamosSolicitados();
    }

    @WebMethod(operationName = "listarPrestamosAtrasados")
    public ArrayList<PrestamosDTO> listarPrestamosAtrasados() {
        return prestamoBO.listarPrestamosAtrasados();
    }

    @WebMethod(operationName = "listarPrestamosDevueltos")
    public ArrayList<PrestamosDTO> listarPrestamosDevueltos() {
        return prestamoBO.listarPrestamosDevueltos();
    }

    @WebMethod(operationName = "listarPrestamosNoCulminados")
    public ArrayList<PrestamosDTO> listarPrestamosNoCulminados() {
        return prestamoBO.listarPrestamosNoCulminados();
    }
    
    @WebMethod(operationName = "listarPrestamosPaginado")
    public List<PrestamosDTO> listarPrestamosPaginado(
            @WebParam(name = "limite") int limite,
            @WebParam(name = "pagina") int pagina
    ) {
        try {
            return prestamoBO.listarTodosPaginado(limite, pagina);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar prestamos paginados: " + e.getMessage());
        } catch (Exception e) {
            throw new WebServiceException("Error inesperado al listar prestamos paginados: " + e.getMessage());
        }
    }
    
    @WebMethod(operationName = "listarPrestamosPorSedePaginado")
    public List<PrestamosDTO> listarPorSedePaginado(
            @WebParam(name = "limite") int limite,
            @WebParam(name = "pagina") int pagina,
            @WebParam(name = "sede") int idSede
    ) {
        try {
            return prestamoBO.listarPorSedePaginado(limite, pagina, idSede);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar prestamos por sede paginados: " + e.getMessage());
        } catch (Exception e) {
            throw new WebServiceException("Error inesperado al listar prestamos por sede paginados: " + e.getMessage());
        }
    }
    
    @WebMethod(operationName = "listarPrestamosPorEstadoPaginado")
    public List<PrestamosDTO> listarPrestamosPorEstadoPaginado(
            @WebParam(name = "estado") EstadoPrestamoEjemplar estado,
            @WebParam(name = "limite") int limite,
            @WebParam(name = "pagina") int pagina
    ) {
        try {
            return prestamoBO.listarPrestamosPorEstadoPaginado(estado, limite, pagina);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar prestamos por estado paginados: " + e.getMessage());
        } catch (Exception e) {
            throw new WebServiceException("Error inesperado al listar prestamos por estado paginados: " + e.getMessage()+estado);
        }
    }

    @WebMethod(operationName = "obtenerEstadoPrestamo")
    public String obtenerEstadoPrestamo(@WebParam(name = "idPrestamo") int idPrestamo) {
        try {
            return prestamoBO.obtenerEstadoPrestamo(idPrestamo);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar ejemplares prestados: " + e.getMessage());
        }
    }
}
