package com.syntaxerror.biblioteca.model;

import com.syntaxerror.biblioteca.model.enums.TipoPersona;
import com.syntaxerror.biblioteca.model.enums.Turnos;
import java.util.Date;

public class PersonasDTO {
    private Integer idPersona;
    private String codigo;
    private String nombre;
    private String paterno;
    private String materno;
    private String direccion;
    private String telefono;
    private String correo;
    private String contrasenha;
    private TipoPersona tipo;
    private Turnos turno;
    private Date fechaContratoInicio;
    private Date fechaContratoFinal;
    private Double deuda;
    private Date fechaSancionFinal;
    private Boolean vigente;
    private NivelesInglesDTO nivel;
    private SedesDTO sede;

    //Constructores
    public PersonasDTO() {
        this.idPersona = null;
        this.codigo = null;
        this.nombre = null;
        this.paterno = null;
        this.materno = null;
        this.direccion = null;
        this.telefono = null;
        this.correo = null;
        this.contrasenha = null;
        this.tipo = null;
        this.turno = null;
        this.fechaContratoInicio = null;
        this.fechaContratoFinal = null;
        this.deuda = null;
        this.fechaSancionFinal = null;
        this.vigente = null;
        this.nivel = null;
        this.sede = null;
    }
    
    public PersonasDTO(Integer idPersona, String codigo, String nombre, String paterno, String materno, String direccion,
            String telefono, String correo, String contrasenha, TipoPersona tipo,
            Turnos turno, Date fechaContratoInicio, Date fechaContratoFinal,
            Double deuda, Date fechaSancionFinal, Boolean viegente, NivelesInglesDTO nivel, SedesDTO sede) {
        this.idPersona = idPersona;
        this.codigo = codigo;
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.contrasenha = contrasenha;
        this.tipo = tipo;
        this.turno = turno;
        this.fechaContratoInicio = fechaContratoInicio;
        this.fechaContratoFinal = fechaContratoFinal;
        this.deuda = deuda;
        this.fechaSancionFinal = fechaSancionFinal;
        this.vigente = viegente;
        this.nivel = nivel;
        this.sede = sede;
    }

    public PersonasDTO(PersonasDTO persona) {
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public SedesDTO getSede() {
        return sede;
    }

    public void setSede(SedesDTO sede) {
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

    public NivelesInglesDTO getNivel() {
        return nivel;
    }

    public void setNivel(NivelesInglesDTO nivel) {
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

    
    public Double getDeuda() {
        return deuda;
    }

    public void setDeuda(Double deuda) {
        this.deuda = deuda;
    }

    public Date getFechaSancionFinal() {
        return fechaSancionFinal;
    }

    public void setFechaSancionFinal(Date fechaSancionFinal) {
        this.fechaSancionFinal = fechaSancionFinal;
    }

    public Boolean getVigente() {
        return vigente;
    }

    public void setVigente(Boolean vigente) {
        this.vigente = vigente;
    }

}
