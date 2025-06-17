
package com.syntaxerror.biblioteca.model;

import com.syntaxerror.biblioteca.model.enums.Nivel;


public class NivelInglesDTO {

    private Integer idNivel;
    private Nivel nivel;
    private String descripcion;

    public NivelInglesDTO() {
        this.idNivel = null;
        this.nivel = null;
        this.descripcion = null;
    }

    public NivelInglesDTO(Integer idNivel, Nivel nivel, String descripcion) {
        this.idNivel = idNivel;
        this.nivel = nivel;
        this.descripcion = descripcion;
    }

    public NivelInglesDTO(NivelInglesDTO nivelDeIngles) {
        this.idNivel = nivelDeIngles.getIdNivel();
        this.nivel = nivelDeIngles.getNivel();
        this.descripcion = nivelDeIngles.getDescripcion();
    }

    public int getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(int idNivel) {
        this.idNivel = idNivel;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
