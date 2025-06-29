/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.syntaxerror.biblioteca.business.util;

public class SeekCursor {

    private String ultimoTitulo;
    private int ultimoId;

    public SeekCursor(String ultimoTitulo, int ultimoId) {
        this.ultimoTitulo = ultimoTitulo;
        this.ultimoId = ultimoId;
    }

    public String getUltimoTitulo() {
        return ultimoTitulo;
    }

    public int getUltimoId() {
        return ultimoId;
    }

    public boolean esPrimeraPagina() {
        return ultimoTitulo == null || ultimoTitulo.isBlank();
    }
}
