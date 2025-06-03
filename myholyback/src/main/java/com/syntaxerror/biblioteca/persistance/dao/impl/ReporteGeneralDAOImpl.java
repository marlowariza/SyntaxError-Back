package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.model.PersonaDTO;
import com.syntaxerror.biblioteca.model.PrestamoDTO;
import java.util.ArrayList;
import com.syntaxerror.biblioteca.model.ReporteGeneralDTO;
import com.syntaxerror.biblioteca.persistance.dao.ReporteGeneralDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.ReporteGeneralParametros;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReporteGeneralDAOImpl extends DAOImplBase implements ReporteGeneralDAO {

    private ReporteGeneralDTO reporte;

    public ReporteGeneralDAOImpl() {
        super("BIB_REPORTE_GENERAL");
    }

    @Override
    protected void configurarListaDeColumnas() {
        //no se incluirán columnas pues este DAO no corresponde
        //a un CRUD típico
    }

    @Override
    public void insertarDatosDePrueba() {
        String sql = "{call SP_BIB_INSERTAR_DATOS_PRUEBA_REPORTE_GENERAL()}";
        Boolean conTransaccion = true;
        this.ejecutarProcedimientoAlmacenado(sql, conTransaccion);
    }

    @Override
    public void eliminarDatosDePrueba() {
        String sql = "{call SP_BIB_ELIMINAR_DATOS_PRUEBA_REPORTE_GENERAL()}";
        Boolean conTransaccion = true;
        this.ejecutarProcedimientoAlmacenado(sql, conTransaccion);
    }

    @Override
    public void generarReporteGeneral(Integer anio, Integer mes) {
        Object parametros = new ReporteGeneralParametros(anio, mes, null, null);
        String sql = "{call SP_BIB_GENERAR_REPORTE_GENERAL(?, ?)}";
        Boolean conTransaccion = false;
        this.ejecutarProcedimientoAlmacenado(sql, this::incluirParametrosReporteGeneral, parametros, conTransaccion);
    }

    private void incluirParametrosReporteGeneral(Object objetoParametros) {
        ReporteGeneralParametros parametros = (ReporteGeneralParametros) objetoParametros;
        try {
            this.statement.setInt(1, parametros.getAnio());
            this.statement.setInt(2, parametros.getMes());
        } catch (SQLException ex) {
            Logger.getLogger(ReporteGeneralDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<ReporteGeneralDTO> listarPorPeriodo(Integer anio, Integer mes, Integer idPrestamo, Integer idPersona) {
        Object parametros = new ReporteGeneralParametros(anio, mes, idPrestamo, idPersona);
        String sql = this.generarSQLParaListarPorPeriodo();

        return (ArrayList<ReporteGeneralDTO>) super.listarTodos(sql,
                this::incluirValorDeParametrosParaListarPorPeriodo,
                parametros
        );
    }

    private void incluirValorDeParametrosParaListarPorPeriodo(Object objetoParametros) {
        ReporteGeneralParametros parametros = (ReporteGeneralParametros) objetoParametros;
        try {
            this.statement.setInt(1, parametros.getAnio());
            this.statement.setInt(2, parametros.getMes());

            this.statement.setObject(3, parametros.getIdPrestamo(), java.sql.Types.INTEGER);
            this.statement.setObject(4, parametros.getIdPrestamo(), java.sql.Types.INTEGER);

            this.statement.setObject(5, parametros.getIdPersona(), java.sql.Types.INTEGER);
            this.statement.setObject(6, parametros.getIdPersona(), java.sql.Types.INTEGER);
        } catch (SQLException ex) {
            Logger.getLogger(ReporteGeneralDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String generarSQLParaListarPorPeriodo() {
        String sql = "SELECT r.ANHIO, ";
        sql += "r.MES, ";
        sql += "p.ID_PRESTAMO, p.FECHA_SOLICITUD, p.FECHA_PRESTAMO, p.FECHA_DEVOLUCION, ";
        sql += "per.ID_PERSONA, per.NOMBRE, per.PATERNO, per.MATERNO ";
        sql += "FROM BIB_REPORTE_GENERAL r ";
        sql += "JOIN BIB_PRESTAMO p ON p.ID_PRESTAMO = r.PRESTAMO_IDPRESTAMO ";
        sql += "JOIN BIB_PERSONA per ON per.ID_PERSONA = r.PERSONA_IDPERSONA ";
        sql += "WHERE r.ANHIO = ? AND r.MES = ? ";
        sql += "AND (? IS NULL OR r.PRESTAMO_IDPRESTAMO = ?) ";
        sql += "AND (? IS NULL OR r.PERSONA_IDPERSONA = ?) ";
        return sql;
    }

    
    
    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.reporte);
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.reporte = new ReporteGeneralDTO();

        this.reporte.setAnio(this.resultSet.getInt("ANHIO"));
        this.reporte.setMes(this.resultSet.getInt("MES"));

        // Prestamo
        PrestamoDTO prestamo = new PrestamoDTO();
        prestamo.setIdPrestamo(this.resultSet.getInt("ID_PRESTAMO"));
        prestamo.setFechaSolicitud(this.resultSet.getDate("FECHA_SOLICITUD"));
        prestamo.setFechaPrestamo(this.resultSet.getDate("FECHA_PRESTAMO"));
        prestamo.setFechaDevolucion(this.resultSet.getDate("FECHA_DEVOLUCION"));
        this.reporte.setPrestamo(prestamo);

        // Persona
        PersonaDTO persona = new PersonaDTO();
        persona.setIdPersona(this.resultSet.getInt("ID_PERSONA"));
        persona.setNombre(this.resultSet.getString("NOMBRE"));
        persona.setPaterno(this.resultSet.getString("PATERNO"));
        persona.setMaterno(this.resultSet.getString("MATERNO"));
        this.reporte.setPersona(persona);
    }

}
