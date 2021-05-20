package com.mangoslr.application.repositorios;

import com.mangoslr.application.model.Producto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductoRepo extends CrudRepository<Producto, Integer> {

    @Query("SELECT p.idProducto FROM Producto p LEFT JOIN Detalle d ON d.producto = p GROUP BY p.idProducto ORDER BY SUM(d.cantidad) DESC")
    List<Integer> masIdVendidos(Pageable pageable);

    Producto findByIdProducto(int id);
    List<Producto> findAllByNombreContaining(String nombre);
    List<Producto> findAllByNombreContaining(String nombre, Pageable pageable);

    long countAllByNombreContaining(String nombre);

    @Query("SELECT COUNT(p) FROM Producto AS p WHERE CAST(p.idProducto AS text) LIKE :id+'%' AND p.nombre LIKE '%'+:nombre+'%'")
    long countAllByIdProductoStartsWithAndNombreContainingIgnoreCase(String id, String nombre);

    @Query("SELECT DISTINCT p FROM Producto AS p WHERE CAST(p.idProducto AS text) LIKE :id+'%' AND p.nombre LIKE '%'+:nombre+'%'")
    List<Producto> findAllByIdProductoStartsWithAndNombreContainingIgnoreCase(String id, String nombre);

    @Query("SELECT DISTINCT p FROM Producto AS p WHERE CAST(p.idProducto AS text) LIKE :id+'%' AND p.nombre LIKE '%'+:nombre+'%'")
    List<Producto> findAllByIdProductoStartsWithAndNombreContainingIgnoreCase(String id, String nombre, Pageable pageable);
}

