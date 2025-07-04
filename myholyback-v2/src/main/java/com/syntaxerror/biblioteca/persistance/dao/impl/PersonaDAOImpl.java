package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.persistance.dao.impl.base.DAOImplBase;
import com.syntaxerror.biblioteca.model.NivelesInglesDTO;
import com.syntaxerror.biblioteca.model.PersonasDTO;
import com.syntaxerror.biblioteca.model.SedesDTO;
import com.syntaxerror.biblioteca.model.enums.TipoPersona;
import com.syntaxerror.biblioteca.model.enums.Turnos;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.syntaxerror.biblioteca.persistance.dao.PersonaDAO;

public class PersonaDAOImpl extends DAOImplBase implements PersonaDAO {

    private PersonasDTO persona;

    public PersonaDAOImpl() {
        super("BIB_PERSONAS");
        this.retornarLlavePrimaria = true;
        this.persona = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("ID_PERSONA", true, true));
        this.listaColumnas.add(new Columna("CODIGO", false, false));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("PATERNO", false, false));
        this.listaColumnas.add(new Columna("MATERNO", false, false));
        this.listaColumnas.add(new Columna("DIRECCION", false, false));
        this.listaColumnas.add(new Columna("TELEFONO", false, false));
        this.listaColumnas.add(new Columna("CORREO", false, false));
        this.listaColumnas.add(new Columna("CONTRASENHA", false, false));
        this.listaColumnas.add(new Columna("TIPO_PERSONA", false, false));
        this.listaColumnas.add(new Columna("TURNO", false, false));
        this.listaColumnas.add(new Columna("FECHA_CONTRATO_INI", false, false));
        this.listaColumnas.add(new Columna("FECHA_CONTRATO_FIN", false, false));
        this.listaColumnas.add(new Columna("DEUDA_ACUMULADA", false, false));
        this.listaColumnas.add(new Columna("FECHA_SANCION_FIN", false, false));
        this.listaColumnas.add(new Columna("VIGENTE", false, false));
        this.listaColumnas.add(new Columna("NIVEL_IDNIVEL", false, false));
        this.listaColumnas.add(new Columna("SEDE_IDSEDE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.persona.getCodigo());
        this.statement.setString(2, this.persona.getNombre());
        this.statement.setString(3, this.persona.getPaterno());
        this.statement.setString(4, this.persona.getMaterno());
        this.statement.setString(5, this.persona.getDireccion());
        this.statement.setString(6, this.persona.getTelefono());
        this.statement.setString(7, this.persona.getCorreo());
        this.statement.setString(8, this.persona.getContrasenha());
        this.statement.setString(9, this.persona.getTipo().name());

        if (this.persona.getTurno() != null) {
            this.statement.setString(10, this.persona.getTurno().name());
        } else {
            this.statement.setNull(10, java.sql.Types.VARCHAR);
        }

        this.statement.setDate(11, this.persona.getFechaContratoInicio() != null
                ? new java.sql.Date(this.persona.getFechaContratoInicio().getTime())
                : null);
        this.statement.setDate(12, this.persona.getFechaContratoFinal() != null
                ? new java.sql.Date(this.persona.getFechaContratoFinal().getTime())
                : null);

        this.statement.setDouble(13, this.persona.getDeuda() != null
                ? this.persona.getDeuda() : 0.0);

        if (this.persona.getFechaSancionFinal() != null) {
            this.statement.setDate(14, new java.sql.Date(this.persona.getFechaSancionFinal().getTime()));
        } else {
            this.statement.setNull(14, java.sql.Types.DATE);
        }

        this.statement.setInt(15, this.persona.getVigente() != null && this.persona.getVigente() ? 1 : 0);

        if (this.persona.getNivel() != null) {
            this.statement.setInt(16, this.persona.getNivel().getIdNivel());
        } else {
            this.statement.setNull(16, java.sql.Types.INTEGER);
        }

        this.statement.setInt(17, this.persona.getSede().getIdSede());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        incluirValorDeParametrosParaInsercion(); // mismos campos que inserción
        this.statement.setInt(18, this.persona.getIdPersona());
        //En modificar el ID va al ultimo
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.persona.getIdPersona());
        //Para eliminar solo va el id
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.persona.getIdPersona());
        //Para obtener por Id igual solo el id
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.persona = new PersonasDTO();

        this.persona.setIdPersona(this.resultSet.getInt("ID_PERSONA"));
        this.persona.setCodigo(this.resultSet.getString("CODIGO"));
        this.persona.setNombre(this.resultSet.getString("NOMBRE"));
        this.persona.setPaterno(this.resultSet.getString("PATERNO"));
        this.persona.setMaterno(this.resultSet.getString("MATERNO"));
        this.persona.setDireccion(this.resultSet.getString("DIRECCION"));
        this.persona.setTelefono(this.resultSet.getString("TELEFONO"));
        this.persona.setCorreo(this.resultSet.getString("CORREO"));
        this.persona.setContrasenha(this.resultSet.getString("CONTRASENHA"));
        this.persona.setTipo(TipoPersona.valueOf(this.resultSet.getString("TIPO_PERSONA")));

        String turnoStr = this.resultSet.getString("TURNO");
        this.persona.setTurno(turnoStr != null ? Turnos.valueOf(turnoStr) : null);

        this.persona.setFechaContratoInicio(this.resultSet.getDate("FECHA_CONTRATO_INI"));
        this.persona.setFechaContratoFinal(this.resultSet.getDate("FECHA_CONTRATO_FIN"));
        this.persona.setDeuda(this.resultSet.getDouble("DEUDA_ACUMULADA"));
        this.persona.setFechaSancionFinal(this.resultSet.getDate("FECHA_SANCION_FIN"));
        this.persona.setVigente(this.resultSet.getInt("VIGENTE") == 1);

        int idNivel = this.resultSet.getInt("NIVEL_IDNIVEL");
        if (!this.resultSet.wasNull()) {
            NivelesInglesDTO nivel = new NivelesInglesDTO();
            nivel.setIdNivel(idNivel);
            this.persona.setNivel(nivel);
        }

        // Relación sede
        SedesDTO sede = new SedesDTO();
        sede.setIdSede(this.resultSet.getInt("SEDE_IDSEDE"));
        this.persona.setSede(sede);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.persona = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.persona);
    }

    @Override
    public Integer insertar(PersonasDTO persona) {
        if (persona == null) {
            throw new IllegalArgumentException("La persona no puede ser null");
        }
        this.persona = persona;
        return super.insertar();
    }

    @Override
    public PersonasDTO obtenerPorId(Integer idPersona) {
        if (idPersona == null || idPersona <= 0) {
            throw new IllegalArgumentException("El ID de la persona debe ser válido");
        }
        this.persona = new PersonasDTO();
        this.persona.setIdPersona(idPersona);
        super.obtenerPorId();
        return this.persona;
    }

    @Override
    public ArrayList<PersonasDTO> listarTodos() {
        return (ArrayList<PersonasDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(PersonasDTO persona) {
        if (persona == null || persona.getIdPersona() == null || persona.getIdPersona() <= 0) {
            throw new IllegalArgumentException("La persona y su ID deben ser válidos para modificar");
        }
        this.persona = persona;
        return super.modificar();
    }

    @Override
    public Integer eliminar(PersonasDTO persona) {
        if (persona == null || persona.getIdPersona() == null || persona.getIdPersona() <= 0) {
            throw new IllegalArgumentException("La persona y su ID deben ser válidos para eliminar");
        }
        this.persona = persona;
        return super.eliminar();
    }

    @Override
    public PersonasDTO obtenerPorCredenciales(String identificador, String contrasenha) {
        try {
            this.abrirConexion();

            String sql = """
            SELECT %s
            FROM BIB_PERSONAS
            WHERE (CORREO = ? OR CODIGO = ?) AND CONTRASENHA = ?
        """.formatted(this.generarListaDeCampos());

            this.colocarSQLenStatement(sql);
            this.statement.setString(1, identificador);
            this.statement.setString(2, identificador);
            this.statement.setString(3, contrasenha); // ya cifrada
            this.ejecutarConsultaEnBD();

            if (this.resultSet.next()) {
                this.instanciarObjetoDelResultSet();
                return this.persona;
            }

        } catch (SQLException e) {
            System.err.println("Error en obtenerPorCredenciales: " + e);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException e) {
                System.err.println("Error cerrando conexión: " + e);
            }
        }
        return null;
    }

    @Override
    public List<PersonasDTO> listarTodosPaginado(int limite, int offset) {
        String sql = """
        SELECT %s
        FROM BIB_PERSONAS
        ORDER BY NOMBRE, PATERNO, MATERNO
        LIMIT ? OFFSET ?
    """.formatted(this.generarListaDeCampos());

        return (List<PersonasDTO>) this.listarTodos(
                sql,
                obj -> {
                    try {
                        this.statement.setInt(1, limite);
                        this.statement.setInt(2, offset);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                },
                null
        );
    }

    @Override
    public int contar() {
        return super.contar();
    }

}
