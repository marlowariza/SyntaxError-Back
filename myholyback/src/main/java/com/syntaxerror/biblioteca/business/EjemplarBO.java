package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.EjemplarDTO;
import com.syntaxerror.biblioteca.model.MaterialDTO;
import com.syntaxerror.biblioteca.model.SedeDTO;
import com.syntaxerror.biblioteca.model.enums.FormatoDigital;
import com.syntaxerror.biblioteca.model.enums.TipoEjemplar;
import com.syntaxerror.biblioteca.persistance.dao.EjemplarDAO;
import com.syntaxerror.biblioteca.persistance.dao.MaterialDAO;
import com.syntaxerror.biblioteca.persistance.dao.SedeDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.EjemplarDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.MaterialDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.SedeDAOImpl;
import java.util.ArrayList;
import java.util.Date;

public class EjemplarBO {

    private final EjemplarDAO ejemplarDAO;
    private final SedeDAO sedeDAO;
    private final MaterialDAO materialDAO;

    public EjemplarBO() {
        this.ejemplarDAO = new EjemplarDAOImpl();
        this.sedeDAO = new SedeDAOImpl();
        this.materialDAO = new MaterialDAOImpl();
    }

    public int insertar(Date fechaAdquisicion, Boolean disponible, TipoEjemplar tipo,
            FormatoDigital formatoDigital, String ubicacion,
            Integer idSede, Integer idMaterial) throws BusinessException {
        validarDatos(disponible, tipo, formatoDigital, ubicacion, idSede, idMaterial);
        EjemplarDTO ejemplar = new EjemplarDTO();
        ejemplar.setFechaAdquisicion(fechaAdquisicion);
        ejemplar.setDisponible(disponible);
        ejemplar.setTipo(tipo);
        ejemplar.setFormatoDigital(formatoDigital);
        ejemplar.setUbicacion(ubicacion);

        SedeDTO sede = this.sedeDAO.obtenerPorId(idSede);
        sede.setIdSede(idSede);

        ejemplar.setSede(sede);

        MaterialDTO material = materialDAO.obtenerPorId(idMaterial);
        material.setIdMaterial(idMaterial);

        ejemplar.setMaterial(material);

        return this.ejemplarDAO.insertar(ejemplar);
    }

    public int modificar(Integer idEjemplar, Date fechaAdquisicion, Boolean disponible,
            TipoEjemplar tipo, FormatoDigital formatoDigital, String ubicacion,
            Integer idSede, Integer idMaterial) throws BusinessException {
        BusinessValidator.validarId(idEjemplar, "ejemplar");
        validarDatos(disponible, tipo, formatoDigital, ubicacion, idSede, idMaterial);
        EjemplarDTO ejemplar = new EjemplarDTO();
        ejemplar.setIdEjemplar(idEjemplar);
        ejemplar.setFechaAdquisicion(fechaAdquisicion);
        ejemplar.setDisponible(disponible);
        ejemplar.setTipo(tipo);
        ejemplar.setFormatoDigital(formatoDigital);
        ejemplar.setUbicacion(ubicacion);

        SedeDTO sede = this.sedeDAO.obtenerPorId(idSede);
        sede.setIdSede(idSede);

        ejemplar.setSede(sede);

        MaterialDTO material = materialDAO.obtenerPorId(idMaterial);
        material.setIdMaterial(idMaterial);

        ejemplar.setMaterial(material);

        return this.ejemplarDAO.modificar(ejemplar);
    }

    public int eliminar(Integer idEjemplar) throws BusinessException {
        BusinessValidator.validarId(idEjemplar, "ejemplar");
        EjemplarDTO ejemplar = new EjemplarDTO();
        ejemplar.setIdEjemplar(idEjemplar);
        return this.ejemplarDAO.eliminar(ejemplar);
    }

    public EjemplarDTO obtenerPorId(Integer idEjemplar) throws BusinessException {
        BusinessValidator.validarId(idEjemplar, "ejemplar");
        return this.ejemplarDAO.obtenerPorId(idEjemplar);
    }

    public ArrayList<EjemplarDTO> listarTodos() {
        return this.ejemplarDAO.listarTodos();
    }

    public int contarTotalEjemplaresPorMaterial(int idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");

        int total = 0;
        for (EjemplarDTO ej : this.listarTodos()) {
            if (ej.getMaterial() != null && ej.getMaterial().getIdMaterial() == idMaterial) {
                total++;
            }
        }
        return total;
    }

    public int contarEjemplaresDisponiblesPorMaterial(int idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");

        int disponibles = 0;
        for (EjemplarDTO ej : this.listarTodos()) {
            if (ej.getMaterial() != null
                    && ej.getMaterial().getIdMaterial() == idMaterial
                    && Boolean.TRUE.equals(ej.getDisponible())) {
                disponibles++;
            }
        }
        return disponibles;
    }

    public int contarEjemplaresPorSede(int idSede) throws BusinessException {
        BusinessValidator.validarId(idSede, "sede");

        int total = 0;
        for (EjemplarDTO ej : this.listarTodos()) {
            if (ej.getSede() != null && ej.getSede().getIdSede() == idSede) {
                total++;
            }
        }
        return total;
    }

    public int contarEjemplaresDisponiblesPorSede(int idSede) throws BusinessException {
        BusinessValidator.validarId(idSede, "sede");

        int disponibles = 0;
        for (EjemplarDTO ej : this.listarTodos()) {
            if (ej.getSede() != null
                    && ej.getSede().getIdSede() == idSede
                    && Boolean.TRUE.equals(ej.getDisponible())) {
                disponibles++;
            }
        }
        return disponibles;
    }

    private void validarDatos(Boolean disponible, TipoEjemplar tipo,
            FormatoDigital formato, String ubicacion,
            Integer idSede, Integer idMaterial) throws BusinessException {

        if (disponible == null) {
            throw new BusinessException("Debe indicar si el ejemplar está disponible.");
        }

        if (tipo == null) {
            throw new BusinessException("Debe especificar el tipo de ejemplar.");
        }

        if (tipo == TipoEjemplar.DIGITAL && formato == null) {
            throw new BusinessException("Debe especificar el formato digital para ejemplares digitales.");
        }

        if (tipo == TipoEjemplar.FISICO && (ubicacion == null || ubicacion.isBlank())) {
            throw new BusinessException("Debe especificar la ubicación para ejemplares físicos.");
        }

        BusinessValidator.validarId(idSede, "sede");
        BusinessValidator.validarId(idMaterial, "material");
    }
}
