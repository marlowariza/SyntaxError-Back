package com.syntaxerror.biblioteca.persistance.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.syntaxerror.biblioteca.model.MaterialDTO;
import com.syntaxerror.biblioteca.model.TemaDTO;
import com.syntaxerror.biblioteca.model.enums.Categoria;
import com.syntaxerror.biblioteca.model.enums.NivelDeIngles;
import com.syntaxerror.biblioteca.persistance.dao.MaterialTemaDAO;

public class MaterialTemaDAOImpl extends RelacionDAOImplBase<MaterialDTO, TemaDTO> implements MaterialTemaDAO {

    private static final Logger LOGGER = Logger.getLogger(MaterialTemaDAOImpl.class.getName());

    public MaterialTemaDAOImpl() {
        super("BIB_MATERIAL_TEMA", "MATERIAL_IDMATERIAL", "TEMA_IDTEMA", "BIB_MATERIAL", "BIB_TEMA");
    }

    @Override
    public Integer asociar(Integer materialId, Integer temaId) {
        return crearRelacion(materialId, temaId);
    }

    @Override
    public Integer desasociar(Integer materialId, Integer temaId) {
        return eliminarRelacion(materialId, temaId);
    }

    @Override
    public boolean existeRelacion(Integer materialId, Integer temaId) {
        return super.existeRelacion(materialId, temaId);
    }

    @Override
    public ArrayList<TemaDTO> listarTemasPorMaterial(Integer idMaterial) {
        ArrayList<TemaDTO> temas = new ArrayList<>();
        ArrayList<Integer> idsTemas = buscarRelacionadosPorId1(idMaterial);
        for (Integer idTema : idsTemas) {
            try {
                temas.add(obtenerEntidad2(idTema));
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Error al obtener tema con ID: " + idTema, ex);
            }
        }
        return temas;
    }

    @Override
    public ArrayList<MaterialDTO> listarMaterialesPorTema(Integer idTema) {
        ArrayList<MaterialDTO> materiales = new ArrayList<>();
        ArrayList<Integer> idsMateriales = buscarRelacionadosPorId2(idTema);
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
    protected TemaDTO obtenerEntidad2(Integer id) throws SQLException {
        TemaDTO tema = new TemaDTO();
        try {
            this.abrirConexion();
            String sql = "SELECT * FROM BIB_TEMA WHERE ID_TEMA = ?";
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, id);
            this.ejecutarConsultaEnBD();
            if (this.resultSet.next()) {
                tema.setIdTema(this.resultSet.getInt("ID_TEMA"));
                tema.setDescripcion(this.resultSet.getString("DESCRIPCION"));
                tema.setCategoria(Categoria.valueOf(this.resultSet.getString("CATEGORIA")));
            }
        } finally {
            this.cerrarConexion();
        }
        return tema;
    }
}
