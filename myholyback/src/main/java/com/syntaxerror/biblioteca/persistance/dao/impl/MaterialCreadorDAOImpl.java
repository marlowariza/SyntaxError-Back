package com.syntaxerror.biblioteca.persistance.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.syntaxerror.biblioteca.model.CreadorDTO;
import com.syntaxerror.biblioteca.model.MaterialDTO;
import com.syntaxerror.biblioteca.model.enums.NivelDeIngles;
import com.syntaxerror.biblioteca.model.enums.TipoAutor;
import com.syntaxerror.biblioteca.persistance.dao.MaterialCreadorDAO;

public class MaterialCreadorDAOImpl extends RelacionDAOImplBase<MaterialDTO, CreadorDTO> implements MaterialCreadorDAO {
    private static final Logger LOGGER = Logger.getLogger(MaterialCreadorDAOImpl.class.getName());
    
    public MaterialCreadorDAOImpl() {
        super("BIB_MATERIAL_AUTOR", "MATERIAL_IDMATERIAL", "AUTOR_IDAUTOR", "BIB_MATERIAL", "BIB_AUTOR");
    }

    @Override
    public Integer asociar(Integer idMaterial, Integer idCreador) {
        return crearRelacion(idMaterial, idCreador);
    }

    @Override
    public Integer desasociar(Integer idMaterial, Integer idCreador) {
        return eliminarRelacion(idMaterial, idCreador);
    }

    @Override
    public boolean existeRelacion(Integer idMaterial, Integer idAutor) {
        return super.existeRelacion(idMaterial, idAutor);
    }

    @Override
    public ArrayList<CreadorDTO> listarCreadoresPorMaterial(Integer idMaterial) {
        ArrayList<CreadorDTO> creadores = new ArrayList<>();
        ArrayList<Integer> idsCreadores = buscarRelacionadosPorId1(idMaterial);
        for (Integer idCreador : idsCreadores) {
            try {
                creadores.add(obtenerEntidad2(idCreador));
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Error al obtener creador con ID: " + idCreador, ex);
            }
        }
        return creadores;
    }

    @Override
    public ArrayList<MaterialDTO> listarMaterialesPorCreador(Integer idCreador) {
        ArrayList<MaterialDTO> materiales = new ArrayList<>();
        ArrayList<Integer> idsMateriales = buscarRelacionadosPorId2(idCreador);
        for (Integer idMaterial : idsMateriales) {
            try {
                materiales.add(obtenerEntidad1(idMaterial));
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Error al obtener material con ID: " + idMaterial, ex);
            }
        }
        return materiales;
    }

    @Override
    protected MaterialDTO obtenerEntidad1(Integer id) throws SQLException {
        MaterialDTO material = new MaterialDTO();
        try {
            this.abrirConexion();
            String sql = "SELECT * FROM BIB_MATERIAL WHERE ID_MATERIAL = ?";
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, id);
            this.ejecutarConsultaEnBD();
            if (this.resultSet.next()) {
                material.setIdMaterial(this.resultSet.getInt("ID_MATERIAL"));
                material.setTitulo(this.resultSet.getString("TITULO"));
                material.setEdicion(this.resultSet.getString("EDICION"));
                material.setNivel(NivelDeIngles.valueOf(this.resultSet.getString("NIVEL")));
                material.setAnioPublicacion(this.resultSet.getInt("ANHIO_PUBLICACION"));
            }
        } finally {
            this.cerrarConexion();
        }
        return material;
    }

    @Override
    protected CreadorDTO obtenerEntidad2(Integer id) throws SQLException {
        CreadorDTO creador = new CreadorDTO();
        try {
            this.abrirConexion();
            String sql = "SELECT * FROM BIB_AUTOR WHERE ID_AUTOR = ?";
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, id);
            this.ejecutarConsultaEnBD();
            if (this.resultSet.next()) {
                creador.setIdAutor(this.resultSet.getInt("ID_AUTOR"));
                creador.setNombre(this.resultSet.getString("NOMBRE"));
                creador.setPaterno(this.resultSet.getString("PATERNO"));
                creador.setMaterno(this.resultSet.getString("MATERNO"));
                creador.setSeudonimo(this.resultSet.getString("SEUDONIMO"));
                creador.setTipo(TipoAutor.valueOf(this.resultSet.getString("TIPO_AUTOR")));
                creador.setNacionalidad(this.resultSet.getString("NACIONALIDAD"));
                creador.setActivo(this.resultSet.getInt("ACTIVO") == 1);
            }
        } finally {
            this.cerrarConexion();
        }
        return creador;
    }
}
