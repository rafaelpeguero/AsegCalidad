package com.mangoslr.application.repositorios;

import com.mangoslr.application.model.Persona;
import com.mangoslr.application.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonaRepo extends CrudRepository<Persona,Integer> {
    Persona findByNombre(String nombre);
    Persona findByCedula(String cedula);
    Persona findById(int id);
    List<Persona> findAllByNombreContainingIgnoreCase(String nombre);

}
