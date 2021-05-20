package com.mangoslr.application.servicio;

import com.mangoslr.application.controller.GenericResponse;
import com.mangoslr.application.model.PasswordResetToken;
import com.mangoslr.application.repositorios.UsuarioRepo;
import com.mangoslr.application.model.Usuario;
import com.mangoslr.application.repositorios.PasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Locale;

@Service
public class UsuarioServicio implements UserDetailsService {

    PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepo usuarioRepo;
    @Autowired
    private PasswordTokenRepository passwordTokenRepository;
//    @Autowired
    private SimpleMailMessage simpleMailMessage;
    @Autowired
    Environment env;
    private GenericResponse messages;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByNombreUsuario(s);
        if (usuario == null) {
            throw new UsernameNotFoundException(s);
        }
        return usuario;
    }
    public UserDetails loadUserByEmail(String e)throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByCorreo(e);
        if(usuario == null){
            throw new UsernameNotFoundException(e);
        }
        return usuario;
    }

    public void createPasswordResetTokenForUser(String email, String token){
        PasswordResetToken myToken = new PasswordResetToken(token, email); //Modelo : Creacion Token
//        System.out.format("myToken = Email:[%s],Token:[%s]\n\n",myToken.getEmail(),myToken.getToken());$
        passwordTokenRepository.save(myToken);
    }

    public void changeUserPassword(Usuario user, String password) {
        user.setClave(password); //Falta Encriptar "la clave"
        usuarioRepo.save(user);
    }

    //Contructor de link con el token
    private SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, String email) {
        String url = contextPath + "login/restablecer?token=" + token;
        String message = messages.getMessage("message.resetPassword", null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, email);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             String correo) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(correo);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpirationDate().before(cal.getTime());
    }
}
