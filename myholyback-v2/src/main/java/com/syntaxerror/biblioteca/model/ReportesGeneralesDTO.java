package com.syntaxerror.biblioteca.model;

public class ReportesGeneralesDTO {

    private Integer anio;
    private Integer mes;
    private PrestamosDTO prestamo;
    private PersonasDTO persona;

    // Constructores
    public ReportesGeneralesDTO() {
        this.anio = null;
        this.mes = null;
        this.prestamo = null;
        this.persona = null;
    }

    public ReportesGeneralesDTO(Integer anio, Integer mes, PrestamosDTO prestamo, PersonasDTO persona) {
        this.anio = anio;
        this.mes = mes;
        this.prestamo = prestamo;
        this.persona = persona;
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

    public PersonasDTO getPersona() {
        return persona;
    }

    public void setPersona(PersonasDTO persona) {
        this.persona = persona;
    }

}
