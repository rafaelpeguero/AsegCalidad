package com.mangoslr.application.servicio.implementacion;

import com.mangoslr.application.model.Notificacion;
import com.mangoslr.application.model.Usuario;
import com.mangoslr.application.repositorios.NotificacionRepo;
import com.mangoslr.application.servicio.AuthServicio;
import com.mangoslr.application.servicio.UsuarioControlServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServicioImpl implements AuthServicio {
    @Autowired
    UsuarioControlServicio usuarioServicio;
    @Autowired
    NotificacionRepo notificacionRepo;
    public Boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    public Usuario getUserAuthenticated() {
        if (isAuthenticated()) {
            Usuario actual = usuarioServicio.getUsuarioByNombreUsuario(
                    SecurityContextHolder.getContext().getAuthentication().getName());
            return actual;
        }
        return null;
    }

    public Boolean crearNotificacion(String tema, String mensaje) {
        Notificacion notificacion = new Notificacion(tema, mensaje, new Date(), false, getUserAuthenticated());
        notificacionRepo.save(notificacion);
        return true;
    }
}
