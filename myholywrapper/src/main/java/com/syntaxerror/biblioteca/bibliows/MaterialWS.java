package com.syntaxerror.biblioteca.bibliows;

import com.syntaxerror.biblioteca.business.MaterialBO;
import com.syntaxerror.biblioteca.business.MaterialCreadorBO;
import com.syntaxerror.biblioteca.business.MaterialTemaBO;
import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.CreadorDTO;
import com.syntaxerror.biblioteca.model.EjemplarDTO;
import com.syntaxerror.biblioteca.model.MaterialDTO;
import com.syntaxerror.biblioteca.model.enums.NivelDeIngles;
import com.syntaxerror.biblioteca.persistance.dao.EjemplarDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.EjemplarDAOImpl;
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
    private final MaterialCreadorBO materialCreadorBO;
    private final MaterialTemaBO materialTemaBO;

    public MaterialWS() {
        materialBO = new MaterialBO();
        materialCreadorBO = new MaterialCreadorBO();
        materialTemaBO =new MaterialTemaBO();
    }
    
    @WebMethod(operationName = "insertarMaterial")
    public int insertarMaterial(
        @WebParam(name = "titulo") String titulo,
        @WebParam(name = "edicion") String edicion,
        @WebParam(name = "nivel") String nivel,
        @WebParam(name = "anioPublicacion") Integer anioPublicacion,
        @WebParam(name = "portada") String portada,
        @WebParam(name = "idEditorial") Integer idEditorial
    ) {
        try {
            NivelDeIngles nivelDeIngles = NivelDeIngles.valueOf(nivel.toUpperCase());
            return materialBO.insertar(titulo,edicion,nivelDeIngles,anioPublicacion,portada,idEditorial);

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
    public ArrayList<MaterialDTO> listarTodos() {
        try {
            return materialBO.listarTodos();
        } catch (Exception e) {
            throw new WebServiceException("Error al listar" + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarCreadoresPorMaterial")
    public ArrayList<CreadorDTO> listarCreadoresPorMaterial(
            @WebParam(name = "idMaterial") Integer idMaterial
    ) {
        try {
            return materialCreadorBO.listarCreadoresPorMaterial(idMaterial);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar creadores por material: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "modificarMaterial")
    public int modificarMaterial(
            @WebParam(name = "idMaterial") Integer idMaterial,
            @WebParam(name = "titulo") String titulo,
            @WebParam(name = "edicion") String edicion,
            @WebParam(name = "nivel") NivelDeIngles nivel,
            @WebParam(name = "anioPublicacion") Integer anioPublicacion,
            @WebParam(name = "portada") String portada,
            @WebParam(name = "idEditorial") Integer idEditorial
    ) {
        try {
            return materialBO.modificar(idMaterial, titulo, edicion, nivel, anioPublicacion, portada, idEditorial);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al modificar material: " + e.getMessage());
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
    public MaterialDTO obtenerPorId(@WebParam(name = "idMaterial") Integer idMaterial) {
        try {
            return materialBO.obtenerPorId(idMaterial); // Llamada al método en el MaterialBO
        } catch (BusinessException e) {
            throw new WebServiceException("Error al obtener el material: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarEjemplaresMaterial")
    public ArrayList<EjemplarDTO> listarEjemplaresMaterial(
            @WebParam(name = "idMaterial") Integer idMaterial
    ) {
        try {
            return materialBO.listarEjemplaresMaterial(idMaterial);
        } catch (Exception e) {
            throw new WebServiceException("Error al modificar material: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarMaterialPorCaracteres")
    public ArrayList<MaterialDTO> listarMaterialPorCaracteres(@WebParam(name = "caracteres") String car) {
        try {
            return materialBO.listarPorCaracteres(car);
        } catch (Exception e) {
            throw new WebServiceException("Error al listar Material por caracteres" + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarPorCaracter_Creador")
    public ArrayList<MaterialDTO> listarPorCaracter_Creador(@WebParam(name = "caracteres") String car) {
        try {
            return materialBO.listarPorCaracter_Creador(car);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar Material por car_creadores" + e.getMessage());
        }
    }

    @WebMethod(operationName = "listarMaterialesPorSede")
    public ArrayList<MaterialDTO> listarMaterialesPorSede(
            @WebParam(name = "idSede") Integer idSede
    ) {
        try {
            return materialBO.listarMaterialesPorSede(idSede);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al listar creadores por material: " + e.getMessage());
        }
    }
  
    @WebMethod(operationName = "asociarMaterialTema")
    public Integer asociarMaterialPorTema(
        @WebParam(name = "idMaterial") Integer idMaterial,
        @WebParam(name = "idTema") Integer idTema
    ) throws BusinessException {
        try {
            if (materialTemaBO.existeRelacion(idMaterial, idTema)) {
                throw new BusinessException("La relación entre el material y el tema ya existe.");
            }
            return materialTemaBO.asociar(idMaterial, idTema);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al asociar material con tema: " + e.getMessage());
        } catch (Exception e) {
            throw new WebServiceException("Error inesperado al asociar material con tema: " + e.getMessage());
        }
    }
    @WebMethod(operationName = "asociarMaterialCreador")
    public Integer asociarMaterialPorCreador(
            @WebParam(name = "idMaterial") Integer idMaterial,
            @WebParam(name = "idCreador") Integer idCreador
    ) throws BusinessException {
        try {
            if (materialCreadorBO.existeRelacion(idMaterial, idCreador)) {
                throw new BusinessException("La relación entre el material y el creador ya existe.");
            }
            return materialCreadorBO.asociar(idMaterial, idCreador);
        } catch (BusinessException e) {
            throw new WebServiceException("Error al asociar material con creador: " + e.getMessage());
        } catch (Exception e) {
            throw new WebServiceException("Error inesperado al asociar material con creador: " + e.getMessage());
        }
    }
}
