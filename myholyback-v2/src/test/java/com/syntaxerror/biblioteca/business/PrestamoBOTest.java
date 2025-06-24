//package com.syntaxerror.biblioteca.business;
//
//import com.syntaxerror.biblioteca.model.EjemplaresDTO;
//import com.syntaxerror.biblioteca.model.PrestamosDTO;
//import com.syntaxerror.biblioteca.model.enums.EstadoPrestamoEjemplar;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// *
// * @author Fabian
// */
//public class PrestamoBOTest {
//
//    public PrestamoBOTest() {
//    }
//
//    @BeforeAll
//    public static void setUpClass() {
//    }
//
//    @AfterAll
//    public static void tearDownClass() {
//    }
//
//    @BeforeEach
//    public void setUp() {
//    }
//
//    @AfterEach
//    public void tearDown() {
//    }
//
//    /**
//     * Test of insertar method, of class PrestamoBO.
//     */
//    @Test
//    public void testSolicitarPrestamo() throws Exception {
//        System.out.println("solicitarPrestamo");
//
//         1️⃣ Arrange: datos de prueba
//        Integer idPersona = 7; // ID de persona válida en tu BD
//        List<Integer> idEjemplares = new ArrayList<>();
//        idEjemplares.add(63); // ID de ejemplar válido y disponible
//        idEjemplares.add(74); // Otro ejemplar válido y disponible
//
//        PrestamoBO instance = new PrestamoBO();
//
//         2️⃣ Act: ejecutar método
//        try {
//            instance.solicitarPrestamo(idPersona, idEjemplares);
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail("Error inesperado: " + e.getMessage());
//        }
//
//         3️⃣ Assert: verifica que los ejemplares ahora estén marcados como no disponibles
//        EjemplarBO ejemplarBO = new EjemplarBO();
//        for (Integer idEjemplar : idEjemplares) {
//            EjemplaresDTO ejemplar = ejemplarBO.obtenerPorId(idEjemplar);
//            assertFalse(ejemplar.getDisponible(),
//                    "El ejemplar ID " + idEjemplar + " debería estar marcado como NO disponible después del préstamo.");
//        }
//
//        System.out.println("Préstamo solicitado correctamente con ejemplares: " + idEjemplares);
//    }
//
//    @Test
//    void testFlujoCompletoReal() throws Exception {
//        Integer idPersona = 8;  // E00001
//        Integer idEjemplar1 = 68; // Ejemplar físico disponible
//        Integer idEjemplar2 = 70; // Otro ejemplar físico disponible
//      PrestamoBO prestamoBO = new PrestamoBO();
//        prestamoBO.solicitarPrestamo(idPersona, List.of(idEjemplar1, idEjemplar2));
//      
//        prestamoBO.recogerPrestamo(17);
//        System.out.println("Préstamo recogido correctamente.");
//
//         👉 4) DEVOLVER uno
//        prestamoBO.devolverPrestamo(17, List.of(idEjemplar1));
//        System.out.println("Ejemplar devuelto correctamente.");
//    }
//
//}
