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

public class MaterialesTemasDAO {
    
    private static final String TABLA_INTERMEDIA = "BIB_MATERIALES_TEMAS";
    private Connection conexion;
    private ResultSet resultSet;
    
    public MaterialesTemasDAO() {
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
    
    public void asociarTema(Integer idMaterial, Integer idTema) {
        String sql = "INSERT INTO " + TABLA_INTERMEDIA + " (MATERIAL_IDMATERIAL, TEMA_IDTEMA) VALUES (?, ?)";
        try {
            this.abrirConexion();
            PreparedStatement pstmt = this.conexion.prepareStatement(sql);
            pstmt.setInt(1, idMaterial);
            pstmt.setInt(2, idTema);
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
    
    public void desasociarTema(Integer idMaterial, Integer idTema) {
        String sql = "DELETE FROM " + TABLA_INTERMEDIA + " WHERE MATERIAL_IDMATERIAL = ? AND TEMA_IDTEMA = ?";
        try {
            this.abrirConexion();
            PreparedStatement pstmt = this.conexion.prepareStatement(sql);
            pstmt.setInt(1, idMaterial);
            pstmt.setInt(2, idTema);
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
    
    public boolean existeRelacionConTema(Integer idMaterial, Integer idTema) {
        String sql = "SELECT COUNT(*) FROM " + TABLA_INTERMEDIA + 
                    " WHERE MATERIAL_IDMATERIAL = ? AND TEMA_IDTEMA = ?";
        try {
            this.abrirConexion();
            PreparedStatement pstmt = this.conexion.prepareStatement(sql);
            pstmt.setInt(1, idMaterial);
            pstmt.setInt(2, idTema);
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
    
    public ArrayList<TemasDTO> listarPorMaterial(Integer idMaterial) {
        ArrayList<TemasDTO> temas = new ArrayList<>();
        String sql = "SELECT t.* FROM BIB_TEMAS t " +
                    "INNER JOIN " + TABLA_INTERMEDIA + " mt ON t.ID_TEMA = mt.TEMA_IDTEMA " +
                    "WHERE mt.MATERIAL_IDMATERIAL = ?";
        try {
            this.abrirConexion();
            PreparedStatement pstmt = this.conexion.prepareStatement(sql);
            pstmt.setInt(1, idMaterial);
            this.resultSet = pstmt.executeQuery();
            while (this.resultSet.next()) {
                TemasDTO tema = new TemasDTO();
                tema.setIdTema(this.resultSet.getInt("ID_TEMA"));
                tema.setDescripcion(this.resultSet.getString("DESCRIPCION"));
                tema.setCategoria(Categoria.valueOf(this.resultSet.getString("CATEGORIA")));
                if (this.resultSet.getInt("ID_TEMA_PADRE") > 0) {
                    TemasDTO temaPadre = new TemasDTO();
                    temaPadre.setIdTema(this.resultSet.getInt("ID_TEMA_PADRE"));
                    tema.setTemaPadre(temaPadre);
                }
                temas.add(tema);
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
            PreparedStatement pstmt = this.conexion.prepareStatement(sql);
            pstmt.setInt(1, idTema);
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
    
    public void eliminarAsociacionesTemas(Integer idMaterial) {
        String sql = "DELETE FROM " + TABLA_INTERMEDIA + " WHERE MATERIAL_IDMATERIAL = ?";
        try {
            this.abrirConexion();
            PreparedStatement pstmt = this.conexion.prepareStatement(sql);
            pstmt.setInt(1, idMaterial);
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