package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.model.EditorialDTO;
import com.syntaxerror.biblioteca.persistance.dao.EditorialDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditorialDAOImpl extends DAOImplBase implements EditorialDAO {

    private EditorialDTO editorial;

    public EditorialDAOImpl() {
        super("BIB_EDITORIAL");
        this.retornarLlavePrimaria = true;
        this.editorial = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("ID_EDITORIAL", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("SITIOWEB", false, false));
        this.listaColumnas.add(new Columna("PAIS", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {

        //si es autoincremental, se salta el (1,ID)
        this.statement.setString(1, this.editorial.getNombre());
        this.statement.setString(2, this.editorial.getSitioWeb());
        this.statement.setString(3, this.editorial.getPais());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {

        this.statement.setString(1, this.editorial.getNombre());
        this.statement.setString(2, this.editorial.getSitioWeb());
        this.statement.setString(3, this.editorial.getPais());
        this.statement.setInt(4, this.editorial.getIdEditorial());
        //En modificar el ID va al ultimo
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.editorial.getIdEditorial());
        //Para eliminar solo va el id
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.editorial.getIdEditorial());
        //Para obtener por Id igual solo el id
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.editorial = new EditorialDTO();
        this.editorial.setIdEditorial(this.resultSet.getInt("ID_EDITORIAL"));
        this.editorial.setNombre(this.resultSet.getString("NOMBRE"));
        this.editorial.setSitioWeb(this.resultSet.getString("SITIOWEB"));
        this.editorial.setPais(this.resultSet.getString("PAIS"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.editorial = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.editorial);
    }

    @Override
    public Integer insertar(EditorialDTO editorial) {
        this.editorial = editorial;
        return super.insertar();
    }

    @Override
    public EditorialDTO obtenerPorId(Integer idEditorial) {
        this.editorial = new EditorialDTO();
        this.editorial.setIdEditorial(idEditorial);
        super.obtenerPorId();
        return this.editorial;
    }

    @Override
    public ArrayList<EditorialDTO> listarTodos() {
        return (ArrayList<EditorialDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(EditorialDTO editorial) {
        this.editorial = editorial;
        return super.modificar();
    }

    @Override
    public Integer eliminar(EditorialDTO editorial) {
        this.editorial = editorial;
        return super.eliminar();
    }

}
