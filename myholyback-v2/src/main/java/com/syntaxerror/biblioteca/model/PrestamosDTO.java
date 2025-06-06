package com.syntaxerror.biblioteca.model;

import java.util.Date;

public class PrestamosDTO {

    private Integer idPrestamo;
    private Date fechaSolicitud;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    private PersonasDTO persona;

    // Constructores
    public PrestamosDTO() {
        this.idPrestamo = null;
        this.fechaSolicitud = null;
        this.fechaPrestamo = null;
        this.fechaDevolucion = null;
        this.persona = null;
    }

    public PrestamosDTO(Integer idPrestamo, Date fechaSolicitud, Date fechaPrestamo, Date fechaDevolucion, PersonasDTO persona) {
        this.idPrestamo = idPrestamo;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.fechaSolicitud = fechaSolicitud;
        this.persona = persona;
    }

    public PrestamosDTO(PrestamosDTO prestamo) {
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

    public PersonasDTO getPersona() {
        return persona;
    }

    public void setPersona(PersonasDTO persona) {
        this.persona = persona;
    }

}
