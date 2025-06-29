package com.syntaxerror.biblioteca.business;

import com.syntaxerror.biblioteca.business.util.BusinessException;
import com.syntaxerror.biblioteca.business.util.BusinessValidator;
import com.syntaxerror.biblioteca.business.util.PaginacionDTO;
import com.syntaxerror.biblioteca.business.util.SeekPaginator;
import com.syntaxerror.biblioteca.model.CreadoresDTO;
import com.syntaxerror.biblioteca.model.EditorialesDTO;
import com.syntaxerror.biblioteca.model.EjemplaresDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.NivelesInglesDTO;
import com.syntaxerror.biblioteca.model.TemasDTO;
import com.syntaxerror.biblioteca.model.enums.Nivel;
import com.syntaxerror.biblioteca.model.enums.TipoEjemplar;
import com.syntaxerror.biblioteca.persistance.dao.CreadorDAO;

import com.syntaxerror.biblioteca.persistance.dao.impl.EditorialDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.MaterialDAOImpl;
import java.util.List;
import com.syntaxerror.biblioteca.persistance.dao.impl.EjemplarDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.NivelInglesDAOImpl;
import java.util.ArrayList;
import com.syntaxerror.biblioteca.persistance.dao.EditorialDAO;
import com.syntaxerror.biblioteca.persistance.dao.EjemplarDAO;
import com.syntaxerror.biblioteca.persistance.dao.MaterialDAO;
import com.syntaxerror.biblioteca.persistance.dao.NivelInglesDAO;
import com.syntaxerror.biblioteca.persistance.dao.TemaDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.CreadorDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.TemaDAOImpl;

public class MaterialBO {

    private final MaterialDAO materialDAO;
    private final EditorialDAO editorialDAO;
    private final NivelInglesDAO nivelDAO;
    private final EjemplarDAO ejemplarDAO;
    private final CreadorDAO creadorDAO;
    private final TemaDAO temaDAO;

    public MaterialBO() {
        this.materialDAO = new MaterialDAOImpl();
        this.editorialDAO = new EditorialDAOImpl();
        this.nivelDAO = new NivelInglesDAOImpl();
        this.ejemplarDAO = new EjemplarDAOImpl();
        this.creadorDAO = new CreadorDAOImpl();
        this.temaDAO = new TemaDAOImpl();
    }

    public int insertar(MaterialesDTO material) throws BusinessException {
        BusinessValidator.validarTexto(material.getTitulo(), "título");

        // Validar nivel si existe
        if (material.getNivel() != null) {
            Integer idNivel = material.getNivel().getIdNivel();
            NivelesInglesDTO nivel = nivelDAO.obtenerPorId(idNivel);
            if (nivel == null) {
                throw new BusinessException("El nivel con ID " + idNivel + " no existe.");
            }
            material.setNivel(nivel);
        }
        // Validar editorial si existe
        if (material.getEditorial() != null) {
            Integer idEditorial = material.getEditorial().getIdEditorial();
            EditorialesDTO editorial = editorialDAO.obtenerPorId(idEditorial);
            if (editorial == null) {
                throw new BusinessException("La editorial con ID " + idEditorial + " no existe.");
            }
            material.setEditorial(editorial);
        }

        // Validar creadores (lista)
        if (material.getCreadores() != null) {
            List<CreadoresDTO> creadoresValidados = new ArrayList<>();
            for (CreadoresDTO creador : material.getCreadores()) {
                CreadoresDTO val = creadorDAO.obtenerPorId(creador.getIdCreador());
                if (val == null) {
                    throw new BusinessException("El creador con ID " + creador.getIdCreador() + " no existe.");
                }
                creadoresValidados.add(val);
            }
            material.setCreadores(creadoresValidados);
        }

        // Validar temas (lista)
        if (material.getTemas() != null) {
            List<TemasDTO> temasValidados = new ArrayList<>();
            for (TemasDTO tema : material.getTemas()) {
                TemasDTO val = temaDAO.obtenerPorId(tema.getIdTema());
                if (val == null) {
                    throw new BusinessException("El tema con ID " + tema.getIdTema() + " no existe.");
                }
                temasValidados.add(val);
            }
            material.setTemas(temasValidados);
        }

        return this.materialDAO.insertar(material);
    }
//    public int insertar(String titulo, String edicion,
//            Integer anioPublicacion, String portada, Boolean vigente,
//            Integer idNivel, Integer idEditorial) throws BusinessException {
//        BusinessValidator.validarTexto(titulo, "título");
//        MaterialesDTO material = new MaterialesDTO();
//        material.setTitulo(titulo);
//        material.setEdicion(edicion);
//        material.setAnioPublicacion(anioPublicacion);
//        material.setPortada(portada);
//        material.setVigente(vigente);
//
//        if (idNivel != null) {
//            NivelesInglesDTO nivel = nivelDAO.obtenerPorId(idNivel);
//            if (nivel == null) {
//                throw new BusinessException("El nivel con ID " + idNivel + " no existe.");
//            }
//            material.setNivel(nivel);
//        }
//        if (idEditorial != null) {
//            EditorialesDTO editorial = editorialDAO.obtenerPorId(idEditorial);
//            if (editorial == null) {
//                throw new BusinessException("La editorial con ID " + idEditorial + " no existe.");
//            }
//            material.setEditorial(editorial);
//        }
//
//        return this.materialDAO.insertar(material);
//    }

