package com.mangoslr.application.controller.api;

import com.mangoslr.application.Application;
import com.mangoslr.application.aspecto.NecesitaReCaptcha;
import com.mangoslr.application.model.Farmacia;
import com.mangoslr.application.model.Persona;
import com.mangoslr.application.model.Producto;
import com.mangoslr.application.model.Usuario;
import com.mangoslr.application.servicio.UsuarioControlServicio;
import com.mangoslr.application.servicio.UsuarioServicio;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.mangoslr.application.repositorios.FarmaciaRepo;
import com.mangoslr.application.repositorios.PersonaRepo;
import com.mangoslr.application.repositorios.UsuarioRepo;
import com.mangoslr.application.servicio.AuthServicio;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/controldeusuarios")
public class ApiControlUsuario {
    @Autowired
    private UsuarioRepo usuarioRepo;
    @Autowired
    private FarmaciaRepo farmaciaRepo;
    @Autowired
    private PersonaRepo personaRepo;
    @Autowired
    UsuarioControlServicio usuarioServicio;
    @Autowired
    AuthServicio authServicio;

    @PostMapping(value = "/validar", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer validar(@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido, @RequestParam("cedula") String cedula, @RequestParam("nombreUsuario") String nombreUsuario,
                           @RequestParam("clave") String clave, @RequestParam("tipoUsuario") String tipoUsuario, @RequestParam("email") String email) {
        Integer result = 1;
        // Validar que no exista...
        Usuario usuario = usuarioRepo.findByNombreUsuario(nombreUsuario);
        Boolean validate = nombre != "" && apellido != "" && nombreUsuario != "" && clave != "" && tipoUsuario != "" && email != "" &&
                           nombre != null && apellido != null && nombreUsuario != null && clave != null && tipoUsuario != null && email != null;
        if (usuario != null) {
            return 4;
        } else if (usuarioRepo.findByCorreo(email) != null) {
            return 3;
        } else if (!validate) {
            return 5;
        }

        Persona newPersona = personaRepo.findByCedula(cedula);
        // Devolver si la persona con la cédula existe.
        if (newPersona != null) {
            return 2;
        }
        return result;
    }

    @PostMapping(value = "/agregar", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer agregar(@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido, @RequestParam("sexo") String sexo,
                           @RequestParam("tipoSangre") String sangre, @RequestParam("fechaNacimiento") String fechaNacimiento, @RequestParam("direccion") String direccion,
                           @RequestParam("telefono") String telefono, @RequestParam("cedula") String cedula, @RequestParam("nombreUsuario") String nombreUsuario,
                           @RequestParam("clave") String clave, @RequestParam("tipoUsuario") String tipoUsuario, @RequestParam("email") String email) {
        Integer result = 1;
        // Validar que no exista...
        Usuario usuario = usuarioRepo.findByNombreUsuario(nombreUsuario);
        Boolean validate = nombre != "" && apellido != "" && nombreUsuario != "" && clave != "" && tipoUsuario != "" && email != "" &&
                nombre != null && apellido != null && nombreUsuario != null && clave != null && tipoUsuario != null && email != null;
        if (usuario != null) {
            return 4;
        } else if (usuarioRepo.findByCorreo(email) != null) {
            return 3;
        } else if (!validate) {
            return 5;
        }

        // Verificar si existe la farmacia...
        Farmacia farmacia = farmaciaRepo.findByNombre("MangoSLR");
        // Crear en caso de que no exista.
        if (farmacia == null) {
            farmacia = new Farmacia("MangoSLR");
            farmaciaRepo.save(farmacia);
            farmacia = farmaciaRepo.findByNombre("MangoSLR");
        }

        Persona newPersona = personaRepo.findByCedula(cedula);
        // Devolver si la persona con la cédula existe.
        if (newPersona != null) {
            return 2;
        }

        Date fecha = null;

        if (fechaNacimiento != "") {
            try {
                fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimiento);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Persona persona = new Persona(nombre, apellido, sexo, sangre, fecha,
                direccion, telefono, cedula);
        personaRepo.save(persona);
        // nuevamente:
        newPersona = personaRepo.findByCedula(cedula);

        if (newPersona != null) {
            usuario = new Usuario(nombreUsuario, email, clave, tipoUsuario, farmacia, newPersona);
            // Guardar usuario
            usuarioRepo.save(usuario);
            authServicio.crearNotificacion("Usuarios", "Se ha creado un usuario");
            // Mensaje de registro (Temporal)...
            LoggerFactory.getLogger(ApiControlUsuario.class).info(String.format("Se agregó el [%s] con nombre de usuario [%s].", tipoUsuario, nombreUsuario));
        } else
            result = 0;
        return result;
    }


    @PostMapping(value = "/modificarPerfil", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer modificarPerfil(@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido, @RequestParam("sexo") String sexo,
                             @RequestParam("tipoSangre") String sangre, @RequestParam("fechaNacimiento") String fechaNacimiento, @RequestParam("direccion") String direccion,
                             @RequestParam("telefono") String telefono, @RequestParam("cedula") String cedula, @RequestParam("nombreUsuario") String nombreUsuario,
                             @RequestParam("clave") String clave, @RequestParam("tipoUsuario") String tipoUsuario, @RequestParam("email") String email) {
        if (authServicio.isAuthenticated()) {
            Usuario actualizar = usuarioRepo.findByNombreUsuario(nombreUsuario);
            if (actualizar != null) {
                actualizar.getPersona().setNombre(nombre);
                actualizar.getPersona().setApellido(apellido);
                actualizar.getPersona().setSexo(sexo);
                Date fecha = null;
                if (fechaNacimiento != "") {
                    try {
                        fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimiento);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                actualizar.getPersona().setFechaNacimiento(fecha);
                actualizar.getPersona().setTipoSangre(sangre);
                actualizar.getPersona().setDireccion(direccion);
                actualizar.getPersona().setTelefono(telefono);
                actualizar.getPersona().setCedula(cedula);
                actualizar.setCorreo(email);
                usuarioRepo.save(actualizar);
                authServicio.crearNotificacion("Perfil", "Usted ha actualizado su perfil");
                return 1;
            }
            return 2;
        }
        return 3;
    }

    @PostMapping(value ="/cambiarClave", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer cambiarClave (@RequestParam("clave") String clave, @RequestParam("newclave") String claveNueva) {
        if (authServicio.isAuthenticated()) {
            if (clave.compareTo(claveNueva) != 0) {
                Usuario usuario = usuarioRepo.findByNombreUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
                if (usuario != null) {
                    if (usuario.changeClave(clave, claveNueva)) {
                        usuarioRepo.save(usuario);
                        return 1;
                    } else
                        return 3;
                } else
                    return -1;
            } else
                return 2;
        } else
            return -1;
    }

    @PostMapping(value = "/contar", produces = MediaType.APPLICATION_JSON_VALUE)
    public long contar(@RequestParam("id") String id, @RequestParam("nombre") String name) {
        return usuarioServicio.contar(id, name);
    }

    @PostMapping(value = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Usuario> fetch(@RequestParam("id") String id, @RequestParam("nombre") String name, @RequestParam("pagina") int pagina, @RequestParam("mostrar") int mostrar) {
        List<Usuario> usuariosList = (List<Usuario>) usuarioServicio.getUsuarios(id, name, PageRequest.of(pagina, mostrar));
        return usuariosList;
    }
}
