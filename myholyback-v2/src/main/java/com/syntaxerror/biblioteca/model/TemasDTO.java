package com.syntaxerror.biblioteca.model;

import com.syntaxerror.biblioteca.model.enums.Categoria;
import java.util.ArrayList;
import java.util.List;

public class TemasDTO {

    private Integer idTema;
    private String descripcion;
    private Categoria categoria;

    private TemasDTO temaPadre;
    
    private List<MaterialesDTO> materiales;

    // Constructores
    public TemasDTO() {
        this.idTema = null;
        this.descripcion = null;
        this.categoria = null;
        this.temaPadre = null;
        this.materiales = new ArrayList<>();
    }

    public TemasDTO(Integer idTema, String descripcion, Categoria categoria, TemasDTO temaP) {
        this.idTema = idTema;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.temaPadre = temaP;
        this.materiales = new ArrayList<>();
    }
    
    public TemasDTO(Integer idTema, String descripcion, Categoria categoria, TemasDTO temaP, List<MaterialesDTO> materiales) {
        this(idTema, descripcion, categoria, temaP);
        this.materiales = new ArrayList<>(materiales);
    }

    public TemasDTO(TemasDTO tema) {
        this.idTema = tema.getIdTema();
        this.descripcion = tema.getDescripcion();
        this.categoria = tema.getCategoria();
        this.temaPadre = tema.getTemaPadre();
        this.materiales = tema.getMateriales(); //metodo ya protege la lista con una copia
    }

    public Integer getIdTema() {
        return idTema;
    }

    public void setIdTema(Integer idTema) {
        this.idTema = idTema;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public TemasDTO getTemaPadre() {
        return temaPadre;
    }

    public void setTemaPadre(TemasDTO temaPadre) {
        this.temaPadre = temaPadre;
    }
    
    public ArrayList<MaterialesDTO> getMateriales() {
        return new ArrayList<>(materiales);
    }
    
    public void setMateriales(List<MaterialesDTO> materiales) {
        this.materiales = new ArrayList<>(materiales);
    }
    
    public void addMaterial(MaterialesDTO material) {
        this.materiales.add(material);
    }

    public void removeMaterial(MaterialesDTO material) {
        this.materiales.remove(material);
    }
}
