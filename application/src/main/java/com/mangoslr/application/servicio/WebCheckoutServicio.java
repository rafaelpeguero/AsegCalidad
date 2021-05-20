package com.mangoslr.application.servicio;

import com.mangoslr.application.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class WebCheckoutServicio {
    private Map<Producto, Integer> productos = new HashMap<>();
    private double total;

    public void setReview(CarritoServicio carritoServicio) {
        productos.putAll(carritoServicio.checkout());
        total = carritoServicio.getTotal();
    }

    public Map<Producto, Integer> getProductos() {
        return productos;
    }

    public double getTotal() {
        return total;
    }

}
