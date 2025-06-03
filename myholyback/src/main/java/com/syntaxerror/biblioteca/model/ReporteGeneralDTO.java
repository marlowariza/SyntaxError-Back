package com.syntaxerror.biblioteca.model;

public class ReporteGeneralDTO {

    private Integer anio;
    private Integer mes;
    private PrestamoDTO prestamo;
    private PersonaDTO persona;

    // Constructores
    public ReporteGeneralDTO() {
        this.anio = null;
        this.mes = null;
        this.prestamo = null;
        this.persona = null;
    }

    public ReporteGeneralDTO(Integer anio, Integer mes, PrestamoDTO prestamo, PersonaDTO persona) {
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

    public PrestamoDTO getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(PrestamoDTO prestamo) {
        this.prestamo = prestamo;
    }

    public PersonaDTO getPersona() {
        return persona;
    }

    public void setPersona(PersonaDTO persona) {
        this.persona = persona;
    }

}
