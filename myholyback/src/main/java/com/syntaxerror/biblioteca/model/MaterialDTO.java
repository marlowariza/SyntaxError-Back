package com.syntaxerror.biblioteca.model;

public class MaterialDTO {

    private Integer idMaterial;
    private String titulo;
    private String edicion;
    private Integer anioPublicacion;
    private String portada;
    private Boolean vigente;
    private NivelInglesDTO nivel;
    private EditorialDTO editorial;

    // Constructores
    public MaterialDTO() {
        this.idMaterial = null;
        this.titulo = null;
        this.edicion = null;
        this.anioPublicacion = null;
        this.portada = null;
        this.vigente = null;
        this.nivel = null;
        this.editorial = null;

    }

    public MaterialDTO(Integer idMaterial, String titulo, String edicion, Integer anioPublicacion, String portada, Boolean vigente, NivelInglesDTO nivel, EditorialDTO editorial) {
        this.idMaterial = idMaterial;
        this.titulo = titulo;
        this.edicion = edicion;
        this.anioPublicacion = anioPublicacion;
        this.portada = portada;
        this.vigente = vigente;
        this.nivel = nivel;
        this.editorial = editorial;
    }

    public MaterialDTO(MaterialDTO material) {
        this.idMaterial = material.idMaterial;
        this.titulo = material.titulo;
        this.edicion = material.edicion;
        this.anioPublicacion = material.anioPublicacion;
        this.portada = material.portada;
        this.vigente = material.vigente;
        this.nivel = material.nivel;
        this.editorial = material.editorial;

    }

    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public Boolean getVigente() {
        return vigente;
    }

    public void setVigente(Boolean vigente) {
        this.vigente = vigente;
    }

    public NivelInglesDTO getNivel() {
        return nivel;
    }

    public void setNivel(NivelInglesDTO nivel) {
        this.nivel = nivel;
    }

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public EditorialDTO getEditorial() {
        return editorial;
    }

    public void setEditorial(EditorialDTO editorial) {
        this.editorial = editorial;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }
}
