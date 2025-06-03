package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.model.PrestamoEjemplarDTO;
import com.syntaxerror.biblioteca.persistance.dao.PrestamoEjemplarDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.PrestamoEjemplarDAOImpl;

import java.util.ArrayList;

public class PrestamoEjemplarBO {

    private final PrestamoEjemplarDAO dao;

    public PrestamoEjemplarBO() {
        this.dao = new PrestamoEjemplarDAOImpl();
    }

    public Integer insertar(Integer idPrestamo, Integer idEjemplar, PrestamoEjemplarDTO dto) throws BusinessException {
        validarIds(idPrestamo, idEjemplar);
        if (dao.existeRelacion(idPrestamo, idEjemplar)) {
            throw new BusinessException("La relación entre préstamo y ejemplar ya existe.");
        }

        dto.setIdPrestamo(idPrestamo);
        dto.setIdEjemplar(idEjemplar);
        return dao.insertar(dto);
    }

    public Integer modificar(PrestamoEjemplarDTO dto) throws BusinessException {
        validarIds(dto.getIdPrestamo(), dto.getIdEjemplar());
        return dao.modificar(dto);
    }

    public Integer eliminar(Integer idPrestamo, Integer idEjemplar) throws BusinessException {
        validarIds(idPrestamo, idEjemplar);
        return dao.eliminar(idPrestamo, idEjemplar);
    }

    public PrestamoEjemplarDTO obtenerPorIds(Integer idPrestamo, Integer idEjemplar) throws BusinessException {
        validarIds(idPrestamo, idEjemplar);
        PrestamoEjemplarDTO dto = dao.obtenerPorIds(idPrestamo, idEjemplar);
        if (dto == null) {
            throw new BusinessException("No se encontró la relación préstamo-ejemplar especificada.");
        }
        return dto;
    }

    public ArrayList<PrestamoEjemplarDTO> listarTodos() {
        return dao.listarTodos();
    }

    public ArrayList<PrestamoEjemplarDTO> listarPorPrestamo(Integer idPrestamo) throws BusinessException {
        if (idPrestamo == null || idPrestamo <= 0) {
            throw new BusinessException("ID de préstamo inválido.");
        }
        return dao.listarPorPrestamo(idPrestamo);
    }

    public ArrayList<PrestamoEjemplarDTO> listarPorEjemplar(Integer idEjemplar) throws BusinessException {
        if (idEjemplar == null || idEjemplar <= 0) {
            throw new BusinessException("ID de ejemplar inválido.");
        }
        return dao.listarPorEjemplar(idEjemplar);
    }

    private void validarIds(Integer idPrestamo, Integer idEjemplar) throws BusinessException {
        if (idPrestamo == null || idPrestamo <= 0) {
            throw new BusinessException("ID de préstamo inválido.");
        }
        if (idEjemplar == null || idEjemplar <= 0) {
            throw new BusinessException("ID de ejemplar inválido.");
        }
    }
}
