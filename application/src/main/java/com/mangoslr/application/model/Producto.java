package com.mangoslr.application.model;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

/* Producto = Hecho.
* 1. Verificar los atributos faltantes al modelo de clases. (Reparar esta parte).
* */
@Entity
@Table(name="Productos")
public class Producto {
    //region Atributos
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;
    @Column(length = 32, nullable = false)
    private String nombre;
    @Column(length = 512, nullable = true)
    private String descripcion;
    @Column(length = 128)
    private String descripcion_corta;
    @Column(nullable = true)
    @DecimalMin(value = "0", inclusive = true)
    private int cantidad;
    @Column(nullable = true)
    @DecimalMin(value = "0", inclusive = true)
    private float costo;
    @DecimalMin(value = "0", inclusive = true)
    private int cantidad_min;
    @Column(length = 256)
    private String imageUrl;
    @DecimalMin(value="0")
    private Integer autorizacion;

    //endregion

    //region Constructor
    public Producto() {
        super();
    }

    public Producto(String nombre, int cantidad, float costo) {
        super();
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.costo = costo;
        this.autorizacion = 15; // Todos los permisos por defecto
    }

    public Producto(String nombre, String descripcion, float costo) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
        this.autorizacion = 15;
    }

    public Producto(Integer idProducto, String nombre, String descripcion, int cantidad, float costo) {
        super();
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.costo = costo;
        this.autorizacion = 15;
    }

    public Producto(String nombre, String descripcion, String descripcion_corta, @DecimalMin(value = "0", inclusive = true) int cantidad, @DecimalMin(value = "0", inclusive = true) float costo, @DecimalMin(value = "0", inclusive = true) int cantidad_min, String imageUrl) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.descripcion_corta = descripcion_corta;
        this.cantidad = cantidad;
        this.costo = costo;
        this.cantidad_min = cantidad_min;
        this.imageUrl = imageUrl;
        this.autorizacion = 15;
    }

    public Producto(String nombre, String descripcion, String descripcion_corta, @DecimalMin(value = "0", inclusive = true) int cantidad, @DecimalMin(value = "0", inclusive = true) float costo, @DecimalMin(value = "0", inclusive = true) int cantidad_min, String imageUrl, int auto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.descripcion_corta = descripcion_corta;
        this.cantidad = cantidad;
        this.costo = costo;
        this.cantidad_min = cantidad_min;
        this.imageUrl = imageUrl;
        this.autorizacion = auto % 16;
    }

    //endregion

    @Override
    public boolean equals(Object o) {
        if(this==o) {
            return true;
        }
        if(o instanceof Producto) {
            return ((Producto) o).idProducto.equals(this.idProducto);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return idProducto;
    }

    //region Getters and Setters
    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescripcion_corta() {
        return descripcion_corta;
    }

    public void setDescripcion_corta(String descripcion_corta) {
        this.descripcion_corta = descripcion_corta;
    }

    public int getCantidad_min() {
        return cantidad_min;
    }

    public void setCantidad_min(int cantidad_min) {
        this.cantidad_min = cantidad_min;
    }

    public Integer getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(Integer autorizacion) {
        this.autorizacion = autorizacion;
    }

    //@Override
    public String toString() {
        return String.format("{id:%s, nombre:%s, cantidad:%d, costo:%.2f}", idProducto, nombre, cantidad, costo);
    }
    //endregion
}
