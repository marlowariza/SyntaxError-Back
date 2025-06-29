package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.persistance.dao.impl.base.DAOImplBase;
import com.syntaxerror.biblioteca.persistance.dao.impl.base.CreadorMaterialDAOImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.syntaxerror.biblioteca.model.CreadoresDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.enums.TipoCreador;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;
import com.syntaxerror.biblioteca.persistance.dao.CreadorDAO;

public class CreadorDAOImpl extends DAOImplBase implements CreadorDAO {

    private CreadoresDTO creador;
    private CreadorMaterialDAOImpl creadoresMaterialesDAO;

    public CreadorDAOImpl() {
        super("BIB_CREADORES");
        this.retornarLlavePrimaria = true;
        this.creador = null;
        this.creadoresMaterialesDAO = new CreadorMaterialDAOImpl();
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
            creadoresMaterialesDAO.eliminarAsociacionesConMateriales(creador.getIdCreador());
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
        creadoresMaterialesDAO.eliminarAsociacionesConMateriales(creador.getIdCreador());
        return super.eliminar();
    }

    @Override
    public List<CreadoresDTO> listarNombresAutores() {
        List<CreadoresDTO> lista = new ArrayList<>();
        String sql = """
        SELECT ID_CREADOR,
               CASE 
                 WHEN TRIM(SEUDONIMO) IS NOT NULL AND TRIM(SEUDONIMO) != ''
                 THEN TRIM(SEUDONIMO)
                 ELSE CONCAT_WS(' ', TRIM(NOMBRE), TRIM(PATERNO), TRIM(MATERNO))
               END AS NOMBRE_COMPLETO
        FROM BIB_CREADORES
        WHERE TIPO_CREADOR = 'AUTOR' AND ACTIVO = 1
        ORDER BY NOMBRE_COMPLETO
    """;

        try {
            this.abrirConexion();
            this.colocarSQLenStatement(sql);
            this.ejecutarConsultaEnBD();

            while (this.resultSet.next()) {
                int id = this.resultSet.getInt("ID_CREADOR");
                String nombre = this.resultSet.getString("NOMBRE_COMPLETO");

                CreadoresDTO autor = new CreadoresDTO();
                autor.setIdCreador(id);
                autor.setSeudonimo(nombre); // usando el campo `seudonimo` para contener el nombre final

                lista.add(autor);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar nombres de autores", e);
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
