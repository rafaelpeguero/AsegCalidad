package com.mangoslr.application.controller;

import com.mangoslr.application.model.ResetPwdRequest;
import com.mangoslr.application.model.Usuario;
import com.mangoslr.application.repositorios.UsuarioRepo;
import com.mangoslr.application.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Security;
import java.util.Locale;
import java.util.UUID;

//Mail
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

@RestController
@RequestMapping("/api/recuperar")
public class PasswordResetController {

    @Autowired
    UsuarioRepo usuarioRepo;
    @Autowired
    UsuarioServicio usuarioServicio;

    MailSender mailSender;


    GenericResponse messages;
    Environment env;


//    private MailSender mailSender;
//    private SimpleMailMessage templateMessage;
//
//        public MailSender getMailSender() {
//            return mailSender;
//        }
//        public void setMailSender(MailSender mailSender) {
//            this.mailSender = mailSender;
//        }
//        public SimpleMailMessage getTemplateMessage() {
//            return templateMessage;
//        }
//        public void setTemplateMessage(SimpleMailMessage templateMessage) {
//            this.templateMessage = templateMessage;
//        }

    @PostMapping(value = "/clave", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse resetPassword(HttpServletRequest request,@RequestBody ResetPwdRequest datos) {
        String correo = datos.getEmail();
        Usuario usuario = usuarioRepo.findByCorreo(correo);
        if (usuario == null) {
            //Todo: Hacer Retornar al Login.html si el correo no exite
            return new GenericResponse("Correo no existe");
        } else {
            String token = UUID.randomUUID().toString();
            usuarioServicio.createPasswordResetTokenForUser(correo, token); // Creacion en la BD.

            mailSender.send(constructResetTokenEmail(getAppUrl(request),
                    request.getLocale(), token, correo));
            return new GenericResponse(
                    messages.getMessage("message.resetPasswordEmail", null,
                            request.getLocale()));
        }//Fin else
    }

    private String getAppUrl(HttpServletRequest request) {

        return "null";

    }

    //    And here is method constructResetTokenEmail() – used to send an email with the reset token:
    private SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, String correo) {
        String url = contextPath + "/user/changePassword?token=" + token;
        String message = messages.getMessage("message.resetPassword",
                null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, correo);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             String correo) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(correo);
        email.setFrom(env.getProperty("support.email")); //Tiene que ver con el bean , creo
        return email;
    }
    //End constructResetTokenEmail()
//    public void enviarcorreo(String email){
//        // Do the business calculations...
//
//        // Call the collaborators to persist the order...
//
//        // Create a thread safe "copy" of the template message and customize it
//        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
//        msg.setTo(email);
////        msg.setTo(order.getCustomer().getEmailAddress());
//        msg.setText("Hola");
//        try{
//            this.mailSender.send(msg);
//        }
//        catch (MailException ex) {
//            // simply log it and go on...
//            System.err.println(ex.getMessage());
//        }
//    }

    //Enviando al HTML
//    @GetMapping("login/restablecer")
//    public String showChangePasswordPage(Locale locale, Model model,
//                                         @RequestParam("token") String token) {
//        //String result = securityService.validatePasswordResetToken(token);
//        String result = "test";
//        if(result != null) {
//            String message = messages.getMessage("auth.message." + result, null, locale);
//            return "redirect:/login.html?lang="
//                    + locale.getLanguage() + "&message=" + message;
//        } else {
//            model.addAttribute("token", token);
//            return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
//        }
//    }
}