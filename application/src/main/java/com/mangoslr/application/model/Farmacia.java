package com.mangoslr.application.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;


@Entity
@Table(name="Farmacia")
public class Farmacia {
    //region Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="idFarmacia", unique = true, nullable = false)
    private Integer idFarmacia;
    @Column(length = 50, nullable = false)
    private String nombre;
    @Column(length = 512)
    private String direccion;
    @Column(length = 20)
    private String telefono;
    @Column(length = 256)
    private String imageUrl;

    // Foreign Keys
    @OneToMany(mappedBy = "farmacia")
    private List<Usuario> usuarios;
    @OneToMany(mappedBy = "farmacia")
    private List<Factura> facturas;
    //endregion

    //region Constructores
    public Farmacia() {
    }

    public Farmacia(String nombre) {
        this.nombre = nombre;
    }

    public Farmacia(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }
    //endregion

    //region Getters and Setters.
    public Integer getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(Integer idFarmacia) {
        this.idFarmacia = idFarmacia;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    //endregion
}
