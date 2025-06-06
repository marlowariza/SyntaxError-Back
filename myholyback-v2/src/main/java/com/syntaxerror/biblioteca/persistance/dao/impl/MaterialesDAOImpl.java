package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.persistance.dao.impl.base.DAOImplBase;
import com.syntaxerror.biblioteca.persistance.dao.impl.base.MaterialesTemasDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.base.CreadoresMaterialesDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.syntaxerror.biblioteca.model.CreadoresDTO;
import com.syntaxerror.biblioteca.model.EditorialesDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.NivelesInglesDTO;
import com.syntaxerror.biblioteca.model.TemasDTO;
import com.syntaxerror.biblioteca.persistance.dao.MaterialesDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;

public class MaterialesDAOImpl extends DAOImplBase implements MaterialesDAO {

    private MaterialesDTO material;
    private CreadoresMaterialesDAO creadoresMaterialesDAO;
    private MaterialesTemasDAO materialesTemasDAO;

    public MaterialesDAOImpl() {
        super("BIB_MATERIALES");
        this.retornarLlavePrimaria = true;
        this.material = null;
        this.creadoresMaterialesDAO = new CreadoresMaterialesDAO();
        this.materialesTemasDAO = new MaterialesTemasDAO();
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
        
        // Cargar relaciones
        this.material.setCreadores(creadoresMaterialesDAO.listarPorMaterial(this.material.getIdMaterial()));
        this.material.setTemas(materialesTemasDAO.listarPorMaterial(this.material.getIdMaterial()));
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
        Integer idMaterial = super.insertar();
        if (idMaterial != null && idMaterial > 0) {
            // Insertar relaciones con creadores
            if (material.getCreadores() != null) {
                for (CreadoresDTO creador : material.getCreadores()) {
                    creadoresMaterialesDAO.asociarMaterial(creador.getIdCreador(), idMaterial);
                }
            }
            // Insertar relaciones con temas
            if (material.getTemas() != null) {
                for (TemasDTO tema : material.getTemas()) {
                    materialesTemasDAO.asociarTema(idMaterial, tema.getIdTema());
                }
            }
        }
        return idMaterial;
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
        Integer resultado = super.modificar();
        if (resultado != null && resultado > 0) {
            // Eliminar y recrear relaciones con creadores
            creadoresMaterialesDAO.eliminarAsociacionesMateriales(material.getIdMaterial());
            if (material.getCreadores() != null) {
                for (CreadoresDTO creador : material.getCreadores()) {
                    creadoresMaterialesDAO.asociarMaterial(creador.getIdCreador(), material.getIdMaterial());
                }
            }
            
            // Eliminar y recrear relaciones con temas
            materialesTemasDAO.eliminarAsociacionesTemas(material.getIdMaterial());
            if (material.getTemas() != null) {
                for (TemasDTO tema : material.getTemas()) {
                    materialesTemasDAO.asociarTema(material.getIdMaterial(), tema.getIdTema());
                }
            }
        }
        return resultado;
    }

    @Override
    public Integer eliminar(MaterialesDTO material) {
        this.material = material;
        // Primero eliminar las relaciones
        creadoresMaterialesDAO.eliminarAsociacionesMateriales(material.getIdMaterial());
        materialesTemasDAO.eliminarAsociacionesTemas(material.getIdMaterial());
        return super.eliminar();
    }
}
