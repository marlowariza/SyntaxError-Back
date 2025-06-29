package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.persistance.dao.impl.base.DAOImplBase;
import com.syntaxerror.biblioteca.model.NivelesInglesDTO;
import com.syntaxerror.biblioteca.model.enums.Nivel;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.syntaxerror.biblioteca.persistance.dao.NivelInglesDAO;

public class NivelInglesDAOImpl extends DAOImplBase implements NivelInglesDAO {

    private NivelesInglesDTO nivel;

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
        this.nivel = new NivelesInglesDTO();
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
    public Integer insertar(NivelesInglesDTO nivel) {
        this.nivel = nivel;
        return super.insertar();
    }

    @Override
    public NivelesInglesDTO obtenerPorId(Integer idNivel) {
        this.nivel = new NivelesInglesDTO();
        this.nivel.setIdNivel(idNivel);
        super.obtenerPorId();
        return this.nivel;
    }

    @Override
    public ArrayList<NivelesInglesDTO> listarTodos() {
        return (ArrayList<NivelesInglesDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(NivelesInglesDTO nivel) {
        this.nivel = nivel;
        return super.modificar();
    }

    @Override
    public Integer eliminar(NivelesInglesDTO nivel) {
        this.nivel = nivel;
        return super.eliminar();
    }

    @Override
    public List<NivelesInglesDTO> listarNombresNiveles() {
        List<NivelesInglesDTO> lista = new ArrayList<>();
        String sql = "SELECT ID_NIVEL, NIVEL FROM BIB_NIVELES_INGLES ORDER BY ID_NIVEL";

        try {
            this.abrirConexion();
            this.colocarSQLenStatement(sql);
            this.ejecutarConsultaEnBD();

            while (this.resultSet.next()) {
                int id = this.resultSet.getInt("ID_NIVEL");
                String nivelStr = this.resultSet.getString("NIVEL");

                NivelesInglesDTO dto = new NivelesInglesDTO();
                dto.setIdNivel(id);
                dto.setNivel(Nivel.valueOf(nivelStr)); // enum Nivel

                lista.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar nombres de niveles", e);
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
