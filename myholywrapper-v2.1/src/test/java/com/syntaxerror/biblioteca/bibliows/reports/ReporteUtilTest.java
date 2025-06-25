///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
// */
//package com.syntaxerror.biblioteca.bibliows.reports;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Calendar;
//import java.util.HashMap;
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
//public class ReporteUtilTest {
//    
//    public ReporteUtilTest() {
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
//     * Test of invocarReporte method, of class ReporteUtil.
//     */
//        @Test
//    void generarReportePorSede() {
//        // Parámetros de prueba
////        Integer sedeId = 0; // 0 = todas las sedes
////        Integer anho = Calendar.getInstance().get(Calendar.YEAR);
////        Integer mes = Calendar.getInstance().get(Calendar.MONTH) + 1; // Mes actual (1-12)
////        
////            System.err.println(anho);
////            System.out.println(mes);
//        
//        // Llamada al método
//        byte[] reporte = ReporteUtil.reportePrestamosPorSede(1, 2025,6);
//
//        // Validación del resultado
//        assertNotNull(reporte, "El reporte no debe ser nulo.");
//        assertTrue(reporte.length > 0, "El reporte generado debe tener contenido.");
//
//        // Opcional: guardar el PDF para verificación manual
//        try (FileOutputStream fos = new FileOutputStream("ReporteSedeTest.pdf")) {
//            fos.write(reporte);
//            System.out.println("✅ Reporte generado correctamente como 'ReporteSedeTest.pdf'");
//        } catch (IOException e) {
//            fail("Error al guardar el archivo PDF: " + e.getMessage());
//        }
//    }
//    
//}
