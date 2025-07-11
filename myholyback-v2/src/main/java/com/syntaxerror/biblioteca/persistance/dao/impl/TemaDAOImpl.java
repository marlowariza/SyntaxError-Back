package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.persistance.dao.impl.base.DAOImplBase;
import com.syntaxerror.biblioteca.persistance.dao.impl.base.MaterialTemaDAOImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.syntaxerror.biblioteca.model.TemasDTO;
import com.syntaxerror.biblioteca.model.enums.Categoria;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.persistance.dao.TemaDAO;

public class TemaDAOImpl extends DAOImplBase implements TemaDAO {

    private TemasDTO tema;
    private MaterialTemaDAOImpl materialesTemasDAO;

    public TemaDAOImpl() {
        super("BIB_TEMAS");
        this.retornarLlavePrimaria = true;
        this.tema = null;
        this.materialesTemasDAO = new MaterialTemaDAOImpl();
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
        this.tema = new TemasDTO();
        this.tema.setIdTema(this.resultSet.getInt("ID_TEMA"));
        this.tema.setDescripcion(this.resultSet.getString("DESCRIPCION"));
        this.tema.setCategoria(Categoria.valueOf(this.resultSet.getString("CATEGORIA")));

        int idPadre = resultSet.getInt("ID_TEMA_PADRE");
        if (!resultSet.wasNull()) {
            TemasDTO padre = new TemasDTO();
            padre.setIdTema(idPadre);
            this.tema.setTemaPadre(padre);
        } else {
            this.tema.setTemaPadre(null);
        }

        // Cargar materiales asociados
        if (this.tema.getIdTema() != null) {
            this.tema.setMateriales(materialesTemasDAO.listarPorTema(this.tema.getIdTema()));
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
    public Integer insertar(TemasDTO tema) {
        this.tema = tema;
        Integer idTema = super.insertar();
        if (idTema != null && tema.getMateriales() != null) {
            for (MaterialesDTO material : tema.getMateriales()) {
                materialesTemasDAO.asociarTema(idTema, material.getIdMaterial());
            }
        }
        return idTema;
    }

    @Override
    public TemasDTO obtenerPorId(Integer idTema) {
        this.tema = new TemasDTO();
        this.tema.setIdTema(idTema);
        super.obtenerPorId();
        return this.tema;
    }

    @Override
    public ArrayList<TemasDTO> listarTodos() {
        return (ArrayList<TemasDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(TemasDTO tema) {
        this.tema = tema;
        Integer resultado = super.modificar();
        if (resultado != null && tema.getMateriales() != null) {
            // Eliminar todas las asociaciones existentes
            materialesTemasDAO.eliminarAsociacionesConMateriales(tema.getIdTema());
            // Crear las nuevas asociaciones
            for (MaterialesDTO material : tema.getMateriales()) {
                materialesTemasDAO.asociarTema(tema.getIdTema(), material.getIdMaterial());
            }
        }
        return resultado;
    }

    @Override
    public Integer eliminar(TemasDTO tema) {
        this.tema = tema;
        // Primero eliminar las asociaciones con materiales
        materialesTemasDAO.eliminarAsociacionesConMateriales(tema.getIdTema());
        return super.eliminar();
    }

    @Override
    public List<TemasDTO> listarNombresTemas() {
        List<TemasDTO> lista = new ArrayList<>();
        String sql = "SELECT ID_TEMA, DESCRIPCION FROM BIB_TEMAS ORDER BY DESCRIPCION";

        try {
            this.abrirConexion();
            this.colocarSQLenStatement(sql);
            this.ejecutarConsultaEnBD();

            while (this.resultSet.next()) {
                TemasDTO tema = new TemasDTO();
                tema.setIdTema(this.resultSet.getInt("ID_TEMA"));
                tema.setDescripcion(this.resultSet.getString("DESCRIPCION")); // o setNombre() si aplica
                lista.add(tema);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar nombres de temas", e);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }

        return lista;
    }

}
