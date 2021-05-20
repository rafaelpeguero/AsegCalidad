package com.mangoslr.application.model;

import java.util.Date;

public class Pedido {
    // Parte del usuario
    private Integer idUsuario;
    private String nombreUsuario;
    // Parte de la factura
    private Integer idFactura;
    private Date fecha;
    private float total;
    private Boolean enCurso;
    /*Esta clase está hecha con el fin de simplificar lo que hay que hacer con el historial y los pedidos en curso*/

    public Pedido() {
    }

    public Pedido(Integer idUsuario, String nombreUsuario, Integer idFactura) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.idFactura = idFactura;
    }

    public Pedido(Integer idUsuario, String nombreUsuario, Integer idFactura, Date fecha, float total, Boolean enCurso) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.total = total;
        this.enCurso = enCurso;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Boolean getEnCurso() {
        return enCurso;
    }

    public void setEnCurso(Boolean enCurso) {
        this.enCurso = enCurso;
    }
}
