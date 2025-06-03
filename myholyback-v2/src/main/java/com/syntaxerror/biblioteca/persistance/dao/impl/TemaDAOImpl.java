package com.syntaxerror.biblioteca.persistance.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.syntaxerror.biblioteca.model.CreadorDTO;
import com.syntaxerror.biblioteca.model.MaterialDTO;
import com.syntaxerror.biblioteca.model.TemaDTO;
import com.syntaxerror.biblioteca.model.enums.Categoria;
import com.syntaxerror.biblioteca.persistance.dao.TemaDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;

public class TemaDAOImpl extends DAOImplRelacion implements TemaDAO {

    private TemaDTO tema;
    private MaterialDTO material;

    public TemaDAOImpl() {
        super("BIB_TEMA", "BIB_MATERIAL_TEMA", "TEMA_IDTEMA", "MATERIAL_IDMATERIAL");
        this.retornarLlavePrimaria = true;
        this.tema = null;
        this.material = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("ID_TEMA", true, true));
        this.listaColumnas.add(new Columna("DESCRIPCION", false, false));
        this.listaColumnas.add(new Columna("CATEGORIA", false, false));
        this.listaColumnas.add(new Columna("ID_TEMA_PADRE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.tema.getDescripcion());
        this.statement.setString(2, this.tema.getCategoria().name());
        if (this.tema.getTemaPadre() != null) {
            this.statement.setInt(3, this.tema.getTemaPadre().getIdTema());
        } else {
            this.statement.setNull(3, java.sql.Types.INTEGER);
        }
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.tema.getDescripcion());
        this.statement.setString(2, this.tema.getCategoria().name());
        if (this.tema.getTemaPadre() != null) {
            this.statement.setInt(3, this.tema.getTemaPadre().getIdTema());
        } else {
            this.statement.setNull(3, java.sql.Types.INTEGER);
        }
        this.statement.setInt(4, this.tema.getIdTema());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.tema.getIdTema());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.tema.getIdTema());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.tema = new TemaDTO();
        this.tema.setIdTema(this.resultSet.getInt("ID_TEMA"));
        this.tema.setDescripcion(this.resultSet.getString("DESCRIPCION"));
        this.tema.setCategoria(Categoria.valueOf(this.resultSet.getString("CATEGORIA")));
        
        int idPadre = resultSet.getInt("ID_TEMA_PADRE");
        if (!resultSet.wasNull()) {
            TemaDTO padre = new TemaDTO();
            padre.setIdTema(idPadre);
            this.tema.setTemaPadre(padre);
        } else {
            this.tema.setTemaPadre(null);
        }
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.tema = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.tema);
    }

    @Override
    public Integer insertar(TemaDTO tema) {
        this.tema = tema;
        return super.insertar();
    }

    @Override
    public TemaDTO obtenerPorId(Integer idTema) {
        this.tema = new TemaDTO();
        this.tema.setIdTema(idTema);
        super.obtenerPorId();
        return this.tema;
    }

    @Override
    public ArrayList<TemaDTO> listarTodos() {
        return (ArrayList<TemaDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(TemaDTO tema) {
        this.tema = tema;
        return super.modificar();
    }

    @Override
    public Integer eliminar(TemaDTO tema) {
        this.tema = tema;
        return super.eliminar();
    }

    @Override
    public Integer asociarMaterial(MaterialDTO material) {
        this.material = material;
        this.nombreTablaIntermedia = "BIB_MATERIAL_TEMA";
        this.nombreColumnaPrimeraEntidad = "TEMA_IDTEMA";
        this.nombreColumnaSegundaEntidad = "MATERIAL_IDMATERIAL";
        return this.asociar(this.tema.getIdTema(), this.material.getIdMaterial());
    }

    @Override
    public Integer desasociarMaterial(MaterialDTO material) {
        this.material = material;
        this.nombreTablaIntermedia = "BIB_MATERIAL_TEMA";
        this.nombreColumnaPrimeraEntidad = "TEMA_IDTEMA";
        this.nombreColumnaSegundaEntidad = "MATERIAL_IDMATERIAL";
        return this.desasociar(this.tema.getIdTema(), this.material.getIdMaterial());
    }

    @Override
    public boolean existeRelacionConMaterial(MaterialDTO material) {
        this.material = material;
        this.nombreTablaIntermedia = "BIB_MATERIAL_TEMA";
        this.nombreColumnaPrimeraEntidad = "TEMA_IDTEMA";
        this.nombreColumnaSegundaEntidad = "MATERIAL_IDMATERIAL";
        return this.existeRelacion(this.tema.getIdTema(), this.material.getIdMaterial());
    }

    @Override
    public ArrayList<TemaDTO> listarPorMaterial(MaterialDTO material) {
        this.material = material;
        this.nombreTablaIntermedia = "BIB_MATERIAL_TEMA";
        this.nombreColumnaPrimeraEntidad = "TEMA_IDTEMA";
        this.nombreColumnaSegundaEntidad = "MATERIAL_IDMATERIAL";
        return (ArrayList<TemaDTO>) this.listarRelacionados(this.material.getIdMaterial(), "MATERIAL_IDMATERIAL");
    }

    @Override
    protected void instanciarObjetoRelacionadoDelResultSet() throws SQLException {
        this.tema = new TemaDTO();
        this.tema.setIdTema(this.resultSet.getInt("ID_TEMA"));
        this.tema.setDescripcion(this.resultSet.getString("DESCRIPCION"));
        this.tema.setCategoria(Categoria.valueOf(this.resultSet.getString("CATEGORIA")));
        
        int idPadre = resultSet.getInt("ID_TEMA_PADRE");
        if (!resultSet.wasNull()) {
            TemaDTO padre = new TemaDTO();
            padre.setIdTema(idPadre);
            this.tema.setTemaPadre(padre);
        } else {
            this.tema.setTemaPadre(null);
        }
    }

    @Override
    protected Object obtenerObjetoRelacionado() {
        return this.tema;
    }
}
