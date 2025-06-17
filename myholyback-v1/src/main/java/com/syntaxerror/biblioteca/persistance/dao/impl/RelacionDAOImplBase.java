package com.syntaxerror.biblioteca.persistance.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;

public abstract class RelacionDAOImplBase<T, U> extends DAOImplBase {
    protected String columnaId1;
    protected String columnaId2;
    protected String tablaEntidad1;
    protected String tablaEntidad2;

    public RelacionDAOImplBase(String nombreTabla, String columnaId1, String columnaId2, 
                             String tablaEntidad1, String tablaEntidad2) {
        super(nombreTabla);
        this.columnaId1 = columnaId1;
        this.columnaId2 = columnaId2;
        this.tablaEntidad1 = tablaEntidad1;
        this.tablaEntidad2 = tablaEntidad2;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas = new ArrayList<>();
        this.listaColumnas.add(new Columna(columnaId1, true, false));
        this.listaColumnas.add(new Columna(columnaId2, true, false));
    }

    // Métodos específicos para relaciones
    public int crearRelacion(Integer id1, Integer id2) {
        int resultado = 0;
        try {
            this.iniciarTransaccion();
            String sql = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?)",
                    nombreTabla, columnaId1, columnaId2);
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, id1);
            this.statement.setInt(2, id2);
            resultado = this.ejecutarModificacionEnBD();
            this.comitarTransaccion();
        } catch (SQLException ex) {
            Logger.getLogger(RelacionDAOImplBase.class.getName()).log(Level.SEVERE, null, ex);
            try {
                this.rollbackTransaccion();
            } catch (SQLException ex1) {
                Logger.getLogger(RelacionDAOImplBase.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(RelacionDAOImplBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }

    public int eliminarRelacion(Integer id1, Integer id2) {
        int resultado = 0;
        try {
            this.iniciarTransaccion();
            String sql = String.format("DELETE FROM %s WHERE %s = ? AND %s = ?",
                    nombreTabla, columnaId1, columnaId2);
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, id1);
            this.statement.setInt(2, id2);
            resultado = this.ejecutarModificacionEnBD();
            this.comitarTransaccion();
        } catch (SQLException ex) {
            Logger.getLogger(RelacionDAOImplBase.class.getName()).log(Level.SEVERE, null, ex);
            try {
                this.rollbackTransaccion();
            } catch (SQLException ex1) {
                Logger.getLogger(RelacionDAOImplBase.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(RelacionDAOImplBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }

    public boolean existeRelacion(Integer id1, Integer id2) {
        boolean existe = false;
        try {
            this.abrirConexion();
            String sql = String.format("SELECT 1 FROM %s WHERE %s = ? AND %s = ?",
                    nombreTabla, columnaId1, columnaId2);
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, id1);
            this.statement.setInt(2, id2);
            this.ejecutarConsultaEnBD();
            existe = this.resultSet.next();
        } catch (SQLException ex) {
            Logger.getLogger(RelacionDAOImplBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(RelacionDAOImplBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return existe;
    }

    protected ArrayList<Integer> buscarRelacionadosPorId1(Integer id) {
        return buscarRelacionados(id, columnaId1, columnaId2);
    }

    protected ArrayList<Integer> buscarRelacionadosPorId2(Integer id) {
        return buscarRelacionados(id, columnaId2, columnaId1);
    }

    private ArrayList<Integer> buscarRelacionados(Integer id, String columnaFiltro, String columnaResultado) {
        ArrayList<Integer> resultados = new ArrayList<>();
        try {
            this.abrirConexion();
            String sql = String.format("SELECT %s FROM %s WHERE %s = ?",
                    columnaResultado, nombreTabla, columnaFiltro);
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, id);
            this.ejecutarConsultaEnBD();
            while (this.resultSet.next()) {
                resultados.add(this.resultSet.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RelacionDAOImplBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(RelacionDAOImplBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultados;
    }

    // Métodos abstractos que las clases hijas deben implementar
    protected abstract T obtenerEntidad1(Integer id) throws SQLException;
    protected abstract U obtenerEntidad2(Integer id) throws SQLException;

    // Métodos abstractos requeridos por DAOImplBase
    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        // No se usa
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        // No se usa
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        // No se usa
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        // No se usa
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        // No se usa
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        // No se usa
    }
}
