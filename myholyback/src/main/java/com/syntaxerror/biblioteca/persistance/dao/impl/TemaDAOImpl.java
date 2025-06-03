package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.model.TemaDTO;
import com.syntaxerror.biblioteca.model.enums.Categoria;
import com.syntaxerror.biblioteca.persistance.dao.TemaDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TemaDAOImpl extends DAOImplBase implements TemaDAO {

    private TemaDTO tema;

    public TemaDAOImpl() {
        super("BIB_TEMA");
        this.retornarLlavePrimaria = true;
        this.tema = null;
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
        //si es autoincremental, se salta el (1,ID)
        this.statement.setString(1, this.tema.getDescripcion());
        this.statement.setString(2, this.tema.getCategoria().name());
        if (this.tema.getTemaPadre() != null) {
            this.statement.setInt(3, this.tema.getTemaPadre().getIdTema());
        } else {
            this.statement.setNull(3, java.sql.Types.INTEGER);  // Establece el valor null para el ID del tema padre
        }
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {

        this.statement.setString(1, this.tema.getDescripcion());
        this.statement.setString(2, this.tema.getCategoria().name());
        if (this.tema.getTemaPadre() != null) {
            this.statement.setInt(3, this.tema.getTemaPadre().getIdTema());
        } else {
            this.statement.setNull(3, java.sql.Types.INTEGER);  // Si el temaPadre es null, usamos setNull
        }
        this.statement.setInt(4, this.tema.getIdTema());
        //En modificar el ID va al ultimo
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.tema.getIdTema());
        //Para eliminar solo va el id
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.tema.getIdTema());
        //Para obtener por Id igual solo el id
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.tema = new TemaDTO();
        this.tema.setIdTema(this.resultSet.getInt("ID_TEMA"));
        this.tema.setDescripcion(this.resultSet.getString("DESCRIPCION"));
        this.tema.setCategoria(Categoria.valueOf(this.resultSet.getString("CATEGORIA")));
        // Crear objetos DTO básicos para las relaciones
        // Verificamos si el temaPadre existe en el resultado
        int idPadre = resultSet.getInt("ID_TEMA_PADRE");
        if (!resultSet.wasNull()) {  // Si no fue null, creamos el temaPadre
            TemaDTO padre = new TemaDTO();
            padre.setIdTema(idPadre);
            this.tema.setTemaPadre(padre);
        } else {
            this.tema.setTemaPadre(null);  // Si es null, aseguramos que no se setee un temaPadre
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
    public Integer insertar(TemaDTO tema) {
        this.tema = tema;
        return super.insertar();
    }

    @Override
    public TemaDTO obtenerPorId(Integer idTema) {
        this.tema = new TemaDTO();
        this.tema.setIdTema(idTema);
        super.obtenerPorId();
        // Verificar si el tema tiene un temaPadre, en caso de que sea null
        if (this.tema.getTemaPadre() == null) {
            System.out.println("Este tema no tiene tema padre.");
        }
        return this.tema;
    }

    @Override
    public ArrayList<TemaDTO> listarTodos() {
        return (ArrayList<TemaDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(TemaDTO tema) {
        this.tema = tema;
        return super.modificar();
    }

    @Override
    public Integer eliminar(TemaDTO tema) {
        this.tema = tema;
        return super.eliminar();
    }
}
