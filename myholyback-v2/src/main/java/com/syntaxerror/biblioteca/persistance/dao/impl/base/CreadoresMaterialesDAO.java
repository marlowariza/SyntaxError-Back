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

public class CreadoresMaterialesDAO {
    
    private static final String TABLA_INTERMEDIA = "BIB_MATERIALES_CREADORES";
    private Connection conexion;
    private ResultSet resultSet;
    
    public CreadoresMaterialesDAO() {
        // Constructor vacío
    }
    
    private void abrirConexion() {
        this.conexion = DBManager.getInstance().getConnection();
    }
    
    private void cerrarConexion() throws SQLException {
        if (this.conexion != null) {
            this.conexion.close();
            this.conexion = null;
        }
    }
    
    public void asociarMaterial(Integer idCreador, Integer idMaterial) {
        String sql = "INSERT INTO " + TABLA_INTERMEDIA + " (CREADOR_IDCREADOR, MATERIAL_IDMATERIAL) VALUES (?, ?)";
        try {
            this.abrirConexion();
            PreparedStatement pstmt = this.conexion.prepareStatement(sql);
            pstmt.setInt(1, idCreador);
            pstmt.setInt(2, idMaterial);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void desasociarMaterial(Integer idCreador, Integer idMaterial) {
        String sql = "DELETE FROM " + TABLA_INTERMEDIA + " WHERE CREADOR_IDCREADOR = ? AND MATERIAL_IDMATERIAL = ?";
        try {
            this.abrirConexion();
            PreparedStatement pstmt = this.conexion.prepareStatement(sql);
            pstmt.setInt(1, idCreador);
            pstmt.setInt(2, idMaterial);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean existeRelacionConMaterial(Integer idCreador, Integer idMaterial) {
        String sql = "SELECT COUNT(*) FROM " + TABLA_INTERMEDIA + 
                    " WHERE CREADOR_IDCREADOR = ? AND MATERIAL_IDMATERIAL = ?";
        try {
            this.abrirConexion();
            PreparedStatement pstmt = this.conexion.prepareStatement(sql);
            pstmt.setInt(1, idCreador);
            pstmt.setInt(2, idMaterial);
            this.resultSet = pstmt.executeQuery();
            if (this.resultSet.next()) {
                return this.resultSet.getInt(1) > 0;
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
        return false;
    }
    
    public ArrayList<MaterialesDTO> listarPorCreador(Integer idCreador) {
        ArrayList<MaterialesDTO> materiales = new ArrayList<>();
        String sql = "SELECT m.* FROM BIB_MATERIALES m " +
                    "INNER JOIN " + TABLA_INTERMEDIA + " mc ON m.ID_MATERIAL = mc.MATERIAL_IDMATERIAL " +
                    "WHERE mc.CREADOR_IDCREADOR = ?";
        try {
            this.abrirConexion();
            PreparedStatement pstmt = this.conexion.prepareStatement(sql);
            pstmt.setInt(1, idCreador);
            this.resultSet = pstmt.executeQuery();
            while (this.resultSet.next()) {
                MaterialesDTO material = new MaterialesDTO();
                material.setIdMaterial(this.resultSet.getInt("ID_MATERIAL"));
                material.setTitulo(this.resultSet.getString("TITULO"));
                material.setEdicion(this.resultSet.getString("EDICION"));
                material.setAnioPublicacion(this.resultSet.getInt("ANHIO_PUBLICACION"));
                material.setPortada(this.resultSet.getString("PORTADA"));
                material.setVigente(this.resultSet.getInt("VIGENTE") == 1);
                materiales.add(material);
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
            PreparedStatement pstmt = this.conexion.prepareStatement(sql);
            pstmt.setInt(1, idMaterial);
            this.resultSet = pstmt.executeQuery();
            while (this.resultSet.next()) {
                CreadoresDTO creador = new CreadoresDTO();
                creador.setIdCreador(this.resultSet.getInt("ID_CREADOR"));
                creador.setNombre(this.resultSet.getString("NOMBRE"));
                creador.setPaterno(this.resultSet.getString("PATERNO"));
                creador.setMaterno(this.resultSet.getString("MATERNO"));
                creador.setSeudonimo(this.resultSet.getString("SEUDONIMO"));
                creador.setTipo(TipoCreador.valueOf(this.resultSet.getString("TIPO_CREADOR")));
                creador.setNacionalidad(this.resultSet.getString("NACIONALIDAD"));
                creador.setActivo(this.resultSet.getInt("ACTIVO") == 1);
                creadores.add(creador);
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
    
    public void eliminarAsociacionesMateriales(Integer idCreador) {
        String sql = "DELETE FROM " + TABLA_INTERMEDIA + " WHERE CREADOR_IDCREADOR = ?";
        try {
            this.abrirConexion();
            PreparedStatement pstmt = this.conexion.prepareStatement(sql);
            pstmt.setInt(1, idCreador);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
} 