package com.syntaxerror.biblioteca.persistance.dao.impl;

import com.syntaxerror.biblioteca.model.EjemplarDTO;
import com.syntaxerror.biblioteca.model.MaterialDTO;
import com.syntaxerror.biblioteca.model.SedeDTO;
import com.syntaxerror.biblioteca.model.enums.FormatoDigital;
import com.syntaxerror.biblioteca.model.enums.TipoEjemplar;
import com.syntaxerror.biblioteca.persistance.dao.EjemplarDAO;
import com.syntaxerror.biblioteca.persistance.dao.impl.util.Columna;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EjemplarDAOImpl extends DAOImplBase implements EjemplarDAO {

    private EjemplarDTO ejemplar;

    public EjemplarDAOImpl() {
        super("BIB_EJEMPLAR");
        this.retornarLlavePrimaria = true;
        this.ejemplar = null;
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
        this.ejemplar = new EjemplarDTO();
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
        SedeDTO sede = new SedeDTO();
        sede.setIdSede(this.resultSet.getInt("SEDE_IDSEDE"));
        this.ejemplar.setSede(sede);

        MaterialDTO material = new MaterialDTO();
        material.setIdMaterial(this.resultSet.getInt("MATERIAL_IDMATERIAL"));
        this.ejemplar.setMaterial(material);
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
    public Integer insertar(EjemplarDTO ejemplar) {
        if (ejemplar == null) {
            throw new IllegalArgumentException("El ejemplar no puede ser null");
        }
        this.ejemplar = ejemplar;
        return super.insertar();
    }

    @Override
    public EjemplarDTO obtenerPorId(Integer idEjemplar) {
        if (idEjemplar == null || idEjemplar <= 0) {
            throw new IllegalArgumentException("El ID del ejemplar debe ser válido");
        }
        this.ejemplar = new EjemplarDTO();
        this.ejemplar.setIdEjemplar(idEjemplar);
        super.obtenerPorId();
        return this.ejemplar;
    }

    @Override
    public ArrayList<EjemplarDTO> listarTodos() {
        return (ArrayList<EjemplarDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(EjemplarDTO ejemplar) {
        if (ejemplar == null || ejemplar.getIdEjemplar() == null || ejemplar.getIdEjemplar() <= 0) {
            throw new IllegalArgumentException("El ejemplar y su ID deben ser válidos");
        }
        this.ejemplar = ejemplar;
        return super.modificar();
    }

    @Override
    public Integer eliminar(EjemplarDTO ejemplar) {
        if (ejemplar == null || ejemplar.getIdEjemplar() == null || ejemplar.getIdEjemplar() <= 0) {
            throw new IllegalArgumentException("El ejemplar y su ID deben ser válidos");
        }
        this.ejemplar = ejemplar;
        return super.eliminar();
    }
}
