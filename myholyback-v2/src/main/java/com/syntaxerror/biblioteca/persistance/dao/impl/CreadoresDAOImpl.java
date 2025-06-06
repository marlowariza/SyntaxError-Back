package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.model.CreadoresDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.syntaxerror.biblioteca.model.enums.TipoCreador;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;
import com.syntaxerror.biblioteca.persistance.dao.CreadoresDAO;

public class CreadoresDAOImpl extends DAOImplRelacion implements CreadoresDAO {

    private CreadoresDTO creador;
    private MaterialesDTO material;

    public CreadoresDAOImpl() {
        super("BIB_CREADORES", "BIB_MATERIALES_CREADORES","CREADOR_IDCREADOR","MATERIAL_IDMATERIAL");
        this.retornarLlavePrimaria = true;
        this.creador = null;
        this.material = null;
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
        return super.insertar();
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
        return super.modificar();
    }

    @Override
    public Integer eliminar(CreadoresDTO creador) {
        this.creador = creador;
        return super.eliminar();
    }

    @Override
    public Integer asociarMaterial(MaterialesDTO material) {
        this.material = material;
        this.nombreTablaIntermedia = "BIB_MATERIALES_CREADORES";
        this.nombreColumnaPrimeraEntidad = "CREADOR_IDCREADOR";
        this.nombreColumnaSegundaEntidad = "MATERIAL_IDMATERIAL";
        return this.asociar(this.creador.getIdCreador(), this.material.getIdMaterial());
    }

    @Override
    public Integer desasociarMaterial(MaterialesDTO material) {
        this.material = material;
        this.nombreTablaIntermedia = "BIB_MATERIALES_CREADORES";
        this.nombreColumnaPrimeraEntidad = "CREADOR_IDCREADOR";
        this.nombreColumnaSegundaEntidad = "MATERIAL_IDMATERIAL";
        return this.desasociar(this.creador.getIdCreador(), this.material.getIdMaterial());
    }

    @Override
    public boolean existeRelacionConMaterial(MaterialesDTO material) {
        this.material = material;
        this.nombreTablaIntermedia = "BIB_MATERIALES_CREADORES";
        this.nombreColumnaPrimeraEntidad = "CREADOR_IDCREADOR";
        this.nombreColumnaSegundaEntidad = "MATERIAL_IDMATERIAL";
        return this.existeRelacion(this.creador.getIdCreador(), this.material.getIdMaterial());
    }

    @Override
    public ArrayList<CreadoresDTO> listarPorMaterial(MaterialesDTO material) {
        this.material = material;
        this.nombreTablaIntermedia = "BIB_MATERIALES_CREADORES";
        this.nombreColumnaPrimeraEntidad = "CREADOR_IDCREADOR";
        this.nombreColumnaSegundaEntidad = "MATERIAL_IDMATERIAL";
        return (ArrayList<CreadoresDTO>) this.listarRelacionados(this.material.getIdMaterial(), "MATERIAL_IDMATERIAL");
    }

    @Override
    protected void instanciarObjetoRelacionadoDelResultSet() throws SQLException {
        this.creador = new CreadoresDTO();
        this.creador.setIdCreador(this.resultSet.getInt("ID_CREADOR"));
        this.creador.setNombre(this.resultSet.getString("NOMBRE"));
        this.creador.setPaterno(this.resultSet.getString("PATERNO"));
        this.creador.setMaterno(this.resultSet.getString("MATERNO"));
        this.creador.setSeudonimo(this.resultSet.getString("SEUDONIMO"));
        this.creador.setTipo(TipoCreador.valueOf(this.resultSet.getString("TIPO_CREADOR")));
        this.creador.setNacionalidad(this.resultSet.getString("NACIONALIDAD"));
        this.creador.setActivo(this.resultSet.getInt("ACTIVO") == 1);
    }

    @Override
    protected Object obtenerObjetoRelacionado() {
        return this.creador;
    }
}
