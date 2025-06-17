package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.MaterialDTO;
import com.syntaxerror.biblioteca.model.TemaDTO;
import com.syntaxerror.biblioteca.persistance.dao.MaterialTemaDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.MaterialTemaDAOImpl;

import java.util.ArrayList;

public class MaterialTemaBO {

    private final MaterialTemaDAO dao;

    public MaterialTemaBO() {
        this.dao = new MaterialTemaDAOImpl();
    }

    public Integer asociar(Integer idMaterial, Integer idTema) throws BusinessException {
        validarIds(idMaterial, idTema);
        if (dao.existeRelacion(idMaterial, idTema)) {
            throw new BusinessException("La relación entre el material y el tema ya existe.");
        }
        return dao.asociar(idMaterial, idTema);
    }

    public Integer desasociar(Integer idMaterial, Integer idTema) throws BusinessException {
        validarIds(idMaterial, idTema);
        return dao.desasociar(idMaterial, idTema);
    }

    public ArrayList<TemaDTO> listarTemasPorMaterial(Integer idMaterial) throws BusinessException {
        if (idMaterial == null || idMaterial <= 0) {
            throw new BusinessException("ID de material no válido.");
        }
        return dao.listarTemasPorMaterial(idMaterial);
    }

    public ArrayList<MaterialDTO> listarMaterialesPorTema(Integer idTema) throws BusinessException {
        if (idTema == null || idTema <= 0) {
            throw new BusinessException("ID de tema no válido.");
        }
        return dao.listarMaterialesPorTema(idTema);
    }

    public boolean existeRelacion(Integer idMaterial, Integer idTema) throws BusinessException {
        validarIds(idMaterial, idTema);
        return dao.existeRelacion(idMaterial, idTema);
    }

    private void validarIds(Integer idMaterial, Integer idTema) throws BusinessException {
        if (idMaterial == null || idMaterial <= 0) {
            throw new BusinessException("ID de material no válido.");
        }
        if (idTema == null || idTema <= 0) {
            throw new BusinessException("ID de tema no válido.");
        }
    }
}
