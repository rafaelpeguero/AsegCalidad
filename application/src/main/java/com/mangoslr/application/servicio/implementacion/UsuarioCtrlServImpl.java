package com.mangoslr.application.servicio.implementacion;

import com.mangoslr.application.Application;
import com.mangoslr.application.model.Farmacia;
import com.mangoslr.application.model.Persona;
import com.mangoslr.application.repositorios.FarmaciaRepo;
import com.mangoslr.application.repositorios.PersonaRepo;
import com.mangoslr.application.repositorios.UsuarioRepo;
import com.mangoslr.application.model.Usuario;
import com.mangoslr.application.servicio.UsuarioControlServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class UsuarioCtrlServImpl implements UsuarioControlServicio {
    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private PersonaRepo personaRepo;

    @Autowired
    private FarmaciaRepo farmaciaRepo;

    @Override
    public void guardarUsuario(Usuario usuario) {
        usuarioRepo.save(usuario);
    }
    @Override
    public void eliminarUsuario(int id) {
        usuarioRepo.deleteById(id);
    }
    @Override
    public Iterable<Usuario> getUsuarios() {
        return usuarioRepo.findAll();
    }
    @Override
    public Iterable<Usuario> getUsuarios(String id, String nombreUsuario, Pageable page) {
        Iterable<Usuario> result = usuarioRepo.findAllByIdUsuarioStartsWithAndNombreUsuarioContainingIgnoreCase(String.valueOf(id), nombreUsuario, page);
        return result;
    }
    @Override
    public long contar() {
        return usuarioRepo.count();
    }
    @Override
    public long contar(String id, String nombreUsuario) {
        long result =usuarioRepo.countAllByIdUsuarioStartsWithAndNombreUsuarioContainingIgnoreCase(String.valueOf(id), nombreUsuario);
        return result;
    }
    @Override
    public Usuario getUsuario(int id) {
        return usuarioRepo.findById(id);
    }
    @Override
    public Usuario getUsuarioByNombreUsuario(String nombreUsuario) {
        return usuarioRepo.findByNombreUsuario(nombreUsuario);
    }
    /*@Override
    public Boolean registrar(String nombre, String apellido, String fechaNacimiento, String direccion, String sexo,
                             String sangre, String telefono, String cedula, String nombreUsuario, String clave, String tipoUsuario,
                             String email)
    {
        // Validar que no exista...
        Usuario usuario = usuarioRepo.findByNombreUsuario(nombreUsuario);
        Boolean validate = nombre != "" && apellido != "" && nombreUsuario != "" && clave != "" && tipoUsuario != "" && email != "" &&
                nombre != null && apellido != null && nombreUsuario != null && clave != null && tipoUsuario != null && email != null;
        if (usuario != null || usuarioRepo.findByCorreo(email) != null || !validate) {
            return false;
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
            return false;
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
            // Mensaje de registro (Temporal)...
            //Application.getLogger().info(String.format("Se agregó el [%s] con nombre de usuario [%s].", tipoUsuario, nombreUsuario));
        } else
            return false;

        return true;
    }*/

    @Override
    public Boolean modificar(int idUsuario, String nombre, String apellido, String fechaNacimiento, String direccion, String sexo,
              String tipoSangre, String telefono, String cedula, String nombreUsuario, String clave, String tipoUsuario,
              String correo) {
        Usuario usuario = getUsuario(idUsuario);
        Date fecha = null;
        if (fechaNacimiento != null && fechaNacimiento != "") {
            try {
                fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimiento);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        usuario.getPersona().setNombre(nombre);
        usuario.getPersona().setApellido(apellido);
        usuario.getPersona().setFechaNacimiento(fecha);
        usuario.getPersona().setSexo(sexo);
        usuario.getPersona().setDireccion(direccion);
        usuario.getPersona().setTipoSangre(tipoSangre);
        usuario.getPersona().setTelefono(telefono);
        usuario.getPersona().setCedula(cedula);
        usuario.setClave(clave);
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setCorreo(correo);
        personaRepo.save(usuario.getPersona());
        usuarioRepo.save(usuario);
        return true;
    };

    public Boolean eliminar(int idUsuario) {
        Usuario usuario = getUsuario(idUsuario);
        usuarioRepo.delete(usuario);
        return true;
    }
}
