package com.syntaxerror.biblioteca.bibliows;

import com.syntaxerror.biblioteca.business.MaterialBO;
import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.CreadoresDTO;
import com.syntaxerror.biblioteca.model.EjemplaresDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.TemasDTO;
import com.syntaxerror.biblioteca.model.enums.Nivel;
import jakarta.xml.ws.WebServiceException;
import java.util.List;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;

/**
 *
 * @author JL
 */
@WebService(serviceName = "MaterialWS")
public class MaterialWS {

    private final MaterialBO materialBO;

    public MaterialWS() {
        materialBO = new MaterialBO();
    }

    @WebMethod(operationName = "insertarMaterial")
    public int insertarMaterial(
            @WebParam(name = "material") MaterialesDTO material,
            @WebParam(name = "creadores") List<CreadoresDTO> creadores,
            @WebParam(name = "temas") List<TemasDTO> temas
    ) {
        try {
            material.setCreadores(creadores);
            material.setTemas(temas);

            return materialBO.insertar(material);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al insertar material: " + e.getMessage());
        } catch (Exception e) {
            throw new WebServiceException("Error inesperado al insertar material: " + e.getMessage());
        }
    }

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "listarTodos")
    public ArrayList<MaterialesDTO> listarTodos() {
        try {
            return (ArrayList<MaterialesDTO>) materialBO.listarTodos();
        } catch (Exception e) {
            throw new WebServiceException("Error al listar" + e.getMessage());
        }
    }

