package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.TemasDTO;
import com.syntaxerror.biblioteca.model.enums.Categoria;
import com.syntaxerror.biblioteca.persistance.dao.impl.TemaDAOImpl;
import java.util.ArrayList;
import com.syntaxerror.biblioteca.persistance.dao.TemaDAO;

public class TemaBO {

    private final TemaDAO temaDAO;

    public TemaBO() {
        this.temaDAO = new TemaDAOImpl();
    }

    public int insertar(TemasDTO tema) throws BusinessException {
        validarDatos(tema.getDescripcion(), tema.getCategoria());
        if (tema.getTemaPadre() != null && tema.getTemaPadre().getIdTema() != null) {
            BusinessValidator.validarId(tema.getTemaPadre().getIdTema(), "tema padre");
        }

        return this.temaDAO.insertar(tema);
    }
//    public int insertar(String descripcion, Categoria categoria, Integer idTemaPadre) throws BusinessException {
//        validarDatos(descripcion, categoria);
//        TemasDTO tema = new TemasDTO();
//        tema.setDescripcion(descripcion);
//        tema.setCategoria(categoria);
//
//        if (idTemaPadre != null) {
//            BusinessValidator.validarId(idTemaPadre, "tema padre");
//            TemasDTO temaPadre = new TemasDTO();
//            temaPadre.setIdTema(idTemaPadre);
//            tema.setTemaPadre(temaPadre);
//        }
//
//        return this.temaDAO.insertar(tema);
//    }

    public int modificar(TemasDTO tema) throws BusinessException {
        BusinessValidator.validarId(tema.getIdTema(), "tema");
        validarDatos(tema.getDescripcion(), tema.getCategoria());
        if (tema.getTemaPadre() != null && tema.getTemaPadre().getIdTema() != null) {
            BusinessValidator.validarId(tema.getTemaPadre().getIdTema(), "tema padre");
        }

        return this.temaDAO.modificar(tema);
    }
//    public int modificar(Integer idTema, String descripcion, Categoria categoria, Integer idTemaPadre) throws BusinessException {
//        BusinessValidator.validarId(idTema, "tema");
//        validarDatos(descripcion, categoria);
//        TemasDTO tema = new TemasDTO();
//        tema.setIdTema(idTema);
//        tema.setDescripcion(descripcion);
//        tema.setCategoria(categoria);
//
//        if (idTemaPadre != null) {
//            BusinessValidator.validarId(idTemaPadre, "tema padre");
//            TemasDTO temaPadre = new TemasDTO();
//            temaPadre.setIdTema(idTemaPadre);
//            tema.setTemaPadre(temaPadre);
//        }
//
//        return this.temaDAO.modificar(tema);
//    }

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
