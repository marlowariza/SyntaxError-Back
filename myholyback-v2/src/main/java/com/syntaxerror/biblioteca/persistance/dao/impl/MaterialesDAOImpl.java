package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.model.CreadoresDTO;
import com.syntaxerror.biblioteca.model.EditorialesDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.NivelesInglesDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.syntaxerror.biblioteca.model.TemasDTO;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;
import com.syntaxerror.biblioteca.persistance.dao.MaterialesDAO;

public class MaterialesDAOImpl extends DAOImplRelacion implements MaterialesDAO {

    private MaterialesDTO material;
    private CreadoresDTO creador;
    private TemasDTO tema;

    public MaterialesDAOImpl() {
        super("BIB_MATERIALES", "BIB_MATERIALES_CREADORES", "MATERIAL_IDMATERIAL", "CREADOR_IDCREADOR");
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
        this.listaColumnas.add(new Columna("ANHIO_PUBLICACION", false, false));
        this.listaColumnas.add(new Columna("PORTADA", false, false));
        this.listaColumnas.add(new Columna("VIGENTE", false, false));
        this.listaColumnas.add(new Columna("NIVEL_IDNIVEL", false, false));
        this.listaColumnas.add(new Columna("EDITORIAL_IDEDITORIAL", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.material.getTitulo());
        this.statement.setString(2, this.material.getEdicion());
        this.statement.setInt(3, this.material.getAnioPublicacion());
        this.statement.setString(4, this.material.getPortada());
        this.statement.setInt(5, this.material.getVigente()? 1 : 0);
        this.statement.setInt(6, this.material.getNivel().getIdNivel());
        this.statement.setInt(7, this.material.getEditorial().getIdEditorial());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.material.getTitulo());
        this.statement.setString(2, this.material.getEdicion());
        this.statement.setInt(3, this.material.getAnioPublicacion());
        this.statement.setString(4, this.material.getPortada());
        this.statement.setInt(5, this.material.getVigente()? 1 : 0); 
        this.statement.setInt(6, this.material.getNivel().getIdNivel()); 
        this.statement.setInt(7, this.material.getEditorial().getIdEditorial());
        this.statement.setInt(8, this.material.getIdMaterial());
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
        this.material = new MaterialesDTO();
        this.material.setIdMaterial(this.resultSet.getInt("ID_MATERIAL"));
        this.material.setTitulo(this.resultSet.getString("TITULO"));
        this.material.setEdicion(this.resultSet.getString("EDICION"));
        this.material.setAnioPublicacion(this.resultSet.getInt("ANHIO_PUBLICACION"));
        this.material.setPortada(this.resultSet.getString("PORTADA"));
        this.material.setVigente(this.resultSet.getInt("VIGENTE") == 1);

        // Relación con NivelesIngles
        NivelesInglesDTO nivel = new NivelesInglesDTO();
        nivel.setIdNivel(this.resultSet.getInt("NIVEL_IDNIVEL"));
        this.material.setNivel(nivel);

        EditorialesDTO editorial = new EditorialesDTO();
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
    public Integer insertar(MaterialesDTO material) {
        this.material = material;
        return super.insertar();
    }

    @Override
    public MaterialesDTO obtenerPorId(Integer idMaterial) {
        this.material = new MaterialesDTO();
        this.material.setIdMaterial(idMaterial);
        super.obtenerPorId();
        return this.material;
    }

    @Override
    public ArrayList<MaterialesDTO> listarTodos() {
        return (ArrayList<MaterialesDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(MaterialesDTO material) {
        this.material = material;
        return super.modificar();
    }

    @Override
    public Integer eliminar(MaterialesDTO material) {
        this.material = material;
        return super.eliminar();
    }

    @Override
    public Integer asociarCreador(CreadoresDTO creador) {
        this.creador = creador;
        return this.asociar(this.material.getIdMaterial(), this.creador.getIdCreador());
    }

    @Override
    public Integer desasociarCreador(CreadoresDTO creador) {
        this.creador = creador;
        return this.desasociar(this.material.getIdMaterial(), this.creador.getIdCreador());
    }

    @Override
    public boolean existeRelacionConCreador(CreadoresDTO creador) {
        this.creador = creador;
        return this.existeRelacion(this.material.getIdMaterial(), this.creador.getIdCreador());
    }

    @Override
    public ArrayList<MaterialesDTO> listarPorCreador(CreadoresDTO creador) {
        this.creador = creador;
        return (ArrayList<MaterialesDTO>) this.listarRelacionados(this.creador.getIdCreador(), "CREADOR_IDCREADOR");
    }

    @Override
    public Integer asociarTema(TemasDTO tema) {
        this.tema = tema;
        this.nombreTablaIntermedia = "BIB_MATERIALES_TEMAS";
        this.nombreColumnaPrimeraEntidad = "MATERIAL_IDMATERIAL";
        this.nombreColumnaSegundaEntidad = "TEMA_IDTEMA";
        return this.asociar(this.material.getIdMaterial(), this.tema.getIdTema());
    }

    @Override
    public Integer desasociarTema(TemasDTO tema) {
        this.tema = tema;
        this.nombreTablaIntermedia = "BIB_MATERIALES_TEMAS";
        this.nombreColumnaPrimeraEntidad = "MATERIAL_IDMATERIAL";
        this.nombreColumnaSegundaEntidad = "TEMA_IDTEMA";
        return this.desasociar(this.material.getIdMaterial(), this.tema.getIdTema());
    }

    @Override
    public boolean existeRelacionConTema(TemasDTO tema) {
        this.tema = tema;
        this.nombreTablaIntermedia = "BIB_MATERIALES_TEMAS";
        this.nombreColumnaPrimeraEntidad = "MATERIAL_IDMATERIAL";
        this.nombreColumnaSegundaEntidad = "TEMA_IDTEMA";
        return this.existeRelacion(this.material.getIdMaterial(), this.tema.getIdTema());
    }

    @Override
    public ArrayList<MaterialesDTO> listarPorTema(TemasDTO tema) {
        this.tema = tema;
        this.nombreTablaIntermedia = "BIB_MATERIALES_TEMAS";
        this.nombreColumnaPrimeraEntidad = "MATERIAL_IDMATERIAL";
        this.nombreColumnaSegundaEntidad = "TEMA_IDTEMA";
        return (ArrayList<MaterialesDTO>) this.listarRelacionados(this.tema.getIdTema(), "TEMA_IDTEMA");
    }

    @Override
    protected void instanciarObjetoRelacionadoDelResultSet() throws SQLException {
        this.material = new MaterialesDTO();
        this.material.setIdMaterial(this.resultSet.getInt("ID_MATERIAL"));
        this.material.setTitulo(this.resultSet.getString("TITULO"));
        this.material.setEdicion(this.resultSet.getString("EDICION"));
        this.material.setAnioPublicacion(this.resultSet.getInt("ANHIO_PUBLICACION"));
        this.material.setPortada(this.resultSet.getString("PORTADA"));
        this.material.setVigente(this.resultSet.getInt("VIGENTE") == 1);

        NivelesInglesDTO nivel = new NivelesInglesDTO();
        nivel.setIdNivel(this.resultSet.getInt("NIVEL_IDNIVEL"));
        this.material.setNivel(nivel);

        EditorialesDTO editorial = new EditorialesDTO();
        editorial.setIdEditorial(this.resultSet.getInt("EDITORIAL_IDEDITORIAL"));
        this.material.setEditorial(editorial);
    }

    @Override
    protected Object obtenerObjetoRelacionado() {
        return this.material;
    }
}
