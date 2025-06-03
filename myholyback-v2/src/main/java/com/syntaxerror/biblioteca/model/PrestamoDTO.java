package com.syntaxerror.biblioteca.model;

import java.util.Date;

public class PrestamoDTO {

    private Integer idPrestamo;
    private Date fechaSolicitud;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    private PersonaDTO persona;

    // Constructores
    public PrestamoDTO() {
        this.idPrestamo = null;
        this.fechaSolicitud = null;
        this.fechaPrestamo = null;
        this.fechaDevolucion = null;
        this.persona = null;
    }

    public PrestamoDTO(Integer idPrestamo, Date fechaSolicitud, Date fechaPrestamo, Date fechaDevolucion, PersonaDTO persona) {
        this.idPrestamo = idPrestamo;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.fechaSolicitud = fechaSolicitud;
        this.persona = persona;
    }

    public PrestamoDTO(PrestamoDTO prestamo) {
        this.idPrestamo = prestamo.idPrestamo;
        this.fechaPrestamo = prestamo.fechaPrestamo;
        this.fechaDevolucion = prestamo.fechaDevolucion;
        this.fechaSolicitud = prestamo.fechaSolicitud;
        this.persona = prestamo.persona;
    }

    // Getters y Setters
    public Integer getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Integer idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public PersonaDTO getPersona() {
        return persona;
    }

    public void setPersona(PersonaDTO persona) {
        this.persona = persona;
    }

}
