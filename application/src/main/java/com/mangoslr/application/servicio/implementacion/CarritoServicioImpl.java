package com.mangoslr.application.servicio.implementacion;

import com.mangoslr.application.model.Producto;
import com.mangoslr.application.model.Usuario;
import com.mangoslr.application.repositorios.ProductoRepo;
import com.mangoslr.application.repositorios.UsuarioRepo;
import com.mangoslr.application.servicio.CarritoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class CarritoServicioImpl implements CarritoServicio {
    @Autowired
    ProductoRepo productoRepo;
    @Autowired
    UsuarioRepo usuarioRepo;

    private Map<Producto, Integer> productos = new HashMap<>();
    private Usuario usuario = null;


    @Override
    public boolean agregar(Producto producto, int cantidad) {
        if(cantidad<0) {
            return false;
        }
        if (getProductos().containsKey(producto))
            productos.put(producto, productos.get(producto) + cantidad);
        else
            productos.put(producto, cantidad);
        if (usuario != null)
            usuarioRepo.save(usuario);
        return true;
    }

    @Override
    public boolean set(Producto producto, int cantidad) {
        if(cantidad<1)
            getProductos().remove(producto);
        else
            getProductos().put(producto, cantidad);
        if (usuario != null)
            usuarioRepo.save(usuario);
        return true;
    }

    @Override
    public boolean eliminar(Producto producto) {
        boolean paso;
        if (paso = getProductos().containsKey(producto)) {
            productos.remove(producto);
            if (usuario != null)
                usuarioRepo.save(usuario);
        }
        return paso;
    }

    @Override
    public double getTotal() {
        double suma = 0;
        for (Map.Entry<Producto, Integer> detalle : productos.entrySet()
        ) {
            suma += detalle.getKey().getCosto() * detalle.getValue();
        }
        return suma;
    }

    @Override
    public Map<Producto, Integer> getProductos() {
        if (usuario == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                usuario = (Usuario) authentication.getPrincipal();
                if (usuario.getCarrito() == null)
                    usuario.setCarrito(new HashMap<>());
                if (!productos.isEmpty())
                    usuario.getCarrito().putAll(productos);
                productos = usuario.getCarrito();
                usuarioRepo.save(usuario);
            }
        }
        return productos;
    }

    @Override
    public boolean limpiar() {
        if (productos.isEmpty())
            return false;
        getProductos().clear();
        if (usuario != null)
            usuarioRepo.save(usuario);
        return true;
    }

    @Override
    public Map<Producto, Integer> checkout() {
        if(usuario==null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken))
                usuario = (Usuario) authentication.getPrincipal();
        }
        usuario.setCarrito(productos);
        usuarioRepo.save(usuario);
        return productos;
    }
}