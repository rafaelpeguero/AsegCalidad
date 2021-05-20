package com.mangoslr.application.model;

//import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.*;
//todo completar factura

/* Factura = ...
 * 1. Verificar los atributos faltantes al modelo de clases. (Reparar esta parte).
 * */
@Entity
@Table(name="Factura")
public class Factura {
    //region Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="idFactura", unique = true, nullable = false)
    private Integer idFactura;

    @Column
    @DecimalMin(value = "0.0")
    private Float total;

    @Column
    private Date fecha;

    @Column
    private Boolean enCurso;

    @Column
    private String rnc;

    // Foreign Keys
    @ManyToOne
    @JoinColumn(name="idFarmacia")
    private Farmacia farmacia;

    @ManyToOne
    @JoinColumn(name="idUsuario", nullable = true)
    private Usuario usuario;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<Detalle> detalles;

    //endregion

    //region Constructores.
    public Factura() {
    }

    public Factura(@DecimalMin(value = "0.0") Float total) {
        this.total = total;
    }

    public Factura(@DecimalMin(value = "0.0") Float total, Date fecha) {
        this.total = total;
        this.fecha = fecha;
    }

    public Factura(@DecimalMin(value = "0.0") Float total, Date fecha, Boolean curso) {
        this.total = total;
        this.fecha = fecha;
        this.enCurso = curso;
    }

//endregion

    //region Getters and Setters.
    public String getRnc() {
        return rnc;
    }

    public void setRnc(String rnc) {
        this.rnc = rnc;
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }


    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Farmacia getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Detalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Detalle> detalles) {
        this.detalles = detalles;
    }

    public Boolean getEnCurso() {
        return enCurso;
    }

    public void setEnCurso(Boolean enCurso) {
        this.enCurso = enCurso;
    }

    //endregion
}