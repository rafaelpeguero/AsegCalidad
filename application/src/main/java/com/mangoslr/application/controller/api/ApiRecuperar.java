package com.mangoslr.application.controller.api;

import com.mangoslr.application.Application;
import com.mangoslr.application.model.ResetPwdRequest;
import com.mangoslr.application.model.Usuario;
import com.mangoslr.application.repositorios.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
//@RequestMapping("/api/recuperar")
public class ApiRecuperar {
//    @Autowired
//    UsuarioRepo usuarioRepo;
//
//    //Todo : Debo validar si Email Existe : Si ? envio Token, No? Retorno al login
//    @PostMapping(value = "/clave", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Boolean resetPassword(@RequestBody ResetPwdRequest datos){
//        String correo = datos.getEmail();
////        System.out.println(correo);
//        Usuario usuario = usuarioRepo.findByCorreo(correo);
//        if (usuario == null) {
//            Application.getLogger().info(String.format("El correo [%s] no existe.", correo));
//            return false;
//        }else{
//            Application.getLogger().info(String.format("El correo [%s] existe.", correo));
//        }
//
//        String token  = UUID.randomUUID().toString();
//        Application.getLogger().info(String.format("Token: [%s]", token));
//
//        return true;
//    }

//    public Boolean recuperar(@RequestBody Map<String,String> datos){
//        String clave = datos.get("pass1");
//        String nombreUsuario  = datos.get("nombreUsuario");
//
//        Usuario usuario = usuarioRepo.findByNombreUsuario("nombreUsuario");
//        if(usuario == null){
//            Application.getLogger().info(String.format("El Usuario : [%s] no existe.", nombreUsuario));
//            return true;
//        }
//        return false;
//    }
}