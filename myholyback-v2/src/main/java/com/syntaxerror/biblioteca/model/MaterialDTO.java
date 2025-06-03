package com.syntaxerror.biblioteca.model;

import com.syntaxerror.biblioteca.model.enums.NivelDeIngles;
import java.util.ArrayList;
import java.util.List;

public class MaterialDTO {
    private Integer idMaterial;
    private String titulo;
    private String edicion;
    private NivelDeIngles nivel;
    private Integer anioPublicacion;
    private String portada;
    private EditorialDTO editorial;
    private List<CreadorDTO> creadores;
    private List<TemaDTO> temas;

    // Constructores
    public MaterialDTO() {
        this.idMaterial = null;
        this.titulo = null;
        this.edicion = null;
        this.nivel = null;
        this.anioPublicacion = null;
        this.portada = null;
        this.editorial = null;
        this.creadores = new ArrayList<CreadorDTO>();
        this.temas = new ArrayList<TemaDTO>();
    }

    public MaterialDTO(Integer idMaterial, String titulo, String edicion, NivelDeIngles nivel, Integer anioPublicacion, String portada,
    EditorialDTO editorial) {
        this.idMaterial = idMaterial;
        this.titulo = titulo;
        this.edicion = edicion;
        this.nivel = nivel;
        this.anioPublicacion = anioPublicacion;
        this.portada = portada;
        this.editorial = editorial;
        this.creadores = new ArrayList<CreadorDTO>();
        this.temas = new ArrayList<TemaDTO>();
    }
    
    public MaterialDTO(Integer idMaterial, String titulo, String edicion, NivelDeIngles nivel, Integer anioPublicacion,
            String portada, EditorialDTO editorial, List<CreadorDTO> creadores, List<TemaDTO> temas){
        this(idMaterial, titulo, edicion, nivel, anioPublicacion, portada, editorial);
        this.creadores = new ArrayList<>(creadores);
        this.temas = new ArrayList<>(temas);
    }

    public MaterialDTO(MaterialDTO material) {
        this.idMaterial = material.getIdMaterial();
        this.titulo = material.getTitulo();
        this.edicion = material.getEdicion();
        this.nivel = material.getNivel();
        this.anioPublicacion = material.getAnioPublicacion();
        this.portada = material.getPortada();
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

    public NivelDeIngles getNivel() {
        return nivel;
    }

    public void setNivel(NivelDeIngles nivel) {
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

    public EditorialDTO getEditorial() {
        return editorial;
    }

    public void setEditorial(EditorialDTO editorial) {
        this.editorial = editorial;
    }

    public ArrayList<CreadorDTO> getCreadores() {
        return new ArrayList<>(this.creadores);
    }
    
    public void setCreadores(List<CreadorDTO> creadores) {
        this.creadores = new ArrayList<>(creadores);
    }
    
    public void addCreador(CreadorDTO creador) {
        this.creadores.add(creador);
    }

    public void removeCreador(CreadorDTO creador) {
        this.creadores.remove(creador);
    }
    
    public ArrayList<TemaDTO> getTemas() {
        return new ArrayList<>(this.temas);
    }
    
    public void setTemas(List<TemaDTO> temas) {
        this.temas = new ArrayList<>(temas);
    }
    
    public void addTema(TemaDTO tema) {
        this.temas.add(tema);
    }

    public void removeTema(TemaDTO tema) {
        this.temas.remove(tema);
    }
}
