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
}
