package com.mangoslr.application.controller;

import com.mangoslr.application.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mangoslr.application.servicio.AuthServicio;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
public class Login {

    @Autowired
    AuthServicio authServicio;

    //reset
    @Autowired
    UsuarioServicio usuarioServicio;
    GenericResponse messages;
    @RequestMapping("/cambiar/clave")
    public String showChangePasswordPage(Locale locale, Model model,
                                         @RequestParam("token") String token) {
        String result = usuarioServicio.validatePasswordResetToken(token);
        if(result != null) {
            String message = messages.getMessage("auth.message." + result, null, locale);
            return "redirect:/login/registrar.html";
//            return "login/registrar";


            // + locale.getLanguage() + "&message=" + message;
        } else {
            model.addAttribute("token", token);
//            return "redirect:/restablecer"; // ?lang="; //+ locale.getLanguage();
            return "/login/restablecer";

        }
    }
    //end reset

    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        if (authServicio.isAuthenticated()) {
            return "redirect:"+request.getSession().getAttribute("url_prior_login");
        }
        String referrer = request.getHeader("Referer");
        if(referrer!=null){
            request.getSession().setAttribute("url_prior_login", referrer);
        }
        return "login/login";
    }

    @RequestMapping("/recuperar")
    public String registrar(Model model) {
        if (authServicio.isAuthenticated()) {
            return "redirect:/";
        }
        return "login/recuperar";
    }

    //Todo: Controlar acceso , solo con autentificacion del token
    @RequestMapping("/restablecer")
    public String restablecer(Model model) {
        if (authServicio.isAuthenticated()) {
            return "redirect:/";
        }
        return "login/restablecer";
    }

    @RequestMapping("/access-denied")
    public String notFound() {
        return "access-denied";
    }
}
