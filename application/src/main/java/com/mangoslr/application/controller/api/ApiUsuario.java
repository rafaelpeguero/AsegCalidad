package com.mangoslr.application.controller.api;

import com.mangoslr.application.Application;
import com.mangoslr.application.aspecto.NecesitaReCaptcha;
import com.mangoslr.application.model.Farmacia;
import com.mangoslr.application.model.Persona;
import com.mangoslr.application.model.Usuario;
import com.mangoslr.application.repositorios.FarmaciaRepo;
import com.mangoslr.application.repositorios.PersonaRepo;
import com.mangoslr.application.repositorios.UsuarioRepo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuario")
public class ApiUsuario {
    @Autowired
    private UsuarioRepo usuarioRepo;
    @Autowired
    private FarmaciaRepo farmaciaRepo;
    @Autowired
    private PersonaRepo personaRepo;

    @PostMapping(value = "/selectpick", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> primerosUsuario(@RequestParam("usuario") String username) {
        return usuarioRepo.selectpick(username, PageRequest.of(0,10));
    }

    @PostMapping(value = "/registrar", produces = MediaType.APPLICATION_JSON_VALUE)
    @NecesitaReCaptcha
    public Boolean registrar(@RequestBody Map<String, String> datos) {
        String nombre = datos.get("nombre");
        String apellido = datos.get("apellido");
        String cedula = datos.get("cedula");
        String nombreUsuario = datos.get("nombreUsuario");
        String correo  = datos.get("email");
        String clave  = datos.get("clave");

        Usuario usuario = usuarioRepo.findByNombreUsuario(nombreUsuario);
        if (usuario == null && usuarioRepo.findByCorreo(correo) == null) {
            // Verificar si existe la farmacia...
            Farmacia farmacia = farmaciaRepo.findByNombre("MangoSLR");
            // Crear en caso de que no exista.
            if (farmacia == null) {
                farmacia = new Farmacia("MangoSLR");
                farmaciaRepo.save(farmacia);
                farmacia = farmaciaRepo.findByNombre("MangoSLR");
            }

            // Crear la persona
            Persona persona = new Persona(nombre, apellido, cedula);
            personaRepo.save(persona);
            persona = personaRepo.findByNombre(nombre);
            // Crear usuario que está vinculado a una farmacia y a una persona...
            usuario = new Usuario(nombreUsuario, correo, clave, "Cliente", farmacia, persona);
            // Guardar usuario
            usuarioRepo.save(usuario);
            // Mensaje de registro...
            LoggerFactory.getLogger(ApiUsuario.class).info(String.format("Se registro [%s]@[%s] con password [%s].", nombreUsuario, correo, clave));
            return true;
        }
        return false;
    }
}
