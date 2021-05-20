package com.mangoslr.application.controller.api;

import com.mangoslr.application.aspecto.NecesitaReCaptcha;
import com.mangoslr.application.model.Producto;
import com.mangoslr.application.repositorios.ProductoRepo;
import com.mangoslr.application.repositorios.UsuarioRepo;
import com.mangoslr.application.servicio.AuthServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Map;

@RestController
@RequestMapping("/api/autorizacion")
public class ApiAutorizacion {
    @Autowired
    private ProductoRepo productoRepo;
    @Autowired
    AuthServicio authServicio;

    @PostMapping(value = "/autorizar",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Boolean autorizar(@RequestBody Map<String, String> datos) {
        Boolean result = true;
        Producto producto;
        int counter = 0;
        for (Map.Entry<String,String> entry : datos.entrySet()) {
            producto = productoRepo.findByIdProducto(Integer.parseInt(entry.getKey()));
            if (producto != null) {
                producto.setAutorizacion(Integer.parseInt(entry.getValue()));
                productoRepo.save(producto);
                counter++;
            } else {
                result = false;
            }
        }
        authServicio.crearNotificacion("Autorizaciones", "Se ha cambiado la autorización de : " + counter + " productos.");
        return result;
    }

}
