package com.mangoslr.application.servicio;

import com.mangoslr.application.model.Usuario;
import org.springframework.data.domain.Pageable;

public interface UsuarioControlServicio {
    public abstract void guardarUsuario(Usuario usuario);
    public abstract void eliminarUsuario(int id);
    public abstract Iterable<Usuario> getUsuarios();
    public abstract Iterable<Usuario> getUsuarios(String id, String nombre, Pageable page);
    public abstract long contar();
    public abstract long contar(String id, String nombre);
    public abstract Usuario getUsuario(int id);
    public abstract Usuario getUsuarioByNombreUsuario(String nombreUsuario);

    /*public abstract Boolean registrar(String nombre,
                                      String apellido,
                                      String fechaNacimiento,
                                      String direccion,
                                      String sexo,
                                      String sangre,
                                      String telefono,
                                      String cedula,
                                      String nombreUsuario,
                                      String clave,
                                      String tipoUsuario,
                                      String email);*/

    public abstract Boolean modificar(int idUsuario,
                                      String nombre,
                                      String apellido,
                                      String fechaNacimiento,
                                      String direccion,
                                      String sexo,
                                      String sangre,
                                      String telefono,
                                      String cedula,
                                      String nombreUsuario,
                                      String clave,
                                      String tipoUsuario,
                                      String email);
    public abstract Boolean eliminar(int idUsuario);
}
