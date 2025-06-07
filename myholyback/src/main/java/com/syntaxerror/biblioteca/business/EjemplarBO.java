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

    public int contarTotalEjemplaresPorMaterial(int idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return contarEjemplares(idMaterial, null, false, null);
    }

    public int contarEjemplaresDisponiblesPorMaterial(int idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return contarEjemplares(idMaterial, null, true, null);
    }

    public int contarEjemplaresFisicosDisponiblesPorMaterial(int idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return contarEjemplares(idMaterial, null, true, "FISICO");
    }

    public int contarEjemplaresPorSede(int idSede) throws BusinessException {
        BusinessValidator.validarId(idSede, "sede");
        return contarEjemplares(null, idSede, false, null);
    }

    public int contarEjemplaresDisponiblesPorSede(int idSede) throws BusinessException {
        BusinessValidator.validarId(idSede, "sede");
        return contarEjemplares(null, idSede, true, null);
    }

    private int contarEjemplares(Integer idMaterial, Integer idSede, boolean soloDisponibles, String tipoEjemplar) {
        int total = 0;
        for (EjemplarDTO ej : this.listarTodos()) {
            boolean cumpleMaterial = (idMaterial == null
                    || (ej.getMaterial() != null && ej.getMaterial().getIdMaterial() == idMaterial));
            boolean cumpleSede = (idSede == null
                    || (ej.getSede() != null && ej.getSede().getIdSede() == idSede));
            boolean cumpleDisponibilidad = !soloDisponibles || Boolean.TRUE.equals(ej.getDisponible());
            boolean cumpleTipo = (tipoEjemplar == null
                    || (ej.getTipo() != null && tipoEjemplar.equalsIgnoreCase(ej.getTipo().name())));

            if (cumpleMaterial && cumpleSede && cumpleDisponibilidad && cumpleTipo) {
                total++;
            }
        }
        return total;
    }

    public ArrayList<EjemplarDTO> listarEjemplaresFisicosDisponiblesPorMaterial(int idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");

        ArrayList<EjemplarDTO> disponibles = new ArrayList<>();
        for (EjemplarDTO ej : this.listarTodos()) {
            if (ej.getMaterial() != null
                    && ej.getMaterial().getIdMaterial() == idMaterial
                    && Boolean.TRUE.equals(ej.getDisponible())
                    && ej.getTipo() != null
                    && "FISICO".equalsIgnoreCase(ej.getTipo().name())) {
                disponibles.add(ej);
            }
        }

        return disponibles;
    }

    public ArrayList<EjemplarDTO> listarEjemplaresFisicosDisponiblesPorMaterialYSede(int idMaterial, int idSede) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        BusinessValidator.validarId(idSede, "sede");

        ArrayList<EjemplarDTO> disponibles = new ArrayList<>();
        for (EjemplarDTO ej : this.listarTodos()) {
            if (ej.getMaterial() != null
                    && ej.getMaterial().getIdMaterial() == idMaterial
                    && ej.getSede() != null
                    && ej.getSede().getIdSede() == idSede
                    && Boolean.TRUE.equals(ej.getDisponible())
                    && ej.getTipo() != null
                    && "FISICO".equalsIgnoreCase(ej.getTipo().name())) {
                disponibles.add(ej);
            }
        }

        return disponibles;
    }

    public EjemplarDTO obtenerPrimerEjemplarFisicoDisponiblePorMaterialYSede(int idMaterial, int idSede) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        BusinessValidator.validarId(idSede, "sede");

        ArrayList<EjemplarDTO> disponibles = this.listarEjemplaresFisicosDisponiblesPorMaterialYSede(idMaterial, idSede);
        return disponibles.isEmpty() ? null : disponibles.get(0);
    }

}
