/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.CreadorDTO;
import com.syntaxerror.biblioteca.model.enums.TipoAutor;
import com.syntaxerror.biblioteca.persistance.dao.CreadorDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.CreadorDAOImpl;
import java.util.ArrayList;

/**
 *
 * @author catolica
 */
public class CreadorBO {

    private final CreadorDAO creadorDAO;

    public CreadorBO() {
        this.creadorDAO = new CreadorDAOImpl();
    }

    public int insertar(String nombre, String paterno, String materno,
            String seudonimo, TipoAutor tipo, String nacionalidad, Boolean activo) throws BusinessException {
        validarDatos(nombre,tipo,activo);
        CreadorDTO creador = new CreadorDTO();

        creador.setNombre(nombre);
        creador.setPaterno(paterno);
        creador.setMaterno(materno);
        creador.setSeudonimo(seudonimo);
        creador.setTipo(tipo);
        creador.setNacionalidad(nacionalidad);
        creador.setActivo(activo);

        return this.creadorDAO.insertar(creador);
    }

    public int modificar(Integer idCreador, String nombre, String paterno, String materno,
            String seudonimo, TipoAutor tipo, String nacionalidad, Boolean activo) throws BusinessException {
        BusinessValidator.validarId(idCreador, "creador");
        validarDatos(nombre, tipo, activo);
        CreadorDTO creador = new CreadorDTO();
        creador.setIdAutor(idCreador);
        creador.setNombre(nombre);
        creador.setPaterno(paterno);
        creador.setMaterno(materno);
        creador.setSeudonimo(seudonimo);
        creador.setTipo(tipo);
        creador.setNacionalidad(nacionalidad);
        creador.setActivo(activo);

        return this.creadorDAO.modificar(creador);
    }

    public int eliminar(Integer idCreador) throws BusinessException {
        BusinessValidator.validarId(idCreador, "creador");
        CreadorDTO creador = new CreadorDTO();
        creador.setIdAutor(idCreador);
        return this.creadorDAO.eliminar(creador);
    }

    public CreadorDTO obtenerPorId(Integer idCreador) throws BusinessException {
        BusinessValidator.validarId(idCreador, "creador");
        return this.creadorDAO.obtenerPorId(idCreador);
    }

    public ArrayList<CreadorDTO> listarTodos() {
        return this.creadorDAO.listarTodos();
    }

    private void validarDatos(String nombre, TipoAutor tipo, Boolean activo) throws BusinessException {
        BusinessValidator.validarTexto(nombre, "nombre del creador");

        if (tipo == null) {
            throw new BusinessException("Debe especificarse un tipo de creador.");
        }
        if (activo == null) {
            throw new BusinessException("Debe indicarse si el creador está activo.");
        }
    }
}
