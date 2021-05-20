package com.mangoslr.application.controller.api;

import com.mangoslr.application.model.Detalle;
import com.mangoslr.application.model.Factura;
import com.mangoslr.application.model.Producto;
import com.mangoslr.application.model.Usuario;
import com.mangoslr.application.repositorios.UsuarioRepo;
import com.mangoslr.application.servicio.FacturacionServicio;
import com.mangoslr.application.servicio.ProductoServicio;
import com.mangoslr.application.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/facturacion")
public class ApiFacturacion {
    @Autowired
    UsuarioRepo usuarioRepo;

    @Autowired
    FacturacionServicio facturacionServicio;

    @Autowired
    ProductoServicio productoServicio;

    @PostMapping(value = "/registrar", produces = MediaType.APPLICATION_JSON_VALUE)
    public Pair<Boolean, String> registrar(@RequestBody Map<String, Object> datos) {
        Factura factura = new Factura();
        Usuario usuario = null;
        switch ((String) datos.get("tipo")) {
            case "Fiscal":
                factura.setRnc((String) datos.get("RNC"));
                break;
            case "Usuario":
                usuario = usuarioRepo.findByNombreUsuario((String) datos.get("usuario"));
                if (usuario == null)
                    return Pair.of(false, "Usuario inválido.");
                factura.setUsuario(usuario);
                break;
        }
//        Aquí va la parte de la tarjet TODO: Tarjeta en facturacion
        List<Detalle> detalles = new ArrayList<>();
        List<Map<String, Object>> requestDetalles = (List) datos.get("detalles");
        Detalle detalle;
        Producto producto;
        if (requestDetalles.size() > 0) {
            for (Map<String, Object> requestDetalle :
                    requestDetalles) {
                detalle = new Detalle();
                detalle.setFactura(factura);
                producto = productoServicio.getProducto((Integer) requestDetalle.get("idProducto"));
                if (producto == null)
                    return Pair.of(false, "Producto inválido.");
                detalle.setPrecioUnidad(Float.valueOf(((Double) requestDetalle.get("precio")).toString()));
                detalle.setCantidad((Integer) requestDetalle.get("cantidad"));
                detalle.setTotal(Float.valueOf(((Double) requestDetalle.get("monto")).toString()));
                detalle.setProducto(producto);
                detalles.add(detalle);
            }
        } else {
            return Pair.of(false, "No se pueden crear facturas vacías.");
        }
        factura.setDetalles(detalles);
        factura.setTotal(Float.valueOf(((Double) datos.get("total")).toString()));
        factura.setEnCurso(false);
        facturacionServicio.registrar(factura);
        return Pair.of(true, factura.getIdFactura().toString());
    }
}
