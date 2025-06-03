package com.syntaxerror.biblioteca.model;

public class SedeDTO {

    private Integer idSede;
    private String nombre;
    private String direccion;
    private String distrito;
    private String telefonoContacto;
    private String correoContacto;

    private Boolean activa;

    public SedeDTO() {
        this.idSede = null;
        this.nombre = null;
        this.direccion = null;
        this.distrito = null;
        this.telefonoContacto = null;
        this.correoContacto = null;
        this.activa = null;
    }

    public SedeDTO(Integer idSede, String nombre, String direccion, String distrito,
            String telefonoContacto, String correoContacto, Boolean activa) {
        this.idSede = idSede;
        this.nombre = nombre;
        this.direccion = direccion;
        this.distrito = distrito;
        this.telefonoContacto = telefonoContacto;
        this.correoContacto = correoContacto;
        this.activa = activa;
    }

    public SedeDTO(SedeDTO otraSede) {
        this.idSede = otraSede.idSede;
        this.nombre = otraSede.nombre;
        this.direccion = otraSede.direccion;
        this.distrito = otraSede.distrito;
        this.telefonoContacto = otraSede.telefonoContacto;
        this.correoContacto = otraSede.correoContacto;
        this.activa = otraSede.activa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    public Integer getIdSede() {
        return idSede;
    }

    public void setIdSede(Integer idSede) {
        this.idSede = idSede;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }
}
