package com.mangoslr.application.servicio;

import com.mangoslr.application.model.Usuario;

public interface AuthServicio {
    public abstract Boolean isAuthenticated();

    public abstract Usuario getUserAuthenticated();

    public abstract Boolean crearNotificacion(String tema, String mensaje);
}
