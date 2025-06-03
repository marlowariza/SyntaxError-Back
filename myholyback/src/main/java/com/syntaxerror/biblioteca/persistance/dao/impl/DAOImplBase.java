package com.syntaxerror.biblioteca.persistance.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.syntaxerror.biblioteca.db.DBManager;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Tipo_Operacion;

public abstract class DAOImplBase {

    protected String nombreTabla;
    protected ArrayList<Columna> listaColumnas;
    protected Boolean retornarLlavePrimaria;
    protected Boolean mostrarSentenciaSQL;
    protected Connection conexion;
    protected CallableStatement statement;
    protected ResultSet resultSet;

    public DAOImplBase(String nombreTabla) {
        this.nombreTabla = nombreTabla;
        this.retornarLlavePrimaria = false;
        this.mostrarSentenciaSQL = true;
        this.incluirListaDeColumnas();
    }

    private void incluirListaDeColumnas() {
        this.listaColumnas = new ArrayList<>();
        this.configurarListaDeColumnas();
    }

    protected abstract void configurarListaDeColumnas();

    protected void abrirConexion() {
        this.conexion = DBManager.getInstance().getConnection();
    }

    protected void cerrarConexion() throws SQLException {
        if (this.conexion != null) {
            this.conexion.close();
            this.conexion = null;
        }
    }

    protected void iniciarTransaccion() throws SQLException {
        this.abrirConexion();
        this.conexion.setAutoCommit(false);
    }

    protected void comitarTransaccion() throws SQLException {
        this.conexion.commit();
    }

    protected void rollbackTransaccion() throws SQLException {
        if (this.conexion != null) {
            this.conexion.rollback();
        }
    }

    protected void colocarSQLenStatement(String sql) throws SQLException {
        this.statement = this.conexion.prepareCall(sql);
    }

    protected Integer ejecutarModificacionEnBD() throws SQLException {
        if (this.mostrarSentenciaSQL) {
            Logger.getLogger(DAOImplBase.class.getName()).log(Level.INFO, this.statement.toString());
        }
        return this.statement.executeUpdate();
    }

    protected void ejecutarConsultaEnBD() throws SQLException {
        this.resultSet = this.statement.executeQuery();
    }

    protected Integer insertar() {
        return this.ejecuta_DML(Tipo_Operacion.INSERTAR);
    }

    protected Integer modificar() {
        return this.ejecuta_DML(Tipo_Operacion.MODIFICAR);
    }

    protected Integer eliminar() {
        return this.ejecuta_DML(Tipo_Operacion.ELIMINAR);
    }

