package com.syntaxerror.biblioteca.persistance.dao.impl.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.syntaxerror.biblioteca.db.DBManager;

/**
 * Clase abstracta para acceso a de tablas intermediasen DAO de relaciones MaM.
 */
public abstract class DAOImplRelaciones extends DAOImplBase {
    protected Connection conexion;
    protected PreparedStatement statement;
    protected ResultSet resultSet;

    public DAOImplRelaciones() {
        super(null); 
    }

    protected abstract String getNombreTabla();
    protected abstract String getColumnaA();
    protected abstract String getColumnaB();
    
    @Override
    protected void configurarListaDeColumnas() {

    }

    protected void abrirConexion() {
        this.conexion = DBManager.getInstance().getConnection();
    }

    protected void cerrarConexion() throws SQLException {
        if (this.conexion != null) {
            this.conexion.close();
            this.conexion = null;
        }
    }

    protected void asociar(Integer idA, Integer idB) {
        String sql = "INSERT INTO " + getNombreTabla() + " (" + getColumnaA() + ", " + getColumnaB() + ") VALUES (?, ?)";
        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareStatement(sql);
            this.statement.setInt(1, idA);
            this.statement.setInt(2, idB);
            this.statement.executeUpdate();
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

    protected void desasociar(Integer idA, Integer idB) {
        String sql = "DELETE FROM " + getNombreTabla() + " WHERE " + getColumnaA() + " = ? AND " + getColumnaB() + " = ?";
        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareStatement(sql);
            this.statement.setInt(1, idA);
            this.statement.setInt(2, idB);
            this.statement.executeUpdate();
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

    protected boolean existeRelacion(Integer idA, Integer idB) {
        String sql = "SELECT COUNT(*) FROM " + getNombreTabla() + " WHERE " + getColumnaA() + " = ? AND " + getColumnaB() + " = ?";
        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareStatement(sql);
            this.statement.setInt(1, idA);
            this.statement.setInt(2, idB);
            this.resultSet = this.statement.executeQuery();
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

    protected void eliminarAsociacionesA(Integer idA) {
        String sql = "DELETE FROM " + getNombreTabla() + " WHERE " + getColumnaA() + " = ?";
        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareStatement(sql);
            this.statement.setInt(1, idA);
            this.statement.executeUpdate();
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

    protected void eliminarAsociacionesB(Integer idB) {
        String sql = "DELETE FROM " + getNombreTabla() + " WHERE " + getColumnaB() + " = ?";
        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareStatement(sql);
            this.statement.setInt(1, idB);
            this.statement.executeUpdate();
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