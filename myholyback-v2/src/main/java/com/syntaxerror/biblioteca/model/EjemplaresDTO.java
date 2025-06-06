package com.syntaxerror.biblioteca.model;

import com.syntaxerror.biblioteca.model.enums.FormatoDigital;
import com.syntaxerror.biblioteca.model.enums.TipoEjemplar;
import java.util.Date;

/**
 *
 * @author josue
 */
public class EjemplaresDTO {

    private Integer idEjemplar;
    private Date fechaAdquisicion;
    private Boolean disponible;
    private TipoEjemplar tipo;
    private FormatoDigital formatoDigital;
    private String ubicacion;

    private SedesDTO sede;
    private MaterialesDTO material;

    public EjemplaresDTO() {
        this.idEjemplar = null;
        this.fechaAdquisicion = null;
        this.disponible = null;
        this.tipo = null;
        this.formatoDigital = null;
        this.ubicacion = null;
        this.sede = null;
        this.material = null;
    }

    public EjemplaresDTO(Integer idEjemplar, Date fechaAdquisicion, Boolean disponible,
            TipoEjemplar tipo, FormatoDigital formato, String ubicacion,
            SedesDTO sede, MaterialesDTO material) {
        this.idEjemplar = idEjemplar;
        this.fechaAdquisicion = fechaAdquisicion;
        this.disponible = disponible;
        this.ubicacion = ubicacion;
        this.tipo = tipo;
        this.formatoDigital = formato;
        this.sede = sede;
        this.material = material;
    }

    /**
     * @return the idEjem
     */
    public Integer getIdEjemplar() {
        return idEjemplar;
    }

    /**
     * @param idEjemplar the idEjem to set
     */
    public void setIdEjemplar(Integer idEjemplar) {
        this.idEjemplar = idEjemplar;
    }

    /**
     * @return the fechaAdquisicion
     */
    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    /**
     * @param fechaAdquisicion the fechaAdquisicion to set
     */
    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    /**
     * @return the disponible
     */
    public Boolean getDisponible() {
        return disponible;
    }

    /**
     * @param disponible the disponible to set
     */
    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public TipoEjemplar getTipo() {
        return tipo;
    }

    public void setTipo(TipoEjemplar tipo) {
        this.tipo = tipo;
    }

    public FormatoDigital getFormatoDigital() {
        return formatoDigital;
    }

    public void setFormatoDigital(FormatoDigital formatoDigital) {
        this.formatoDigital = formatoDigital;
    }

    /**
     * @return the ubicacion
     */
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public SedesDTO getSede() {
        return sede;
    }

    public void setSede(SedesDTO sede) {
        this.sede = sede;
    }

    public MaterialesDTO getMaterial() {
        return material;
    }

    public void setMaterial(MaterialesDTO material) {
        this.material = material;
    }

}
