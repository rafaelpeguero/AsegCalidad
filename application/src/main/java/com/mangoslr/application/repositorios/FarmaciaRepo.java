package com.mangoslr.application.repositorios;

import com.mangoslr.application.model.Farmacia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FarmaciaRepo extends CrudRepository<Farmacia, Integer> {
    Farmacia findFirstByOrderByIdFarmaciaAsc();
    Farmacia findByNombre(String nombre);
    @Query(value = "SELECT * FROM Farmacia WHERE nombre = ?1", nativeQuery = true)
    List<Farmacia> findAllNombres(String nombre);
}
