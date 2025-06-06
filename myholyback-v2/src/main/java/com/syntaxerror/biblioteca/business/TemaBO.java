package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.TemasDTO;
import com.syntaxerror.biblioteca.model.enums.Categoria;
import com.syntaxerror.biblioteca.persistance.dao.impl.TemasDAOImpl;
import java.util.ArrayList;
import com.syntaxerror.biblioteca.persistance.dao.TemasDAO;

public class TemaBO {

    private final TemasDAO temaDAO;

    public TemaBO() {
        this.temaDAO = new TemasDAOImpl();
    }

    public int insertar(String descripcion, Categoria categoria, Integer idTemaPadre) throws BusinessException {
        validarDatos(descripcion, categoria);
        TemasDTO tema = new TemasDTO();
        tema.setDescripcion(descripcion);
        tema.setCategoria(categoria);

        if (idTemaPadre != null) {
            BusinessValidator.validarId(idTemaPadre, "tema padre");
            TemasDTO temaPadre = new TemasDTO();
            temaPadre.setIdTema(idTemaPadre);
            tema.setTemaPadre(temaPadre);
        }

        return this.temaDAO.insertar(tema);
    }

    public int modificar(Integer idTema, String descripcion, Categoria categoria, Integer idTemaPadre) throws BusinessException {
        BusinessValidator.validarId(idTema, "tema");
        validarDatos(descripcion, categoria);
        TemasDTO tema = new TemasDTO();
        tema.setIdTema(idTema);
        tema.setDescripcion(descripcion);
        tema.setCategoria(categoria);

        if (idTemaPadre != null) {
            BusinessValidator.validarId(idTemaPadre, "tema padre");
            TemasDTO temaPadre = new TemasDTO();
            temaPadre.setIdTema(idTemaPadre);
            tema.setTemaPadre(temaPadre);
        }

        return this.temaDAO.modificar(tema);
    }

    public int eliminar(Integer idTema) throws BusinessException {
        BusinessValidator.validarId(idTema, "tema");
        TemasDTO tema = new TemasDTO();
        tema.setIdTema(idTema);
        return this.temaDAO.eliminar(tema);
    }

    public TemasDTO obtenerPorId(Integer idTema) throws BusinessException {
        BusinessValidator.validarId(idTema, "tema");
        return this.temaDAO.obtenerPorId(idTema);
    }

    public ArrayList<TemasDTO> listarTodos() {
        return this.temaDAO.listarTodos();
    }

    private void validarDatos(String descripcion, Categoria categoria) throws BusinessException {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new BusinessException("La descripción del tema no puede estar vacía.");
        }
        if (categoria == null) {
            throw new BusinessException("Debe especificarse una categoría válida.");
        }
    }
}
