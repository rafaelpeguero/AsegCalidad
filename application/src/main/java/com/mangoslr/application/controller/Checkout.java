package com.mangoslr.application.controller;

import com.mangoslr.application.servicio.CarritoServicio;
import com.mangoslr.application.servicio.WebCheckoutServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v/checkout")
public class Checkout {
    @Autowired
    WebCheckoutServicio webCheckoutServicio;

    @Autowired
    CarritoServicio carritoServicio;

    @RequestMapping
    public String checkout(Model model) {
        webCheckoutServicio.setReview(carritoServicio);
        if(webCheckoutServicio.getProductos().isEmpty())
            return "redirect:/v/carrito";
        return "redirect:/v/checkout/review";
    }

    @RequestMapping("/review")
    public String review(Model model) {
        model.addAttribute("titulo", "Checkout - Revisión");
        model.addAttribute("destino", "dashboard/tienda/checkout");
        model.addAttribute("carrito", webCheckoutServicio.getProductos());
        model.addAttribute("total", webCheckoutServicio.getTotal());
        return "dashboard/tienda/void";
    }
}
