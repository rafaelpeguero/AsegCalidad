package com.mangoslr.application.controller.api;

import com.mangoslr.application.model.Producto;
import com.mangoslr.application.servicio.CarritoServicio;
import com.mangoslr.application.servicio.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/tienda")
public class ApiTienda {

    @Autowired
    CarritoServicio carritoServicio;

    @Autowired
    ProductoServicio productoServicio;

    @PostMapping(value = "/carrito/agregar", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean agregarAlCarrito(@RequestParam("id") int id, @RequestParam("cantidad") int cantidad) {
        Producto producto = productoServicio.getProducto(id);
        if(producto!=null) {
            return carritoServicio.agregar(producto, cantidad);
        }
        return false;
    }

    @PostMapping(value = "/carrito/asignar", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean asignarAlCarrito(@RequestParam("id") int id, @RequestParam("cantidad") int cantidad) {
        Producto producto = productoServicio.getProducto(id);
        if(producto!=null) {
            return carritoServicio.set(producto, cantidad);
        }
        return false;
    }

    @PostMapping(value = "/carrito/eliminar", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean eliminarAlCarrito(@RequestParam("id") int id) {
        Producto producto = productoServicio.getProducto(id);
        if(producto!=null)
            return carritoServicio.eliminar(producto);
        return false;
    }

    @PostMapping(value = "/carrito", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Producto, Integer> carrito() {
        return carritoServicio.getProductos();
    }

    @PostMapping(value = "/carrito/limpiar", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean limpiar() {
        return carritoServicio.limpiar();
    }
}
