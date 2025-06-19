/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.syntaxerror.biblioteca.persistance.dao;

import com.syntaxerror.biblioteca.model.CreadoresDTO;
import com.syntaxerror.biblioteca.model.EditorialesDTO;
import com.syntaxerror.biblioteca.model.MaterialesDTO;
import com.syntaxerror.biblioteca.model.NivelesInglesDTO;
import com.syntaxerror.biblioteca.model.TemasDTO;
import com.syntaxerror.biblioteca.model.enums.Categoria;
import com.syntaxerror.biblioteca.model.enums.TipoCreador;
import com.syntaxerror.biblioteca.persistance.dao.impl.MaterialDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.base.CreadorMaterialDAOImpl;
import com.syntaxerror.biblioteca.persistance.dao.impl.base.MaterialTemaDAOImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author aml
 */
@TestMethodOrder(OrderAnnotation.class)
public class MaterialDAOTest {
    private MaterialDAO materialDAO;
    private CreadorMaterialDAOImpl creadorMaterialDAO;
    private MaterialTemaDAOImpl materialTemaDAO;
    private MaterialesDTO material;
    private EditorialesDTO editorial;
    private NivelesInglesDTO nivel;
    private List<CreadoresDTO> creadores;
    private List<TemasDTO> temas;
    private Integer idMaterialInsertado;

    @BeforeEach
    void setUp() {
        materialDAO = new MaterialDAOImpl();
        creadorMaterialDAO = new CreadorMaterialDAOImpl();
        materialTemaDAO = new MaterialTemaDAOImpl();
        
        // Configurar editorial
        editorial = new EditorialesDTO();
        editorial.setIdEditorial(1);
        editorial.setNombre("Editorial Test");
        
        // Configurar nivel
        nivel = new NivelesInglesDTO();
        nivel.setIdNivel(1);
        nivel.setDescripcion("A1");
        
        // Configurar creadores
        creadores = new ArrayList<>();
        CreadoresDTO creador1 = new CreadoresDTO();
        creador1.setIdCreador(1);
        creador1.setNombre("Autor");
        creador1.setTipo(TipoCreador.AUTOR);
        creadores.add(creador1);
        
        // Configurar temas
        temas = new ArrayList<>();
        TemasDTO tema1 = new TemasDTO();
        tema1.setIdTema(1);
        tema1.setDescripcion("Tema Test");
        tema1.setCategoria(Categoria.GENERO);
        temas.add(tema1);
        
        // Configurar material
        material = new MaterialesDTO();
        material.setTitulo("Material Test");
        material.setEdicion("1ra Edición");
        material.setAnioPublicacion(2024);
        material.setPortada("portada.jpg");
        material.setVigente(true);
        material.setEditorial(editorial);
        material.setNivel(nivel);
        material.setCreadores(creadores);
        material.setTemas(temas);
    }

    @AfterEach
    void tearDown() {
        // Limpiar datos de prueba
        if (idMaterialInsertado != null) {
            try {
                // Primero eliminar las relaciones
                creadorMaterialDAO.eliminarAsociacionesConCreadores(idMaterialInsertado);
                materialTemaDAO.eliminarAsociacionesConTemas(idMaterialInsertado);
                
                // Luego eliminar el material
                MaterialesDTO materialAEliminar = new MaterialesDTO();
                materialAEliminar.setIdMaterial(idMaterialInsertado);
                materialDAO.eliminar(materialAEliminar);
            } catch (Exception e) {
                System.err.println("Error al limpiar datos de prueba: " + e.getMessage());
            } finally {
                idMaterialInsertado = null;
            }
        }
    }

    @Test
    @Order(1)
    @DisplayName("Test insertar material con todas sus relaciones")
    void testInsertarMaterial() {
        // Act
        idMaterialInsertado = materialDAO.insertar(material);
        
        // Assert
        assertNotNull(idMaterialInsertado);
        assertTrue(idMaterialInsertado > 0);
        
        // Verificar que se guardó correctamente
        MaterialesDTO materialGuardado = materialDAO.obtenerPorId(idMaterialInsertado);
        assertNotNull(materialGuardado);
        assertEquals(material.getTitulo(), materialGuardado.getTitulo());
        assertEquals(material.getEdicion(), materialGuardado.getEdicion());
        assertEquals(material.getAnioPublicacion(), materialGuardado.getAnioPublicacion());
        assertEquals(material.getPortada(), materialGuardado.getPortada());
        assertEquals(material.getVigente(), materialGuardado.getVigente());
        assertNotNull(materialGuardado.getEditorial());
        assertEquals(material.getEditorial().getIdEditorial(), materialGuardado.getEditorial().getIdEditorial());
        assertNotNull(materialGuardado.getNivel());
        assertEquals(material.getNivel().getIdNivel(), materialGuardado.getNivel().getIdNivel());
        assertNotNull(materialGuardado.getCreadores());
        assertFalse(materialGuardado.getCreadores().isEmpty());
        assertNotNull(materialGuardado.getTemas());
        assertFalse(materialGuardado.getTemas().isEmpty());
    }

