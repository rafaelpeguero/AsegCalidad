package com.mangoslr.application.servicio;

import com.mangoslr.application.model.Factura;
import com.mangoslr.application.model.Producto;
import com.mangoslr.application.model.Usuario;
import com.mangoslr.application.repositorios.FacturaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class FacturacionServicio {
    @Autowired
    FacturaRepo facturaRepo;

    public Factura registrar(Factura factura) {
        facturaRepo.save(factura);
        return null;
    }
}
