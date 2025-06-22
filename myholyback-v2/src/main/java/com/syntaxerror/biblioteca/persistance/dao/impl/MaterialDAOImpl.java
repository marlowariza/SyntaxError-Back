package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.persistance.dao.impl.base.DAOImplBase;
import com.syntaxerror.biblioteca.persistance.dao.impl.base.MaterialTemaDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.base.CreadorMaterialDAOImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.syntaxerror.biblioteca.model.CreadoresDTO;
import com.syntaxerror.biblioteca.model.EditorialesDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.NivelesInglesDTO;
import com.syntaxerror.biblioteca.model.TemasDTO;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;
import com.syntaxerror.biblioteca.persistance.dao.MaterialDAO;
import com.syntaxerror.biblioteca.model.EjemplaresDTO;
import com.syntaxerror.biblioteca.model.enums.Nivel;
import java.util.function.Consumer;

public class MaterialDAOImpl extends DAOImplBase implements MaterialDAO {

    private MaterialesDTO material;
    private CreadorMaterialDAOImpl creadoresMaterialesDAO;
    private MaterialTemaDAOImpl materialesTemasDAO;
    private EditorialDAOImpl editorialDAO;
    private NivelInglesDAOImpl nivelDAO;

    private boolean cargarRelaciones = true;