    private Integer ejecuta_DML(Tipo_Operacion tipo_Operacion) {
        int resultado = 0;
        try {
            this.iniciarTransaccion();
            // this.conexion = DBManager.getInstance().getConnection();
            // this.conexion.setAutoCommit(false);
            String sql = null;
            switch (tipo_Operacion) {
                case INSERTAR ->
                    sql = this.generarSQLParaInsercion();
                case MODIFICAR ->
                    sql = this.generarSQLParaModificacion();
                case ELIMINAR ->
                    sql = this.generarSQLParaEliminacion();
            }
            this.colocarSQLenStatement(sql);
            switch (tipo_Operacion) {
                case INSERTAR ->
                    this.incluirValorDeParametrosParaInsercion();
                case MODIFICAR ->
                    this.incluirValorDeParametrosParaModificacion();
                case ELIMINAR ->
                    this.incluirValorDeParametrosParaEliminacion();
            }
            resultado = this.ejecutarModificacionEnBD();
            if (this.retornarLlavePrimaria && tipo_Operacion == Tipo_Operacion.INSERTAR) {
                resultado = this.retornarUltimoAutoGenerado();
            }
            this.comitarTransaccion();
        } catch (SQLException ex) {
            System.err.println("Error al intentar ejecutar consulta - " + tipo_Operacion.toString() + ": " + ex);
            try {
                this.rollbackTransaccion();
                // if (this.conexion != null) {
                // this.conexion.rollback();
                // }
            } catch (SQLException ex1) {
                System.err.println("Error al hacer rollback - " + ex);
            }
        } finally {
            try {
                this.cerrarConexion();
                // if (this.conexion != null) {
                // this.conexion.close();
                // }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
        return resultado;
    }

    protected String generarSQLParaInsercion() {
        // sentencia SQL a generar es similar a
        // INSERT INTO INV_ALMACENES (NOMBRE, ALMACEN_CENTRAL) VALUES (?,?)
        String sql = "INSERT INTO ";
        sql = sql.concat(this.nombreTabla);
        sql = sql.concat("(");
        String sql_columnas = "";
        String sql_parametros = "";
        for (Columna columna : this.listaColumnas) {
            if (!columna.getEsAutoGenerado()) {
                if (!sql_columnas.isBlank()) {
                    sql_columnas = sql_columnas.concat(", ");
                    sql_parametros = sql_parametros.concat(", ");
                }
                sql_columnas = sql_columnas.concat(columna.getNombre());
                sql_parametros = sql_parametros.concat("?");
            }
        }
        sql = sql.concat(sql_columnas);
        sql = sql.concat(") VALUES (");
        sql = sql.concat(sql_parametros);
        sql = sql.concat(")");
        return sql;
    }

    protected String generarSQLParaModificacion() {
        // sentencia SQL a generar es similar a
        // UPDATE INV_ALMACENES SET NOMBRE=?, ALMACEN_CENTRAL=? WHERE ALMACEN_ID=?
        String sql = "UPDATE ";
        sql = sql.concat(this.nombreTabla);
        sql = sql.concat(" SET ");
        String sql_columnas = "";
        String sql_predicado = "";
        for (Columna columna : this.listaColumnas) {
            if (columna.getEsllavePrimaria()) {
                if (!sql_predicado.isBlank()) {
                    sql_predicado = sql_predicado.concat(", ");
                }
                sql_predicado = sql_predicado.concat(columna.getNombre());
                sql_predicado = sql_predicado.concat("=?");
            } else {
                if (!sql_columnas.isBlank()) {
                    sql_columnas = sql_columnas.concat(", ");
                }
                sql_columnas = sql_columnas.concat(columna.getNombre());
                sql_columnas = sql_columnas.concat("=?");
            }
        }
        sql = sql.concat(sql_columnas);
        sql = sql.concat(" WHERE ");
        sql = sql.concat(sql_predicado);
        return sql;
    }

    protected String generarSQLParaEliminacion() {
        // sentencia SQL a generar es similar a
        // DELETE FROM INV_ALMACENES WHERE ALMACEN_ID=?
        String sql = "DELETE FROM ";
        sql = sql.concat(this.nombreTabla);
        sql = sql.concat(" WHERE ");
        String sql_predicado = "";
        for (Columna columna : this.listaColumnas) {
            if (columna.getEsllavePrimaria()) {
                if (!sql_predicado.isBlank()) {
                    sql_predicado = sql_predicado.concat(", ");
                }
                sql_predicado = sql_predicado.concat(columna.getNombre());
                sql_predicado = sql_predicado.concat("=?");
            }
        }
        sql = sql.concat(sql_predicado);
        return sql;
    }

    protected String generarSQLParaObtenerPorId() {
        // sentencia SQL a generar es similar a
        // SELECT ALMACEN_ID, NOMBRE, ALMACEN_CENTRAL FROM INV_ALMACENES WHERE
        // ALMACEN_ID = ?
        String sql = "SELECT ";
        String sql_columnas = "";
        String sql_predicado = "";
        for (Columna columna : this.listaColumnas) {
            if (columna.getEsllavePrimaria()) {
                if (!sql_predicado.isBlank()) {
                    sql_predicado = sql_predicado.concat(", ");
                }
                sql_predicado = sql_predicado.concat(columna.getNombre());
                sql_predicado = sql_predicado.concat("=?");
            }
            if (!sql_columnas.isBlank()) {
                sql_columnas = sql_columnas.concat(", ");
            }
            sql_columnas = sql_columnas.concat(columna.getNombre());
        }
        sql = sql.concat(sql_columnas);
        sql = sql.concat(" FROM ");
        sql = sql.concat(this.nombreTabla);
        sql = sql.concat(" WHERE ");
        sql = sql.concat(sql_predicado);
        return sql;
    }

    protected String generarSQLParaListarTodos() {
        // sentencia SQL a generar es similar a
        // SELECT ALMACEN_ID, NOMBRE, ALMACEN_CENTRAL FROM INV_ALMACENES
        String sql = "SELECT ";
        String sql_columnas = "";
        for (Columna columna : this.listaColumnas) {
            if (!sql_columnas.isBlank()) {
                sql_columnas = sql_columnas.concat(", ");
            }
            sql_columnas = sql_columnas.concat(columna.getNombre());
        }
        sql = sql.concat(sql_columnas);
        sql = sql.concat(" FROM ");
        sql = sql.concat(this.nombreTabla);
        return sql;
    }

    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        throw new UnsupportedOperationException("Método no sobreescrito.");
    }

    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        throw new UnsupportedOperationException("Método no sobreescrito.");
    }

    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        throw new UnsupportedOperationException("Método no sobreescrito.");
    }

    public Integer retornarUltimoAutoGenerado() {
        Integer resultado = null;
        try {
            String sql = "select @@last_insert_id as id";
            this.statement = this.conexion.prepareCall(sql);
            this.resultSet = this.statement.executeQuery();
            if (this.resultSet.next()) {
                resultado = this.resultSet.getInt("id");
            }
        } catch (SQLException ex) {
            System.err.println("Error al intentar retornarUltimoAutoGenerado - " + ex);
        }
        return resultado;
    }

    public void obtenerPorId() {
        try {
            this.abrirConexion();
            String sql = this.generarSQLParaObtenerPorId();
            this.colocarSQLenStatement(sql);
            this.incluirValorDeParametrosParaObtenerPorId();
            this.ejecutarConsultaEnBD();
            if (this.resultSet.next()) {
                instanciarObjetoDelResultSet();
            } else {
                limpiarObjetoDelResultSet();
            }
        } catch (SQLException ex) {
            System.err.println("Error al intentar obtenerPorId - " + ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
    }

    //ListarTodos anterior, el nuevo es para los reportes tmb
    
//    public List listarTodos() {
//        List lista = new ArrayList<>();
//        try {
//            this.abrirConexion();
//            String sql = this.generarSQLParaListarTodos();
//            this.colocarSQLenStatement(sql);
//            this.ejecutarConsultaEnBD();
//            while (this.resultSet.next()) {
//                agregarObjetoALaLista(lista);
//            }
//        } catch (SQLException ex) {
//            System.err.println("Error al intentar listarTodos - " + ex);
//        } finally {
//            try {
//                this.cerrarConexion();
//            } catch (SQLException ex) {
//                System.err.println("Error al cerrar la conexión - " + ex);
//            }
//        }
//        return lista;
//    }
    public List listarTodos() {
        String sql = null;
        Consumer incluirValorDeParametros = null;
        Object parametros = null;
        return this.listarTodos(sql, incluirValorDeParametros, parametros);
    }

    public List listarTodos(String sql, Consumer incluirValorDeParametros, Object parametros) {
        List lista = new ArrayList<>();
        try {
            this.abrirConexion();
            if (sql == null) {
                sql = this.generarSQLParaListarTodos();
            }
            this.colocarSQLenStatement(sql);
            if (incluirValorDeParametros != null) {
                incluirValorDeParametros.accept(parametros);
            }
            this.ejecutarConsultaEnBD();
            while (this.resultSet.next()) {
                agregarObjetoALaLista(lista);
            }
        } catch (SQLException ex) {
            System.err.println("Error al intentar listarTodos - " + ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
        return lista;
    }

    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        throw new UnsupportedOperationException("Método no sobreescrito.");
    }

    protected void instanciarObjetoDelResultSet() throws SQLException {
        throw new UnsupportedOperationException("Método no sobreescrito.");
    }

    protected void limpiarObjetoDelResultSet() {
        throw new UnsupportedOperationException("Método no sobreescrito.");
    }

    protected void agregarObjetoALaLista(List lista) throws SQLException {
        throw new UnsupportedOperationException("Método no sobreescrito.");
    }

    protected void ejecutarProcedimientoAlmacenado(String sql, Boolean conTransaccion) {
        Consumer incluirValorDeParametros = null;
        Object parametros = null;
        this.ejecutarProcedimientoAlmacenado(sql, incluirValorDeParametros, parametros, conTransaccion);
    }

    protected void ejecutarProcedimientoAlmacenado(String sql, Consumer incluirValorDeParametros, Object parametros,
            Boolean conTransaccion) {
        try {
            if (conTransaccion) {
                this.iniciarTransaccion();
            } else {
                this.abrirConexion();
            }
            this.colocarSQLenStatement(sql);
            if (incluirValorDeParametros != null) {
                incluirValorDeParametros.accept(parametros);
            }
            this.ejecutarModificacionEnBD();
            if (conTransaccion) {
                this.comitarTransaccion();
            }
        } catch (SQLException ex) {
            if (conTransaccion)
                try {
                this.rollbackTransaccion();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOImplBase.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DAOImplBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(DAOImplBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
