/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.syntaxerror.biblioteca.business.util;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class SeekPaginator {

    @FunctionalInterface
    public interface SeekQuery<T> {

        List<T> buscar(String ultimoCampo, int ultimoId, int limite);
    }

    public static <T> PaginacionDTO<T> paginar(
            int cantidad,
            int pagina,
            SeekQuery<T> query,
            Function<T, String> campoOrden,
            Function<T, Integer> idExtractor) {

        validarPaginacion(cantidad, pagina);

        String ultimoCampo = null;
        int ultimoId = 0;

        for (int i = 1; i < pagina; i++) {
            List<T> temp = query.buscar(ultimoCampo, ultimoId, cantidad);
            if (temp.isEmpty()) {
                return new PaginacionDTO<>(Collections.emptyList(), null, false);
            }
            T ultimo = temp.get(temp.size() - 1);
            ultimoCampo = campoOrden.apply(ultimo);
            ultimoId = idExtractor.apply(ultimo);
        }

        List<T> lista = query.buscar(ultimoCampo, ultimoId, cantidad + 1);
        boolean hayMas = lista.size() > cantidad;
        List<T> datos = hayMas ? lista.subList(0, cantidad) : lista;

        SeekCursor siguienteCursor = null;
        if (hayMas && !datos.isEmpty()) {
            T ultimo = datos.get(datos.size() - 1);
            siguienteCursor = new SeekCursor(campoOrden.apply(ultimo), idExtractor.apply(ultimo));
        }

        return new PaginacionDTO<>(datos, siguienteCursor, hayMas);
    }

    private static void validarPaginacion(int cantidad, int pagina) {
        if (cantidad <= 0 || pagina <= 0) {
            throw new IllegalArgumentException("Cantidad y página deben ser mayores que cero.");
        }
    }
}
