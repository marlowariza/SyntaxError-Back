package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.model.EditorialDTO;
import com.syntaxerror.biblioteca.model.MaterialDTO;
import com.syntaxerror.biblioteca.model.NivelInglesDTO;
import com.syntaxerror.biblioteca.persistance.dao.MaterialDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MaterialDAOImpl extends DAOImplBase implements MaterialDAO {

    private MaterialDTO material;

    public MaterialDAOImpl() {
        super("BIB_MATERIALES");
        this.retornarLlavePrimaria = true;
        this.material = null;
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

        //si es autoincremental, se salta el (1,ID)
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
        //En modificar el ID va al ultimo
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.material.getIdMaterial());
        //Para eliminar solo va el id
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.material.getIdMaterial());
        //Para obtener por Id igual solo el id
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.material = new MaterialDTO();
        this.material.setIdMaterial(this.resultSet.getInt("ID_MATERIAL"));
        this.material.setTitulo(this.resultSet.getString("TITULO"));
        this.material.setEdicion(this.resultSet.getString("EDICION"));
        this.material.setAnioPublicacion(this.resultSet.getInt("ANHIO_PUBLICACION"));
        this.material.setPortada(this.resultSet.getString("PORTADA"));
        this.material.setVigente(this.resultSet.getInt("VIGENTE") == 1);

        // Relación con NivelesIngles
        NivelInglesDTO nivel = new NivelInglesDTO();
        nivel.setIdNivel(this.resultSet.getInt("NIVEL_IDNIVEL"));
        this.material.setNivel(nivel);

        EditorialDTO editorial = new EditorialDTO();
        editorial.setIdEditorial(this.resultSet.getInt("EDITORIAL_IDEDITORIAL"));
        this.material.setEditorial(editorial);

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
    public Integer insertar(MaterialDTO material) {
        this.material = material;
        return super.insertar();
    }

    @Override
    public MaterialDTO obtenerPorId(Integer idMaterial) {
        this.material = new MaterialDTO();
        this.material.setIdMaterial(idMaterial);
        super.obtenerPorId();
        return this.material;
    }

    @Override
    public ArrayList<MaterialDTO> listarTodos() {
        return (ArrayList<MaterialDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(MaterialDTO material) {
        this.material = material;
        return super.modificar();
    }

    @Override
    public Integer eliminar(MaterialDTO material) {
        this.material = material;
        return super.eliminar();
    }

    @Override
    public List<MaterialDTO> listarMasSolicitados(int limite, int offset) {
        String sql = """
        SELECT m.*, COUNT(pde.EJEMPLAR_IDEJEMPLAR) AS total_solicitudes
        FROM BIB_MATERIALES m
        JOIN BIB_EJEMPLARES e ON m.ID_MATERIAL = e.MATERIAL_IDMATERIAL
        JOIN BIB_PRESTAMOS_DE_EJEMPLARES pde ON e.ID_EJEMPLAR = pde.EJEMPLAR_IDEJEMPLAR
        GROUP BY m.ID_MATERIAL
        ORDER BY total_solicitudes DESC, m.ID_MATERIAL DESC
        LIMIT ? OFFSET ?
    """;

        return (List<MaterialDTO>) this.listarTodos(
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
    }

    @Override
    public List<MaterialDTO> listarMasRecientes(int limite, int offset) {
        String sql = """
        SELECT *
        FROM BIB_MATERIALES
        ORDER BY ANHIO_PUBLICACION DESC, ID_MATERIAL DESC
        LIMIT ? OFFSET ?
    """;

        return (List<MaterialDTO>) this.listarTodos(
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
    }

    public List<MaterialDTO> listarPorSedeYFiltro(Integer idSede, String filtro, boolean porTitulo) {
        String sql;
        if (porTitulo) {
            sql = """
            SELECT DISTINCT m.*
            FROM BIB_MATERIALES m
            JOIN BIB_EJEMPLARES e ON m.ID_MATERIAL = e.MATERIAL_IDMATERIAL
            WHERE m.VIGENTE = TRUE
              AND e.SEDE_IDSEDE = ?
              AND LOWER(m.TITULO) LIKE ?
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
            """;
        }

        // Consumer para bindear parámetros:
        Consumer<Object> binder = params -> {
            try {
                String likeFiltro = "%" + filtro.toLowerCase().trim() + "%";
                this.statement.setInt(1, idSede);
                this.statement.setString(2, likeFiltro);
                if (!porTitulo) {
                    this.statement.setString(3, likeFiltro);
                    this.statement.setString(4, likeFiltro);
                    this.statement.setString(5, likeFiltro);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };

        return this.listarTodos(sql, binder, null);
    }
}
