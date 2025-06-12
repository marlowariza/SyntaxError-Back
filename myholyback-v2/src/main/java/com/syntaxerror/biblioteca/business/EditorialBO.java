package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.EditorialesDTO;
import com.syntaxerror.biblioteca.persistance.dao.impl.EditorialDAOImpl;
import java.util.ArrayList;
import com.syntaxerror.biblioteca.persistance.dao.EditorialDAO;

public class EditorialBO {

    private final EditorialDAO editorialDAO;

    public EditorialBO() {
        this.editorialDAO = new EditorialDAOImpl();
    }

    public int insertar(String nombre, String sitioWeb, String pais) throws BusinessException {
        BusinessValidator.validarTexto(nombre, "nombre");
        EditorialesDTO editorial = new EditorialesDTO();
        editorial.setNombre(nombre);
        editorial.setSitioWeb(sitioWeb);
        editorial.setPais(pais);

        return this.editorialDAO.insertar(editorial);
    }

    public int modificar(Integer idEditorial, String nombre, String sitioWeb, String pais) throws BusinessException {
        BusinessValidator.validarId(idEditorial, "editorial");
        BusinessValidator.validarTexto(nombre, "nombre");
        EditorialesDTO editorial = new EditorialesDTO();
        editorial.setIdEditorial(idEditorial);
        editorial.setNombre(nombre);
        editorial.setSitioWeb(sitioWeb);
        editorial.setPais(pais);

        return this.editorialDAO.modificar(editorial);
    }

    public int eliminar(Integer idEditorial) throws BusinessException {
        BusinessValidator.validarId(idEditorial, "editorial");
        EditorialesDTO editorial = new EditorialesDTO();
        editorial.setIdEditorial(idEditorial);
        return this.editorialDAO.eliminar(editorial);
    }

    public EditorialesDTO obtenerPorId(Integer idEditorial) throws BusinessException {
        BusinessValidator.validarId(idEditorial, "editorial");
        return this.editorialDAO.obtenerPorId(idEditorial);
    }

    public ArrayList<EditorialesDTO> listarTodos() {
        return this.editorialDAO.listarTodos();
    }
}
