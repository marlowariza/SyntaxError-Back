package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.persistance.dao.impl.base.DAOImplBase;
import com.syntaxerror.biblioteca.model.EjemplaresDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.SedesDTO;
import com.syntaxerror.biblioteca.model.enums.FormatoDigital;
import com.syntaxerror.biblioteca.model.enums.TipoEjemplar;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.syntaxerror.biblioteca.persistance.dao.EjemplarDAO;

public class EjemplarDAOImpl extends DAOImplBase implements EjemplarDAO {

    private EjemplaresDTO ejemplar;
    private SedeDAOImpl sedeDAO;
    private MaterialDAOImpl materialDAO;

    public EjemplarDAOImpl() {
        super("BIB_EJEMPLARES");
        this.retornarLlavePrimaria = true;
        this.ejemplar = null;
        this.sedeDAO = new SedeDAOImpl();
        this.materialDAO = new MaterialDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("ID_EJEMPLAR", true, true));
        this.listaColumnas.add(new Columna("FECHA_ADQUISICION", false, false));
        this.listaColumnas.add(new Columna("DISPONIBLE", false, false));
        this.listaColumnas.add(new Columna("TIPO_EJEMPLAR", false, false));
        this.listaColumnas.add(new Columna("FORMATO_DIGITAL", false, false));
        this.listaColumnas.add(new Columna("UBICACION", false, false));
        this.listaColumnas.add(new Columna("SEDE_IDSEDE", false, false));
        this.listaColumnas.add(new Columna("MATERIAL_IDMATERIAL", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setDate(1, new Date(this.ejemplar.getFechaAdquisicion().getTime()));
        this.statement.setInt(2, this.ejemplar.getDisponible() ? 1 : 0);
        this.statement.setString(3, this.ejemplar.getTipo().name());

        // Manejo especial de FormatoDigital según el tipo de ejemplar
        FormatoDigital formato = this.ejemplar.getFormatoDigital();
        if (this.ejemplar.getTipo() == TipoEjemplar.FISICO) {
            this.statement.setNull(4, java.sql.Types.VARCHAR);
        } else {
            if (formato == null) {
                throw new SQLException("Los ejemplares digitales deben tener un formato digital especificado");
            }
            this.statement.setString(4, formato.name());
        }

        this.statement.setString(5, this.ejemplar.getUbicacion());
        this.statement.setInt(6, this.ejemplar.getSede().getIdSede());
        this.statement.setInt(7, this.ejemplar.getMaterial().getIdMaterial());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setDate(1, new Date(this.ejemplar.getFechaAdquisicion().getTime()));
        this.statement.setInt(2, this.ejemplar.getDisponible() ? 1 : 0);
        this.statement.setString(3, this.ejemplar.getTipo().name());

        // Manejo especial de FormatoDigital según el tipo de ejemplar
        FormatoDigital formato = this.ejemplar.getFormatoDigital();
        if (this.ejemplar.getTipo() == TipoEjemplar.FISICO) {
            this.statement.setNull(4, java.sql.Types.VARCHAR);
        } else {
            if (formato == null) {
                throw new SQLException("Los ejemplares digitales deben tener un formato digital especificado");
            }
            this.statement.setString(4, formato.name());
        }

        this.statement.setString(5, this.ejemplar.getUbicacion());
        this.statement.setInt(6, this.ejemplar.getSede().getIdSede());
        this.statement.setInt(7, this.ejemplar.getMaterial().getIdMaterial());
        this.statement.setInt(8, this.ejemplar.getIdEjemplar());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.ejemplar.getIdEjemplar());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.ejemplar.getIdEjemplar());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.ejemplar = new EjemplaresDTO();
        this.ejemplar.setIdEjemplar(this.resultSet.getInt("ID_EJEMPLAR"));
        this.ejemplar.setFechaAdquisicion(this.resultSet.getDate("FECHA_ADQUISICION"));
        this.ejemplar.setDisponible(this.resultSet.getInt("DISPONIBLE") == 1);

        TipoEjemplar tipo = TipoEjemplar.valueOf(this.resultSet.getString("TIPO_EJEMPLAR"));
        this.ejemplar.setTipo(tipo);

        // Manejo especial de FormatoDigital según el tipo de ejemplar
        String formatoStr = this.resultSet.getString("FORMATO_DIGITAL");
        if (tipo == TipoEjemplar.FISICO) {
            this.ejemplar.setFormatoDigital(null);
            if (formatoStr != null) {
                throw new SQLException("Los ejemplares físicos no deben tener formato digital");
            }
        } else {
            if (formatoStr == null) {
                throw new SQLException("Los ejemplares digitales deben tener un formato digital");
            }
            this.ejemplar.setFormatoDigital(FormatoDigital.valueOf(formatoStr));
        }

        this.ejemplar.setUbicacion(this.resultSet.getString("UBICACION"));

        // Crear objetos DTO básicos para las relaciones
//        SedesDTO sede = new SedesDTO();
//        sede.setIdSede(this.resultSet.getInt("SEDE_IDSEDE"));
//        this.ejemplar.setSede(sede);
        
        int idSede = this.resultSet.getInt("SEDE_IDSEDE");
        if (!this.resultSet.wasNull()) {
            SedesDTO sede = sedeDAO.obtenerPorId(idSede);
            this.ejemplar.setSede(sede);
        } else {
            this.ejemplar.setSede(null);
        }

//        MaterialesDTO material = new MaterialesDTO();
//        material.setIdMaterial(this.resultSet.getInt("MATERIAL_IDMATERIAL"));
//        this.ejemplar.setMaterial(material);
        
