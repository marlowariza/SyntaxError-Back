package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.EditorialDTO;
import com.syntaxerror.biblioteca.model.EjemplarDTO;
import com.syntaxerror.biblioteca.model.MaterialDTO;
import com.syntaxerror.biblioteca.model.enums.NivelDeIngles;
import com.syntaxerror.biblioteca.persistance.dao.EditorialDAO;
import com.syntaxerror.biblioteca.persistance.dao.EjemplarDAO;
import com.syntaxerror.biblioteca.persistance.dao.MaterialDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.EditorialDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.EjemplarDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.MaterialDAOImpl;
import java.util.ArrayList;
import java.util.List;

public class MaterialBO {

    private final MaterialDAO materialDAO;
    private final EditorialDAO editorialDAO;
    private final EjemplarDAO ejemplarDAO;

    public MaterialBO() {
        this.materialDAO = new MaterialDAOImpl();
        this.editorialDAO = new EditorialDAOImpl();
        this.ejemplarDAO = new EjemplarDAOImpl();
    }

    public int insertar(String titulo, String edicion, NivelDeIngles nivel, Integer anioPublicacion, String portada, Integer idEditorial) throws BusinessException {
        BusinessValidator.validarTexto(titulo, "título");
        MaterialDTO material = new MaterialDTO();
        material.setTitulo(titulo);
        material.setEdicion(edicion);
        material.setNivel(nivel);
        material.setAnioPublicacion(anioPublicacion);
        material.setPortada(portada);

        if (idEditorial != null) {
            EditorialDTO editorial = editorialDAO.obtenerPorId(idEditorial);
            if (editorial == null) {
                throw new BusinessException("La editorial con ID " + idEditorial + " no existe.");
            }
            material.setEditorial(editorial);
        }

        return this.materialDAO.insertar(material);
    }

    public int modificar(Integer idMaterial, String titulo, String edicion, NivelDeIngles nivel, Integer anioPublicacion, String portada,
            Integer idEditorial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        BusinessValidator.validarTexto(titulo, "título");
        MaterialDTO material = new MaterialDTO();
        material.setIdMaterial(idMaterial);
        material.setTitulo(titulo);
        material.setEdicion(edicion);
        material.setNivel(nivel);
        material.setAnioPublicacion(anioPublicacion);
        material.setPortada(portada);

        if (idEditorial != null) {
            EditorialDTO editorial = editorialDAO.obtenerPorId(idEditorial);
            if (editorial == null) {
                throw new BusinessException("La editorial con ID " + idEditorial + " no existe.");
            }
            material.setEditorial(editorial);
        }

        return this.materialDAO.modificar(material);
    }

    public int eliminar(Integer idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        MaterialDTO material = new MaterialDTO();
        material.setIdMaterial(idMaterial);
        return this.materialDAO.eliminar(material);
    }

    public MaterialDTO obtenerPorId(Integer idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return this.materialDAO.obtenerPorId(idMaterial);
    }

    public ArrayList<MaterialDTO> listarTodos() {
        return this.materialDAO.listarTodos();
    }
    public ArrayList<EjemplarDTO> listarEjemplaresMaterial(Integer idMaterial){
        ArrayList<EjemplarDTO> ejemplares= ejemplarDAO.listarTodos();
        ArrayList<EjemplarDTO> ejemplaresFiltrados = new ArrayList<>();
        for (EjemplarDTO ej : ejemplares) {
            if (ej.getMaterial().getIdMaterial().equals(idMaterial)) {
                ejemplaresFiltrados.add(ej);
            }
        }
        return ejemplaresFiltrados;
    }
}
