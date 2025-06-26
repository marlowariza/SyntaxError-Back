/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.syntaxerror.biblioteca.db.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Fabian
 */
public class CifradoTest {

    public CifradoTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of cifrarMD5 method, of class Cifrado.
     */
    @Test
    public void testCifrarMD5() {
        String[] contrasenas = {"admin123", "admin456", "est123", "est234", "est345", "est456", "prof123", "prof234", "prof345", "prof456"};

        for (int i = 0; i < 10; i++) {
            System.out.println("cifrarMD5");
            String result = Cifrado.cifrarMD5(contrasenas[i]);
            System.out.println(result);
        }
    }

    /**
     * Test of descifrarMD5 method, of class Cifrado.
     */
    @Test
    public void testDescifrarMD5() {
        System.out.println("descifrarMD5");
        String textoEncriptado = "";
        String expResult = "";
        String result = Cifrado.descifrarMD5(textoEncriptado);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
