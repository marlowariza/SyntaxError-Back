/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.CreadoresDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.enums.TipoCreador;
import com.syntaxerror.biblioteca.persistance.dao.impl.CreadoresDAOImpl;
import java.util.ArrayList;
import com.syntaxerror.biblioteca.persistance.dao.CreadoresDAO;
import java.util.List;

/**
 *
 * @author catolica
 */
public class CreadorBO {

    private final CreadoresDAO creadoresDAO;

    public CreadorBO() {
        this.creadoresDAO = new CreadoresDAOImpl();
    }
    
    
    public int insertar(String nombre, String paterno, String materno,
            String seudonimo, TipoCreador tipo, String nacionalidad,
            Boolean activo,List<MaterialesDTO> materiales) throws BusinessException {
        validarDatos(nombre,tipo,activo);
        
        CreadoresDTO creador = new CreadoresDTO();
        
        creador.setNombre(nombre);
        creador.setPaterno(paterno);
        creador.setMaterno(materno);
        creador.setSeudonimo(seudonimo);
        creador.setTipo(tipo);
        creador.setNacionalidad(nacionalidad);
        creador.setActivo(activo);
        creador.setMateriales(materiales);
        return this.creadoresDAO.insertar(creador);
    }

    public int modificar(Integer idCreador, String nombre, String paterno, String materno,
            String seudonimo, TipoCreador tipo, String nacionalidad,
            Boolean activo,List<MaterialesDTO> materiales) throws BusinessException {
        BusinessValidator.validarId(idCreador, "creador");
        validarDatos(nombre, tipo, activo);
        
        CreadoresDTO creador = new CreadoresDTO();
        
        creador.setIdCreador(idCreador);
        creador.setNombre(nombre);
        creador.setPaterno(paterno);
        creador.setMaterno(materno);
        creador.setSeudonimo(seudonimo);
        creador.setTipo(tipo);
        creador.setNacionalidad(nacionalidad);
        creador.setActivo(activo);
        creador.setMateriales(materiales);

        return this.creadoresDAO.modificar(creador);
    }

    public int eliminar(Integer idCreador) throws BusinessException {
        BusinessValidator.validarId(idCreador, "creador");
        CreadoresDTO creador = new CreadoresDTO();
        creador.setIdCreador(idCreador);
        return this.creadoresDAO.eliminar(creador);
    }

    public CreadoresDTO obtenerPorId(Integer idCreador) throws BusinessException {
        BusinessValidator.validarId(idCreador, "creador");
        return this.creadoresDAO.obtenerPorId(idCreador);
    }

    public ArrayList<CreadoresDTO> listarTodos() {
        return this.creadoresDAO.listarTodos();
    }

    private void validarDatos(String nombre, TipoCreador tipo, Boolean activo) throws BusinessException {
        BusinessValidator.validarTexto(nombre, "nombre del creador");

        if (tipo == null) {
            throw new BusinessException("Debe especificarse un tipo de creador.");
        }
        if (activo == null) {
            throw new BusinessException("Debe indicarse si el creador está activo.");
        }
    }
}
