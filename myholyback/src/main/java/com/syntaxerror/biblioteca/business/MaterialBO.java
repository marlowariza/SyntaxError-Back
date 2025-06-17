package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.model.CreadorDTO;
import com.syntaxerror.biblioteca.model.EditorialDTO;
import com.syntaxerror.biblioteca.model.EjemplarDTO;
import com.syntaxerror.biblioteca.model.MaterialDTO;
import com.syntaxerror.biblioteca.model.NivelInglesDTO;
import com.syntaxerror.biblioteca.persistance.dao.EditorialDAO;
import com.syntaxerror.biblioteca.persistance.dao.EjemplarDAO;
import com.syntaxerror.biblioteca.persistance.dao.MaterialDAO;
import com.syntaxerror.biblioteca.persistance.dao.NivelInglesDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.EditorialDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.EjemplarDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.MaterialDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.NivelInglesDAOImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MaterialBO {

    private final MaterialDAO materialDAO;
    private final EditorialDAO editorialDAO;
    private final EjemplarDAO ejemplarDAO;
    private final NivelInglesDAO nivelDAO;

    public MaterialBO() {
        this.materialDAO = new MaterialDAOImpl();
        this.editorialDAO = new EditorialDAOImpl();
        this.ejemplarDAO = new EjemplarDAOImpl();
        this.nivelDAO = new NivelInglesDAOImpl();
    }

    public int insertar(String titulo, String edicion,
            Integer anioPublicacion, String portada, Boolean vigente,
            Integer idNivel, Integer idEditorial) throws BusinessException {

        BusinessValidator.validarTexto(titulo, "título");
        MaterialDTO material = new MaterialDTO();
        material.setTitulo(titulo);
        material.setEdicion(edicion);
        material.setAnioPublicacion(anioPublicacion);
        material.setPortada(portada);
        material.setVigente(vigente);

        if (idNivel != null) {
            NivelInglesDTO nivel = nivelDAO.obtenerPorId(idNivel);
            if (nivel == null) {
                throw new BusinessException("El nivel con ID " + idNivel + " no existe.");
            }
            material.setNivel(nivel);
        }

        if (idEditorial != null) {
            EditorialDTO editorial = editorialDAO.obtenerPorId(idEditorial);
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
        MaterialDTO material = new MaterialDTO();
        material.setIdMaterial(idMaterial);
        material.setTitulo(titulo);
        material.setEdicion(edicion);
        material.setAnioPublicacion(anioPublicacion);
        material.setPortada(portada);
        material.setVigente(vigente);

        if (idEditorial != null) {
            EditorialDTO editorial = editorialDAO.obtenerPorId(idEditorial);
            if (editorial == null) {
                throw new BusinessException("La editorial con ID " + idEditorial + " no existe.");
            }
            material.setEditorial(editorial);
        }
        if (idNivel != null) {
            NivelInglesDTO nivel = nivelDAO.obtenerPorId(idNivel);
            if (nivel == null) {
                throw new BusinessException("El nivel con ID " + idNivel + " no existe.");
            }
            material.setNivel(nivel);
        }

        return this.materialDAO.modificar(material);
    }

    public int eliminar(Integer idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        MaterialDTO material = new MaterialDTO();
        material.setIdMaterial(idMaterial);
        return this.materialDAO.eliminar(material);
    }

    public MaterialDTO obtenerPorId(Integer idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return this.materialDAO.obtenerPorId(idMaterial);
    }

    public ArrayList<MaterialDTO> listarTodos() {
        return this.materialDAO.listarTodos();
    }

    public ArrayList<EjemplarDTO> listarEjemplaresMaterial(Integer idMaterial) {
        ArrayList<EjemplarDTO> ejemplares = ejemplarDAO.listarTodos();
        ArrayList<EjemplarDTO> ejemplaresFiltrados = new ArrayList<>();
        for (EjemplarDTO ej : ejemplares) {
            if (ej.getMaterial().getIdMaterial().equals(idMaterial)) {
                ejemplaresFiltrados.add(ej);
            }

        }
        return ejemplaresFiltrados;
    }

    public ArrayList<MaterialDTO> listarPorCaracteres(String car) {
        ArrayList<MaterialDTO> listaMateriales = new MaterialBO().listarTodos();
        ArrayList<MaterialDTO> listaFiltrada = new ArrayList<>();
        if (car == null || car.trim().isEmpty()) {
            return listaFiltrada;
        }
        String filtro = car.toLowerCase().trim();
        for (MaterialDTO m : listaMateriales) {
            String titulo = m.getTitulo();
            if (titulo != null && titulo.toLowerCase().contains(filtro)) {
                listaFiltrada.add(m);
            }
        }
        return listaFiltrada;
    }

    public ArrayList<MaterialDTO> listarPorCaracter_Creador(String filtro) throws BusinessException {
        ArrayList<MaterialDTO> resultado = new ArrayList<>();
        //en verdad filtra por autor, falta cambiar
        if (filtro == null || filtro.trim().isEmpty()) {
            return resultado;
        }

        filtro = filtro.trim().toLowerCase();

        CreadorBO creadorBO = new CreadorBO();
        MaterialCreadorBO materialCreadorBO = new MaterialCreadorBO();

        ArrayList<CreadorDTO> creadores = creadorBO.listarTodos();
        HashSet<Integer> idsMaterialesAgregados = new HashSet<>();

        for (CreadorDTO c : creadores) {
            if (c.getTipo().name().equals("AUTOR")
                    && (coincide(filtro, c.getNombre())
                    || coincide(filtro, c.getPaterno())
                    || coincide(filtro, c.getMaterno())
                    || coincide(filtro, c.getSeudonimo()))) {

                ArrayList<MaterialDTO> materiales = materialCreadorBO.listarMaterialesPorCreador(c.getIdAutor());

                for (MaterialDTO m : materiales) {
                    if (!idsMaterialesAgregados.contains(m.getIdMaterial())) {
                        resultado.add(m);
                        idsMaterialesAgregados.add(m.getIdMaterial());
                    }
                }
            }
        }

        return resultado;
    }

    private boolean coincide(String filtro, String campo) {
        return campo != null && campo.toLowerCase().contains(filtro);
    }

    public ArrayList<MaterialDTO> listarMaterialesPorSede(Integer idSede) throws BusinessException {

        BusinessValidator.validarId(idSede, "sede");
        EjemplarBO ejemplarBO = new EjemplarBO();
        ArrayList<EjemplarDTO> ejemplares = ejemplarBO.listarTodos();
        ArrayList<MaterialDTO> materialesPorSede = new ArrayList<>();
        HashSet<Integer> idsUnicos = new HashSet<>();

        for (EjemplarDTO ej : ejemplares) {
            if (ej.getSede() != null && ej.getSede().getIdSede().equals(idSede)) {
                MaterialDTO material = ej.getMaterial();
                if (material != null && !idsUnicos.contains(material.getIdMaterial())) {
                    materialesPorSede.add(material);
                    idsUnicos.add(material.getIdMaterial());
                }
            }
        }

        return materialesPorSede;
    }

    //ORDENA DESCENDENTE POR CANTIDAD DE PRESTAMOS, PERMITE LISTAR POR CANTIDAD Y PAGINA
    public List<MaterialDTO> listarMasSolicitados(int limite, int pagina) throws BusinessException {
        BusinessValidator.validarPaginacion(limite, pagina);
        int offset = (pagina - 1) * limite;
        return this.materialDAO.listarMasSolicitados(limite, offset);
    }
    //ORDENA DESCENDENTE POR MATERIALES RECIENTES, PERMITE LISTAR POR CANTIDAD Y PAGINA

    public List<MaterialDTO> listarMasRecientes(int limite, int pagina) throws BusinessException {
        BusinessValidator.validarPaginacion(limite, pagina);
        int offset = (pagina - 1) * limite;
        return this.materialDAO.listarMasRecientes(limite, offset);
    }

    public List<MaterialDTO> listarPorSedeYFiltro(Integer idSede, String filtro, boolean porTitulo) throws BusinessException {
        BusinessValidator.validarId(idSede, "sede");
        if (filtro == null || filtro.isBlank()) {
            throw new BusinessException("El filtro de búsqueda no puede estar vacío.");
        }
        return new ArrayList<>(materialDAO.listarPorSedeYFiltro(idSede, filtro, porTitulo));
    }

}
