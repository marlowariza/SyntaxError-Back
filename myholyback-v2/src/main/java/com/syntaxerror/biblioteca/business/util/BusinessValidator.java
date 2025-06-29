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

    public static void validarIdNegativoPermitido(Integer id, String nombreCampo) throws BusinessException {
        if (id == null) {
            throw new BusinessException("El campo " + nombreCampo + " no puede ser nulo.");
        }
        // Se permite -1 como valor especial, pero no otros negativos
        if (id < -1) {
            throw new BusinessException("El campo " + nombreCampo + " tiene un valor inválido.");
        }
    }

}
