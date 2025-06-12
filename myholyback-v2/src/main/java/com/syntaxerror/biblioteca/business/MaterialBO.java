package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.EditorialesDTO;
import com.syntaxerror.biblioteca.model.EjemplaresDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.NivelesInglesDTO;

import com.syntaxerror.biblioteca.persistance.dao.impl.EditorialDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.MaterialDAOImpl;
import java.util.List;
import com.syntaxerror.biblioteca.persistance.dao.impl.EjemplarDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.NivelInglesDAOImpl;
import java.util.ArrayList;
import com.syntaxerror.biblioteca.persistance.dao.EditorialDAO;
import com.syntaxerror.biblioteca.persistance.dao.EjemplarDAO;
import com.syntaxerror.biblioteca.persistance.dao.MaterialDAO;
import com.syntaxerror.biblioteca.persistance.dao.NivelInglesDAO;

public class MaterialBO {

    private final MaterialDAO materialDAO;
    private final EditorialDAO editorialDAO;
    private final NivelInglesDAO nivelDAO;
    private final EjemplarDAO ejemplarDAO;

    public MaterialBO() {
        this.materialDAO = new MaterialDAOImpl();
        this.editorialDAO = new EditorialDAOImpl();
        this.nivelDAO = new NivelInglesDAOImpl();
        this.ejemplarDAO = new EjemplarDAOImpl();
    }

    public int insertar(String titulo, String edicion,
            Integer anioPublicacion, String portada, Boolean vigente,
            Integer idNivel, Integer idEditorial) throws BusinessException {
        BusinessValidator.validarTexto(titulo, "título");
        MaterialesDTO material = new MaterialesDTO();
        material.setTitulo(titulo);
        material.setEdicion(edicion);
        material.setAnioPublicacion(anioPublicacion);
        material.setPortada(portada);
        material.setVigente(vigente);

        if (idNivel != null) {
            NivelesInglesDTO nivel = nivelDAO.obtenerPorId(idNivel);
            if (nivel == null) {
                throw new BusinessException("El nivel con ID " + idNivel + " no existe.");
            }
            material.setNivel(nivel);
        }
        if (idEditorial != null) {
            EditorialesDTO editorial = editorialDAO.obtenerPorId(idEditorial);
            if (editorial == null) {
                throw new BusinessException("La editorial con ID " + idEditorial + " no existe.");
            }
            material.setEditorial(editorial);
        }

        return this.materialDAO.insertar(material);
    }

    public int modificar(Integer idMaterial, String titulo, String edicion,
            Integer anioPublicacion, String portada, Boolean vigente,
            Integer idNivel, Integer idEditorial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        BusinessValidator.validarTexto(titulo, "título");
        MaterialesDTO material = new MaterialesDTO();
        material.setIdMaterial(idMaterial);
        material.setTitulo(titulo);
        material.setEdicion(edicion);
        material.setAnioPublicacion(anioPublicacion);
        material.setPortada(portada);
        material.setVigente(vigente);

        if (idNivel != null) {
            NivelesInglesDTO nivel = nivelDAO.obtenerPorId(idNivel);
            if (nivel == null) {
                throw new BusinessException("El nivel con ID " + idNivel + " no existe.");
            }
            material.setNivel(nivel);
        }
        if (idEditorial != null) {
            EditorialesDTO editorial = editorialDAO.obtenerPorId(idEditorial);
            if (editorial == null) {
                throw new BusinessException("La editorial con ID " + idEditorial + " no existe.");
            }
            material.setEditorial(editorial);
        }

        return this.materialDAO.modificar(material);
    }

    public int eliminar(Integer idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        MaterialesDTO material = new MaterialesDTO();
        material.setIdMaterial(idMaterial);
        return this.materialDAO.eliminar(material);
    }

    public MaterialesDTO obtenerPorId(Integer idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return this.materialDAO.obtenerPorId(idMaterial);
    }

    public List<MaterialesDTO> listarTodos() {
        return this.materialDAO.listarTodos();

    }

    //LISTA EJEMPLARES POR ID MATERIAL (SEA FISICO O DIGITAL / DISPONIBLE O NO)
    public ArrayList<EjemplaresDTO> listarEjemplaresMaterial(Integer idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return ejemplarDAO.listarPorIdMaterial(idMaterial);
    }

    //LISTA MATERIALES POR CARACTERES EN EL BUSCADOR ( DISPONIBLE O NO)
    public ArrayList<MaterialesDTO> listarPorCaracteres(String car) throws BusinessException {
        if (car == null || car.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return materialDAO.listarPorTituloConteniendo(car);
    }

    // LISTA MATERIALES VIGENTES POR CARACTERES EN EL BUSCADOR
    public ArrayList<MaterialesDTO> listarVigentesPorCaracteres(String car) throws BusinessException {
        if (car == null || car.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return materialDAO.listarVigentesPorTituloConteniendo(car);
    }

    //LISTA MATERIALES POR ID SEDE ( VIGENTE O NO)
    public ArrayList<MaterialesDTO> listarMaterialesPorSede(Integer idSede) throws BusinessException {
        BusinessValidator.validarId(idSede, "sede");
        return materialDAO.listarPorSede(idSede);
    }

    //LISTA MATERIALES VIGENTES POR SEDE
    public List<MaterialesDTO> listarMaterialesVigentesPorSede(Integer idSede) throws BusinessException {
        BusinessValidator.validarId(idSede, "sede");
        return materialDAO.listarVigentesPorSede(idSede);
    }

    //INDICA SI EXISTE EJEMPLAR DIGITAL (AHORA QUE ME DOY CUENTA SOLO ES NECESARIO QYE HAYA UNO NO?)
    public boolean existeEjemplarDigitalPorMaterial(Integer idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return ejemplarDAO.existeEjemplarDigitalPorMaterial(idMaterial);
    }

    //BUSCA MATERIALES VIGENTES POR caracteres SOLO AUTORES
    public List<MaterialesDTO> listarPorCaracter_Creador(String filtro) throws BusinessException {
        if (filtro == null || filtro.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return new MaterialDAOImpl().listarMaterialesVigentesPorCreadorFiltro(filtro);
    }

}
