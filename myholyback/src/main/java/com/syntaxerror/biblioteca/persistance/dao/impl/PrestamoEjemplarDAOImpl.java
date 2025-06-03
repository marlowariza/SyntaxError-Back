package com.syntaxerror.biblioteca.persistance.dao.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.syntaxerror.biblioteca.model.PrestamoEjemplarDTO;
import com.syntaxerror.biblioteca.model.enums.EstadoPrestamoEjemplar;
import com.syntaxerror.biblioteca.persistance.dao.PrestamoEjemplarDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;

public class PrestamoEjemplarDAOImpl extends RelacionDAOImplBase<PrestamoEjemplarDTO, PrestamoEjemplarDTO> implements PrestamoEjemplarDAO {
    private static final Logger LOGGER = Logger.getLogger(PrestamoEjemplarDAOImpl.class.getName());
    
    public PrestamoEjemplarDAOImpl() {
        super("BIB_PRESTAMO_EJEMPLAR", "PRESTAMO_IDPRESTAMO", "EJEMPLAR_IDEJEMPLAR", "BIB_PRESTAMO", "BIB_EJEMPLAR");
    }

    @Override
    protected void configurarListaDeColumnas() {
        super.configurarListaDeColumnas();
        this.listaColumnas.add(new Columna("FECHA_REAL_DEVOLUCION", false, false));
        this.listaColumnas.add(new Columna("ESTADO", false, false));
    }

    @Override
    public Integer insertar(PrestamoEjemplarDTO prestamoEjemplar) {
        int resultado = 0;
        try {
            this.iniciarTransaccion();
            String sql = String.format("INSERT INTO %s (%s, %s, FECHA_REAL_DEVOLUCION, ESTADO) VALUES (?, ?, ?, ?)",
                    nombreTabla, columnaId1, columnaId2);
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, prestamoEjemplar.getIdPrestamo());
            this.statement.setInt(2, prestamoEjemplar.getIdEjemplar());
            this.statement.setDate(3, prestamoEjemplar.getFechaRealDevolucion() != null ? 
                    new Date(prestamoEjemplar.getFechaRealDevolucion().getTime()) : null);
            this.statement.setString(4, prestamoEjemplar.getEstado().name());
            resultado = this.ejecutarModificacionEnBD();
            this.comitarTransaccion();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            try {
                this.rollbackTransaccion();
            } catch (SQLException ex1) {
                LOGGER.log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }

    @Override
    public PrestamoEjemplarDTO obtenerPorIds(Integer idPrestamo, Integer idEjemplar) {
        PrestamoEjemplarDTO prestamoEjemplar = null;
        try {
            this.abrirConexion();
            String sql = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?",
                    nombreTabla, columnaId1, columnaId2);
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, idPrestamo);
            this.statement.setInt(2, idEjemplar);
            this.ejecutarConsultaEnBD();
            if (this.resultSet.next()) {
                prestamoEjemplar = mapearResultSetADTO();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        return prestamoEjemplar;
    }

    @Override
    public ArrayList<PrestamoEjemplarDTO> listarPorPrestamo(Integer idPrestamo) {
        return new ArrayList<>(buscarRelacionados(idPrestamo, columnaId1));
    }

    @Override
    public ArrayList<PrestamoEjemplarDTO> listarPorEjemplar(Integer idEjemplar) {
        return new ArrayList<>(buscarRelacionados(idEjemplar, columnaId2));
    }

    @Override
    public ArrayList<PrestamoEjemplarDTO> listarTodos() {
        ArrayList<PrestamoEjemplarDTO> resultados = new ArrayList<>();
        try {
            this.abrirConexion();
            String sql = String.format("SELECT * FROM %s", nombreTabla);
            this.colocarSQLenStatement(sql);
            this.ejecutarConsultaEnBD();
            while (this.resultSet.next()) {
                resultados.add(mapearResultSetADTO());
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        return resultados;
    }

    @Override
    public Integer modificar(PrestamoEjemplarDTO prestamoEjemplar) {
        int resultado = 0;
        try {
            this.iniciarTransaccion();
            String sql = String.format("UPDATE %s SET FECHA_REAL_DEVOLUCION = ?, ESTADO = ? WHERE %s = ? AND %s = ?",
                    nombreTabla, columnaId1, columnaId2);
            this.colocarSQLenStatement(sql);
            this.statement.setDate(1, prestamoEjemplar.getFechaRealDevolucion() != null ? 
                    new Date(prestamoEjemplar.getFechaRealDevolucion().getTime()) : null);
            this.statement.setString(2, prestamoEjemplar.getEstado().name());
            this.statement.setInt(3, prestamoEjemplar.getIdPrestamo());
            this.statement.setInt(4, prestamoEjemplar.getIdEjemplar());
            resultado = this.ejecutarModificacionEnBD();
            this.comitarTransaccion();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            try {
                this.rollbackTransaccion();
            } catch (SQLException ex1) {
                LOGGER.log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }

    @Override
    public Integer eliminar(Integer idPrestamo, Integer idEjemplar) {
        return eliminarRelacion(idPrestamo, idEjemplar);
    }

    @Override
    public boolean existeRelacion(Integer idPrestamo, Integer idEjemplar) {
        return super.existeRelacion(idPrestamo, idEjemplar);
    }

    private List<PrestamoEjemplarDTO> buscarRelacionados(Integer id, String columnaFiltro) {
        List<PrestamoEjemplarDTO> resultados = new ArrayList<>();
        try {
            this.abrirConexion();
            String sql = String.format("SELECT * FROM %s WHERE %s = ?", nombreTabla, columnaFiltro);
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, id);
            this.ejecutarConsultaEnBD();
            while (this.resultSet.next()) {
                resultados.add(mapearResultSetADTO());
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        return resultados;
    }

    private PrestamoEjemplarDTO mapearResultSetADTO() throws SQLException {
        PrestamoEjemplarDTO dto = new PrestamoEjemplarDTO();
        dto.setIdPrestamo(this.resultSet.getInt(columnaId1));
        dto.setIdEjemplar(this.resultSet.getInt(columnaId2));
        dto.setFechaRealDevolucion(this.resultSet.getDate("FECHA_REAL_DEVOLUCION"));
        dto.setEstado(EstadoPrestamoEjemplar.valueOf(this.resultSet.getString("ESTADO")));
        return dto;
    }

    @Override
    protected PrestamoEjemplarDTO obtenerEntidad1(Integer id) throws SQLException {
        return obtenerPorIds(id, null);
    }

    @Override
    protected PrestamoEjemplarDTO obtenerEntidad2(Integer id) throws SQLException {
        return obtenerPorIds(null, id);
    }
}
