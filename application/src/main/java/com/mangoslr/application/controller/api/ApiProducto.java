package com.mangoslr.application.controller.api;

import com.mangoslr.application.model.Producto;
import com.mangoslr.application.servicio.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/producto")
public class ApiProducto {

    @Autowired
    ProductoServicio productoServicio;

    @PostMapping(value = "/registrar", produces = MediaType.APPLICATION_JSON_VALUE)
    public Producto registrar(@RequestParam("nombre") String name,
                             @RequestParam("desc") String desc,
                             @RequestParam("descripCorta") String descCorta,
                             @RequestParam("cantidad") int cant,
                             @RequestParam("cantmin") int cantmin,
                             @RequestParam("precio") float precio,
                             @RequestParam("image") MultipartFile multipartFile) throws IOException {

        return productoServicio.registrar(name, desc, descCorta, cant, cantmin, precio, multipartFile);
    }

    @PostMapping(value = "/contar", produces = MediaType.APPLICATION_JSON_VALUE)
    public long contar(@RequestParam("id") String id, @RequestParam("nombre") String name) {
        return productoServicio.contar(id, name);
    }

    @PostMapping(value = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Producto> fetch(@RequestParam("id") String id, @RequestParam("nombre") String name, @RequestParam("pagina") int pagina, @RequestParam("mostrar") int mostrar) {
        List<Producto> productoList = (List<Producto>) productoServicio.getProductos(id, name, PageRequest.of(pagina, mostrar));
        return productoList;
    }

}
