package com.syntaxerror.biblioteca.model;

public class ReportesPorSedeDTO {

    private Integer anio;
    private Integer mes;
    private PrestamosDTO prestamo;
    private PersonasDTO persona;

    private SedesDTO sede;

    // Constructores
    public ReportesPorSedeDTO() {
        this.anio = null;
        this.mes = null;
        this.prestamo = null;
        this.persona = null;
        this.sede = null;
    }

    public ReportesPorSedeDTO(Integer anio, Integer mes, PrestamosDTO prestamo, PersonasDTO persona, SedesDTO sede) {
        this.anio = anio;
        this.mes = mes;
        this.prestamo = prestamo;
        this.persona = persona;
        this.sede = sede;
    }

    // Getters y Setters
    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public PrestamosDTO getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(PrestamosDTO prestamo) {
        this.prestamo = prestamo;
    }

    public SedesDTO getSede() {
        return sede;
    }

    public void setSede(SedesDTO sede) {
        this.sede = sede;
    }

    public PersonasDTO getPersona() {
        return persona;
    }

    public void setPersona(PersonasDTO persona) {
        this.persona = persona;
    }
}
