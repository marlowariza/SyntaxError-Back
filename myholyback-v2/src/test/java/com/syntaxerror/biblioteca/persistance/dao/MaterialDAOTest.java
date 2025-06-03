/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.syntaxerror.biblioteca.persistance.dao;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.syntaxerror.biblioteca.model.CreadorDTO;
import com.syntaxerror.biblioteca.model.EditorialDTO;
import com.syntaxerror.biblioteca.model.MaterialDTO;
import com.syntaxerror.biblioteca.model.TemaDTO;
import com.syntaxerror.biblioteca.model.enums.Categoria;
import com.syntaxerror.biblioteca.model.enums.NivelDeIngles;
import com.syntaxerror.biblioteca.model.enums.TipoCreador;
import com.syntaxerror.biblioteca.persistance.dao.impl.CreadorDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.EditorialDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.MaterialDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.TemaDAOImpl;

/**
 *
 * @author MARLOW
 */
public class MaterialDAOTest {
    
    private MaterialDAO materialDAO;
    private EditorialDAO editorialDAO;
    private CreadorDAO creadorDAO;
    private TemaDAO temaDAO;
    private MaterialDTO material;
    private CreadorDTO creador;
    private TemaDTO tema;
    private EditorialDTO editorial;
    
    @BeforeEach
    public void setUp() {
        materialDAO = new MaterialDAOImpl();
        editorialDAO = new EditorialDAOImpl();
        creadorDAO = new CreadorDAOImpl();
        temaDAO = new TemaDAOImpl();
        
        // Crear y guardar editorial de prueba
        editorial = new EditorialDTO();
        editorial.setNombre("Editorial Test");
        Integer idEditorial = editorialDAO.insertar(editorial);
        assertNotNull(idEditorial, "La editorial debería insertarse correctamente");
        editorial.setIdEditorial(idEditorial);
        
        // Crear material de prueba
        material = new MaterialDTO();
        material.setTitulo("Material Test");
        material.setEdicion("1ra Edición");
        material.setNivel(NivelDeIngles.BASICO);
        material.setAnioPublicacion(2024);
        material.setEditorial(editorial);
        
        // Crear y guardar creador de prueba
        creador = new CreadorDTO();
        creador.setNombre("Autor");
        creador.setPaterno("Test");
        creador.setMaterno("Prueba");
        creador.setTipo(TipoCreador.AUTOR);
        creador.setNacionalidad("Peruana");
        creador.setActivo(true);
        Integer idCreador = creadorDAO.insertar(creador);
        assertNotNull(idCreador, "El creador debería insertarse correctamente");
        creador.setIdCreador(idCreador);
        
        // Crear y guardar tema de prueba
        tema = new TemaDTO();
        tema.setDescripcion("Tema Test");
        tema.setCategoria(Categoria.GENERO);
        Integer idTema = temaDAO.insertar(tema);
        assertNotNull(idTema, "El tema debería insertarse correctamente");
        tema.setIdTema(idTema);
    }
    
    @Test
    public void testAsociarCreador() {
        // Primero insertamos el material
        Integer idMaterial = materialDAO.insertar(material);
        assertNotNull(idMaterial, "El material debería insertarse correctamente");
        material.setIdMaterial(idMaterial);
        
        // Luego asociamos el creador
        Integer resultado = materialDAO.asociarCreador(creador);
        assertTrue(resultado > 0, "La asociación debería ser exitosa");
        
        // Verificamos que existe la relación
        boolean existe = materialDAO.existeRelacionConCreador(creador);
        assertTrue(existe, "Debería existir la relación");
    }
    
    @Test
    public void testAsociarTema() {
        // Primero insertamos el material
        Integer idMaterial = materialDAO.insertar(material);
        assertNotNull(idMaterial, "El material debería insertarse correctamente");
        material.setIdMaterial(idMaterial);
        
        // Luego asociamos el tema
        Integer resultado = materialDAO.asociarTema(tema);
        assertTrue(resultado > 0, "La asociación debería ser exitosa");
        
        // Verificamos que existe la relación
        boolean existe = materialDAO.existeRelacionConTema(tema);
        assertTrue(existe, "Debería existir la relación");
    }
    
    @Test
    public void testListarPorCreador() {
        // Primero insertamos el material
        Integer idMaterial = materialDAO.insertar(material);
        assertNotNull(idMaterial, "El material debería insertarse correctamente");
        material.setIdMaterial(idMaterial);
        
        // Asociamos el creador
        materialDAO.asociarCreador(creador);
        
        // Listamos los materiales por creador
        ArrayList<MaterialDTO> materiales = materialDAO.listarPorCreador(creador);
        assertNotNull(materiales, "La lista no debería ser null");
        assertFalse(materiales.isEmpty(), "Debería haber al menos un material");
    }
    
    @Test
    public void testListarPorTema() {
        // Primero insertamos el material
        Integer idMaterial = materialDAO.insertar(material);
        assertNotNull(idMaterial, "El material debería insertarse correctamente");
        material.setIdMaterial(idMaterial);
        
        // Asociamos el tema
        materialDAO.asociarTema(tema);
        
        // Listamos los materiales por tema
        ArrayList<MaterialDTO> materiales = materialDAO.listarPorTema(tema);
        assertNotNull(materiales, "La lista no debería ser null");
        assertFalse(materiales.isEmpty(), "Debería haber al menos un material");
    }
    
    @Test
    public void testDesasociarCreador() {
        // Primero insertamos el material
        Integer idMaterial = materialDAO.insertar(material);
        assertNotNull(idMaterial, "El material debería insertarse correctamente");
        material.setIdMaterial(idMaterial);
        
        // Asociamos el creador
        materialDAO.asociarCreador(creador);
        
        // Desasociamos
        Integer resultado = materialDAO.desasociarCreador(creador);
        assertTrue(resultado > 0, "La desasociación debería ser exitosa");
        
        // Verificamos que ya no existe la relación
        boolean existe = materialDAO.existeRelacionConCreador(creador);
        assertFalse(existe, "No debería existir la relación");
    }
    
    @Test
    public void testDesasociarTema() {
        // Primero insertamos el material
        Integer idMaterial = materialDAO.insertar(material);
        assertNotNull(idMaterial, "El material debería insertarse correctamente");
        material.setIdMaterial(idMaterial);
        
        // Asociamos el tema
        materialDAO.asociarTema(tema);
        
        // Desasociamos
        Integer resultado = materialDAO.desasociarTema(tema);
        assertTrue(resultado > 0, "La desasociación debería ser exitosa");
        
        // Verificamos que ya no existe la relación
        boolean existe = materialDAO.existeRelacionConTema(tema);
        assertFalse(existe, "No debería existir la relación");
    }
}
