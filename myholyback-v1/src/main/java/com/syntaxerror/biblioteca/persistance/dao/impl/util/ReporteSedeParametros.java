package com.syntaxerror.biblioteca.persistance.dao.impl.util;

public class ReporteSedeParametros {

    private Integer anio;
    private Integer mes;
    private Integer idSede;
    private Integer idPrestamo;
    private Integer idPersona;

    public ReporteSedeParametros(Integer anio, Integer mes, Integer idSede, Integer idPrestamo, Integer idPersona) {
        this.anio = anio;
        this.mes = mes;
        this.idSede = idSede;
        this.idPrestamo = idPrestamo;
        this.idPersona = idPersona;
    }

    public Integer getAnio() {
        return anio;
    }

    public Integer getMes() {
        return mes;
    }

    public Integer getIdSede() {
        return idSede;
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

    public void setIdSede(Integer idSede) {
        this.idSede = idSede;
    }

    public void setIdPrestamo(Integer idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

}
