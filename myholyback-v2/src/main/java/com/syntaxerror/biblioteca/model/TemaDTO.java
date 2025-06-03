package com.syntaxerror.biblioteca.model;

import com.syntaxerror.biblioteca.model.enums.Categoria;
import java.util.ArrayList;
import java.util.List;

public class TemaDTO {

    private Integer idTema;
    private String descripcion;
    private Categoria categoria;

    private TemaDTO temaPadre;
    
    private List<MaterialDTO> materiales;

    // Constructores
    public TemaDTO() {
        this.idTema = null;
        this.descripcion = null;
        this.categoria = null;
        this.temaPadre = null;
        this.materiales = new ArrayList<>();
    }

    public TemaDTO(Integer idTema, String descripcion, Categoria categoria, TemaDTO temaP) {
        this.idTema = idTema;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.temaPadre = temaP;
        this.materiales = new ArrayList<>();
    }
    
    public TemaDTO(Integer idTema, String descripcion, Categoria categoria, TemaDTO temaP, List<MaterialDTO> materiales) {
        this(idTema, descripcion, categoria, temaP);
        this.materiales = new ArrayList<>(materiales);
    }

    public TemaDTO(TemaDTO tema) {
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

    public TemaDTO getTemaPadre() {
        return temaPadre;
    }

    public void setTemaPadre(TemaDTO temaPadre) {
        this.temaPadre = temaPadre;
    }
    
    public ArrayList<MaterialDTO> getMateriales() {
        return new ArrayList<>(materiales);
    }
    
    public void setMateriales(List<MaterialDTO> materiales) {
        this.materiales = new ArrayList<>(materiales);
    }
    
    public void addMaterial(MaterialDTO material) {
        this.materiales.add(material);
    }

    public void removeMaterial(MaterialDTO material) {
        this.materiales.remove(material);
    }
}
