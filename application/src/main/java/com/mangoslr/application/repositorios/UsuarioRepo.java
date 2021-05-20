package com.mangoslr.application.repositorios;

import com.mangoslr.application.model.Usuario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepo extends CrudRepository<Usuario, Integer> {
    Usuario findByNombreUsuario(String nombreUsuario);
    Usuario findByCorreo(String correo);
    Usuario findById(int id);
    List<Usuario> findAllByNombreUsuarioContainingIgnoreCase(String nombreUsuario);

    // Para el control de usuarios
    List<Usuario> findAllByNombreUsuarioContaining(String nombreUsuario, Pageable pageable);


    @Query(value = "SELECT p.nombreUsuario FROM Usuario AS p WHERE p.nombreUsuario LIKE :d+'%' ORDER BY LEN(p.nombreUsuario), p.nombreUsuario")
    List<String> selectpick(String d, Pageable pageable);

    long countAllByNombreUsuarioContaining(String nombreUsuario);

    @Query("SELECT COUNT(p) FROM Usuario AS p WHERE CAST(p.idUsuario AS text) LIKE :id+'%' AND p.nombreUsuario LIKE '%'+:nombreUsuario+'%'")
    long countAllByIdUsuarioStartsWithAndNombreUsuarioContainingIgnoreCase(String id, String nombreUsuario);

    @Query("SELECT DISTINCT p FROM Usuario AS p WHERE CAST(p.idUsuario AS text) LIKE :id+'%' AND p.nombreUsuario LIKE '%'+:nombreUsuario+'%'")
    List<Usuario> findAllByIdUsuarioStartsWithAndNombreUsuarioContainingIgnoreCase(String id, String nombreUsuario, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Usuario AS p WHERE CAST(p.idUsuario AS text) LIKE :id+'%' AND p.nombreUsuario LIKE '%'+:nombreUsuario+'%'")
    List<Usuario> findAllByIdUsuarioStartsWithAndNombreUsuarioContainingIgnoreCase(String id, String nombreUsuario);
}
