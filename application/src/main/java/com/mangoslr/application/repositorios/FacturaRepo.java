package com.mangoslr.application.repositorios;

import com.mangoslr.application.model.Factura;
import com.mangoslr.application.model.Farmacia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityManager;
import java.util.List;

public interface FacturaRepo extends CrudRepository<Factura,Integer> {
    Factura findByIdFactura(Integer id);
    List<Factura> findAll();
    // Buscar las facturas que tienen activo "enCurso"
    List<Factura> findAllByEnCurso(Boolean enCurso);
    //Factura findFirstByOrderByIdAsc();
}
