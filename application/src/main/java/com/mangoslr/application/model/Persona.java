package com.mangoslr.application.model;

import javax.persistence.*;
import java.util.Date;

// Persona = Hecho.
/*
* Modificar en el modelo:
*   1. Direccion cambiado a longitud y latitud (Las coordenadas permiten usar Google Maps).
*   2. Agregar los atributos faltantes al modelo de clases. (Reparar esta parte).
* */
@Entity
@Table(name="Persona")
public class Persona {
    //region Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="idPersona", unique = true, nullable = false)
    private Integer idPersona;
    @Column(length = 30, nullable = false)
    private String nombre;
    @Column(length = 30, nullable = false)
    private String apellido;
    @Column(length = 1)
    private String sexo;
    @Column(length = 5)
    private String tipoSangre;
    @Column
    private Date fechaNacimiento;
    @Column(length = 512)
    private String direccion;
    // Nueva Direccion: Latitud Longitud.
    @Column
    private Float latDireccion;
    @Column
    private Float lonDireccion;
    @Column(length = 20)
    private String telefono;
    @Column(length = 15)
    private String cedula;
    @Column(length = 20)
    private String celular;


    @OneToOne(mappedBy = "persona")
    private Usuario usuario;
    //endregion

    //region Constructores
    public Persona() {
        super();
    }

    public Persona(String nombre, String apellido) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Persona(String nombre, String apellido, String cedula) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
    }

    public Persona(String nombre, String apellido, String telefono,
                   String celular, String cedula) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.celular = celular;
        this.cedula = cedula;
    }

    public Persona(String nombre, String apellido, String sexo, String tipoSangre, Date fechaNacimiento, String direccion, String telefono, String cedula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.tipoSangre = tipoSangre;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.cedula = cedula;
    }

    //endregion

    //region Getters and Setters
    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer id) {
        this.idPersona = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Float getLatDireccion() {
        return latDireccion;
    }

    public void getLatDireccion(Float latdireccion) {
        this.latDireccion = latdireccion;
    }

    public Float getLonDireccion() {
        return lonDireccion;
    }

    public void geLonDireccion(Float londireccion) {
        this.lonDireccion = londireccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setLatDireccion(Float latDireccion) {
        this.latDireccion = latDireccion;
    }

    public void setLonDireccion(Float lonDireccion) {
        this.lonDireccion = lonDireccion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    //endregion
}
