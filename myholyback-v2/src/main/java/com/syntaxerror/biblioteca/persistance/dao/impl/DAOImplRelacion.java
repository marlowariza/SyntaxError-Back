package com.syntaxerror.biblioteca.persistance.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DAOImplRelacion extends DAOImplBase {
    
    protected String nombreTablaIntermedia;
    protected String nombreColumnaPrimeraEntidad;
    protected String nombreColumnaSegundaEntidad;
    
    public DAOImplRelacion(String nombreTabla, String nombreTablaIntermedia, 
                          String nombreColumnaPrimeraEntidad, String nombreColumnaSegundaEntidad) {
        super(nombreTabla);
        this.nombreTablaIntermedia = nombreTablaIntermedia;
        this.nombreColumnaPrimeraEntidad = nombreColumnaPrimeraEntidad;
        this.nombreColumnaSegundaEntidad = nombreColumnaSegundaEntidad;
    }
    
    protected Integer asociar(Integer idPrimeraEntidad, Integer idSegundaEntidad) {
        try {
            this.iniciarTransaccion();
            String sql = "INSERT INTO " + this.nombreTablaIntermedia + 
                        " (" + this.nombreColumnaPrimeraEntidad + ", " + this.nombreColumnaSegundaEntidad + 
                        ") VALUES (?, ?)";
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, idPrimeraEntidad);
            this.statement.setInt(2, idSegundaEntidad);
            Integer resultado = this.ejecutarModificacionEnBD();
            this.comitarTransaccion();
            return resultado;
        } catch (SQLException ex) {
            System.err.println("Error al intentar asociar - " + ex);
            try {
                this.rollbackTransaccion();
            } catch (SQLException ex1) {
                System.err.println("Error al hacer rollback - " + ex1);
            }
            return 0;
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
    }
    
    protected Integer desasociar(Integer idPrimeraEntidad, Integer idSegundaEntidad) {
        try {
            this.iniciarTransaccion();
            String sql = "DELETE FROM " + this.nombreTablaIntermedia + 
                        " WHERE " + this.nombreColumnaPrimeraEntidad + " = ? AND " + 
                        this.nombreColumnaSegundaEntidad + " = ?";
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, idPrimeraEntidad);
            this.statement.setInt(2, idSegundaEntidad);
            Integer resultado = this.ejecutarModificacionEnBD();
            this.comitarTransaccion();
            return resultado;
        } catch (SQLException ex) {
            System.err.println("Error al intentar desasociar - " + ex);
            try {
                this.rollbackTransaccion();
            } catch (SQLException ex1) {
                System.err.println("Error al hacer rollback - " + ex1);
            }
            return 0;
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
    }
    
    protected boolean existeRelacion(Integer idPrimeraEntidad, Integer idSegundaEntidad) {
        try {
            this.abrirConexion();
            String sql = "SELECT COUNT(*) as total FROM " + this.nombreTablaIntermedia + 
                        " WHERE " + this.nombreColumnaPrimeraEntidad + " = ? AND " + 
                        this.nombreColumnaSegundaEntidad + " = ?";
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, idPrimeraEntidad);
            this.statement.setInt(2, idSegundaEntidad);
            this.ejecutarConsultaEnBD();
            if (this.resultSet.next()) {
                return this.resultSet.getInt("total") > 0;
            }
            return false;
        } catch (SQLException ex) {
            System.err.println("Error al intentar verificar relación - " + ex);
            return false;
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
    }
    
    protected abstract void instanciarObjetoRelacionadoDelResultSet() throws SQLException;
    
    protected List listarRelacionados(Integer idEntidad, String columnaFiltro) {
        List lista = new ArrayList<>();
        try {
            this.abrirConexion();
            String sql = "SELECT t.* FROM " + this.nombreTabla + " t " +
                        "INNER JOIN " + this.nombreTablaIntermedia + " ti ON t.ID_" + 
                        this.nombreTabla.substring(4) + " = ti." + columnaFiltro + 
                        " WHERE ti." + (columnaFiltro.equals(this.nombreColumnaPrimeraEntidad) ? 
                        this.nombreColumnaSegundaEntidad : this.nombreColumnaPrimeraEntidad) + " = ?";
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, idEntidad);
            this.ejecutarConsultaEnBD();
            while (this.resultSet.next()) {
                this.instanciarObjetoRelacionadoDelResultSet();
                lista.add(this.obtenerObjetoRelacionado());
            }
        } catch (SQLException ex) {
            System.err.println("Error al intentar listar relacionados - " + ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
        return lista;
    }
    
    protected abstract Object obtenerObjetoRelacionado();
} 