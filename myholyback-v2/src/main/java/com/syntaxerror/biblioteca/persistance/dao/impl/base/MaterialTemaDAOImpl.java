package com.syntaxerror.biblioteca.persistance.dao.impl.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.syntaxerror.biblioteca.db.DBManager;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.TemasDTO;
import com.syntaxerror.biblioteca.model.enums.Categoria;

public class MaterialTemaDAOImpl extends DAOImplRelaciones {
    private static final String TABLA_INTERMEDIA = "BIB_MATERIALES_TEMAS";
    private static final String COL_MATERIAL = "MATERIAL_IDMATERIAL";
    private static final String COL_TEMA = "TEMA_IDTEMA";

    public MaterialTemaDAOImpl() {
        super();
    }

    // Métodos wrapper para mantener compatibilidad
    public void asociarTema(Integer idMaterial, Integer idTema) {
        this.asociar(idMaterial, idTema);
    }

    public void desasociarTema(Integer idMaterial, Integer idTema) {
        this.desasociar(idMaterial, idTema);
    }

    public boolean existeRelacionConTema(Integer idMaterial, Integer idTema) {
        return this.existeRelacion(idMaterial, idTema);
    }

    /**
     * Elimina todas las asociaciones de temas para un material específico.
     * Este método se usa cuando se quiere eliminar un material y necesitamos
     * eliminar primero todas sus relaciones con temas.
     */
    public void eliminarAsociacionesConTemas(Integer idMaterial) {
        this.eliminarAsociacionesA(idMaterial);
    }

    /**
     * Elimina todas las asociaciones de materiales para un tema específico.
     * Este método se usa cuando se quiere eliminar un tema y necesitamos
     * eliminar primero todas sus relaciones con materiales.
     */
    public void eliminarAsociacionesConMateriales(Integer idTema) {
        this.eliminarAsociacionesB(idTema);
    }

    @Override
    protected String getNombreTabla() {
        return TABLA_INTERMEDIA;
    }

    @Override
    protected String getColumnaA() {
        return COL_MATERIAL;
    }

    @Override
    protected String getColumnaB() {
        return COL_TEMA;
    }

    private TemasDTO mapearTema(ResultSet rs) throws SQLException {
        TemasDTO tema = new TemasDTO();
        tema.setIdTema(rs.getInt("ID_TEMA"));
        tema.setDescripcion(rs.getString("DESCRIPCION"));
        tema.setCategoria(Categoria.valueOf(rs.getString("CATEGORIA")));
        int idPadre = rs.getInt("ID_TEMA_PADRE");
        if (!rs.wasNull() && idPadre > 0) {
            TemasDTO temaPadre = new TemasDTO();
            temaPadre.setIdTema(idPadre);
            tema.setTemaPadre(temaPadre);
        }
        return tema;
    }

    private MaterialesDTO mapearMaterial(ResultSet rs) throws SQLException {
        MaterialesDTO material = new MaterialesDTO();
        material.setIdMaterial(rs.getInt("ID_MATERIAL"));
        material.setTitulo(rs.getString("TITULO"));
        material.setEdicion(rs.getString("EDICION"));
        material.setAnioPublicacion(rs.getInt("ANHIO_PUBLICACION"));
        material.setPortada(rs.getString("PORTADA"));
        material.setVigente(rs.getInt("VIGENTE") == 1);
        return material;
    }

    public ArrayList<TemasDTO> listarPorMaterial(Integer idMaterial) {
        ArrayList<TemasDTO> temas = new ArrayList<>();
        String sql = "SELECT t.* FROM BIB_TEMAS t " +
                "INNER JOIN " + TABLA_INTERMEDIA + " mt ON t.ID_TEMA = mt.TEMA_IDTEMA " +
                "WHERE mt.MATERIAL_IDMATERIAL = ?";
        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareStatement(sql);
            this.statement.setInt(1, idMaterial);
            this.resultSet = this.statement.executeQuery();
            while (this.resultSet.next()) {
                temas.add(mapearTema(this.resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return temas;
    }

    public ArrayList<MaterialesDTO> listarPorTema(Integer idTema) {
        ArrayList<MaterialesDTO> materiales = new ArrayList<>();
        String sql = "SELECT m.* FROM BIB_MATERIALES m " +
                "INNER JOIN " + TABLA_INTERMEDIA + " mt ON m.ID_MATERIAL = mt.MATERIAL_IDMATERIAL " +
                "WHERE mt.TEMA_IDTEMA = ?";
        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareStatement(sql);
            this.statement.setInt(1, idTema);
            this.resultSet = this.statement.executeQuery();
            while (this.resultSet.next()) {
                materiales.add(mapearMaterial(this.resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return materiales;
    }
} 