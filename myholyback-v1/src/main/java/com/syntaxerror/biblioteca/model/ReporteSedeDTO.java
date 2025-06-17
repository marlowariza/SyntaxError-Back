package com.syntaxerror.biblioteca.model;

public class ReporteSedeDTO {

    private Integer anio;
    private Integer mes;
    private PrestamoDTO prestamo;
    private PersonaDTO persona;

    private SedeDTO sede;

    // Constructores
    public ReporteSedeDTO() {
        this.anio = null;
        this.mes = null;
        this.prestamo = null;
        this.persona = null;
        this.sede = null;
    }

    public ReporteSedeDTO(Integer anio, Integer mes, PrestamoDTO prestamo, PersonaDTO persona, SedeDTO sede) {
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

    public PrestamoDTO getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(PrestamoDTO prestamo) {
        this.prestamo = prestamo;
    }

    public SedeDTO getSede() {
        return sede;
    }

    public void setSede(SedeDTO sede) {
        this.sede = sede;
    }

    public PersonaDTO getPersona() {
        return persona;
    }

    public void setPersona(PersonaDTO persona) {
        this.persona = persona;
    }
}
