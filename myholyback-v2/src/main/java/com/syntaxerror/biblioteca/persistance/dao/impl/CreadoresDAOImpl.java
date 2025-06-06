package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.persistance.dao.impl.base.DAOImplBase;
import com.syntaxerror.biblioteca.persistance.dao.impl.base.CreadoresMaterialesDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.syntaxerror.biblioteca.model.CreadoresDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.enums.TipoCreador;
import com.syntaxerror.biblioteca.persistance.dao.CreadoresDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;

public class CreadoresDAOImpl extends DAOImplBase implements CreadoresDAO {

    private CreadoresDTO creador;
    private CreadoresMaterialesDAO creadoresMaterialesDAO;

    public CreadoresDAOImpl() {
        super("BIB_CREADORES");
        this.retornarLlavePrimaria = true;
        this.creador = null;
        this.creadoresMaterialesDAO = new CreadoresMaterialesDAO();
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
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.creador.getNombre());
        this.statement.setString(2, this.creador.getPaterno());
        this.statement.setString(3, this.creador.getMaterno());
        this.statement.setString(4, this.creador.getSeudonimo());
        this.statement.setString(5, this.creador.getTipo().name());
        this.statement.setString(6, this.creador.getNacionalidad());
        this.statement.setInt(7, this.creador.getActivo() ? 1 : 0);
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
        this.statement.setInt(8, this.creador.getIdCreador());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.creador.getIdCreador());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.creador.getIdCreador());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.creador = new CreadoresDTO();
        this.creador.setIdCreador(this.resultSet.getInt("ID_CREADOR"));
        this.creador.setNombre(this.resultSet.getString("NOMBRE"));
        this.creador.setPaterno(this.resultSet.getString("PATERNO"));
        this.creador.setMaterno(this.resultSet.getString("MATERNO"));
        this.creador.setSeudonimo(this.resultSet.getString("SEUDONIMO"));
        this.creador.setTipo(TipoCreador.valueOf(this.resultSet.getString("TIPO_CREADOR")));
        this.creador.setNacionalidad(this.resultSet.getString("NACIONALIDAD"));
        this.creador.setActivo(this.resultSet.getInt("ACTIVO") == 1);
        
        // Cargar los materiales asociados
        this.creador.setMateriales(creadoresMaterialesDAO.listarPorCreador(this.creador.getIdCreador()));
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
    public Integer insertar(CreadoresDTO creador) {
        this.creador = creador;
        Integer idCreador = super.insertar();
        if (idCreador != null && creador.getMateriales() != null) {
            for (MaterialesDTO material : creador.getMateriales()) {
                creadoresMaterialesDAO.asociarMaterial(idCreador, material.getIdMaterial());
            }
        }
        return idCreador;
    }

    @Override
    public CreadoresDTO obtenerPorId(Integer idCreador) {
        this.creador = new CreadoresDTO();
        this.creador.setIdCreador(idCreador);
        super.obtenerPorId();
        return this.creador;
    }

    @Override
    public ArrayList<CreadoresDTO> listarTodos() {
        return (ArrayList<CreadoresDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(CreadoresDTO creador) {
        this.creador = creador;
        Integer resultado = super.modificar();
        if (resultado != null && resultado > 0 && creador.getMateriales() != null) {
            // Eliminar todas las asociaciones existentes
            creadoresMaterialesDAO.eliminarAsociacionesMateriales(creador.getIdCreador());
            // Crear las nuevas asociaciones
            for (MaterialesDTO material : creador.getMateriales()) {
                creadoresMaterialesDAO.asociarMaterial(creador.getIdCreador(), material.getIdMaterial());
            }
        }
        return resultado;
    }

    @Override
    public Integer eliminar(CreadoresDTO creador) {
        this.creador = creador;
        // Primero eliminar las asociaciones con materiales
        creadoresMaterialesDAO.eliminarAsociacionesMateriales(creador.getIdCreador());
        return super.eliminar();
    }
}
