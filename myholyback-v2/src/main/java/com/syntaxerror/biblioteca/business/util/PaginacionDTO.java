
package com.syntaxerror.biblioteca.business.util;

import java.util.List;

public class PaginacionDTO<T> {

    private List<T> datos;
    private SeekCursor siguienteCursor;
    private boolean hayMas;

    public PaginacionDTO(List<T> datos, SeekCursor siguienteCursor, boolean hayMas) {
        this.datos = datos;
        this.siguienteCursor = siguienteCursor;
        this.hayMas = hayMas;
    }

    public List<T> getDatos() {
        return datos;
    }

    public SeekCursor getSiguienteCursor() {
        return siguienteCursor;
    }

    public boolean isHayMas() {
        return hayMas;
    }
}