        int idMaterial = this.resultSet.getInt("MATERIAL_IDMATERIAL");
        if (!this.resultSet.wasNull()) {
            MaterialesDTO material = materialDAO.obtenerPorId(idMaterial);
            this.ejemplar.setMaterial(material);
        } else {
            this.ejemplar.setMaterial(null);
        }
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.ejemplar = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.ejemplar);
    }

    @Override
    public Integer insertar(EjemplaresDTO ejemplar) {
        if (ejemplar == null) {
            throw new IllegalArgumentException("El ejemplar no puede ser null");
        }
        this.ejemplar = ejemplar;
        return super.insertar();
    }

    @Override
    public EjemplaresDTO obtenerPorId(Integer idEjemplar) {
        if (idEjemplar == null || idEjemplar <= 0) {
            throw new IllegalArgumentException("El ID del ejemplar debe ser válido");
        }
        this.ejemplar = new EjemplaresDTO();
        this.ejemplar.setIdEjemplar(idEjemplar);
        super.obtenerPorId();
        return this.ejemplar;
    }

    @Override
    public ArrayList<EjemplaresDTO> listarTodos() {
        return (ArrayList<EjemplaresDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(EjemplaresDTO ejemplar) {
        if (ejemplar == null || ejemplar.getIdEjemplar() == null || ejemplar.getIdEjemplar() <= 0) {
            throw new IllegalArgumentException("El ejemplar y su ID deben ser válidos");
        }
        this.ejemplar = ejemplar;
        return super.modificar();
    }

    @Override
    public Integer eliminar(EjemplaresDTO ejemplar) {
        if (ejemplar == null || ejemplar.getIdEjemplar() == null || ejemplar.getIdEjemplar() <= 0) {
            throw new IllegalArgumentException("El ejemplar y su ID deben ser válidos");
        }
        this.ejemplar = ejemplar;
        return super.eliminar();
    }

    @Override
    public ArrayList<EjemplaresDTO> listarEjemplaresPorFiltros(Integer idMaterial, Integer idSede, Boolean disponible, TipoEjemplar tipo) {
        StringBuilder sql = new StringBuilder("SELECT * FROM BIB_EJEMPLARES WHERE 1=1");

        List<Object> parametros = new ArrayList<>();
        if (idMaterial != null) {
            sql.append(" AND MATERIAL_IDMATERIAL = ?");
            parametros.add(idMaterial);
        }
        if (idSede != null) {
            sql.append(" AND SEDE_IDSEDE = ?");
            parametros.add(idSede);
        }
        if (disponible != null) {
            sql.append(" AND DISPONIBLE = ?");
            parametros.add(disponible ? 1 : 0);
        }
        if (tipo != null) {
            sql.append(" AND UPPER(TRIM(TIPO_EJEMPLAR)) = ?");
            parametros.add(tipo.name());

        }

        return (ArrayList<EjemplaresDTO>) super.listarTodos(
                sql.toString(),
                p -> {
                    try {
                        for (int i = 0; i < parametros.size(); i++) {
                            this.statement.setObject(i + 1, parametros.get(i));
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                },
                parametros
        );
    }

    @Override
    public int contarEjemplaresPorFiltros(Integer idMaterial, Integer idSede, Boolean disponible, TipoEjemplar tipo) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM BIB_EJEMPLARES WHERE 1=1");
        List<Object> parametros = new ArrayList<>();

        if (idMaterial != null) {
            sql.append(" AND MATERIAL_IDMATERIAL = ?");
            parametros.add(idMaterial);
        }
        if (idSede != null) {
            sql.append(" AND SEDE_IDSEDE = ?");
            parametros.add(idSede);
        }
        if (disponible != null) {
            sql.append(" AND DISPONIBLE = ?");
            parametros.add(disponible ? 1 : 0);
        }
        if (tipo != null) {
            sql.append(" AND UPPER(TRIM(TIPO_EJEMPLAR)) = ?");
            parametros.add(tipo.name());
        }

        return contarPorSQLyParametros(sql.toString(), stmt -> {
            for (int i = 0; i < parametros.size(); i++) {
                try {
                    stmt.setObject(i + 1, parametros.get(i));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public ArrayList<EjemplaresDTO> listarPorIdMaterial(Integer idMaterial) {
        String sql = """
        SELECT * FROM BIB_EJEMPLARES
        WHERE MATERIAL_IDMATERIAL = ?
    """;

        return (ArrayList<EjemplaresDTO>) this.listarTodos(
                sql,
                obj -> {
                    try {
                        this.statement.setInt(1, (Integer) obj);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                },
                idMaterial
        );
    }

    @Override
    public boolean existeEjemplarDigitalPorMaterial(Integer idMaterial) {
        String sql = """
        SELECT 1
        FROM BIB_EJEMPLARES
        WHERE MATERIAL_IDMATERIAL = ?
          AND UPPER(TIPO_EJEMPLAR) = 'DIGITAL'
        LIMIT 1
    """;

        try {
            this.abrirConexion();
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, idMaterial);
            this.ejecutarConsultaEnBD();
            return this.resultSet.next();  // Si hay al menos un resultado, retorna true
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public List<EjemplaresDTO> listarPorIdMaterialPaginado(Integer idMaterial, int limite, int offset) {
        String sql = """
        SELECT * FROM BIB_EJEMPLARES
        WHERE MATERIAL_IDMATERIAL = ?
        ORDER BY ID_EJEMPLAR
        LIMIT ? OFFSET ?
    """;
        return (List<EjemplaresDTO>) this.listarTodos(
                sql,
                params -> {
                    try {
                        this.statement.setInt(1, idMaterial);
                        this.statement.setInt(2, limite);
                        this.statement.setInt(3, offset);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                },
                null
        );
    }

}
