package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.model.SedeDTO;
import com.syntaxerror.biblioteca.persistance.dao.SedeDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SedeDAOImpl extends DAOImplBase implements SedeDAO {

    private SedeDTO sede;

    public SedeDAOImpl() {
        super("BIB_BIBLIOTECA_SEDE");
        this.retornarLlavePrimaria = true;
        this.sede = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("ID_SEDE", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("DIRECCION", false, false));
        this.listaColumnas.add(new Columna("DISTRITO", false, false));
        this.listaColumnas.add(new Columna("TELEFONO_CONTACTO", false, false));
        this.listaColumnas.add(new Columna("CORREO_CONTACTO", false, false));
        this.listaColumnas.add(new Columna("ACTIVA", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {

        //si es autoincremental, se salta el (1,ID)
        this.statement.setString(1, this.sede.getNombre());
        this.statement.setString(2, this.sede.getDireccion());
        this.statement.setString(3, this.sede.getDistrito());
        this.statement.setString(4, this.sede.getTelefonoContacto());
        this.statement.setString(5, this.sede.getCorreoContacto());
        this.statement.setInt(6, this.sede.getActiva()? 1 : 0);

        //Si no es autoincremental 
//        this.statement.setInt(1, this.sede.getIdSede());
//        this.statement.setString(2, this.sede.getNombre());
//        this.statement.setString(3, this.sede.getDireccion());
//        this.statement.setString(4, this.sede.getDistrito());
//        this.statement.setString(5, this.sede.getTelefono_contacto());
//        this.statement.setString(6, this.sede.getCorreo_contacto());
//        this.statement.setInt(7, this.sede.getActiva() ? 1 : 0);

    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {

        this.statement.setString(1, this.sede.getNombre());
        this.statement.setString(2, this.sede.getDireccion());
        this.statement.setString(3, this.sede.getDistrito());
        this.statement.setString(4, this.sede.getTelefonoContacto());
        this.statement.setString(5, this.sede.getCorreoContacto());
        this.statement.setInt(6, this.sede.getActiva() ? 1 : 0);
        this.statement.setInt(7, this.sede.getIdSede());
        //En modificar el ID va al ultimo
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.sede.getIdSede());
        //Para eliminar solo va el id
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.sede.getIdSede());
        //Para obtener por Id igual solo el id
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.sede = new SedeDTO();
        this.sede.setIdSede(this.resultSet.getInt("ID_SEDE"));
        this.sede.setNombre(this.resultSet.getString("NOMBRE"));
        this.sede.setDireccion(this.resultSet.getString("DIRECCION"));
        this.sede.setDistrito(this.resultSet.getString("DISTRITO"));
        this.sede.setTelefonoContacto(this.resultSet.getString("TELEFONO_CONTACTO"));
        this.sede.setCorreoContacto(this.resultSet.getString("CORREO_CONTACTO"));
        this.sede.setActiva(this.resultSet.getInt("ACTIVA")==1);
        
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.sede = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.sede);
    }

    @Override
    public Integer insertar(SedeDTO sede) {
        this.sede = sede;
        return super.insertar();
    }

    @Override
    public SedeDTO obtenerPorId(Integer idSede) {
        this.sede = new SedeDTO();
        this.sede.setIdSede(idSede);
        super.obtenerPorId();
        return this.sede;
    }

    @Override
    public ArrayList<SedeDTO> listarTodos() {
        return (ArrayList<SedeDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(SedeDTO sede) {
        this.sede = sede;
        return super.modificar();
    }

    @Override
    public Integer eliminar(SedeDTO sede) {
        this.sede = sede;
        return super.eliminar();
    }
}
