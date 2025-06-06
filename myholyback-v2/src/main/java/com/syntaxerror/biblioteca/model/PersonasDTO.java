package com.syntaxerror.biblioteca.model;

import com.syntaxerror.biblioteca.model.enums.NivelDeIngles;
import com.syntaxerror.biblioteca.model.enums.TipoPersona;
import com.syntaxerror.biblioteca.model.enums.Turnos;
import java.util.Date;

public class PersonaDTO {

    private Integer idPersona;
    private String nombre;
    private String paterno;
    private String materno;
    private String direccion;
    private String telefono;
    private String correo;
    private String contrasenha;
    private TipoPersona tipo;
    private NivelDeIngles nivel;
    private Turnos turno;
    private Date fechaContratoInicio;
    private Date fechaContratoFinal;
    private Boolean vigente;

    private SedeDTO sede;

    //Constructores
    public PersonaDTO(Integer idPersona, String nombre, String paterno, String materno, String direccion,
            String telefono, String correo, String contrasenha, TipoPersona tipo, NivelDeIngles nivel,
            Turnos turno, Date fechaIni, Date fechaFin, Boolean viegente, SedeDTO sede) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.contrasenha = contrasenha;
        this.tipo = tipo;
        this.nivel = nivel;
        this.turno = turno;
        this.fechaContratoInicio = fechaIni;
        this.fechaContratoFinal = fechaFin;
        this.vigente = viegente;
        this.sede = sede;
    }

    public PersonaDTO() {
        this.idPersona = null;
        this.nombre = null;
        this.paterno = null;
        this.materno = null;
        this.direccion = null;
        this.telefono = null;
        this.correo = null;
        this.contrasenha = null;
        this.tipo = null;
        this.nivel = null;
        this.turno = null;
        this.fechaContratoInicio = null;
        this.fechaContratoFinal = null;
        this.vigente = null;
        this.sede = null;
    }

    public PersonaDTO(PersonaDTO persona) {
        this.idPersona = persona.idPersona;
        this.nombre = persona.nombre;
        this.direccion = persona.direccion;
        this.telefono = persona.telefono;
        this.correo = persona.correo;
        this.contrasenha = persona.contrasenha;
        this.sede = persona.sede;
    }

    //Setters y getters
    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String email) {
        this.correo = email;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public SedeDTO getSede() {
        return sede;
    }

    public void setSede(SedeDTO sede) {
        this.sede = sede;
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

    public TipoPersona getTipo() {
        return tipo;
    }

    public void setTipo(TipoPersona tipo) {
        this.tipo = tipo;
    }

    public NivelDeIngles getNivel() {
        return nivel;
    }

    public void setNivel(NivelDeIngles nivel) {
        this.nivel = nivel;
    }

    public Turnos getTurno() {
        return turno;
    }

    public void setTurno(Turnos turno) {
        this.turno = turno;
    }

    public Date getFechaContratoInicio() {
        return fechaContratoInicio;
    }

    public void setFechaContratoInicio(Date fechaContratoInicio) {
        this.fechaContratoInicio = fechaContratoInicio;
    }

    public Date getFechaContratoFinal() {
        return fechaContratoFinal;
    }

    public void setFechaContratoFinal(Date fechaContratoFinal) {
        this.fechaContratoFinal = fechaContratoFinal;
    }

    public Boolean getVigente() {
        return vigente;
    }

    public void setVigente(Boolean vigente) {
        this.vigente = vigente;
    }

}
