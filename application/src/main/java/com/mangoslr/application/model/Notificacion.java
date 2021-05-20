package com.mangoslr.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Notificacion")
public class Notificacion {

    //region Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_notificacion", unique = true, nullable = false)
    private Integer idNotificacion;

    @Column
    private String tema;

    @Column
    private String mensaje;

    @Column
    private Date fecha;

    @Column
    private Boolean visto;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="idUsuario", nullable = true)
    private Usuario usuario;

    public Notificacion() {
    }

    public Notificacion(String tema, String mensaje, Date fecha, Boolean visto, Usuario usuario) {
        this.tema = tema;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.visto = visto;
        this.usuario = usuario;
    }

    public Notificacion(Integer idNotificacion, String tema, String mensaje, Date fecha, Boolean visto, Usuario usuario) {
        this.idNotificacion = idNotificacion;
        this.tema = tema;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.visto = visto;
        this.usuario = usuario;
    }

    public Integer getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Integer idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getVisto() {
        return visto;
    }

    public void setVisto(Boolean visto) {
        this.visto = visto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


}
