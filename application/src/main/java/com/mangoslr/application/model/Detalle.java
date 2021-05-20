package com.mangoslr.application.model;
import javax.persistence.*;
import javax.validation.constraints.*;
@Entity
@Table(name="Detalle")
public class Detalle {
    //region Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="idDetalle", unique = true, nullable = false)
    private Integer idDetalle;
    @Column
    @DecimalMin(value = "0.0")
    private Integer cantidad;
    @Column
    @DecimalMin(value = "0.0")
    private Float precioUnidad;
    @Column
    @DecimalMin(value = "0.0")
    private Float total;

    // Foreign Keys
    @ManyToOne
    @JoinColumn(name="idFactura", nullable = false)
    private Factura factura;

    @ManyToOne
    @JoinColumn(name="idProducto")
    private Producto producto;

    //endregion

    //region Constructor
    public Detalle() {
    }

    public Detalle(@DecimalMin(value = "0.0") Integer cantidad, @DecimalMin(value = "0.0") Float precioUnidad, @DecimalMin(value = "0.0") Float total) {
        this.cantidad = cantidad;
        this.precioUnidad = precioUnidad;
        this.total = total;
    }
    //endregion

    //region Getters and Setters.
    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Float getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(Float precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    //endregion
}
