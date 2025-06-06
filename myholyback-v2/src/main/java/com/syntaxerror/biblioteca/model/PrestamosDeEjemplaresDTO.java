
package com.syntaxerror.biblioteca.model;

import com.syntaxerror.biblioteca.model.enums.EstadoPrestamoEjemplar;
import java.util.Date;

/**
 *
 * @author MARLOW
 */
public class PrestamoEjemplarDTO {
    private Integer idPrestamo;
    private Integer idEjemplar;
    private EstadoPrestamoEjemplar estado; 
    private Date fechaRealDevolucion;

    // Constructores
    public PrestamoEjemplarDTO() {
        this.idPrestamo = null;
        this.idEjemplar = null;
        this.estado = null;
        this.fechaRealDevolucion = null;
    }

    public PrestamoEjemplarDTO(Integer idPrestamo, Integer idEjemplar, EstadoPrestamoEjemplar estado,
            Date fechaRealDevolucion) {
        this.idPrestamo = idPrestamo;
        this.idEjemplar = idEjemplar;
        this.estado = estado;
        this.fechaRealDevolucion = fechaRealDevolucion;
    }

    // Getters y Setters
    public Integer getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Integer idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Integer getIdEjemplar() {
        return idEjemplar;
    }

    public void setIdEjemplar(Integer idEjemplar) {
        this.idEjemplar = idEjemplar;
    }

    public EstadoPrestamoEjemplar getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamoEjemplar estado) {
        this.estado = estado;
    }

    public Date getFechaRealDevolucion() {
        return fechaRealDevolucion;
    }

    public void setFechaRealDevolucion(Date fechaRealDevolucion) {
        this.fechaRealDevolucion = fechaRealDevolucion;
    }
}
