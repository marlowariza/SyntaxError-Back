package com.syntaxerror.biblioteca.persistance.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.syntaxerror.biblioteca.model.CreadorDTO;
import com.syntaxerror.biblioteca.model.enums.TipoAutor;
import com.syntaxerror.biblioteca.persistance.dao.CreadorDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;

public class CreadorDAOImpl extends DAOImplBase implements CreadorDAO {

    private CreadorDTO creador;

    public CreadorDAOImpl() {
        super("BIB_CREADOR");
        this.retornarLlavePrimaria = true;
        this.creador = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("ID_CREADOR", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("PATERNO", false, false));
        this.listaColumnas.add(new Columna("MATERNO", false, false));
        this.listaColumnas.add(new Columna("SEUDONIMO", false, false));
        this.listaColumnas.add(new Columna("TIPO_CREADOR", false, false));
        this.listaColumnas.add(new Columna("NACIONALIDAD", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    public Integer insertar(CreadorDTO creador) {
        this.creador = creador;
        return super.insertar();
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() {
        try {
            this.statement.setString(1, this.creador.getNombre());
            this.statement.setString(2, this.creador.getPaterno());
            this.statement.setString(3, this.creador.getMaterno());
            this.statement.setString(4, this.creador.getSeudonimo());
            this.statement.setString(5, this.creador.getTipo().name());
            this.statement.setString(6, this.creador.getNacionalidad());
            this.statement.setInt(7, this.creador.getActivo() ? 1 : 0);
        } catch (SQLException ex) {
            Logger.getLogger(CreadorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {

        this.statement.setString(1, this.creador.getNombre());
        this.statement.setString(2, this.creador.getPaterno());
        this.statement.setString(3, this.creador.getMaterno());
        this.statement.setString(4, this.creador.getSeudonimo());
        this.statement.setString(5, this.creador.getTipo().name());
        this.statement.setString(6, this.creador.getNacionalidad());
        this.statement.setInt(7, this.creador.getActivo() ? 1 : 0);
        this.statement.setInt(8, this.creador.getIdAutor());
        //En modificar el ID va al ultimo
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.creador.getIdAutor());
        //Para eliminar solo va el id
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.creador.getIdAutor());
        //Para obtener por Id igual solo el id
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.creador = new CreadorDTO();
        creador.setIdAutor(this.resultSet.getInt("ID_CREADOR"));
        creador.setNombre(this.resultSet.getString("NOMBRE"));
        creador.setPaterno(this.resultSet.getString("PATERNO"));
        creador.setMaterno(this.resultSet.getString("MATERNO"));
        creador.setSeudonimo(this.resultSet.getString("SEUDONIMO"));
        creador.setTipo(TipoAutor.valueOf(this.resultSet.getString("TIPO_CREADOR")));
        creador.setNacionalidad(this.resultSet.getString("NACIONALIDAD"));
        creador.setActivo(this.resultSet.getInt("ACTIVO") == 1);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.creador = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.creador);
    }

    @Override
    public CreadorDTO obtenerPorId(Integer idCreador) {
        this.creador = new CreadorDTO();
        this.creador.setIdAutor(idCreador);
        super.obtenerPorId();
        return this.creador;
    }

    @Override
    public ArrayList<CreadorDTO> listarTodos() {
        return (ArrayList<CreadorDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(CreadorDTO creador) {
        this.creador = creador;
        return super.modificar();
    }

    @Override
    public Integer eliminar(CreadorDTO creador) {
        this.creador = creador;
        return super.eliminar();
    }

}
