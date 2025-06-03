package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.model.PersonaDTO;
import com.syntaxerror.biblioteca.model.PrestamoDTO;
import com.syntaxerror.biblioteca.persistance.dao.PrestamoDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAOImpl extends DAOImplBase implements PrestamoDAO {

    private PrestamoDTO prestamo;

    public PrestamoDAOImpl() {
        super("BIB_PRESTAMO");
        this.retornarLlavePrimaria = true;
        this.prestamo = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("ID_PRESTAMO", true, true));
        this.listaColumnas.add(new Columna("FECHA_SOLICITUD", false, false));
        this.listaColumnas.add(new Columna("FECHA_PRESTAMO", false, false));
        this.listaColumnas.add(new Columna("FECHA_DEVOLUCION", false, false));
        this.listaColumnas.add(new Columna("PERSONA_IDPERSONA", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {

        //si es autoincremental, se salta el (1,ID)
        this.statement.setDate(1, new Date(this.prestamo.getFechaSolicitud().getTime()));
        this.statement.setDate(2, new Date(this.prestamo.getFechaPrestamo().getTime()));
        this.statement.setDate(3, new Date(this.prestamo.getFechaDevolucion().getTime()));
        this.statement.setInt(4, this.prestamo.getPersona().getIdPersona());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {

        this.statement.setDate(1, new Date(this.prestamo.getFechaSolicitud().getTime()));
        this.statement.setDate(2, new Date(this.prestamo.getFechaPrestamo().getTime()));
        this.statement.setDate(3, new Date(this.prestamo.getFechaDevolucion().getTime()));
        this.statement.setInt(4, this.prestamo.getPersona().getIdPersona());
        this.statement.setInt(5, this.prestamo.getIdPrestamo());
        //En modificar el ID va al ultimo
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.prestamo.getIdPrestamo());
        //Para eliminar solo va el id
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.prestamo.getIdPrestamo());
        //Para obtener por Id igual solo el id
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.prestamo = new PrestamoDTO();
        this.prestamo.setIdPrestamo(this.resultSet.getInt("ID_PRESTAMO"));
        this.prestamo.setFechaSolicitud(this.resultSet.getDate("FECHA_SOLICITUD"));
        this.prestamo.setFechaPrestamo(this.resultSet.getDate("FECHA_PRESTAMO"));
        this.prestamo.setFechaDevolucion(this.resultSet.getDate("FECHA_DEVOLUCION"));

        // Crear objetos DTO básicos para las relaciones
        PersonaDTO persona = new PersonaDTO();
        persona.setIdPersona(this.resultSet.getInt("PERSONA_IDPERSONA"));
        this.prestamo.setPersona(persona);

    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.prestamo = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.prestamo);
    }

    @Override
    public Integer insertar(PrestamoDTO prestamo) {
        this.prestamo = prestamo;
        return super.insertar();
    }

    @Override
    public PrestamoDTO obtenerPorId(Integer idPrestamo) {
        this.prestamo = new PrestamoDTO();
        this.prestamo.setIdPrestamo(idPrestamo);
        super.obtenerPorId();
        return this.prestamo;
    }

    @Override
    public ArrayList<PrestamoDTO> listarTodos() {
        return (ArrayList<PrestamoDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(PrestamoDTO prestamo) {
        this.prestamo = prestamo;
        return super.modificar();
    }

    @Override
    public Integer eliminar(PrestamoDTO prestamo) {
        this.prestamo = prestamo;
        return super.eliminar();
    }
}
