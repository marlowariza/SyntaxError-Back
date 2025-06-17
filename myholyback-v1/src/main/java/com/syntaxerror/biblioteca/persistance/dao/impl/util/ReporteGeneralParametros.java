
package com.syntaxerror.biblioteca.persistance.dao.impl.util;

public class ReporteGeneralParametros {

    private Integer anio;
    private Integer mes;
    private Integer idPrestamo;
    private Integer idPersona;

    public ReporteGeneralParametros(Integer anio, Integer mes, Integer idPrestamo, Integer idPersona) {
        this.anio = anio;
        this.mes = mes;
        this.idPrestamo = idPrestamo;
        this.idPersona = idPersona;
    }

    public Integer getAnio() {
        return anio;
    }

    public Integer getMes() {
        return mes;
    }

    public Integer getIdPrestamo() {
        return idPrestamo;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public void setIdPrestamo(Integer idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }
}