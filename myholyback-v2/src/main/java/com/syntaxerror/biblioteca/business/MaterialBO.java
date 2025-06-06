package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.CreadoresDTO;
import com.syntaxerror.biblioteca.model.EditorialesDTO;
import com.syntaxerror.biblioteca.model.EjemplaresDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.NivelesInglesDTO;
import com.syntaxerror.biblioteca.model.enums.TipoCreador;
import com.syntaxerror.biblioteca.persistance.dao.CreadoresDAO;

import com.syntaxerror.biblioteca.persistance.dao.impl.EditorialesDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.MaterialesDAOImpl;
import java.util.List;
import com.syntaxerror.biblioteca.persistance.dao.MaterialesDAO;
import com.syntaxerror.biblioteca.persistance.dao.EditorialesDAO;
import com.syntaxerror.biblioteca.persistance.dao.EjemplaresDAO;
import com.syntaxerror.biblioteca.persistance.dao.NivelesInglesDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.CreadoresDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.EjemplaresDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.NivelesInglesDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.base.CreadoresMaterialesDAO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MaterialBO {

    private final MaterialesDAO materialDAO;
    private final EditorialesDAO editorialDAO;
    private final NivelesInglesDAO nivelDAO;
    private final EjemplaresDAO ejemplarDAO;

    public MaterialBO() {
        this.materialDAO = new MaterialesDAOImpl();
        this.editorialDAO = new EditorialesDAOImpl();
        this.nivelDAO = new NivelesInglesDAOImpl();
        this.ejemplarDAO = new EjemplaresDAOImpl();
    }

    public int insertar(String titulo, String edicion,
            Integer anioPublicacion, String portada, Boolean vigente,
            Integer idNivel, Integer idEditorial) throws BusinessException {
        BusinessValidator.validarTexto(titulo, "título");
        MaterialesDTO material = new MaterialesDTO();
        material.setTitulo(titulo);
        material.setEdicion(edicion);
        material.setAnioPublicacion(anioPublicacion);
        material.setPortada(portada);
        material.setVigente(vigente);

        if (idNivel != null) {
            NivelesInglesDTO nivel = nivelDAO.obtenerPorId(idNivel);
            if (nivel == null) {
                throw new BusinessException("El nivel con ID " + idNivel + " no existe.");
            }
            material.setNivel(nivel);
        }
        if (idEditorial != null) {
            EditorialesDTO editorial = editorialDAO.obtenerPorId(idEditorial);
            if (editorial == null) {
                throw new BusinessException("La editorial con ID " + idEditorial + " no existe.");
            }
            material.setEditorial(editorial);
        }

        return this.materialDAO.insertar(material);
    }

    public int modificar(Integer idMaterial, String titulo, String edicion,
            Integer anioPublicacion, String portada, Boolean vigente,
            Integer idNivel, Integer idEditorial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        BusinessValidator.validarTexto(titulo, "título");
        MaterialesDTO material = new MaterialesDTO();
        material.setIdMaterial(idMaterial);
        material.setTitulo(titulo);
        material.setEdicion(edicion);
        material.setAnioPublicacion(anioPublicacion);
        material.setPortada(portada);
        material.setVigente(vigente);

        if (idNivel != null) {
            NivelesInglesDTO nivel = nivelDAO.obtenerPorId(idNivel);
            if (nivel == null) {
                throw new BusinessException("El nivel con ID " + idNivel + " no existe.");
            }
            material.setNivel(nivel);
        }
        if (idEditorial != null) {
            EditorialesDTO editorial = editorialDAO.obtenerPorId(idEditorial);
            if (editorial == null) {
                throw new BusinessException("La editorial con ID " + idEditorial + " no existe.");
            }
            material.setEditorial(editorial);
        }

        return this.materialDAO.modificar(material);
    }

    public int eliminar(Integer idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        MaterialesDTO material = new MaterialesDTO();
        material.setIdMaterial(idMaterial);
        return this.materialDAO.eliminar(material);
    }

    public MaterialesDTO obtenerPorId(Integer idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return this.materialDAO.obtenerPorId(idMaterial);
    }

    public List<MaterialesDTO> listarTodos() {
        return this.materialDAO.listarTodos();

    }

    //LISTA EJEMPLARES POR ID MATERIAL (SEA FISICO O DIGITAL / DISPONIBLE O NO)
    public ArrayList<EjemplaresDTO> listarEjemplaresMaterial(Integer idMaterial) {
        ArrayList<EjemplaresDTO> ejemplares = ejemplarDAO.listarTodos();
        ArrayList<EjemplaresDTO> ejemplaresFiltrados = new ArrayList<>();
        for (EjemplaresDTO ej : ejemplares) {
            if (ej.getMaterial().getIdMaterial().equals(idMaterial)) {
                ejemplaresFiltrados.add(ej);
            }

        }
        return ejemplaresFiltrados;
    }

    //LISTA EJEMPLARES DISPONIBLES (FISICOS O DIGITALES)
    public List<EjemplaresDTO> listarEjemplaresDisponibles() {
        List<EjemplaresDTO> disponibles = new ArrayList<>();
        for (EjemplaresDTO ej : ejemplarDAO.listarTodos()) {
            if (ej.getDisponible()) {
                disponibles.add(ej);
            }
        }
        return disponibles;
    }

    // Lista ejemplares FÍSICOS y DISPONIBLES de un material
    public List<EjemplaresDTO> listarEjemplaresFisicosDisponiblesPorMaterial(Integer idMaterial) {
        return listarEjemplaresDisponiblesPorMaterialYTipo(idMaterial, "FISICO");
    }
    // Lista ejemplares DIGITALES y DISPONIBLES de un material

    public List<EjemplaresDTO> listarEjemplaresDigitalesDisponiblesPorMaterial(Integer idMaterial) {
        return listarEjemplaresDisponiblesPorMaterialYTipo(idMaterial, "DIGITAL");
    }

    private List<EjemplaresDTO> listarEjemplaresDisponiblesPorMaterialYTipo(Integer idMaterial, String tipoEjemplar) {
        List<EjemplaresDTO> resultado = new ArrayList<>();
        for (EjemplaresDTO ej : ejemplarDAO.listarTodos()) {
            if (ej.getMaterial() != null
                    && ej.getMaterial().getIdMaterial().equals(idMaterial)
                    && tipoEjemplar.equalsIgnoreCase(ej.getTipo().name())
                    && ej.getDisponible()) {
                resultado.add(ej);
            }
        }
        return resultado;
    }

    //LISTA MATERIALES POR CARACTERES EN EL BUSCADOR ( DISPONIBLE O NO)
    public ArrayList<MaterialesDTO> listarPorCaracteres(String car) {
        ArrayList<MaterialesDTO> listaMateriales = (ArrayList<MaterialesDTO>) new MaterialBO().listarTodos();
        ArrayList<MaterialesDTO> listaFiltrada = new ArrayList<>();
        if (car == null || car.trim().isEmpty()) {
            return listaFiltrada;
        }
        String filtro = car.toLowerCase().trim();
        for (MaterialesDTO m : listaMateriales) {
            String titulo = m.getTitulo();
            if (titulo != null && titulo.toLowerCase().contains(filtro)) {
                listaFiltrada.add(m);
            }
        }
        return listaFiltrada;
    }

    // LISTA MATERIALES VIGENTES POR CARACTERES EN EL BUSCADOR
    public List<MaterialesDTO> listarVigentesPorCaracteres(String car) {
        List<MaterialesDTO> listaMateriales = this.listarTodos();  // No uses new MaterialBO()
        List<MaterialesDTO> listaFiltrada = new ArrayList<>();

        if (car == null || car.trim().isEmpty()) {
            return listaFiltrada;
        }

        String filtro = car.toLowerCase().trim();

        for (MaterialesDTO m : listaMateriales) {
            String titulo = m.getTitulo();
            if (Boolean.TRUE.equals(m.getVigente())
                    && titulo != null
                    && titulo.toLowerCase().contains(filtro)) {
                listaFiltrada.add(m);
            }
        }

        return listaFiltrada;
    }

    //LISTA MATERIALES VIGENTES EN GENERAL
    public List<MaterialesDTO> listarMaterialesVigentes() {
        List<MaterialesDTO> todos = this.materialDAO.listarTodos();
        List<MaterialesDTO> vigentes = new ArrayList<>();

        for (MaterialesDTO mat : todos) {
            if (Boolean.TRUE.equals(mat.getVigente())) {
                vigentes.add(mat);
            }
        }

        return vigentes;
    }

    //LISTA MATERIALES POR ID SEDE (SEA FISICO O DIGITAL / DISPONIBLE O NO)
    public ArrayList<MaterialesDTO> listarMaterialesPorSede(Integer idSede) throws BusinessException {

        BusinessValidator.validarId(idSede, "sede");
        EjemplarBO ejemplarBO = new EjemplarBO();
        ArrayList<EjemplaresDTO> ejemplares = ejemplarBO.listarTodos();
        ArrayList<MaterialesDTO> materialesPorSede = new ArrayList<>();
        HashSet<Integer> idsUnicos = new HashSet<>();

        for (EjemplaresDTO ej : ejemplares) {
            if (ej.getSede() != null && ej.getSede().getIdSede().equals(idSede)) {
                MaterialesDTO material = ej.getMaterial();
                if (material != null && !idsUnicos.contains(material.getIdMaterial())) {
                    materialesPorSede.add(material);
                    idsUnicos.add(material.getIdMaterial());
                }
            }
        }

        return materialesPorSede;
    }

    //LISTA MATERIALES VIGENTES POR SEDE
    public List<MaterialesDTO> listarMaterialesVigentesPorSede(Integer idSede) throws BusinessException {
        BusinessValidator.validarId(idSede, "sede");

        List<EjemplaresDTO> ejemplares = ejemplarDAO.listarTodos();
        HashSet<Integer> idsUnicos = new HashSet<>();
        List<MaterialesDTO> resultado = new ArrayList<>();

        for (EjemplaresDTO ej : ejemplares) {
            if (ej.getSede() != null
                    && ej.getSede().getIdSede().equals(idSede)) {

                MaterialesDTO material = ej.getMaterial();
                if (material != null
                        && Boolean.TRUE.equals(material.getVigente())
                        && !idsUnicos.contains(material.getIdMaterial())) {

                    resultado.add(material);
                    idsUnicos.add(material.getIdMaterial());
                }
            }
        }

        return resultado;
    }

    //INDICA SI EXISTE EJEMPLAR DIGITAL (AHORA QUE ME DOY CUENTA SOLO ES NECESARIO QYE HAYA UNO NO?)
    public boolean existeEjemplarDigitalPorMaterial(Integer idMaterial) {
        for (EjemplaresDTO ej : ejemplarDAO.listarTodos()) {
            if (ej.getMaterial() != null
                    && ej.getMaterial().getIdMaterial().equals(idMaterial)
                    && "DIGITAL".equalsIgnoreCase(ej.getTipo().name())) {
                return true; // Basta con encontrar uno
            }
        }
        return false;
    }

    private boolean coincide(String filtro, String valor) {
        return valor != null && valor.toLowerCase().contains(filtro);
    }
    //BUSCA MATERIALES VIGENTES POR caracteres SOLO AUTORES
    public List<MaterialesDTO> buscarMaterialesVigentesPorNombreAutor(String filtro) throws BusinessException {
        List<MaterialesDTO> resultado = new ArrayList<>();

        if (filtro == null || filtro.trim().isEmpty()) {
            return resultado;
        }

        filtro = filtro.trim().toLowerCase();

        CreadoresDAO creadorDAO = new CreadoresDAOImpl();
        CreadoresMaterialesDAO cmDAO = new CreadoresMaterialesDAO();

        List<CreadoresDTO> todosCreadores = creadorDAO.listarTodos();
        Set<Integer> idsAgregados = new HashSet<>();

        for (CreadoresDTO creador : todosCreadores) {
            if (creador.getTipo() == TipoCreador.AUTOR
                    && creador.getActivo()
                    && (coincide(filtro, creador.getNombre())
                    || coincide(filtro, creador.getPaterno())
                    || coincide(filtro, creador.getMaterno())
                    || coincide(filtro, creador.getSeudonimo()))) {

                List<MaterialesDTO> materiales = cmDAO.listarPorCreador(creador.getIdCreador());

                for (MaterialesDTO mat : materiales) {
                    if (Boolean.TRUE.equals(mat.getVigente()) && !idsAgregados.contains(mat.getIdMaterial())) {
                        resultado.add(mat);
                        idsAgregados.add(mat.getIdMaterial());
                    }
                }
            }
        }

        return resultado;
    }

}
