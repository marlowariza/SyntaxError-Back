package com.syntaxerror.biblioteca.persistance.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.syntaxerror.biblioteca.model.CreadorDTO;
import com.syntaxerror.biblioteca.model.EditorialDTO;
import com.syntaxerror.biblioteca.model.MaterialDTO;
import com.syntaxerror.biblioteca.model.TemaDTO;
import com.syntaxerror.biblioteca.model.enums.NivelDeIngles;
import com.syntaxerror.biblioteca.persistance.dao.MaterialDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;

public class MaterialDAOImpl extends DAOImplRelacion implements MaterialDAO {

    private MaterialDTO material;
    private CreadorDTO creador;
    private TemaDTO tema;

    public MaterialDAOImpl() {
        super("BIB_MATERIAL", "BIB_MATERIAL_CREADOR", "MATERIAL_IDMATERIAL", "CREADOR_IDCREADOR");
        this.retornarLlavePrimaria = true;
        this.material = null;
        this.creador = null;
        this.tema = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("ID_MATERIAL", true, true));
        this.listaColumnas.add(new Columna("TITULO", false, false));
        this.listaColumnas.add(new Columna("EDICION", false, false));
        this.listaColumnas.add(new Columna("NIVEL", false, false));
        this.listaColumnas.add(new Columna("ANHIO_PUBLICACION", false, false));
        this.listaColumnas.add(new Columna("PORTADA", false, false));
        this.listaColumnas.add(new Columna("EDITORIAL_IDEDITORIAL", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.material.getTitulo());
        this.statement.setString(2, this.material.getEdicion());
        this.statement.setString(3, this.material.getNivel().name());
        this.statement.setInt(4, this.material.getAnioPublicacion());
        this.statement.setString(5, this.material.getPortada());
        this.statement.setInt(6, this.material.getEditorial().getIdEditorial());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.material.getTitulo());
        this.statement.setString(2, this.material.getEdicion());
        this.statement.setString(3, this.material.getNivel().name());
        this.statement.setInt(4, this.material.getAnioPublicacion());
        this.statement.setString(5, this.material.getPortada());
        this.statement.setInt(6, this.material.getEditorial().getIdEditorial());
        this.statement.setInt(7, this.material.getIdMaterial());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.material.getIdMaterial());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.material.getIdMaterial());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.material = new MaterialDTO();
        this.material.setIdMaterial(this.resultSet.getInt("ID_MATERIAL"));
        this.material.setTitulo(this.resultSet.getString("TITULO"));
        this.material.setEdicion(this.resultSet.getString("EDICION"));
        this.material.setNivel(NivelDeIngles.valueOf(this.resultSet.getString("NIVEL")));
        this.material.setAnioPublicacion(this.resultSet.getInt("ANHIO_PUBLICACION"));
        this.material.setPortada(this.resultSet.getString("PORTADA"));

        EditorialDTO editorial = new EditorialDTO();
        editorial.setIdEditorial(this.resultSet.getInt("EDITORIAL_IDEDITORIAL"));
        this.material.setEditorial(editorial);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.material = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.material);
    }

    @Override
    public Integer insertar(MaterialDTO material) {
        this.material = material;
        return super.insertar();
    }

    @Override
    public MaterialDTO obtenerPorId(Integer idMaterial) {
        this.material = new MaterialDTO();
        this.material.setIdMaterial(idMaterial);
        super.obtenerPorId();
        return this.material;
    }

    @Override
    public ArrayList<MaterialDTO> listarTodos() {
        return (ArrayList<MaterialDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(MaterialDTO material) {
        this.material = material;
        return super.modificar();
    }

    @Override
    public Integer eliminar(MaterialDTO material) {
        this.material = material;
        return super.eliminar();
    }

    @Override
    public Integer asociarCreador(CreadorDTO creador) {
        this.creador = creador;
        return this.asociar(this.material.getIdMaterial(), this.creador.getIdCreador());
    }

    @Override
    public Integer desasociarCreador(CreadorDTO creador) {
        this.creador = creador;
        return this.desasociar(this.material.getIdMaterial(), this.creador.getIdCreador());
    }

    @Override
    public boolean existeRelacionConCreador(CreadorDTO creador) {
        this.creador = creador;
        return this.existeRelacion(this.material.getIdMaterial(), this.creador.getIdCreador());
    }

    @Override
    public ArrayList<MaterialDTO> listarPorCreador(CreadorDTO creador) {
        this.creador = creador;
        return (ArrayList<MaterialDTO>) this.listarRelacionados(this.creador.getIdCreador(), "CREADOR_IDCREADOR");
    }

    @Override
    public Integer asociarTema(TemaDTO tema) {
        this.tema = tema;
        this.nombreTablaIntermedia = "BIB_MATERIAL_TEMA";
        this.nombreColumnaPrimeraEntidad = "MATERIAL_IDMATERIAL";
        this.nombreColumnaSegundaEntidad = "TEMA_IDTEMA";
        return this.asociar(this.material.getIdMaterial(), this.tema.getIdTema());
    }

    @Override
    public Integer desasociarTema(TemaDTO tema) {
        this.tema = tema;
        this.nombreTablaIntermedia = "BIB_MATERIAL_TEMA";
        this.nombreColumnaPrimeraEntidad = "MATERIAL_IDMATERIAL";
        this.nombreColumnaSegundaEntidad = "TEMA_IDTEMA";
        return this.desasociar(this.material.getIdMaterial(), this.tema.getIdTema());
    }

    @Override
    public boolean existeRelacionConTema(TemaDTO tema) {
        this.tema = tema;
        this.nombreTablaIntermedia = "BIB_MATERIAL_TEMA";
        this.nombreColumnaPrimeraEntidad = "MATERIAL_IDMATERIAL";
        this.nombreColumnaSegundaEntidad = "TEMA_IDTEMA";
        return this.existeRelacion(this.material.getIdMaterial(), this.tema.getIdTema());
    }

    @Override
    public ArrayList<MaterialDTO> listarPorTema(TemaDTO tema) {
        this.tema = tema;
        this.nombreTablaIntermedia = "BIB_MATERIAL_TEMA";
        this.nombreColumnaPrimeraEntidad = "MATERIAL_IDMATERIAL";
        this.nombreColumnaSegundaEntidad = "TEMA_IDTEMA";
        return (ArrayList<MaterialDTO>) this.listarRelacionados(this.tema.getIdTema(), "TEMA_IDTEMA");
    }

    @Override
    protected void instanciarObjetoRelacionadoDelResultSet() throws SQLException {
        this.material = new MaterialDTO();
        this.material.setIdMaterial(this.resultSet.getInt("ID_MATERIAL"));
        this.material.setTitulo(this.resultSet.getString("TITULO"));
        this.material.setEdicion(this.resultSet.getString("EDICION"));
        this.material.setNivel(NivelDeIngles.valueOf(this.resultSet.getString("NIVEL")));
        this.material.setAnioPublicacion(this.resultSet.getInt("ANHIO_PUBLICACION"));
        this.material.setPortada(this.resultSet.getString("PORTADA"));

        EditorialDTO editorial = new EditorialDTO();
        editorial.setIdEditorial(this.resultSet.getInt("EDITORIAL_IDEDITORIAL"));
        this.material.setEditorial(editorial);
    }

    @Override
    protected Object obtenerObjetoRelacionado() {
        return this.material;
    }
}
