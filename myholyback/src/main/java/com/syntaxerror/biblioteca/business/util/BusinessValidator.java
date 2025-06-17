package com.syntaxerror.biblioteca.business.util;

public class BusinessValidator {

    private BusinessValidator() {
        // Constructor privado para evitar instanciación
    }

    public static void validarId(Integer id, String nombreEntidad) throws BusinessException {
        if (id == null || id <= 0) {
            throw new BusinessException("Debe proporcionar un ID válido para " + nombreEntidad + ".");
        }
    }

    public static void validarTexto(String valor, String campo) throws BusinessException {
        if (valor == null || valor.isBlank()) {
            throw new BusinessException("El campo " + campo + " no puede estar vacío.");
        }
    }

    public static void validarPaginacion(int limite, int pagina) throws BusinessException {
        if (limite <= 0) {
            throw new BusinessException("El límite debe ser mayor que cero.");
        }
        if (pagina <= 0) {
            throw new BusinessException("La página debe ser mayor que cero.");
        }
    }

    //aun no estan siendo usandas (por cambiar)
    public static void validarBoolean(Boolean valor, String campo) throws BusinessException {
        if (valor == null) {
            throw new BusinessException("El campo booleano " + campo + " no puede ser nulo.");
        }
    }

    public static void validarAnio(Integer anio, int anioMinimo, int anioMaximo, String campo) throws BusinessException {
        if (anio == null || anio < anioMinimo || anio > anioMaximo) {
            throw new BusinessException("El campo " + campo + " debe estar entre " + anioMinimo + " y " + anioMaximo + ".");
        }
    }
}
