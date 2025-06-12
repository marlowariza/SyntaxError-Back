package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.persistance.dao.impl.base.DAOImplBase;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.syntaxerror.biblioteca.model.PrestamosDeEjemplaresDTO;
import com.syntaxerror.biblioteca.model.enums.EstadoPrestamoEjemplar;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;
import com.syntaxerror.biblioteca.persistance.dao.PrestamoEjemplarDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PrestamoEjemplarDAOImpl extends DAOImplBase implements PrestamoEjemplarDAO {

    private PrestamosDeEjemplaresDTO prestamoEjemplar;

    public PrestamoEjemplarDAOImpl() {
        super("BIB_PRESTAMOS_DE_EJEMPLARES");
        this.retornarLlavePrimaria = true;
        this.prestamoEjemplar = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("PRESTAMO_IDPRESTAMO", true, false));
        this.listaColumnas.add(new Columna("EJEMPLAR_IDEJEMPLAR", true, false));
        this.listaColumnas.add(new Columna("ESTADO", false, false));
        this.listaColumnas.add(new Columna("FECHA_REAL_DEVOLUCION", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {

        this.statement.setInt(1, this.prestamoEjemplar.getIdPrestamo());
        this.statement.setInt(2, this.prestamoEjemplar.getIdEjemplar());
        this.statement.setString(3, this.prestamoEjemplar.getEstado().name());
        this.statement.setDate(4, new Date(this.prestamoEjemplar.getFechaRealDevolucion().getTime()));
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.prestamoEjemplar.getEstado().name());
        this.statement.setDate(2, new Date(this.prestamoEjemplar.getFechaRealDevolucion().getTime()));
        this.statement.setInt(3, this.prestamoEjemplar.getIdPrestamo());
        this.statement.setInt(4, this.prestamoEjemplar.getIdEjemplar());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.prestamoEjemplar.getIdPrestamo());
        this.statement.setInt(2, this.prestamoEjemplar.getIdEjemplar());
        //Para eliminar solo va el id
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.prestamoEjemplar.getIdPrestamo());
        this.statement.setInt(2, this.prestamoEjemplar.getIdEjemplar());
        //Para obtener por Id igual solo el id
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.prestamoEjemplar = new PrestamosDeEjemplaresDTO();
        this.prestamoEjemplar.setIdPrestamo(this.resultSet.getInt("PRESTAMO_IDPRESTAMO"));
        this.prestamoEjemplar.setIdEjemplar(this.resultSet.getInt("EJEMPLAR_IDEJEMPLAR"));
        this.prestamoEjemplar.setEstado(EstadoPrestamoEjemplar.valueOf(this.resultSet.getString("ESTADO")));
        this.prestamoEjemplar.setFechaRealDevolucion(this.resultSet.getDate("FECHA_REAL_DEVOLUCION"));

    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.prestamoEjemplar = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.prestamoEjemplar);
    }

    @Override
    public Integer insertar(PrestamosDeEjemplaresDTO prestamoEjemplar) {
        this.prestamoEjemplar = prestamoEjemplar;
        return super.insertar();
    }

    @Override
    public PrestamosDeEjemplaresDTO obtenerPorId(Integer idPrestamo, Integer idEjemplar) {
        this.prestamoEjemplar = new PrestamosDeEjemplaresDTO();
        this.prestamoEjemplar.setIdPrestamo(idPrestamo);
        this.prestamoEjemplar.setIdEjemplar(idEjemplar);
        super.obtenerPorId();
        return this.prestamoEjemplar;
    }

    @Override
    public ArrayList<PrestamosDeEjemplaresDTO> listarTodos() {
        return (ArrayList<PrestamosDeEjemplaresDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(PrestamosDeEjemplaresDTO prestamoEjemplar) {
        this.prestamoEjemplar = prestamoEjemplar;
        return super.modificar();
    }

    @Override
    public Integer eliminar(PrestamosDeEjemplaresDTO prestamoEjemplar) {
        this.prestamoEjemplar = prestamoEjemplar;
        return super.eliminar();
    }

    @Override
    public ArrayList<Integer> obtenerIdEjemplaresPrestadosPorIdPersona(int idPersona) {
        String sql = """
        SELECT PDE.EJEMPLAR_IDEJEMPLAR
        FROM BIB_PRESTAMOS_DE_EJEMPLARES PDE
        JOIN BIB_PRESTAMOS P ON P.ID_PRESTAMO = PDE.PRESTAMO_IDPRESTAMO
        WHERE P.PERSONA_IDPERSONA = ? AND PDE.ESTADO IN ('PRESTADO', 'ATRASADO')
    """;

        List<Integer> resultado = new ArrayList<>();

        super.listarTodos(sql, (param) -> {
            try {
                this.statement.setInt(1, (Integer) param);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, idPersona).forEach(obj -> {
            PrestamosDeEjemplaresDTO dto = (PrestamosDeEjemplaresDTO) obj;
            resultado.add(dto.getIdEjemplar());
        });

        return new ArrayList<>(resultado);

    }

    @Override
    public ArrayList<Integer> obtenerIdEjemplaresSolicitadosPorPersona(int idPersona) {
        ArrayList<Integer> lista = new ArrayList<>();
        String sql = """
        SELECT PDE.EJEMPLAR_IDEJEMPLAR
        FROM BIB_PRESTAMOS_DE_EJEMPLARES PDE
        JOIN BIB_PRESTAMOS P ON P.ID_PRESTAMO = PDE.PRESTAMO_IDPRESTAMO
        WHERE P.PERSONA_IDPERSONA = ? AND PDE.ESTADO = 'SOLICITADO'
    """;

        try {
            this.abrirConexion();
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, idPersona);
            this.resultSet = this.statement.executeQuery();

            while (this.resultSet.next()) {
                lista.add(this.resultSet.getInt("EJEMPLAR_IDEJEMPLAR"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // O usa tu sistema de logging
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return lista;
    }

    @Override
    public ArrayList<PrestamosDeEjemplaresDTO> listarPorIdPrestamo(int idPrestamo) {
        String sql = "SELECT * FROM BIB_PRESTAMOS_DE_EJEMPLARES WHERE PRESTAMO_IDPRESTAMO = ?";

        return (ArrayList<PrestamosDeEjemplaresDTO>) this.listarTodos(
                sql,
                obj -> {
                    try {
                        this.statement.setInt(1, (Integer) obj);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                },
                idPrestamo
        );
    }

    @Override
    public ArrayList<PrestamosDeEjemplaresDTO> listarPrestamosDevueltos() {
        return new ArrayList<>(listarPorEstado(EstadoPrestamoEjemplar.DEVUELTO));
    }

    @Override
    public ArrayList<PrestamosDeEjemplaresDTO> listarPrestamosAtrasados() {
        return new ArrayList<>(listarPorEstado(EstadoPrestamoEjemplar.ATRASADO));
    }

    @Override
    public ArrayList<PrestamosDeEjemplaresDTO> listarPrestamosSolicitados() {
        return new ArrayList<>(listarPorEstado(EstadoPrestamoEjemplar.SOLICITADO));
    }

    @Override
    public ArrayList<PrestamosDeEjemplaresDTO> listarPrestamosNoCulminados() {
        return new ArrayList<>(listarPorEstado(EstadoPrestamoEjemplar.PRESTADO));
    }

// Versión para un único estado
    private ArrayList<PrestamosDeEjemplaresDTO> listarPorEstado(EstadoPrestamoEjemplar estado) {
        ArrayList<PrestamosDeEjemplaresDTO> resultados = new ArrayList<>();
        String sql = "SELECT * FROM BIB_PRESTAMOS_DE_EJEMPLARES WHERE ESTADO = ?";

        try {
            this.abrirConexion();
            this.colocarSQLenStatement(sql);
            this.statement.setString(1, estado.name());
            this.resultSet = this.statement.executeQuery();

            while (this.resultSet.next()) {
                PrestamosDeEjemplaresDTO dto = new PrestamosDeEjemplaresDTO();
                dto.setIdPrestamo(this.resultSet.getInt("PRESTAMO_IDPRESTAMO"));
                dto.setIdEjemplar(this.resultSet.getInt("EJEMPLAR_IDEJEMPLAR"));
                dto.setEstado(EstadoPrestamoEjemplar.valueOf(this.resultSet.getString("ESTADO")));
                dto.setFechaRealDevolucion(this.resultSet.getDate("FECHA_REAL_DEVOLUCION"));

                resultados.add(dto);
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar por estado: " + ex.getMessage());
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }

        return resultados;
    }

    @Override
    public int contarPrestamosAtrasadosPorIdPersona(int idPersona) {
        int contador = 0;

        String sql = """
        SELECT COUNT(DISTINCT PDE.PRESTAMO_IDPRESTAMO) AS TOTAL
        FROM BIB_PRESTAMOS_DE_EJEMPLARES PDE
        JOIN BIB_PRESTAMOS P ON P.ID_PRESTAMO = PDE.PRESTAMO_IDPRESTAMO
        WHERE P.PERSONA_IDPERSONA = ? AND PDE.ESTADO = 'ATRASADO'
    """;

        try {
            this.abrirConexion();
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, idPersona);
            this.ejecutarConsultaEnBD();

            if (this.resultSet.next()) {
                contador = this.resultSet.getInt("TOTAL");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return contador;
    }

    @Override
    public int contarPrestamosPorIdMaterial(int idMaterial) {
        int contador = 0;

        String sql = """
        SELECT COUNT(*) AS TOTAL
        FROM BIB_PRESTAMOS_DE_EJEMPLARES PDE
        JOIN BIB_EJEMPLARES E ON E.IDEJEMPLAR = PDE.EJEMPLAR_IDEJEMPLAR
        WHERE E.MATERIAL_IDMATERIAL = ?
    """;

        try {
            this.abrirConexion();
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, idMaterial);
            this.ejecutarConsultaEnBD();

            if (this.resultSet.next()) {
                contador = this.resultSet.getInt("TOTAL");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return contador;
    }

}
