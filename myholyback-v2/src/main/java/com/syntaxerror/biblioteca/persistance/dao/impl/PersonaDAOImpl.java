package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.model.PersonaDTO;
import com.syntaxerror.biblioteca.model.SedeDTO;
import com.syntaxerror.biblioteca.model.enums.NivelDeIngles;
import com.syntaxerror.biblioteca.model.enums.TipoPersona;
import com.syntaxerror.biblioteca.model.enums.Turnos;
import com.syntaxerror.biblioteca.persistance.dao.PersonaDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAOImpl extends DAOImplBase implements PersonaDAO {

    private PersonaDTO persona;

    public PersonaDAOImpl() {
        super("BIB_PERSONA");
        this.retornarLlavePrimaria = true;
        this.persona = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("ID_PERSONA", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("PATERNO", false, false));
        this.listaColumnas.add(new Columna("MATERNO", false, false));
        this.listaColumnas.add(new Columna("DIRECCION", false, false));
        this.listaColumnas.add(new Columna("TELEFONO", false, false));
        this.listaColumnas.add(new Columna("CORREO", false, false));
        this.listaColumnas.add(new Columna("CONTRASENHA", false, false));
        this.listaColumnas.add(new Columna("TIPO_PERSONA", false, false));
        this.listaColumnas.add(new Columna("NIVEL", false, false));
        this.listaColumnas.add(new Columna("TURNO", false, false));
        this.listaColumnas.add(new Columna("FECHA_CONTRATO_INI", false, false));
        this.listaColumnas.add(new Columna("FECHA_CONTRATO_FIN", false, false));
        this.listaColumnas.add(new Columna("VIGENTE", false, false));
        this.listaColumnas.add(new Columna("SEDE_IDSEDE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.persona.getNombre());
        this.statement.setString(2, this.persona.getPaterno());
        this.statement.setString(3, this.persona.getMaterno());
        this.statement.setString(4, this.persona.getDireccion());
        this.statement.setString(5, this.persona.getTelefono());
        this.statement.setString(6, this.persona.getCorreo());
        this.statement.setString(7, this.persona.getContrasenha());
        this.statement.setString(8, this.persona.getTipo().name());

        // NIVEL solo si es LECTOR
        if (this.persona.getTipo() == TipoPersona.LECTOR) {
            if (this.persona.getNivel() == null) {
                throw new SQLException("Los lectores deben tener un nivel de inglés.");
            }
            this.statement.setString(9, this.persona.getNivel().name());

            this.statement.setNull(10, java.sql.Types.VARCHAR); // TURNO
            this.statement.setNull(11, java.sql.Types.DATE);    // FECHA_CONTRATO_INI
            this.statement.setNull(12, java.sql.Types.DATE);    // FECHA_CONTRATO_FIN
            this.statement.setNull(13, java.sql.Types.INTEGER); // VIGENTE
        } // BIBLIOTECARIO
        else if (this.persona.getTipo() == TipoPersona.BIBLIOTECARIO) {
            this.statement.setNull(9, java.sql.Types.VARCHAR); // NIVEL

            if (this.persona.getTurno() == null || this.persona.getFechaContratoInicio() == null || this.persona.getFechaContratoFinal() == null || this.persona.getVigente() == null) {
                throw new SQLException("Los bibliotecarios deben tener turno, fechas de contrato y estado de vigencia.");
            }

            this.statement.setString(10, this.persona.getTurno().name());
            this.statement.setDate(11, new java.sql.Date(this.persona.getFechaContratoInicio().getTime()));
            this.statement.setDate(12, new java.sql.Date(this.persona.getFechaContratoFinal().getTime()));
            this.statement.setInt(13, this.persona.getVigente() ? 1 : 0);
        } else {
            throw new SQLException("Tipo de persona no reconocido.");
        }

        this.statement.setInt(14, this.persona.getSede().getIdSede());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        incluirValorDeParametrosParaInsercion(); // mismos campos que inserción
        this.statement.setInt(15, this.persona.getIdPersona());
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
        this.persona = new PersonaDTO();
        this.persona.setIdPersona(this.resultSet.getInt("ID_PERSONA"));
        this.persona.setNombre(this.resultSet.getString("NOMBRE"));
        this.persona.setPaterno(this.resultSet.getString("PATERNO"));
        this.persona.setMaterno(this.resultSet.getString("MATERNO"));
        this.persona.setDireccion(this.resultSet.getString("DIRECCION"));
        this.persona.setTelefono(this.resultSet.getString("TELEFONO"));
        this.persona.setCorreo(this.resultSet.getString("CORREO"));
        this.persona.setContrasenha(this.resultSet.getString("CONTRASENHA"));

        TipoPersona tipo = TipoPersona.valueOf(this.resultSet.getString("TIPO_PERSONA"));
        this.persona.setTipo(tipo);

        if (tipo == TipoPersona.LECTOR) {
            this.persona.setNivel(NivelDeIngles.valueOf(this.resultSet.getString("NIVEL")));
            this.persona.setTurno(null);
            this.persona.setFechaContratoInicio(null);
            this.persona.setFechaContratoFinal(null);
            this.persona.setVigente(null);
        } else if (tipo == TipoPersona.BIBLIOTECARIO) {
            this.persona.setNivel(null);
            this.persona.setTurno(Turnos.valueOf(this.resultSet.getString("TURNO")));
            this.persona.setFechaContratoInicio(this.resultSet.getDate("FECHA_CONTRATO_INI"));
            this.persona.setFechaContratoFinal(this.resultSet.getDate("FECHA_CONTRATO_FIN"));
            this.persona.setVigente(this.resultSet.getInt("VIGENTE") == 1);
        }

        // Relación sede
        SedeDTO sede = new SedeDTO();
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
    public Integer insertar(PersonaDTO persona) {
        if (persona == null) {
            throw new IllegalArgumentException("La persona no puede ser null");
        }
        this.persona = persona;
        return super.insertar();
    }

    @Override
    public PersonaDTO obtenerPorId(Integer idPersona) {
        if (idPersona == null || idPersona <= 0) {
            throw new IllegalArgumentException("El ID de la persona debe ser válido");
        }
        this.persona = new PersonaDTO();
        this.persona.setIdPersona(idPersona);
        super.obtenerPorId();
        return this.persona;
    }

    @Override
    public ArrayList<PersonaDTO> listarTodos() {
        return (ArrayList<PersonaDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(PersonaDTO persona) {
        if (persona == null || persona.getIdPersona() == null || persona.getIdPersona() <= 0) {
            throw new IllegalArgumentException("La persona y su ID deben ser válidos para modificar");
        }
        this.persona = persona;
        return super.modificar();
    }

    @Override
    public Integer eliminar(PersonaDTO persona) {
        if (persona == null || persona.getIdPersona() == null || persona.getIdPersona() <= 0) {
            throw new IllegalArgumentException("La persona y su ID deben ser válidos para eliminar");
        }
        this.persona = persona;
        return super.eliminar();
    }

}
