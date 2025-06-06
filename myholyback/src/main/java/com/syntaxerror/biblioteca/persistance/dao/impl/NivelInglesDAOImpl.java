package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.model.NivelInglesDTO;
import com.syntaxerror.biblioteca.model.enums.Nivel;
import com.syntaxerror.biblioteca.persistance.dao.NivelInglesDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NivelInglesDAOImpl extends DAOImplBase implements NivelInglesDAO {

    private NivelInglesDTO nivel;

    public NivelInglesDAOImpl() {
        super("BIB_NIVELES_INGLES");
        this.retornarLlavePrimaria = true;
        this.nivel = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("ID_NIVEL", true, true));
        this.listaColumnas.add(new Columna("NIVEL", false, false));
        this.listaColumnas.add(new Columna("DESCRIPCION", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.nivel.getNivel().name()); 
        this.statement.setString(2, this.nivel.getDescripcion());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.nivel.getNivel().name()); 
        this.statement.setString(2, this.nivel.getDescripcion());
        this.statement.setInt(3, this.nivel.getIdNivel());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.nivel.getIdNivel());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.nivel.getIdNivel());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.nivel = new NivelInglesDTO();
        this.nivel.setIdNivel(this.resultSet.getInt("ID_NIVEL"));
        this.nivel.setNivel(Nivel.valueOf(this.resultSet.getString("NIVEL"))); 
        this.nivel.setDescripcion(this.resultSet.getString("DESCRIPCION"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.nivel = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.nivel);
    }

    @Override
    public Integer insertar(NivelInglesDTO nivel) {
        this.nivel = nivel;
        return super.insertar();
    }

    @Override
    public NivelInglesDTO obtenerPorId(Integer idNivel) {
        this.nivel = new NivelInglesDTO();
        this.nivel.setIdNivel(idNivel);
        super.obtenerPorId();
        return this.nivel;
    }

    @Override
    public ArrayList<NivelInglesDTO> listarTodos() {
        return (ArrayList<NivelInglesDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(NivelInglesDTO nivel) {
        this.nivel = nivel;
        return super.modificar();
    }

    @Override
    public Integer eliminar(NivelInglesDTO nivel) {
        this.nivel = nivel;
        return super.eliminar();
    }
}
