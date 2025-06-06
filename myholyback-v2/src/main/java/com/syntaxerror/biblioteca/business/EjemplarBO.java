package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.EjemplaresDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.SedesDTO;
import com.syntaxerror.biblioteca.model.enums.FormatoDigital;
import com.syntaxerror.biblioteca.model.enums.TipoEjemplar;
import com.syntaxerror.biblioteca.persistance.dao.impl.EjemplaresDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.MaterialesDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.SedesDAOImpl;
import java.util.ArrayList;
import java.util.Date;
import com.syntaxerror.biblioteca.persistance.dao.SedesDAO;
import com.syntaxerror.biblioteca.persistance.dao.MaterialesDAO;
import com.syntaxerror.biblioteca.persistance.dao.EjemplaresDAO;

public class EjemplarBO {

    private final EjemplaresDAO ejemplarDAO;
    private final SedesDAO sedeDAO;
    private final MaterialesDAO materialDAO;

    public EjemplarBO() {
        this.ejemplarDAO = new EjemplaresDAOImpl();
        this.sedeDAO = new SedesDAOImpl();
        this.materialDAO = new MaterialesDAOImpl();
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
        for (EjemplaresDTO ej : this.listarTodos()) {
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

    public ArrayList<EjemplaresDTO> listarEjemplaresFisicosDisponiblesPorMaterial(int idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");

        ArrayList<EjemplaresDTO> disponibles = new ArrayList<>();
        for (EjemplaresDTO ej : this.listarTodos()) {
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

    public ArrayList<EjemplaresDTO> listarEjemplaresFisicosDisponiblesPorMaterialYSede(int idMaterial, int idSede) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        BusinessValidator.validarId(idSede, "sede");

        ArrayList<EjemplaresDTO> disponibles = new ArrayList<>();
        for (EjemplaresDTO ej : this.listarTodos()) {
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

    public EjemplaresDTO obtenerPrimerEjemplarFisicoDisponiblePorMaterialYSede(int idMaterial, int idSede) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        BusinessValidator.validarId(idSede, "sede");

        ArrayList<EjemplaresDTO> disponibles = this.listarEjemplaresFisicosDisponiblesPorMaterialYSede(idMaterial, idSede);
        return disponibles.isEmpty() ? null : disponibles.get(0);
    }

}
