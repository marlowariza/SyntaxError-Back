package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.EditorialDTO;
import com.syntaxerror.biblioteca.persistance.dao.EditorialDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.EditorialDAOImpl;
import java.util.ArrayList;

public class EditorialBO {

    private final EditorialDAO editorialDAO;

    public EditorialBO() {
        this.editorialDAO = new EditorialDAOImpl();
    }

    public int insertar(String nombre, String sitioWeb, String pais) throws BusinessException {
        BusinessValidator.validarTexto(nombre, "nombre");
        EditorialDTO editorial = new EditorialDTO();
        editorial.setNombre(nombre);
        editorial.setSitioWeb(sitioWeb);
        editorial.setPais(pais);

        return this.editorialDAO.insertar(editorial);
    }

    public int modificar(Integer idEditorial, String nombre, String sitioWeb, String pais) throws BusinessException {
        BusinessValidator.validarId(idEditorial, "editorial");
        BusinessValidator.validarTexto(nombre, "nombre");
        EditorialDTO editorial = new EditorialDTO();
        editorial.setIdEditorial(idEditorial);
        editorial.setNombre(nombre);
        editorial.setSitioWeb(sitioWeb);
        editorial.setPais(pais);

        return this.editorialDAO.modificar(editorial);
    }

    public int eliminar(Integer idEditorial) throws BusinessException {
        BusinessValidator.validarId(idEditorial, "editorial");
        EditorialDTO editorial = new EditorialDTO();
        editorial.setIdEditorial(idEditorial);
        return this.editorialDAO.eliminar(editorial);
    }

    public EditorialDTO obtenerPorId(Integer idEditorial) throws BusinessException {
        BusinessValidator.validarId(idEditorial, "editorial");
        return this.editorialDAO.obtenerPorId(idEditorial);
    }

    public ArrayList<EditorialDTO> listarTodos() {
        return this.editorialDAO.listarTodos();
    }
}
