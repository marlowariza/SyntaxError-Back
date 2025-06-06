package com.syntaxerror.biblioteca.model;

import java.util.ArrayList;
import java.util.List;

public class MaterialesDTO {
    private Integer idMaterial;
    private String titulo;
    private String edicion;
    private Integer anioPublicacion;
    private String portada;
    private Boolean vigente;
    private NivelesInglesDTO nivel;
    //
    private EditorialesDTO editorial;
    private List<CreadoresDTO> creadores;
    private List<TemasDTO> temas;

    // Constructores
    public MaterialesDTO() {
        this.idMaterial = null;
        this.titulo = null;
        this.edicion = null;
        this.nivel = null;
        this.anioPublicacion = null;
        this.portada = null;
        this.vigente = null;
        this.editorial = null;
        this.creadores = new ArrayList<>();
        this.temas = new ArrayList<>();
    }

    public MaterialesDTO(Integer idMaterial, String titulo, String edicion, Integer anioPublicacion, String portada,
    Boolean vigente, NivelesInglesDTO nivel, EditorialesDTO editorial) {
        this.idMaterial = idMaterial;
        this.titulo = titulo;
        this.edicion = edicion;
        this.anioPublicacion = anioPublicacion;
        this.portada = portada;
        this.vigente = vigente;
        this.nivel = nivel;
        this.editorial = editorial;
        this.creadores = new ArrayList<>();
        this.temas = new ArrayList<>();
    }
    
    public MaterialesDTO(Integer idMaterial, String titulo, String edicion, Integer anioPublicacion,
            String portada,Boolean vigente, NivelesInglesDTO nivel, EditorialesDTO editorial, List<CreadoresDTO> creadores, List<TemasDTO> temas){
        this(idMaterial, titulo, edicion, anioPublicacion, portada, vigente, nivel, editorial);
        this.creadores = new ArrayList<>(creadores);
        this.temas = new ArrayList<>(temas);
    }

    public MaterialesDTO(MaterialesDTO material) {
        this.idMaterial = material.getIdMaterial();
        this.titulo = material.getTitulo();
        this.edicion = material.getEdicion();
        this.anioPublicacion = material.getAnioPublicacion();
        this.portada = material.getPortada();
        this.vigente = material.getVigente();
        this.nivel = material. getNivel();
        this.editorial = material.getEditorial();
        this.creadores = material.getCreadores(); //metodo ya protege la lista con una copia
        this.temas = material.getTemas();
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

    public NivelesInglesDTO getNivel() {
        return nivel;
    }

    public void setNivel(NivelesInglesDTO nivel) {
        this.nivel = nivel;
    }

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }
    
    public Boolean getVigente() {
        return vigente;
    }

    public void setVigente(Boolean vigente) {
        this.vigente = vigente;
    }
    public EditorialesDTO getEditorial() {
        return editorial;
    }

    public void setEditorial(EditorialesDTO editorial) {
        this.editorial = editorial;
    }

    public ArrayList<CreadoresDTO> getCreadores() {
        return new ArrayList<>(this.creadores);
    }
    
    public void setCreadores(List<CreadoresDTO> creadores) {
        this.creadores = new ArrayList<>(creadores);
    }
    
    public void addCreador(CreadoresDTO creador) {
        this.creadores.add(creador);
    }

    public void removeCreador(CreadoresDTO creador) {
        this.creadores.remove(creador);
    }
    
    public ArrayList<TemasDTO> getTemas() {
        return new ArrayList<>(this.temas);
    }
    
    public void setTemas(List<TemasDTO> temas) {
        this.temas = new ArrayList<>(temas);
    }
    
    public void addTema(TemasDTO tema) {
        this.temas.add(tema);
    }

    public void removeTema(TemasDTO tema) {
        this.temas.remove(tema);
    }
}
