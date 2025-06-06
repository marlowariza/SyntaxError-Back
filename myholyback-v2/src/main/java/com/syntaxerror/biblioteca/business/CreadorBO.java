
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
import java.util.Objects;

/**
 *
 * @author catolica
 */
public class CreadorBO {

    private final CreadoresDAO creadorDAO;

    public CreadorBO() {
        this.creadorDAO = new CreadoresDAOImpl();
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

    public ArrayList<CreadoresDTO> listarTodos() {
        return this.creadorDAO.listarTodos();
    }
    
    public void AgregarMateriales(CreadoresDTO creador, MaterialesDTO material) throws BusinessException{
        for (MaterialesDTO temp:creador.getMateriales()) {
            if (Objects.equals(temp.getIdMaterial(), material.getIdMaterial())){
                throw new BusinessException(material.getTitulo()+" ya ha sido agregado.");
            }
        }
        creador.addMaterial(material);
        creadorDAO.modificar(creador);
    }
    
    public void AgregarMateriales(CreadoresDTO creador, List<MaterialesDTO> material) throws BusinessException{
        for (MaterialesDTO temp:creador.getMateriales()) {
            AgregarMateriales(creador, temp);
        }
    }
    
    public void QuitarMateriales(CreadoresDTO creador, MaterialesDTO material) throws BusinessException{
        for (MaterialesDTO temp:creador.getMateriales()) {
            if (Objects.equals(temp.getIdMaterial(), material.getIdMaterial())){
                creador.removeMaterial(material);
                creadorDAO.modificar(creador);
                return;
            }
        }
        throw new BusinessException(material.getTitulo()+" no ha sido agregado.");
    }
    
    public void QuitarMateriales(CreadoresDTO creador, List<MaterialesDTO> material) throws BusinessException{
        for (MaterialesDTO temp:creador.getMateriales()) {
            QuitarMateriales(creador, temp);
        }
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
