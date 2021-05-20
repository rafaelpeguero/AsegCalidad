package com.mangoslr.application.servicio;

import com.mangoslr.application.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

public interface ProductoServicio {
    public abstract void guardar(Producto producto);

    public abstract Iterable<Producto> getMasVendidos(Pageable page);

    public abstract boolean eliminar(int id);

    public abstract Producto getProducto(int id);

    public abstract Iterable<Producto> getProductos();

    public abstract Iterable<Producto> getProductos(String id, String nombre, Pageable page);

    public abstract Iterable<Producto> getProductos(String nombre, Pageable pageable);

    public abstract long contar();

    public abstract long contar(String id, String nombre);

    public abstract long contar(String nombre);

    public abstract Producto registrar(String name,
                                       String desc,
                                       String descCorta,
                                       int cant,
                                       int cantmin,
                                       float precio,
                                       MultipartFile multipartFile) throws IOException;

    public abstract boolean modificar(int id,
                                      boolean imgChange,
                                      String name,
                                      String desc,
                                      String descCorta,
                                      int cant,
                                      int cantmin,
                                      float precio,
                                      MultipartFile multipartFile) throws IOException;
}
