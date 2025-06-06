package com.syntaxerror.biblioteca.business;

import java.util.List;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.CreadoresDTO;
import com.syntaxerror.biblioteca.model.EditorialesDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.NivelesInglesDTO;
import com.syntaxerror.biblioteca.model.TemasDTO;
import com.syntaxerror.biblioteca.persistance.dao.EditorialesDAO;
import com.syntaxerror.biblioteca.persistance.dao.MaterialesDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.EditorialesDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.MaterialesDAOImpl;

public class MaterialBO {

    private final MaterialesDAO materialDAO;
    private final EditorialesDAO editorialDAO;

    public MaterialBO() {
        this.materialDAO = new MaterialesDAOImpl();
        this.editorialDAO = new EditorialesDAOImpl();
    }

    public int insertar(String titulo, String edicion, NivelesInglesDTO nivel, Integer anioPublicacion, 
            String portada, Boolean vigente, Integer idEditorial, List<CreadoresDTO> creadores, 
            List<TemasDTO> temas) throws BusinessException {
        BusinessValidator.validarTexto(titulo, "título");
        MaterialesDTO material = new MaterialesDTO();
        material.setTitulo(titulo);
        material.setEdicion(edicion);
        material.setNivel(nivel);
        material.setAnioPublicacion(anioPublicacion);
        material.setPortada(portada);
        material.setVigente(vigente != null ? vigente : true);
        material.setCreadores(creadores);
        material.setTemas(temas);

        if (idEditorial != null) {
            EditorialesDTO editorial = editorialDAO.obtenerPorId(idEditorial);
            if (editorial == null) {
                throw new BusinessException("La editorial con ID " + idEditorial + " no existe.");
            }
            material.setEditorial(editorial);
        }

        return this.materialDAO.insertar(material);
    }

    public int modificar(Integer idMaterial, String titulo, String edicion, NivelesInglesDTO nivel, 
            Integer anioPublicacion, String portada, Boolean vigente, Integer idEditorial, 
            List<CreadoresDTO> creadores, List<TemasDTO> temas) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        BusinessValidator.validarTexto(titulo, "título");
        MaterialesDTO material = new MaterialesDTO();
        material.setIdMaterial(idMaterial);
        material.setTitulo(titulo);
        material.setEdicion(edicion);
        material.setNivel(nivel);
        material.setAnioPublicacion(anioPublicacion);
        material.setPortada(portada);
        material.setVigente(vigente != null ? vigente : true);
        material.setCreadores(creadores);
        material.setTemas(temas);

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
}
