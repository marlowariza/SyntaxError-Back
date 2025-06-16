package com.syntaxerror.biblioteca.persistance.dao.impl.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.syntaxerror.biblioteca.db.DBManager;
import com.syntaxerror.biblioteca.model.CreadoresDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.enums.TipoCreador;

public class CreadorMaterialDAOImpl extends DAOImplRelaciones {
    private static final String TABLA_INTERMEDIA = "BIB_MATERIALES_CREADORES";
    private static final String COL_CREADOR = "CREADOR_IDCREADOR";
    private static final String COL_MATERIAL = "MATERIAL_IDMATERIAL";
    
    public CreadorMaterialDAOImpl() {
        super();
    }
    public void asociarMaterial(Integer idCreador, Integer idMaterial) {
        this.asociar(idCreador, idMaterial);
    }
    
    public void desasociarMaterial(Integer idCreador, Integer idMaterial) {
        this.desasociar(idCreador, idMaterial);
    }
    
    public boolean existeRelacionConMaterial(Integer idCreador, Integer idMaterial) {
        return this.existeRelacion(idCreador, idMaterial);
    }
    
    public void eliminarAsociacionesConMateriales(Integer idCreador) {
        this.eliminarAsociacionesA(idCreador);
    }

    public void eliminarAsociacionesConCreadores(Integer idMaterial) {
        this.eliminarAsociacionesB(idMaterial);
    }
    
    @Override
    protected String getNombreTabla() {
        return TABLA_INTERMEDIA;
    }
    
    @Override
    protected String getColumnaA() {
        return COL_CREADOR;
    }
    
    @Override
    protected String getColumnaB() {
        return COL_MATERIAL;
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
    
    private CreadoresDTO mapearCreador(ResultSet rs) throws SQLException {
        CreadoresDTO creador = new CreadoresDTO();
        creador.setIdCreador(rs.getInt("ID_CREADOR"));
        creador.setNombre(rs.getString("NOMBRE"));
        creador.setPaterno(rs.getString("PATERNO"));
        creador.setMaterno(rs.getString("MATERNO"));
        creador.setSeudonimo(rs.getString("SEUDONIMO"));
        creador.setTipo(TipoCreador.valueOf(rs.getString("TIPO_CREADOR")));
        creador.setNacionalidad(rs.getString("NACIONALIDAD"));
        creador.setActivo(rs.getInt("ACTIVO") == 1);
        return creador;
    }
    
    public ArrayList<MaterialesDTO> listarPorCreador(Integer idCreador) {
        ArrayList<MaterialesDTO> materiales = new ArrayList<>();
        String sql = "SELECT m.* FROM BIB_MATERIALES m " +
                    "INNER JOIN " + TABLA_INTERMEDIA + " mc ON m.ID_MATERIAL = mc.MATERIAL_IDMATERIAL " +
                    "WHERE mc.CREADOR_IDCREADOR = ?";
        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareStatement(sql);
            this.statement.setInt(1, idCreador);
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
    
    public ArrayList<CreadoresDTO> listarPorMaterial(Integer idMaterial) {
        ArrayList<CreadoresDTO> creadores = new ArrayList<>();
        String sql = "SELECT c.* FROM BIB_CREADORES c " +
                    "INNER JOIN " + TABLA_INTERMEDIA + " mc ON c.ID_CREADOR = mc.CREADOR_IDCREADOR " +
                    "WHERE mc.MATERIAL_IDMATERIAL = ?";
        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareStatement(sql);
            this.statement.setInt(1, idMaterial);
            this.resultSet = this.statement.executeQuery();
            while (this.resultSet.next()) {
                creadores.add(mapearCreador(this.resultSet));
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
        return creadores;
    }
} 