    public int modificar(MaterialesDTO material) throws BusinessException {
        BusinessValidator.validarId(material.getIdMaterial(), "material");
        BusinessValidator.validarTexto(material.getTitulo(), "título");

        // Validar nivel si existe
        if (material.getNivel() != null) {
            Integer idNivel = material.getNivel().getIdNivel();
            NivelesInglesDTO nivel = nivelDAO.obtenerPorId(idNivel);
            if (nivel == null) {
                throw new BusinessException("El nivel con ID " + idNivel + " no existe.");
            }
            material.setNivel(nivel);
        }

        // Validar editorial si existe
        if (material.getEditorial() != null) {
            Integer idEditorial = material.getEditorial().getIdEditorial();
            EditorialesDTO editorial = editorialDAO.obtenerPorId(idEditorial);
            if (editorial == null) {
                throw new BusinessException("La editorial con ID " + idEditorial + " no existe.");
            }
            material.setEditorial(editorial);
        }

        // Validar creadores (lista)
        if (material.getCreadores() != null) {
            List<CreadoresDTO> creadoresValidados = new ArrayList<>();
            for (CreadoresDTO creador : material.getCreadores()) {
                CreadoresDTO val = creadorDAO.obtenerPorId(creador.getIdCreador());
                if (val == null) {
                    throw new BusinessException("El creador con ID " + creador.getIdCreador() + " no existe.");
                }
                creadoresValidados.add(val);
            }
            material.setCreadores(creadoresValidados);
        }

        // Validar temas (lista)
        if (material.getTemas() != null) {
            List<TemasDTO> temasValidados = new ArrayList<>();
            for (TemasDTO tema : material.getTemas()) {
                TemasDTO val = temaDAO.obtenerPorId(tema.getIdTema());
                if (val == null) {
                    throw new BusinessException("El tema con ID " + tema.getIdTema() + " no existe.");
                }
                temasValidados.add(val);
            }
            material.setTemas(temasValidados);
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

    //LISTA TODOS(ORDENADO POR TITULO) SEGUN CANTIDAD Y NUM DE PAGINA
    public List<MaterialesDTO> listarTodosPaginado(int limite, int pagina) throws BusinessException {
        BusinessValidator.validarPaginacion(limite, pagina);
        int offset = (pagina - 1) * limite;
        return this.materialDAO.listarTodosPaginado(limite, offset);
    }

    public PaginacionDTO<MaterialesDTO> listarMaterialesPaginado(int cantidad, int pagina) throws BusinessException {
        BusinessValidator.validarPaginacion(cantidad, pagina);

        return SeekPaginator.paginar(
                cantidad,
                pagina,
                (titulo, id, limit) -> materialDAO.listarTodosSeek(titulo, id, limit),
                MaterialesDTO::getTitulo,
                MaterialesDTO::getIdMaterial
        );
    }

    //LISTA EJEMPLARES POR ID MATERIAL (SEA FISICO O DIGITAL / DISPONIBLE O NO)
    public List<EjemplaresDTO> listarEjemplaresMaterial(Integer idMaterial, int limite, int pagina) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        BusinessValidator.validarPaginacion(limite, pagina);
        int offset = (pagina - 1) * limite;
        return ejemplarDAO.listarPorIdMaterialPaginado(idMaterial, limite, offset);
    }

// LISTA MATERIALES (TODOS) POR CARACTERES EN EL BUSCADOR
    public ArrayList<MaterialesDTO> listarPorCaracteres(String car, int limite, int pagina) throws BusinessException {
        return this.listarPorCaracteresGenerico(car, limite, pagina, false);
    }

// LISTA MATERIALES VIGENTES POR CARACTERES EN EL BUSCADOR
    public ArrayList<MaterialesDTO> listarVigentesPorCaracteres(String car, int limite, int pagina) throws BusinessException {
        return this.listarPorCaracteresGenerico(car, limite, pagina, true);
    }

// MÉTODO GENERALIZADO INTERNO
    private ArrayList<MaterialesDTO> listarPorCaracteresGenerico(String car, int limite, int pagina, boolean soloVigentes) throws BusinessException {
        BusinessValidator.validarPaginacion(limite, pagina);
        if (car == null || car.trim().isEmpty()) {
            return new ArrayList<>();
        }
        int offset = (pagina - 1) * limite;

        List<MaterialesDTO> lista = soloVigentes
                ? materialDAO.listarPorTituloConteniendoGenerico(car, limite, offset, true)
                : materialDAO.listarPorTituloConteniendoGenerico(car, limite, offset, false);

        return new ArrayList<>(lista);
    }

// LISTA MATERIALES (VIGENTES O NO) POR ID DE SEDE
    public List<MaterialesDTO> listarMaterialesPorSede(Integer idSede, int limite, int pagina) throws BusinessException {
        return this.listarMaterialesPorSedeGenerico(idSede, limite, pagina, false);
    }

// LISTA MATERIALES VIGENTES POR ID DE SEDE
    public List<MaterialesDTO> listarMaterialesVigentesPorSede(Integer idSede, int limite, int pagina) throws BusinessException {
        return this.listarMaterialesPorSedeGenerico(idSede, limite, pagina, true);
    }

// MÉTODO AUXILIAR COMÚN
    private List<MaterialesDTO> listarMaterialesPorSedeGenerico(Integer idSede, int limite, int pagina, boolean soloVigentes) throws BusinessException {
        BusinessValidator.validarId(idSede, "sede");
        BusinessValidator.validarPaginacion(limite, pagina);
        int offset = (pagina - 1) * limite;

        return soloVigentes
                ? materialDAO.listarPorSedeGenerico(idSede, limite, offset, true)
                : materialDAO.listarPorSedeGenerico(idSede, limite, offset, false);
    }

    //INDICA SI EXISTE EJEMPLAR DIGITAL)
    public boolean existeEjemplarDigitalPorMaterial(Integer idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return ejemplarDAO.existeEjemplarDigitalPorMaterial(idMaterial);
    }

    //BUSCA MATERIALES VIGENTES POR caracteres SOLO AUTORES
    public List<MaterialesDTO> listarPorCaracter_Creador(String filtro, int limite, int pagina) throws BusinessException {
        if (filtro == null || filtro.trim().isEmpty()) {
            return new ArrayList<>();
        }
        BusinessValidator.validarPaginacion(limite, pagina);
        int offset = (pagina - 1) * limite;
        return new MaterialDAOImpl().listarMaterialesVigentesPorCreadorFiltro(filtro, limite, offset);
    }

    //ORDENA DESCENDENTE POR CANTIDAD DE PRESTAMOS, PERMITE LISTAR POR CANTIDAD Y PAGINA
    public List<MaterialesDTO> listarMasSolicitados(int limite, int pagina) throws BusinessException {
        BusinessValidator.validarPaginacion(limite, pagina);
        int offset = (pagina - 1) * limite;
        return this.materialDAO.listarMasSolicitados(limite, offset);
    }
    //ORDENA DESCENDENTE POR MATERIALES RECIENTES, PERMITE LISTAR POR CANTIDAD Y PAGINA

    public List<MaterialesDTO> listarMasRecientes(int limite, int pagina) throws BusinessException {
        BusinessValidator.validarPaginacion(limite, pagina);
        int offset = (pagina - 1) * limite;
        return this.materialDAO.listarMasRecientes(limite, offset);
    }

    //LISTA POR SEDE Y CARACTERES FILTRO / 0->POR AUTOR, 1->POR TITULO
    public List<MaterialesDTO> listarPorSedeYFiltro(Integer idSede, String filtro, boolean porTitulo, int limite, int pagina) throws BusinessException {
        BusinessValidator.validarId(idSede, "sede");
        if (filtro == null || filtro.isBlank()) {
            throw new BusinessException("El filtro de búsqueda no puede estar vacío.");
        }
        BusinessValidator.validarPaginacion(limite, pagina);
        int offset = (pagina - 1) * limite;
        return materialDAO.listarPorSedeYFiltro(idSede, filtro, porTitulo, limite, offset);
    }

    public int contarTodos() {
        return this.materialDAO.contarTodos();
    }

    public List<CreadoresDTO> listarCreadoresPorMaterial(Integer idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return this.materialDAO.listarCreadoresPorMaterial(idMaterial);
    }

    public List<TemasDTO> listarTemasPorMaterial(Integer idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return this.materialDAO.listarTemasPorMaterial(idMaterial);
    }

    public List<MaterialesDTO> listarPaginadoPorNivel(Nivel nivel, int limite, int pagina) throws BusinessException {
        BusinessValidator.validarPaginacion(limite, pagina);
        if (nivel == null) {
            throw new BusinessException("Debe indicar un nivel de inglés válido.");
        }
        int offset = (pagina - 1) * limite;
        List<MaterialesDTO> materiales = new MaterialDAOImpl().listarPaginadoPorNivel(nivel, limite, offset);
        for (MaterialesDTO mat : materiales) {
            // Rellenar cantidad de ejemplares físicos disponibles
            mat.setDisponiblesFisicos(this.contarDisponiblesFisicosPorMaterial(mat.getIdMaterial()));
            // Rellenar autor principal (si existe)
            try {
                mat.setAutorPrincipal(this.obtenerNombreCreadorRandomPorMaterial(mat.getIdMaterial()));
            } catch (Exception e) {
                mat.setAutorPrincipal("");
            }
        }
        return materiales;
    }

    public List<MaterialesDTO> listarPaginadoPorTema(String descripcionTema, int limite, int pagina) throws BusinessException {
        BusinessValidator.validarTexto(descripcionTema, "descripcion tema");
        BusinessValidator.validarPaginacion(limite, pagina);
        int offset = (pagina - 1) * limite;
        return new MaterialDAOImpl().listarPaginadoPorTema(descripcionTema, limite, offset);
    }

    public List<MaterialesDTO> listarPaginadoPorEditorial(String nombreEditorial, int limite, int pagina) throws BusinessException {
        BusinessValidator.validarTexto(nombreEditorial, "nombre editorial");
        BusinessValidator.validarPaginacion(limite, pagina);
        int offset = (pagina - 1) * limite;
        return materialDAO.listarPaginadoPorEditorial(nombreEditorial, limite, offset);
    }

    public String obtenerNombreCreadorRandomPorMaterial(Integer idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        String nombre = materialDAO.obtenerNombreCreadorRandomPorMaterial(idMaterial);
        return (nombre != null && !nombre.isBlank()) ? nombre : "";
    }

    public int contarStockFisicoPorMaterial(int idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return new EjemplarDAOImpl().contarEjemplaresPorFiltros(idMaterial, null, null, TipoEjemplar.FISICO);
    }

    public int contarDisponiblesFisicosPorMaterial(int idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return new EjemplarDAOImpl().contarEjemplaresPorFiltros(idMaterial, null, true, TipoEjemplar.FISICO);
    }

    public int contarPrestadosFisicosPorMaterial(int idMaterial) throws BusinessException {
        BusinessValidator.validarId(idMaterial, "material");
        return new EjemplarDAOImpl().contarEjemplaresPorFiltros(idMaterial, null, false, TipoEjemplar.FISICO);
    }

    public List<MaterialesDTO> listarMaterialesPorTituloParcialPaginado(String textoBusqueda, Integer sedeId, int limite, int pagina) throws BusinessException {
        BusinessValidator.validarPaginacion(limite, pagina);
        int offset = (pagina - 1) * limite;
        return materialDAO.listarMaterialesPorTituloParcialPaginado(textoBusqueda, sedeId, limite, offset);
    }

    public int contarMaterialesPorSede(int idSede) throws BusinessException {
        BusinessValidator.validarId(idSede, "sede");
        return materialDAO.contarMaterialesPorSede(idSede);
    }

    // BUSCADOR PRINCIPAL PARA USUARIOS
    public List<MaterialesDTO> buscarMaterialesUsuario(
            Integer idTema,
            Integer idAutor,
            Integer idNivel,
            String filtro,
            int limite,
            int pagina
    ) throws BusinessException {

        BusinessValidator.validarPaginacion(limite, pagina);
        BusinessValidator.validarIdNegativoPermitido(idTema, "tema");
        BusinessValidator.validarIdNegativoPermitido(idAutor, "autor");
        BusinessValidator.validarIdNegativoPermitido(idNivel, "nivel");

        int offset = (pagina - 1) * limite;

        return materialDAO.buscarMaterialesUsuario(idTema, idAutor, idNivel, filtro, limite, offset);
    }

    // CONTADOR PARA BUSCADOR DE USUARIOS
    public int contarMaterialesUsuario(
            Integer idTema,
            Integer idAutor,
            Integer idNivel,
            String filtro
    ) throws BusinessException {

        BusinessValidator.validarIdNegativoPermitido(idTema, "tema");
        BusinessValidator.validarIdNegativoPermitido(idAutor, "autor");
        BusinessValidator.validarIdNegativoPermitido(idNivel, "nivel");

        return materialDAO.contarMaterialesUsuario(idTema, idAutor, idNivel, filtro);
    }

    public int contarMaterialesTotalPorFiltro(String textoBusqueda, Integer sedeId) throws BusinessException {
        return materialDAO.contarMaterialesTotalPorFiltro(textoBusqueda, sedeId);
    }

}
