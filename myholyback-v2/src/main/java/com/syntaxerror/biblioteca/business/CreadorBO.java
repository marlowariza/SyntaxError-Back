
package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.CreadoresDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.enums.TipoCreador;
import com.syntaxerror.biblioteca.persistance.dao.impl.CreadoresDAOImpl;
import java.util.ArrayList;
import com.syntaxerror.biblioteca.persistance.dao.CreadoresDAO;
import com.syntaxerror.biblioteca.persistance.dao.MaterialesDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.MaterialesDAOImpl;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author catolica
 */
public class CreadorBO {

    private final CreadoresDAO creadorDAO;
    private final MaterialesDAO materialDAO;

    public CreadorBO() {
        this.creadorDAO = new CreadoresDAOImpl();
        this.materialDAO = new MaterialesDAOImpl();
    }

    public int insertar(String nombre, String paterno, String materno,
            String seudonimo, TipoCreador tipo, String nacionalidad, Boolean activo) throws BusinessException {
        validarDatos(nombre,tipo,activo);
        CreadoresDTO creador = new CreadoresDTO();

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
            String seudonimo, TipoCreador tipo, String nacionalidad, Boolean activo) throws BusinessException {
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

        return this.creadorDAO.modificar(creador);
    }

    public int eliminar(Integer idCreador) throws BusinessException {
        BusinessValidator.validarId(idCreador, "creador");
        CreadoresDTO creador = new CreadoresDTO();
        creador.setIdCreador(idCreador);
        return this.creadorDAO.eliminar(creador);
    }

    public CreadoresDTO obtenerPorId(Integer idCreador) throws BusinessException {
        BusinessValidator.validarId(idCreador, "creador");
        return this.creadorDAO.obtenerPorId(idCreador);
    }
    
    public void AgregarMaterialACreador(Integer idCreador, Integer idMaterial) throws BusinessException{
        
        CreadoresDTO creador = creadorDAO.obtenerPorId(idCreador);
        
        for (MaterialesDTO temp:creador.getMateriales()) {
            if (Objects.equals(temp.getIdMaterial(), idMaterial)){
                throw new BusinessException(temp.getTitulo()+" ya ha sido previamente agregado.");
            }
        }
        
        MaterialesDTO materialNuevo = materialDAO.obtenerPorId(idMaterial);
        creador.addMaterial(materialNuevo);
        creadorDAO.modificar(creador);
    }
    
    
    public void QuitarMateriales(Integer idCreador, Integer idMaterial) throws BusinessException{
        
        CreadoresDTO creador = creadorDAO.obtenerPorId(idCreador);
        MaterialesDTO materialAEliminar = materialDAO.obtenerPorId(idMaterial);
                        
        for (MaterialesDTO temp:creador.getMateriales()) {
            if (Objects.equals(temp.getIdMaterial(), idMaterial)){
                creador.removeMaterial(materialAEliminar);
                creadorDAO.modificar(creador);
                return;
            }
        }
        throw new BusinessException(materialAEliminar.getTitulo()+" no ha sido previamente agregado.");
    }
    
    public int ContarMaterialesPorCreador(Integer creadorId){
        CreadoresDTO creador = creadorDAO.obtenerPorId(creadorId);
        return creador.getMateriales().size();
    }
    
    public ArrayList<CreadoresDTO> listarTodos() {
        return this.creadorDAO.listarTodos();
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
