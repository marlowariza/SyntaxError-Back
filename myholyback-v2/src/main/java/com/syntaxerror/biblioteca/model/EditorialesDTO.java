package com.syntaxerror.biblioteca.model;

public class EditorialesDTO{
    private Integer idEditorial;
    private String nombre;
    private String sitioWeb;
    private String pais;

    public EditorialesDTO() {
        this.idEditorial = null;
        this.nombre = null;
        this.sitioWeb = null;
        this.pais = null;
    }
    public EditorialesDTO(Integer idEditorial, String nombre, String sitioWeb, String pais) {
        this.idEditorial = idEditorial;
        this.nombre = nombre;
        this.sitioWeb = sitioWeb;
        this.pais = pais;
    }
    public EditorialesDTO(EditorialesDTO editorial) {
        this.idEditorial = editorial.idEditorial;
        this.nombre = editorial.nombre;
        this.sitioWeb = editorial.sitioWeb;
        this.pais = editorial.pais;
    }
    public Integer getIdEditorial() {
        return idEditorial;
    }
    public void setIdEditorial(Integer idEditorial) {
        this.idEditorial = idEditorial;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getSitioWeb() {
        return sitioWeb;
    }
    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }
    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }

}
