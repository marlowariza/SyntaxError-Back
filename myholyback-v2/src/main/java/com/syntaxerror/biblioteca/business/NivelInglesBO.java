package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.NivelesInglesDTO;
import com.syntaxerror.biblioteca.model.enums.Nivel;
import com.syntaxerror.biblioteca.persistance.dao.impl.NivelInglesDAOImpl;

import java.util.ArrayList;
import com.syntaxerror.biblioteca.persistance.dao.NivelInglesDAO;
import java.util.List;

public class NivelInglesBO {

    private final NivelInglesDAO nivelesDAO;

    public NivelInglesBO() {
        this.nivelesDAO = new NivelInglesDAOImpl();
    }

    public int insertar(String nombreNivel, String descripcion) throws BusinessException {
        BusinessValidator.validarTexto(nombreNivel, "nivel");

        Nivel nivelEnum = convertirANivel(nombreNivel);

        NivelesInglesDTO nivel = new NivelesInglesDTO();
        nivel.setNivel(nivelEnum);
        nivel.setDescripcion(descripcion);

        return nivelesDAO.insertar(nivel);
    }

    public int modificar(Integer idNivel, String nombreNivel, String descripcion) throws BusinessException {
        BusinessValidator.validarId(idNivel, "nivel");
        BusinessValidator.validarTexto(nombreNivel, "nivel");

        Nivel nivelEnum = convertirANivel(nombreNivel);

        NivelesInglesDTO nivel = new NivelesInglesDTO();
        nivel.setIdNivel(idNivel);
        nivel.setNivel(nivelEnum);
        nivel.setDescripcion(descripcion);

        return nivelesDAO.modificar(nivel);
    }

    public int eliminar(Integer idNivel) throws BusinessException {
        BusinessValidator.validarId(idNivel, "nivel");

        NivelesInglesDTO nivel = new NivelesInglesDTO();
        nivel.setIdNivel(idNivel);

        return nivelesDAO.eliminar(nivel);
    }

    public NivelesInglesDTO obtenerPorId(Integer idNivel) throws BusinessException {
        BusinessValidator.validarId(idNivel, "nivel");

        NivelesInglesDTO nivel = nivelesDAO.obtenerPorId(idNivel);
        if (nivel.getNivel() == null) {
            throw new BusinessException("No se encontró un nivel de inglés con ID " + idNivel + ".");
        }
        return nivel;
    }

    public ArrayList<NivelesInglesDTO> listarTodos() {
        return nivelesDAO.listarTodos();
    }

    private Nivel convertirANivel(String nombreNivel) throws BusinessException {
        try {
            return Nivel.valueOf(nombreNivel.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("El nivel '" + nombreNivel + "' no es válido. Debe ser BASICO, INTERMEDIO o AVANZADO.");
        }
    }

    public List<NivelesInglesDTO> listarNombresNiveles() {
        return nivelesDAO.listarNombresNiveles();
    }
}
