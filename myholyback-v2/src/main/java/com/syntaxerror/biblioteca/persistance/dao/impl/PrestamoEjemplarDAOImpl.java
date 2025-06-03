package com.syntaxerror.biblioteca.persistance.dao.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.syntaxerror.biblioteca.model.PrestamoEjemplarDTO;
import com.syntaxerror.biblioteca.model.enums.EstadoPrestamoEjemplar;
import com.syntaxerror.biblioteca.persistance.dao.PrestamoEjemplarDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;

public class PrestamoEjemplarDAOImpl extends DAOImplBase implements PrestamoEjemplarDAO {
    private PrestamoEjemplarDTO prestamoEjemplar;

    public PrestamoEjemplarDAOImpl() {
        super("BIB_PRESTAMO_EJMPLAR");
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
        this.prestamoEjemplar = new PrestamoEjemplarDTO();
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
    public Integer insertar(PrestamoEjemplarDTO prestamoEjemplar) {
        this.prestamoEjemplar = prestamoEjemplar;
        return super.insertar();
    }

    @Override
    public PrestamoEjemplarDTO obtenerPorId(Integer idPrestamo, Integer idEjemplar) {
        this.prestamoEjemplar = new PrestamoEjemplarDTO();
        this.prestamoEjemplar.setIdPrestamo(idPrestamo);
        this.prestamoEjemplar.setIdEjemplar(idEjemplar);
        super.obtenerPorId();
        return this.prestamoEjemplar;
    }

    @Override
    public ArrayList<PrestamoEjemplarDTO> listarTodos() {
        return (ArrayList<PrestamoEjemplarDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(PrestamoEjemplarDTO prestamoEjemplar) {
        this.prestamoEjemplar = prestamoEjemplar;
        return super.modificar();
    }

    @Override
    public Integer eliminar(PrestamoEjemplarDTO prestamoEjemplar) {
        this.prestamoEjemplar = prestamoEjemplar;
        return super.eliminar();
    }
}