    @Test
    @Order(2)
    @DisplayName("Test modificar material")
    void testModificarMaterial() {
        // Arrange
        idMaterialInsertado = materialDAO.insertar(material);
        material.setIdMaterial(idMaterialInsertado);
        material.setTitulo("Material Modificado");
        
        // Act
        Integer resultado = materialDAO.modificar(material);
        
        // Assert
        assertNotNull(resultado);
        assertTrue(resultado > 0);
        
        MaterialesDTO materialModificado = materialDAO.obtenerPorId(idMaterialInsertado);
        assertEquals("Material Modificado", materialModificado.getTitulo());
    }

    @Test
    @Order(3)
    @DisplayName("Test eliminar material")
    void testEliminarMaterial() {
        // Arrange
        idMaterialInsertado = materialDAO.insertar(material);
        material.setIdMaterial(idMaterialInsertado);
        
        // Act
        // Primero eliminar las relaciones
        creadorMaterialDAO.eliminarAsociacionesConCreadores(idMaterialInsertado);
        materialTemaDAO.eliminarAsociacionesConTemas(idMaterialInsertado);
        
        // Luego eliminar el material
        Integer resultado = materialDAO.eliminar(material);
        
        // Assert
        assertNotNull(resultado);
        assertTrue(resultado > 0);
        
        MaterialesDTO materialEliminado = materialDAO.obtenerPorId(idMaterialInsertado);
        assertNull(materialEliminado);
        
        // Limpiar la referencia ya que el material fue eliminado
        idMaterialInsertado = null;
    }

    @Test
    @Order(4)
    @DisplayName("Test listar todos los materiales")
    void testListarTodos() {
        // Arrange
        idMaterialInsertado = materialDAO.insertar(material);
        
        // Act
        List<MaterialesDTO> materiales = materialDAO.listarTodos();
        
        // Assert
        assertNotNull(materiales);
        assertFalse(materiales.isEmpty());
    }

    @Test
    @Order(5)
    @DisplayName("Test listar materiales por título")
    void testListarPorTituloConteniendo() {
        // Arrange
        idMaterialInsertado = materialDAO.insertar(material);
        
        // Act
        List<MaterialesDTO> materiales = materialDAO.listarPorTituloConteniendo("Test");
        
        // Assert
        assertNotNull(materiales);
        assertFalse(materiales.isEmpty());
        assertTrue(materiales.stream().anyMatch(m -> m.getTitulo().contains("Test")));
    }

    @Test
    @Order(6)
    @DisplayName("Test listar materiales vigentes por título")
    void testListarVigentesPorTituloConteniendo() {
        // Arrange
        idMaterialInsertado = materialDAO.insertar(material);
        
        // Act
        List<MaterialesDTO> materiales = materialDAO.listarVigentesPorTituloConteniendo("Test");
        
        // Assert
        assertNotNull(materiales);
        assertFalse(materiales.isEmpty());
        assertTrue(materiales.stream().allMatch(MaterialesDTO::getVigente));
        assertTrue(materiales.stream().anyMatch(m -> m.getTitulo().contains("Test")));
    }

    @Test
    @Order(7)
    @DisplayName("Test listar materiales por sede")
    void testListarPorSede() {
        // Arrange
        Integer idSede = 1;
        idMaterialInsertado = materialDAO.insertar(material);
        
        // Act
        List<MaterialesDTO> materiales = materialDAO.listarPorSede(idSede);
        
        // Assert
        assertNotNull(materiales);
    }

    @Test
    @Order(8)
    @DisplayName("Test listar materiales vigentes por sede")
    void testListarVigentesPorSede() {
        // Arrange
        Integer idSede = 1;
        idMaterialInsertado = materialDAO.insertar(material);
        
        // Act
        List<MaterialesDTO> materiales = materialDAO.listarVigentesPorSede(idSede);
        
        // Assert
        assertNotNull(materiales);
        assertTrue(materiales.stream().allMatch(MaterialesDTO::getVigente));
    }
}