    public MaterialDAOImpl() {
        super("BIB_MATERIALES");
        this.retornarLlavePrimaria = true;
        this.material = null;
        this.creadoresMaterialesDAO = new CreadorMaterialDAOImpl();
        this.materialesTemasDAO = new MaterialTemaDAOImpl();
        this.editorialDAO = new EditorialDAOImpl();
        this.nivelDAO = new NivelInglesDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("ID_MATERIAL", true, true));
        this.listaColumnas.add(new Columna("TITULO", false, false));
        this.listaColumnas.add(new Columna("EDICION", false, false));
        this.listaColumnas.add(new Columna("ANHIO_PUBLICACION", false, false));
        this.listaColumnas.add(new Columna("PORTADA", false, false));
        this.listaColumnas.add(new Columna("VIGENTE", false, false));
        this.listaColumnas.add(new Columna("NIVEL_IDNIVEL", false, false));
        this.listaColumnas.add(new Columna("EDITORIAL_IDEDITORIAL", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.material.getTitulo());
        this.statement.setString(2, this.material.getEdicion());
        this.statement.setInt(3, this.material.getAnioPublicacion());
        this.statement.setString(4, this.material.getPortada());
        this.statement.setInt(5, this.material.getVigente() ? 1 : 0);
        this.statement.setInt(6, this.material.getNivel().getIdNivel());
        this.statement.setInt(7, this.material.getEditorial().getIdEditorial());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.material.getTitulo());
        this.statement.setString(2, this.material.getEdicion());
        this.statement.setInt(3, this.material.getAnioPublicacion());
        this.statement.setString(4, this.material.getPortada());
        this.statement.setInt(5, this.material.getVigente() ? 1 : 0);
        this.statement.setInt(6, this.material.getNivel().getIdNivel());
        this.statement.setInt(7, this.material.getEditorial().getIdEditorial());
        this.statement.setInt(8, this.material.getIdMaterial());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.material.getIdMaterial());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.material.getIdMaterial());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.material = new MaterialesDTO();
        this.material.setIdMaterial(this.resultSet.getInt("ID_MATERIAL"));
        this.material.setTitulo(this.resultSet.getString("TITULO"));
        this.material.setEdicion(this.resultSet.getString("EDICION"));
        this.material.setAnioPublicacion(this.resultSet.getInt("ANHIO_PUBLICACION"));
        this.material.setPortada(this.resultSet.getString("PORTADA"));
        this.material.setVigente(this.resultSet.getInt("VIGENTE") == 1);

                // Relación con NivelesIngles (CORREGIDO)
        int nivelId = this.resultSet.getInt("NIVEL_IDNIVEL");
        if (!this.resultSet.wasNull()) {
             this.material.setNivel(this.nivelDAO.obtenerPorId(nivelId));
        }
        
        // Relación con Editoriales (CORREGIDO)
        int editorialId = this.resultSet.getInt("EDITORIAL_IDEDITORIAL");
        if (!this.resultSet.wasNull()) {
            this.material.setEditorial(this.editorialDAO.obtenerPorId(editorialId));
        }

        // Cargar relaciones
        // === CONTROL DE RELACIONES ===
        if (this.cargarRelaciones) {
            this.material.setCreadores(creadoresMaterialesDAO.listarPorMaterial(this.material.getIdMaterial()));
            this.material.setTemas(materialesTemasDAO.listarPorMaterial(this.material.getIdMaterial()));
        }
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.material = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.material);
    }

    @Override
    public Integer insertar(MaterialesDTO material) {
        this.material = material;
        Integer idMaterial = super.insertar();
        if (idMaterial != null && idMaterial > 0) {
            // Insertar relaciones con creadores
            if (material.getCreadores() != null) {
                for (CreadoresDTO creador : material.getCreadores()) {
                    creadoresMaterialesDAO.asociarMaterial(creador.getIdCreador(), idMaterial);
                }
            }
            // Insertar relaciones con temas
            if (material.getTemas() != null) {
                for (TemasDTO tema : material.getTemas()) {
                    materialesTemasDAO.asociarTema(idMaterial, tema.getIdTema());
                }
            }
        }
        return idMaterial;
    }

    @Override
    public MaterialesDTO obtenerPorId(Integer idMaterial) {
        this.material = new MaterialesDTO();
        this.material.setIdMaterial(idMaterial);
        super.obtenerPorId();
        return this.material;
    }

    @Override
    public ArrayList<MaterialesDTO> listarTodos() {
        return (ArrayList<MaterialesDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(MaterialesDTO material) {
        this.material = material;
        Integer resultado = super.modificar();
        if (resultado != null && resultado > 0) {
            // Eliminar y recrear relaciones con creadores
            creadoresMaterialesDAO.eliminarAsociacionesConCreadores(material.getIdMaterial());
            if (material.getCreadores() != null) {
                for (CreadoresDTO creador : material.getCreadores()) {
                    creadoresMaterialesDAO.asociarMaterial(creador.getIdCreador(), material.getIdMaterial());
                }
            }

            // Eliminar y recrear relaciones con temas
            materialesTemasDAO.eliminarAsociacionesConTemas(material.getIdMaterial());
            if (material.getTemas() != null) {
                for (TemasDTO tema : material.getTemas()) {
                    materialesTemasDAO.asociarTema(material.getIdMaterial(), tema.getIdTema());
                }
            }
        }
        return resultado;
    }

    @Override
    public Integer eliminar(MaterialesDTO material) {
        this.material = material;
        // Primero eliminar las relaciones
        creadoresMaterialesDAO.eliminarAsociacionesConCreadores(material.getIdMaterial());
        materialesTemasDAO.eliminarAsociacionesConTemas(material.getIdMaterial());

        // Eliminar los ejemplares asociados
        EjemplarDAOImpl ejemplarDAO = new EjemplarDAOImpl();
        ArrayList<EjemplaresDTO> ejemplares = ejemplarDAO.listarEjemplaresPorFiltros(material.getIdMaterial(), null, null, null);
        for (EjemplaresDTO ejemplar : ejemplares) {
            ejemplarDAO.eliminar(ejemplar);
        }

        return super.eliminar();
    }

    @Override
    public List<MaterialesDTO> listarPorTituloConteniendo(String texto, int limite, int offset) {

        // Elige estrategia según longitud del filtro
        boolean usarFulltext = texto.trim().length() >= 3;

        String sql;
        if (usarFulltext) {
            sql = """
            SELECT * FROM BIB_MATERIALES
        WHERE MATCH(TITULO) AGAINST (? IN BOOLEAN MODE)
        LIMIT ? OFFSET ?
        """;
        } else {
            sql = """
            SELECT * FROM BIB_MATERIALES
        WHERE TITULO COLLATE utf8mb4_general_ci LIKE ?
        ORDER BY TITULO
        LIMIT ? OFFSET ?
        """;
        }

        this.cargarRelaciones = false;
        try {
            return (List<MaterialesDTO>) this.listarTodos(
                    sql,
                    obj -> {
                        try {
                            if (usarFulltext) {
                                String filtro = "+" + texto.trim() + "*";
                                this.statement.setString(1, filtro);
                            } else {
                                String filtro = "%" + texto.trim() + "%";
                                this.statement.setString(1, filtro);
                            }
                            this.statement.setInt(2, limite);
                            this.statement.setInt(3, offset);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    null
            );
        } finally {
            this.cargarRelaciones = true;
        }
    }

    @Override
    public ArrayList<MaterialesDTO> listarVigentesPorTituloConteniendo(String texto, int limite, int offset) {
        // Decide si usar FULLTEXT o LIKE según longitud
        boolean usarFulltext = texto.trim().length() >= 3;

        String sql;
        if (usarFulltext) {
            sql = """
            SELECT * FROM BIB_MATERIALES
        WHERE MATCH(TITULO) AGAINST (? IN BOOLEAN MODE)
          AND VIGENTE = TRUE
        LIMIT ? OFFSET ?
        """;
        } else {
            sql = """
            SELECT * FROM BIB_MATERIALES
        WHERE TITULO COLLATE utf8mb4_general_ci LIKE ?
          AND VIGENTE = TRUE
        ORDER BY TITULO
        LIMIT ? OFFSET ?
        """;
        }

        this.cargarRelaciones = false;
        try {
            return (ArrayList<MaterialesDTO>) this.listarTodos(
                    sql,
                    obj -> {
                        try {
                            if (usarFulltext) {
                                String filtro = "+" + texto.trim() + "*";
                                this.statement.setString(1, filtro);
                            } else {
                                String filtro = "%" + texto.trim() + "%";
                                this.statement.setString(1, filtro);
                            }
                            this.statement.setInt(2, limite);
                            this.statement.setInt(3, offset);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    null
            );
        } finally {
            this.cargarRelaciones = true;
        }
    }

    @Override
    public List<MaterialesDTO> listarVigentesPorSede(Integer idSede, int limite, int offset) {
        String sql = """
        SELECT DISTINCT m.*
    FROM BIB_MATERIALES m
    JOIN BIB_EJEMPLARES e ON m.ID_MATERIAL = e.MATERIAL_IDMATERIAL
    WHERE m.VIGENTE = TRUE 
      AND e.SEDE_IDSEDE = ?
    ORDER BY m.TITULO
    LIMIT ? OFFSET ?
    """;

        this.cargarRelaciones = false;
        try {
            return (List<MaterialesDTO>) this.listarTodos(
                    sql,
                    obj -> {
                        try {
                            this.statement.setInt(1, idSede);
                            this.statement.setInt(2, limite);
                            this.statement.setInt(3, offset);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    null
            );
        } finally {
            this.cargarRelaciones = true;
        }
    }

    @Override
    public ArrayList<MaterialesDTO> listarPorSede(Integer idSede, int limite, int offset) {
        String sql = """
        SELECT DISTINCT m.*
    FROM BIB_MATERIALES m
    JOIN BIB_EJEMPLARES e ON m.ID_MATERIAL = e.MATERIAL_IDMATERIAL
    WHERE e.SEDE_IDSEDE = ?
    ORDER BY m.TITULO
    LIMIT ? OFFSET ?
    """;

        this.cargarRelaciones = false;
        try {
            return (ArrayList<MaterialesDTO>) this.listarTodos(
                    sql,
                    obj -> {
                        try {
                            this.statement.setInt(1, idSede);
                            this.statement.setInt(2, limite);
                            this.statement.setInt(3, offset);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    null
            );
        } finally {
            this.cargarRelaciones = true;
        }
    }

    @Override
    public List<MaterialesDTO> listarMaterialesVigentesPorCreadorFiltro(String filtro, int limite, int offset) {
        String sql = """
    SELECT DISTINCT m.*
    FROM BIB_MATERIALES m
    JOIN BIB_MATERIALES_CREADORES mc ON m.ID_MATERIAL = mc.MATERIAL_IDMATERIAL
    JOIN BIB_CREADORES c ON mc.CREADOR_IDCREADOR = c.ID_CREADOR
    WHERE m.VIGENTE = TRUE
      AND c.TIPO_CREADOR = 'AUTOR'
      AND c.ACTIVO = 1
      AND (
          LOWER(c.NOMBRE) LIKE ?
          OR LOWER(c.PATERNO) LIKE ?
          OR LOWER(c.MATERNO) LIKE ?
          OR LOWER(c.SEUDONIMO) LIKE ?
      )
    ORDER BY m.TITULO
    LIMIT ? OFFSET ?
    """;

        this.cargarRelaciones = false;
        try {
            return (List<MaterialesDTO>) this.listarTodos(
                    sql,
                    obj -> {
                        try {
                            String valor = "%" + filtro.trim().toLowerCase() + "%";
                            for (int i = 1; i <= 4; i++) {
                                this.statement.setString(i, valor);
                            }
                            this.statement.setInt(5, limite);
                            this.statement.setInt(6, offset);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    null
            );
        } finally {
            this.cargarRelaciones = true;
        }
    }

//    @Override
//public List<MaterialesDTO> listarPaginadoPorAutor(String filtro, int limite, int offset) {
//    String sql = """
//    SELECT %s
//    FROM BIB_MATERIALES m
//    JOIN BIB_MATERIALES_CREADORES mc ON m.ID_MATERIAL = mc.MATERIAL_IDMATERIAL
//    JOIN BIB_CREADORES c ON mc.CREADOR_IDCREADOR = c.ID_CREADOR
//    WHERE m.VIGENTE = TRUE
//      AND c.TIPO_CREADOR = 'AUTOR'
//      AND c.ACTIVO = 1
//      AND (
//          LOWER(c.NOMBRE) LIKE ?
//          OR LOWER(c.PATERNO) LIKE ?
//          OR LOWER(c.MATERNO) LIKE ?
//          OR LOWER(c.SEUDONIMO) LIKE ?
//      )
//    ORDER BY m.TITULO
//    LIMIT ? OFFSET ?
//    """.formatted(this.generarListaDeCamposConAlias("m"));
//
//    this.cargarRelaciones = false;
//    try {
//        return (List<MaterialesDTO>) this.listarTodos(
//                sql,
//                params -> {
//                    try {
//                        String valor = "%" + filtro.trim().toLowerCase() + "%";
//                        for (int i = 1; i <= 4; i++) {
//                            this.statement.setString(i, valor);
//                        }
//                        this.statement.setInt(5, limite);
//                        this.statement.setInt(6, offset);
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//                },
//                null
//        );
//    } finally {
//        this.cargarRelaciones = true;
//    }
//}
    @Override
    public List<MaterialesDTO> listarMasSolicitados(int limite, int offset) {
        String sql = """
        SELECT m.*, COUNT(pde.EJEMPLAR_IDEJEMPLAR) AS total_solicitudes
        FROM BIB_MATERIALES m
        JOIN BIB_EJEMPLARES e ON m.ID_MATERIAL = e.MATERIAL_IDMATERIAL
        JOIN BIB_PRESTAMOS_DE_EJEMPLARES pde ON e.ID_EJEMPLAR = pde.EJEMPLAR_IDEJEMPLAR
        GROUP BY m.ID_MATERIAL
        ORDER BY total_solicitudes DESC, m.ID_MATERIAL DESC
        LIMIT ? OFFSET ?
    """;

        this.cargarRelaciones = false;
        try {
            return (List<MaterialesDTO>) this.listarTodos(
                    sql,
                    params -> {
                        try {
                            int[] p = (int[]) params;
                            this.statement.setInt(1, p[0]); // limite
                            this.statement.setInt(2, p[1]); // offset
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    new int[]{limite, offset}
            );
        } finally {
            this.cargarRelaciones = true;
        }
    }

    @Override
    public List<MaterialesDTO> listarMasRecientes(int limite, int offset) {
        String sql = """
        SELECT *
        FROM BIB_MATERIALES
        ORDER BY ANHIO_PUBLICACION DESC, ID_MATERIAL DESC
        LIMIT ? OFFSET ?
    """;

        this.cargarRelaciones = false;
        try {
            return (List<MaterialesDTO>) this.listarTodos(
                    sql,
                    params -> {
                        try {
                            int[] p = (int[]) params;
                            this.statement.setInt(1, p[0]); // limite
                            this.statement.setInt(2, p[1]); // offset
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    new int[]{limite, offset}
            );
        } finally {
            this.cargarRelaciones = true;
        }
    }

    /**
     * Lista materiales vigentes por sede y filtro dinámico (título o autor)
     * usando listarTodos genérico.
     */
    @Override
    public List<MaterialesDTO> listarPorSedeYFiltro(Integer idSede, String filtro, boolean porTitulo, int limite, int offset) {
        String sql;
        if (porTitulo) {
            sql = """
        SELECT DISTINCT m.*
        FROM BIB_MATERIALES m
        JOIN BIB_EJEMPLARES e ON m.ID_MATERIAL = e.MATERIAL_IDMATERIAL
        WHERE m.VIGENTE = TRUE
          AND e.SEDE_IDSEDE = ?
          AND LOWER(m.TITULO) LIKE ?
        ORDER BY m.TITULO
        LIMIT ? OFFSET ?
        """;
        } else {
            sql = """
        SELECT DISTINCT m.*
        FROM BIB_MATERIALES m
        JOIN BIB_EJEMPLARES e ON m.ID_MATERIAL = e.MATERIAL_IDMATERIAL
        JOIN BIB_MATERIALES_CREADORES mc ON m.ID_MATERIAL = mc.MATERIAL_IDMATERIAL
        JOIN BIB_CREADORES c ON mc.CREADOR_IDCREADOR = c.ID_CREADOR
        WHERE m.VIGENTE = TRUE
          AND e.SEDE_IDSEDE = ?
          AND c.TIPO_CREADOR = 'AUTOR'
          AND (
              LOWER(c.NOMBRE) LIKE ?
              OR LOWER(c.PATERNO) LIKE ?
              OR LOWER(c.MATERNO) LIKE ?
              OR LOWER(c.SEUDONIMO) LIKE ?
          )
        ORDER BY m.TITULO
        LIMIT ? OFFSET ?
        """;
        }

        Consumer<Object> binder = params -> {
            try {
                String likeFiltro = "%" + filtro.trim().toLowerCase() + "%";
                this.statement.setInt(1, idSede);
                this.statement.setString(2, likeFiltro);
                if (!porTitulo) {
                    this.statement.setString(3, likeFiltro);
                    this.statement.setString(4, likeFiltro);
                    this.statement.setString(5, likeFiltro);
                    this.statement.setInt(6, limite);
                    this.statement.setInt(7, offset);
                } else {
                    this.statement.setInt(3, limite);
                    this.statement.setInt(4, offset);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };

        this.cargarRelaciones = false;
        try {
            return this.listarTodos(sql, binder, null);
        } finally {
            this.cargarRelaciones = true;
        }
    }

    @Override
    public List<MaterialesDTO> listarTodosPaginado(int limite, int offset) {
        String sql = String.format("""
        SELECT %s
        FROM BIB_MATERIALES
        ORDER BY TITULO
        LIMIT ? OFFSET ?
    """, this.generarListaDeCampos());

        this.cargarRelaciones = false;
        try {
            return (List<MaterialesDTO>) this.listarTodos(
                    sql,
                    params -> {
                        int[] p = (int[]) params;
                        try {
                            this.statement.setInt(1, p[0]); // limite
                            this.statement.setInt(2, p[1]); // offset
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    new int[]{limite, offset}
            );
        } finally {
            this.cargarRelaciones = true;
        }
    }

    @Override
    public int contarTodos() {
        return super.contar();
    }

    @Override
    public List<CreadoresDTO> listarCreadoresPorMaterial(Integer idMaterial) {
        return this.creadoresMaterialesDAO.listarPorMaterial(idMaterial);
    }

    @Override
    public List<TemasDTO> listarTemasPorMaterial(Integer idMaterial) {
        return this.materialesTemasDAO.listarPorMaterial(idMaterial);
    }

    @Override
    public List<MaterialesDTO> listarPaginadoPorNivel(Nivel nivel, int limite, int offset) {
        String sql = """
        SELECT %s
        FROM BIB_MATERIALES m
        JOIN BIB_NIVELES_INGLES n ON m.NIVEL_IDNIVEL = n.ID_NIVEL
        WHERE n.NIVEL = ?
        ORDER BY m.TITULO
        LIMIT ? OFFSET ?
    """.formatted(this.generarListaDeCamposConAlias("m"));

        this.cargarRelaciones = false;
        try {
            return (List<MaterialesDTO>) this.listarTodos(
                    sql,
                    params -> {
                        try {
                            Object[] p = (Object[]) params;
                            this.statement.setString(1, nivel.name());
                            this.statement.setInt(2, limite);
                            this.statement.setInt(3, offset);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    new Object[]{nivel.name(), limite, offset}
            );
        } finally {
            this.cargarRelaciones = true;
        }
    }

    @Override
    public List<MaterialesDTO> listarPaginadoPorTema(String descripcionTema, int limite, int offset) {
        String sql = """
        SELECT %s
        FROM BIB_MATERIALES m
        JOIN BIB_MATERIALES_TEMAS mt ON m.ID_MATERIAL = mt.MATERIAL_IDMATERIAL
        JOIN BIB_TEMAS t ON mt.TEMA_IDTEMA = t.ID_TEMA
        WHERE UPPER(TRIM(t.DESCRIPCION)) = ?
        ORDER BY m.TITULO
        LIMIT ? OFFSET ?
    """.formatted(this.generarListaDeCamposConAlias("m"));

        this.cargarRelaciones = false;
        try {
            return (List<MaterialesDTO>) this.listarTodos(
                    sql,
                    params -> {
                        try {
                            Object[] p = (Object[]) params;
                            this.statement.setString(1, descripcionTema.trim().toUpperCase());
                            this.statement.setInt(2, limite);
                            this.statement.setInt(3, offset);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    new Object[]{descripcionTema, limite, offset}
            );
        } finally {
            this.cargarRelaciones = true;
        }
    }

    @Override
    public List<MaterialesDTO> listarPaginadoPorEditorial(String nombreEditorial, int limite, int offset) {
        String sql = """
    SELECT %s
    FROM BIB_MATERIALES m
    JOIN BIB_EDITORIALES e ON m.EDITORIAL_IDEDITORIAL = e.ID_EDITORIAL
    WHERE UPPER(TRIM(e.NOMBRE)) = ?
    ORDER BY m.TITULO
    LIMIT ? OFFSET ?
    """.formatted(this.generarListaDeCamposConAlias("m"));

        this.cargarRelaciones = false;
        try {
            return (List<MaterialesDTO>) this.listarTodos(
                    sql,
                    params -> {
                        try {
                            Object[] p = (Object[]) params;
                            this.statement.setString(1, nombreEditorial.trim().toUpperCase());
                            this.statement.setInt(2, limite);
                            this.statement.setInt(3, offset);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    new Object[]{nombreEditorial, limite, offset}
            );
        } finally {
            this.cargarRelaciones = true;
        }
    }

    @Override
    public String obtenerNombreCreadorRandomPorMaterial(Integer idMaterial) {
        String nombre = null;
        String sql = """
        SELECT 
            CASE 
                WHEN TRIM(c.SEUDONIMO) IS NOT NULL AND TRIM(c.SEUDONIMO) != '' 
                THEN TRIM(c.SEUDONIMO)
                ELSE CONCAT_WS(' ', TRIM(c.NOMBRE), TRIM(c.PATERNO), TRIM(c.MATERNO))
            END AS NOMBRE_COMPLETO
        FROM BIB_CREADORES c
        JOIN BIB_MATERIALES_CREADORES mc ON c.ID_CREADOR = mc.CREADOR_IDCREADOR
        WHERE mc.MATERIAL_IDMATERIAL = ?
          AND c.ACTIVO = 1
        ORDER BY RAND()
        LIMIT 1
    """;

        try {
            this.abrirConexion();
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, idMaterial);
            this.ejecutarConsultaEnBD();
            if (this.resultSet.next()) {
                nombre = this.resultSet.getString("NOMBRE_COMPLETO");
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerNombreCreadorRandomPorMaterial: " + e.getMessage());
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException e) {
                System.err.println("Error cerrar conexión: " + e.getMessage());
            }
        }

        return nombre;
    }
    
    @Override
    public List<MaterialesDTO> listarMaterialesPorTituloParcialPaginado(String textoBusqueda, Integer sedeId, int limite, int offset) {
        String sql = String.format("""
        SELECT DISTINCT %s
        FROM BIB_MATERIALES m
        JOIN BIB_EJEMPLARES e ON m.ID_MATERIAL = e.MATERIAL_IDMATERIAL
        WHERE 1=1
        """, this.generarListaDeCampos());
        
        if (textoBusqueda != null && !textoBusqueda.isEmpty()) {
            sql += " AND m.TITULO LIKE ?";
        }
        
        if (sedeId != -1) {
            sql += " AND e.Sede_IDSEDE = ?";
        }
        
        sql += " ORDER BY m.TITULO LIMIT ? OFFSET ?";

        return (List<MaterialesDTO>) this.listarTodos(
                sql,
                params -> {
                String busqueda = textoBusqueda != null && !textoBusqueda.isEmpty() ? "%" + textoBusqueda + "%" : null;// Agregar los comodines % para la búsqueda parcial
                int[] p = (int[]) params;
                try {
                    if (busqueda != null) {
                        this.statement.setString(1, busqueda); // Establecer el texto de búsqueda con comodines
                    }
                    if (sedeId != -1) {
                        // Si sedeId no es -1, se pasa como parámetro
                        this.statement.setInt(busqueda != null ? 2 : 1, sedeId); 
                        this.statement.setInt(busqueda != null ? 3 : 2, p[0]); // Limite
                        this.statement.setInt(busqueda != null ? 4 : 3, p[1]); // Offset
                    } else {
                        // Si no hay sedeId, solo paginación
                        this.statement.setInt(busqueda != null ? 2 : 1, p[0]);
                        this.statement.setInt(busqueda != null ? 3 : 2, p[1]);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            },
            new int[]{limite, offset}
        );
    }

}
