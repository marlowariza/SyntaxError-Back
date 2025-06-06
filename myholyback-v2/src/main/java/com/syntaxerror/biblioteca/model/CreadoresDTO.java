package com.syntaxerror.biblioteca.model;

import com.syntaxerror.biblioteca.model.enums.TipoCreador;
import java.util.ArrayList;
import java.util.List;

public class CreadoresDTO {

    private Integer idCreador;
    private String nombre;
    private String paterno;
    private String materno;
    private String seudonimo;
    private TipoCreador tipo;
    private String nacionalidad;
    private Boolean activo;
    private List<MaterialesDTO> materiales;

    // Constructores
    public CreadoresDTO() {
        this.idCreador = null;
        this.nombre = null;
        this.paterno = null;
        this.materno = null;
        this.seudonimo = null;
        this.tipo = null;
        this.nacionalidad = null;
        this.activo = null;
        this.materiales = new ArrayList<>();
    }

    public CreadoresDTO(Integer idCreador, String nombre, String paterno, String materno,
            String seudonimo, TipoCreador tipo, String nacionalidad, Boolean activo) {
        this.idCreador = idCreador;
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.seudonimo = seudonimo;
        this.tipo = tipo;
        this.nacionalidad = nacionalidad;
        this.activo = activo;
        this.materiales = new ArrayList<>();
    }
    
    public CreadoresDTO(Integer idCreador, String nombre, String paterno, String materno,
            String seudonimo, TipoCreador tipo, String nacionalidad, Boolean activo, List<MaterialesDTO> materiales) {
        
        this(idCreador, nombre, paterno, materno, seudonimo, tipo, nacionalidad, activo);
        this.materiales = new ArrayList<>(materiales);
    }

    public CreadoresDTO(CreadoresDTO creador) {
        this.idCreador = creador.getIdCreador();
        this.nombre = creador.getNombre();
        this.paterno = creador.getPaterno();
        this.materno = creador.getMaterno();
        this.seudonimo = creador.getSeudonimo();
        this.tipo = creador.getTipo();
        this.nacionalidad = creador.getNacionalidad();
        this.activo = creador.getActivo();
        this.materiales = creador.getMateriales(); //metodo ya protege la lista con una copia
    }

    // Getters y Setters
    public Integer getIdCreador() {
        return idCreador;
    }

    public void setIdCreador(Integer idCreador) {
        this.idCreador = idCreador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getSeudonimo() {
        return seudonimo;
    }

    public void setSeudonimo(String seudonimo) {
        this.seudonimo = seudonimo;
    }

    public TipoCreador getTipo() {
        return tipo;
    }

    public void setTipo(TipoCreador tipo) {
        this.tipo = tipo;
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
