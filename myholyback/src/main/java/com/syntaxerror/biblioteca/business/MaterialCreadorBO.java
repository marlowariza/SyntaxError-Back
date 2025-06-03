package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.CreadorDTO;
import com.syntaxerror.biblioteca.model.MaterialDTO;
import com.syntaxerror.biblioteca.persistance.dao.MaterialCreadorDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.MaterialCreadorDAOImpl;

import java.util.ArrayList;

public class MaterialCreadorBO {

    private final MaterialCreadorDAO dao;

    public MaterialCreadorBO() {
        this.dao = new MaterialCreadorDAOImpl();
    }

    public Integer asociar(Integer idMaterial, Integer idCreador) throws BusinessException {
        validarIds(idMaterial, idCreador);
        if (dao.existeRelacion(idMaterial, idCreador)) {
            throw new BusinessException("La relación entre material y creador ya existe.");
        }
        return dao.asociar(idMaterial, idCreador);
    }

    public Integer desasociar(Integer idMaterial, Integer idCreador) throws BusinessException {
        validarIds(idMaterial, idCreador);
        return dao.desasociar(idMaterial, idCreador);
    }

    public ArrayList<CreadorDTO> listarCreadoresPorMaterial(Integer idMaterial) throws BusinessException {
        if (idMaterial == null || idMaterial <= 0) {
            throw new BusinessException("ID de material inválido.");
        }
        return dao.listarCreadoresPorMaterial(idMaterial);
    }

    public ArrayList<MaterialDTO> listarMaterialesPorCreador(Integer idCreador) throws BusinessException {
        if (idCreador == null || idCreador <= 0) {
            throw new BusinessException("ID de creador inválido.");
        }
        return dao.listarMaterialesPorCreador(idCreador);
    }

    public boolean existeRelacion(Integer idMaterial, Integer idCreador) throws BusinessException {
        validarIds(idMaterial, idCreador);
        return dao.existeRelacion(idMaterial, idCreador);
    }

    private void validarIds(Integer idMaterial, Integer idCreador) throws BusinessException {
        if (idMaterial == null || idMaterial <= 0) {
            throw new BusinessException("ID de material no válido.");
        }
        if (idCreador == null || idCreador <= 0) {
            throw new BusinessException("ID de creador no válido.");
        }
    }
}

