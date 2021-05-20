package com.mangoslr.application.servicio;

import com.mangoslr.application.model.Producto;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface CarritoServicio {
    public abstract boolean agregar(Producto producto, int cantidad);
    public abstract boolean set(Producto producto, int cantidad);
    public abstract boolean eliminar(Producto producto);
    public abstract double getTotal();
    public abstract Map<Producto, Integer> getProductos();
    public abstract boolean limpiar();
    public abstract Map<Producto, Integer> checkout();
}
