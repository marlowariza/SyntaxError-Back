package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.model.PersonaDTO;
import com.syntaxerror.biblioteca.model.PrestamoDTO;
import com.syntaxerror.biblioteca.model.ReporteSedeDTO;
import com.syntaxerror.biblioteca.model.SedeDTO;
import com.syntaxerror.biblioteca.persistance.dao.ReporteSedeDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.ReporteSedeParametros;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReporteSedeDAOImpl extends DAOImplBase implements ReporteSedeDAO {

    private ReporteSedeDTO reporte;

    public ReporteSedeDAOImpl() {
        super("BIB_REPORTE_SEDE");
    }

    @Override
    protected void configurarListaDeColumnas() {
        //no se incluirán columnas pues este DAO no corresponde
        //a un CRUD típico
    }

    @Override
    public void insertarDatosDePrueba() {
        String sql = "{call SP_BIB_INSERTAR_DATOS_PRUEBA_REPORTE_SEDE()}";
        Boolean conTransaccion = true;
        this.ejecutarProcedimientoAlmacenado(sql, conTransaccion);
    }

    @Override
    public void eliminarDatosDePrueba() {
        String sql = "{call SP_BIB_ELIMINAR_DATOS_PRUEBA_REPORTE_SEDE()}";
        Boolean conTransaccion = true;
        this.ejecutarProcedimientoAlmacenado(sql, conTransaccion);
    }

    @Override
    public void generarReportePorSede(Integer anio, Integer mes) {
        Object parametros = new ReporteSedeParametros(anio, mes, null, null, null);
        String sql = "{call SP_BIB_GENERAR_REPORTE_SEDE(?, ?)}";
        Boolean conTransaccion = false;
        this.ejecutarProcedimientoAlmacenado(sql, this::incluirParametrosReporteSede, parametros, conTransaccion);
    }

    private void incluirParametrosReporteSede(Object objetoParametros) {
        ReporteSedeParametros parametros = (ReporteSedeParametros) objetoParametros;
        try {
            this.statement.setInt(1, parametros.getAnio());
            this.statement.setInt(2, parametros.getMes());
        } catch (SQLException ex) {
            Logger.getLogger(ReporteSedeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<ReporteSedeDTO> listarPorPeriodoYSede(
            Integer anio, Integer mes, Integer idSede, Integer idPrestamo, Integer idPersona) {

        Object parametros = new ReporteSedeParametros(anio, mes, idSede, idPrestamo, idPersona);
        String sql = this.generarSQLParaListarPorPeriodo();

        return (ArrayList<ReporteSedeDTO>) super.listarTodos(sql,
                this::incluirValorDeParametrosParaListarPorPeriodo,
                parametros
        );
    }

    private void incluirValorDeParametrosParaListarPorPeriodo(Object objetoParametros) {
        ReporteSedeParametros parametros = (ReporteSedeParametros) objetoParametros;
        try {
            this.statement.setInt(1, parametros.getAnio());
            this.statement.setInt(2, parametros.getMes());

            this.statement.setObject(3, parametros.getIdSede(), java.sql.Types.INTEGER);
            this.statement.setObject(4, parametros.getIdSede(), java.sql.Types.INTEGER);

            this.statement.setObject(5, parametros.getIdPrestamo(), java.sql.Types.INTEGER);
            this.statement.setObject(6, parametros.getIdPrestamo(), java.sql.Types.INTEGER);

            this.statement.setObject(7, parametros.getIdPersona(), java.sql.Types.INTEGER);
            this.statement.setObject(8, parametros.getIdPersona(), java.sql.Types.INTEGER);
        } catch (SQLException ex) {
            Logger.getLogger(ReporteSedeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String generarSQLParaListarPorPeriodo() {
        String sql = "SELECT r.ANHIO, ";
        sql += "r.MES, ";
        sql += "p.ID_PRESTAMO, p.FECHA_SOLICITUD, p.FECHA_PRESTAMO, p.FECHA_DEVOLUCION, ";
        sql += "per.ID_PERSONA, per.NOMBRE, per.PATERNO, per.MATERNO, ";
        sql += "s.ID_SEDE, s.NOMBRE AS NOMBRE_SEDE, s.DIRECCION, s.DISTRITO, s.TELEFONO_CONTACTO, s.CORREO_CONTACTO ";
        sql += "FROM BIB_REPORTE_SEDE r ";
        sql += "JOIN BIB_PRESTAMO p ON p.ID_PRESTAMO = r.PRESTAMO_IDPRESTAMO ";
        sql += "JOIN BIB_PERSONA per ON per.ID_PERSONA = r.PERSONA_IDPERSONA ";
        sql += "JOIN BIB_BIBLIOTECA_SEDE s ON s.ID_SEDE = r.SEDE_IDSEDE ";
        sql += "WHERE r.ANHIO = ? AND r.MES = ? ";
        sql += "AND (? IS NULL OR r.SEDE_IDSEDE = ?) ";
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
        this.reporte = new ReporteSedeDTO();

        // Datos del reporte
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

        // Sede
        SedeDTO sede = new SedeDTO();
        sede.setIdSede(this.resultSet.getInt("ID_SEDE"));
        sede.setNombre(this.resultSet.getString("NOMBRE_SEDE"));
        sede.setDireccion(this.resultSet.getString("DIRECCION"));
        sede.setDistrito(this.resultSet.getString("DISTRITO"));
        sede.setTelefonoContacto(this.resultSet.getString("TELEFONO_CONTACTO"));
        sede.setCorreoContacto(this.resultSet.getString("CORREO_CONTACTO"));
        this.reporte.setSede(sede);
    }

}
