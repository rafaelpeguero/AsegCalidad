package com.mangoslr.application.controller;

import com.mangoslr.application.model.Producto;
import com.mangoslr.application.servicio.CarritoServicio;
import com.mangoslr.application.servicio.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

//esta sera la vista del cliente
@Controller
@RequestMapping("/v")//Request mapping inspirado por amazon
public class Tienda {

    @Autowired
    ProductoServicio productoServicio;

    @Autowired
    CarritoServicio carritoServicio;


    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("titulo", "Tienda");
        model.addAttribute("destino", "dashboard/tienda/index");
        model.addAttribute("tienda", true);
        model.addAttribute("productos", productoServicio.getMasVendidos(PageRequest.of(0,4)));
        return "dashboard/main";
    }

    @RequestMapping("/carrito")
    public String carrito(Model model, Authentication authentication, HttpServletRequest httpServletRequest) {
        model.addAttribute("titulo", "Tienda - Carrito de Compras");
        model.addAttribute("destino", "dashboard/tienda/carrito");
        model.addAttribute("tienda", true);
        model.addAttribute("carrito", carritoServicio.getProductos());
        model.addAttribute("total", carritoServicio.getTotal());
        return "dashboard/main";
    }

    @PostMapping("/carrito/agregar")
    public String carrito(Model model, @RequestParam("id") int id, @RequestParam("cantidad") int cantidad) {
        Producto producto = productoServicio.getProducto(id);
        if (producto!=null) {
            carritoServicio.set(producto, cantidad);
        }
        return "redirect:/v/carrito";
    }

    @RequestMapping("/catalogo")
    public String catalog(Model model,
                          @RequestParam(name = "p", defaultValue = "1") int pagina,
                          @RequestParam(name = "nombre", required = false) String nombre) {
        int perPage = 12;
        int maxPage = Integer.max((int) (productoServicio.contar(nombre != null ? nombre : "") / perPage), 1);
        ArrayList<String> params = new ArrayList<>();
        if (nombre != null) {
            params.add("nombre=" + nombre);
        }
        model.addAttribute("titulo", "Tienda - Catálogo");
        model.addAttribute("destino", "dashboard/tienda/catalog");
        model.addAttribute("tienda", true);
        model.addAttribute("nombre", nombre);
        model.addAttribute("cpage", pagina);
        model.addAttribute("maxPage", maxPage);
        model.addAttribute("pages", new int[]{Math.max(pagina - 2, 1), Math.min(pagina + 2, maxPage)});
        model.addAttribute("getParam", String.join("&", params));
        model.addAttribute("productos", productoServicio.getProductos(nombre != null ? nombre : "", PageRequest.of(pagina - 1, perPage)));
        return "dashboard/main";
    }

    @RequestMapping("/producto/{idProducto}/")
    public String ver(Model model, @PathVariable("idProducto") int idProducto) {
        Producto producto = productoServicio.getProducto(idProducto);
        if (producto == null)
            return "404";
        model.addAttribute("titulo", "Tienda - " + producto.getNombre());
        model.addAttribute("destino", "dashboard/tienda/ver_producto");
        model.addAttribute("tienda", true);
        model.addAttribute("producto", producto);
        return "dashboard/main";
    }
}