//    @WebMethod(operationName = "listarCreadoresPorMaterial")
//    public ArrayList<CreadoresDTO> listarCreadoresPorMaterial(
//            @WebParam(name = "idMaterial") Integer idMaterial
//    ) {
//        try {
//            return materialCreadorBO.listarCreadoresPorMaterial(idMaterial);
//        } catch (BusinessException e) {
//            throw new WebServiceException("Error al listar creadores por material: " + e.getMessage());
//        }
//    }
//    @WebMethod(operationName = "listarTemasPorMaterial")
//    public ArrayList<TemasDTO> listarTemasPorMaterial(
//            @WebParam(name = "idMaterial") Integer idMaterial
//    ) {
//        try {
//            return materialTemaBO.listarTemasPorMaterial(idMaterial);
//        } catch (BusinessException e) {
//            throw new WebServiceException("Error al listar creadores por material: " + e.getMessage());
//        }
//    }
    @WebMethod(operationName = "modificarMaterial")
    public int modificarMaterial(
            @WebParam(name = "material") MaterialesDTO material
    ) {
        try {
            return materialBO.modificar(material);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al modificar material: " + e.getMessage());
        } catch (Exception e) {
            throw new WebServiceException("Error inesperado al modificar material: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "eliminarMaterial")
    public int eliminarMaterial(@WebParam(name = "idMaterial") Integer idMaterial) {
        try {
            return materialBO.eliminar(idMaterial);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al eliminar material: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "obtenerPorId")
    public MaterialesDTO obtenerPorId(@WebParam(name = "idMaterial") Integer idMaterial) {
        try {
            return materialBO.obtenerPorId(idMaterial); // Llamada al método en el MaterialBO
        } catch (BusinessException e) {
            throw new WebServiceException("Error al obtener el material: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarEjemplaresMaterial")
    public List<EjemplaresDTO> listarEjemplaresMaterial(
            @WebParam(name = "idMaterial") Integer idMaterial,
            @WebParam(name = "limite") int limite,
            @WebParam(name = "pagina") int pagina) {
        try {
            return materialBO.listarEjemplaresMaterial(idMaterial, limite, pagina);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar ejemplares: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarMaterialPorTitulo")
    public List<MaterialesDTO> listarMaterialPorTitulo(
            @WebParam(name = "caracteres") String car,
            @WebParam(name = "limite") int limite,
            @WebParam(name = "pagina") int pagina) {
        try {
            return materialBO.listarPorCaracteres(car, limite, pagina);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar por caracteres: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarPorAutor")
    public List<MaterialesDTO> listarPorAutor(
            @WebParam(name = "caracteres") String car,
            @WebParam(name = "limite") int limite,
            @WebParam(name = "pagina") int pagina) {
        try {
            return materialBO.listarPorCaracter_Creador(car, limite, pagina);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar por creador: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarMaterialesPorSede")
    public List<MaterialesDTO> listarMaterialesPorSede(
            @WebParam(name = "idSede") Integer idSede,
            @WebParam(name = "limite") int limite,
            @WebParam(name = "pagina") int pagina) {
        try {
            return materialBO.listarMaterialesPorSede(idSede, limite, pagina);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar materiales por sede: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarMaterialesVigentesPorSede")
    public List<MaterialesDTO> listarMaterialesVigentesPorSede(
            @WebParam(name = "idSede") Integer idSede,
            @WebParam(name = "limite") int limite,
            @WebParam(name = "pagina") int pagina) {
        try {
            return materialBO.listarMaterialesVigentesPorSede(idSede, limite, pagina);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar materiales vigentes por sede: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarMasSolicitados")
    public List<MaterialesDTO> listarMasSolicitados(
            @WebParam(name = "limite") int limite,
            @WebParam(name = "pagina") int pagina
    ) {
        try {
            return materialBO.listarMasSolicitados(limite, pagina);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar materiales más solicitados: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarMasRecientes")
    public List<MaterialesDTO> listarMasRecientes(
            @WebParam(name = "limite") int limite,
            @WebParam(name = "pagina") int pagina
    ) {
        try {
            return materialBO.listarMasRecientes(limite, pagina);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar materiales más recientes: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarMaterialesPorSedeYFiltro")
    public List<MaterialesDTO> listarMaterialesPorSedeYFiltro(
            @WebParam(name = "idSede") Integer idSede,
            @WebParam(name = "filtro") String filtro,
            @WebParam(name = "porTitulo") boolean porTitulo,
            @WebParam(name = "limite") int limite,
            @WebParam(name = "pagina") int pagina) {
        try {
            return materialBO.listarPorSedeYFiltro(idSede, filtro, porTitulo, limite, pagina);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar materiales por sede y filtro: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarMaterialesPaginado")
    public List<MaterialesDTO> listarMaterialesPaginado(
            @WebParam(name = "limite") int limite,
            @WebParam(name = "pagina") int pagina
    ) {
        try {
            return materialBO.listarTodosPaginado(limite, pagina);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar materiales paginados: " + e.getMessage());
        } catch (Exception e) {
            throw new WebServiceException("Error inesperado al listar materiales paginados: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "contarMateriales")
    public int contarMateriales() {
        try {
            return materialBO.contarTodos();
        } catch (Exception e) {
            throw new WebServiceException("Error al contar todos los materiales: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarCreadoresPorMaterial")
    public List<CreadoresDTO> listarCreadoresPorMaterial(
            @WebParam(name = "idMaterial") Integer idMaterial
    ) {
        try {
            return materialBO.listarCreadoresPorMaterial(idMaterial);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar creadores por material: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarTemasPorMaterial")
    public List<TemasDTO> listarTemasPorMaterial(
            @WebParam(name = "idMaterial") Integer idMaterial
    ) {
        try {
            return materialBO.listarTemasPorMaterial(idMaterial);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar temas por material: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarTodosPaginadoBasico")
    public List<MaterialesDTO> listarTodosPaginadoBasico(
            @WebParam(name = "limite") int limite,
            @WebParam(name = "pagina") int pagina
    ) throws BusinessException {
        return materialBO.listarPaginadoPorNivel(Nivel.BASICO, limite, pagina);
    }

    @WebMethod(operationName = "listarTodosPaginadoIntermedio")
    public List<MaterialesDTO> listarTodosPaginadoIntermedio(
            @WebParam(name = "limite") int limite,
            @WebParam(name = "pagina") int pagina
    ) throws BusinessException {
        return materialBO.listarPaginadoPorNivel(Nivel.INTERMEDIO, limite, pagina);
    }

    @WebMethod(operationName = "listarTodosPaginadoAvanzado")
    public List<MaterialesDTO> listarTodosPaginadoAvanzado(
            @WebParam(name = "limite") int limite,
            @WebParam(name = "pagina") int pagina
    ) throws BusinessException {
        return materialBO.listarPaginadoPorNivel(Nivel.AVANZADO, limite, pagina);
    }

    @WebMethod(operationName = "listarMaterialesPaginadoPorTema")
    public List<MaterialesDTO> listarMaterialesPaginadoPorTema(
            @WebParam(name = "descripcionTema") String descripcionTema,
            @WebParam(name = "limite") int limite,
            @WebParam(name = "pagina") int pagina
    ) throws BusinessException {
        return new MaterialBO().listarPaginadoPorTema(descripcionTema, limite, pagina);
    }

    @WebMethod(operationName = "listarMaterialesPaginadoPorEditorial")
    public List<MaterialesDTO> listarPaginadoPorEditorial(@WebParam(name = "nombreEditorial") String nombreEditorial, @WebParam(name = "limite") int limite, @WebParam(name = "pagina") int pagina) throws BusinessException {
        return materialBO.listarPaginadoPorEditorial(nombreEditorial, limite, pagina);
    }

    @WebMethod
    public String obtenerNombreCreadorRandomPorMaterial(
            @WebParam(name = "idMaterial") Integer idMaterial) throws BusinessException {
        return this.materialBO.obtenerNombreCreadorRandomPorMaterial(idMaterial);
    }

    @WebMethod
    public int contarStockFisicoPorMaterial(@WebParam(name = "idMaterial") int idMaterial) throws BusinessException {
        return materialBO.contarStockFisicoPorMaterial(idMaterial);
    }

    @WebMethod
    public int contarDisponiblesFisicosPorMaterial(@WebParam(name = "idMaterial") int idMaterial) throws BusinessException {
        return materialBO.contarDisponiblesFisicosPorMaterial(idMaterial);
    }

    @WebMethod
    public int contarPrestadosFisicosPorMaterial(@WebParam(name = "idMaterial") int idMaterial) throws BusinessException {
        return materialBO.contarPrestadosFisicosPorMaterial(idMaterial);
    }

    @WebMethod(operationName = "listarMaterialesPorTituloParcialPaginado")
    public List<MaterialesDTO> listarMaterialesPorTituloParcialPaginado(
            @WebParam(name = "textoBusqueda") String textoBusqueda,
            @WebParam(name = "sedeId") int sedeId,
            @WebParam(name = "limite") int limite,
            @WebParam(name = "pagina") int pagina) throws BusinessException {
        //Integer sedeIdToPass = (sedeId == -1) ? null : sedeId;
        return materialBO.listarMaterialesPorTituloParcialPaginado(textoBusqueda, sedeId, limite, pagina);
    }

    @WebMethod(operationName = "contarMaterialesPorSede")
    public int contarMaterialesPorSede(@WebParam(name = "idSede") int idSede) throws BusinessException {
        return materialBO.contarMaterialesPorSede(idSede);
    }

}
