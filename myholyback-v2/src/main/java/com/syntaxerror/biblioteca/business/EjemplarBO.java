package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.EjemplaresDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.SedesDTO;
import com.syntaxerror.biblioteca.model.enums.FormatoDigital;
import com.syntaxerror.biblioteca.model.enums.TipoEjemplar;
import com.syntaxerror.biblioteca.persistance.dao.impl.EjemplarDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.MaterialDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.SedeDAOImpl;
import java.util.ArrayList;
import java.util.Date;
import com.syntaxerror.biblioteca.persistance.dao.EjemplarDAO;
import com.syntaxerror.biblioteca.persistance.dao.MaterialDAO;
import com.syntaxerror.biblioteca.persistance.dao.SedeDAO;

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
        EjemplaresDTO ejemplar = new EjemplaresDTO();
        ejemplar.setFechaAdquisicion(fechaAdquisicion);
        ejemplar.setDisponible(disponible);
        ejemplar.setTipo(tipo);
        ejemplar.setFormatoDigital(formatoDigital);
        ejemplar.setUbicacion(ubicacion);

        SedesDTO sede = this.sedeDAO.obtenerPorId(idSede);
        sede.setIdSede(idSede);

        ejemplar.setSede(sede);

        MaterialesDTO material = materialDAO.obtenerPorId(idMaterial);
        material.setIdMaterial(idMaterial);

        ejemplar.setMaterial(material);

        return this.ejemplarDAO.insertar(ejemplar);
    }

    public int modificar(Integer idEjemplar, Date fechaAdquisicion, Boolean disponible,
            TipoEjemplar tipo, FormatoDigital formatoDigital, String ubicacion,
            Integer idSede, Integer idMaterial) throws BusinessException {
        BusinessValidator.validarId(idEjemplar, "ejemplar");
        validarDatos(disponible, tipo, formatoDigital, ubicacion, idSede, idMaterial);
        EjemplaresDTO ejemplar = new EjemplaresDTO();
        ejemplar.setIdEjemplar(idEjemplar);
        ejemplar.setFechaAdquisicion(fechaAdquisicion);
        ejemplar.setDisponible(disponible);
        ejemplar.setTipo(tipo);
        ejemplar.setFormatoDigital(formatoDigital);
        ejemplar.setUbicacion(ubicacion);

        SedesDTO sede = this.sedeDAO.obtenerPorId(idSede);
        sede.setIdSede(idSede);

        ejemplar.setSede(sede);

        MaterialesDTO material = materialDAO.obtenerPorId(idMaterial);
        material.setIdMaterial(idMaterial);

        ejemplar.setMaterial(material);

        return this.ejemplarDAO.modificar(ejemplar);
    }

    public int eliminar(Integer idEjemplar) throws BusinessException {
        BusinessValidator.validarId(idEjemplar, "ejemplar");
        EjemplaresDTO ejemplar = new EjemplaresDTO();
        ejemplar.setIdEjemplar(idEjemplar);
        return this.ejemplarDAO.eliminar(ejemplar);
    }

    public EjemplaresDTO obtenerPorId(Integer idEjemplar) throws BusinessException {
        BusinessValidator.validarId(idEjemplar, "ejemplar");
        return this.ejemplarDAO.obtenerPorId(idEjemplar);
    }

    public ArrayList<EjemplaresDTO> listarTodos() {
        return this.ejemplarDAO.listarTodos();
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

// Métodos de conteo
    public int contarTotalEjemplaresPorMaterial(int idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return this.ejemplarDAO.contarEjemplaresPorFiltros(idMaterial, null, null, null);
    }

    public int contarEjemplaresDisponiblesPorMaterial(int idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return this.ejemplarDAO.contarEjemplaresPorFiltros(idMaterial, null, true, null);
    }

    public int contarEjemplaresFisicosDisponiblesPorMaterial(int idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return this.ejemplarDAO.contarEjemplaresPorFiltros(idMaterial, null, true, TipoEjemplar.FISICO);
    }

    public int contarEjemplaresFisicosDisponiblesPorMaterialYSede(int idMaterial, int idSede) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        BusinessValidator.validarId(idSede, "sede");
        return this.ejemplarDAO.contarEjemplaresPorFiltros(idMaterial, idSede, true, TipoEjemplar.FISICO);
    }

    public int contarEjemplaresPorSede(int idSede) throws BusinessException {
        BusinessValidator.validarId(idSede, "sede");
        return this.ejemplarDAO.contarEjemplaresPorFiltros(null, idSede, null, null);
    }

    public int contarEjemplaresDisponiblesPorSede(int idSede) throws BusinessException {
        BusinessValidator.validarId(idSede, "sede");
        return this.ejemplarDAO.contarEjemplaresPorFiltros(null, idSede, true, null);
    }

    public ArrayList<EjemplaresDTO> listarEjemplaresFisicosDisponiblesPorMaterial(int idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return this.ejemplarDAO.listarEjemplaresPorFiltros(idMaterial, null, true, TipoEjemplar.FISICO);
    }

    public ArrayList<EjemplaresDTO> listarEjemplaresFisicosDisponiblesPorMaterialYSede(int idMaterial, int idSede) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        BusinessValidator.validarId(idSede, "sede");
        return this.ejemplarDAO.listarEjemplaresPorFiltros(idMaterial, idSede, true, TipoEjemplar.FISICO);
    }

    // Ejemplares disponibles por material y sede
    public int contarEjemplaresDisponiblesPorMaterialYSede(int idMaterial, int idSede) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        BusinessValidator.validarId(idSede, "sede");
        return this.ejemplarDAO.contarEjemplaresPorFiltros(idMaterial, idSede, true, null);
    }

// 2️⃣ Ejemplares NO disponibles por material y sede
    public int contarEjemplaresNoDisponiblesPorMaterialYSede(int idMaterial, int idSede) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        BusinessValidator.validarId(idSede, "sede");
        return this.ejemplarDAO.contarEjemplaresPorFiltros(idMaterial, idSede, false, null);
    }

    public EjemplaresDTO obtenerPrimerEjemplarFisicoDisponiblePorMaterialYSede(int idMaterial, int idSede) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        BusinessValidator.validarId(idSede, "sede");
        ArrayList<EjemplaresDTO> disponibles = this.ejemplarDAO.listarEjemplaresPorFiltros(idMaterial, idSede, true, TipoEjemplar.FISICO);
        return disponibles.isEmpty() ? null : disponibles.get(0);
    }
}
