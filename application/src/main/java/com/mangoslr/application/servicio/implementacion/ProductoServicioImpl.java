package com.mangoslr.application.servicio.implementacion;

import com.mangoslr.application.Application;
import com.mangoslr.application.model.Producto;
import com.mangoslr.application.model.Usuario;
import com.mangoslr.application.repositorios.ProductoRepo;
import com.mangoslr.application.servicio.ProductoServicio;
import com.mangoslr.application.servicio.UsuarioControlServicio;
import com.mangoslr.application.servicio.UsuarioServicio;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.mangoslr.application.servicio.AuthServicio;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServicioImpl implements ProductoServicio {
    @Autowired
    UsuarioControlServicio usuarioServicio;

    @Autowired
    ProductoRepo productoRepo;

    @Autowired
    AuthServicio authServicio;

    //            0b      D          C         B        A
    // bit working: [disponible][vendedor][empleado][cliente]
    private final static Integer maskA = 1 << 0;
    private final static Integer maskB = 1 << 1;
    private final static Integer maskC = 1 << 2;
    private final static Integer maskD = 1 << 3;

    private final static String target = "img_productos/";

    @Override
    public void guardar(Producto producto) {
        productoRepo.save(producto);
    }

    @Override
    public Iterable<Producto> getMasVendidos(Pageable page) {
        List<Producto> productoList = new ArrayList<Producto>();
        for (Integer i:
                productoRepo.masIdVendidos(page)) {
            productoList.add(productoRepo.findByIdProducto(i));
        };
        return productoList;
    }

    @Override
    public boolean eliminar(int id) {
        try {
            productoRepo.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Producto getProducto(int id) {
        Optional<Producto> resultado = productoRepo.findById(id);
        if (resultado.isPresent())
            return resultado.get();
        return null;
    }

    private Iterable<Producto> filtrarProductos(Iterable<Producto> filtrarProductos) {
        Boolean general = true;
        Usuario usuario = authServicio.getUserAuthenticated();
        ArrayList<Producto> todos = (ArrayList<Producto>) filtrarProductos;
        ArrayList<Producto> filtered = new ArrayList<Producto>();

        if (usuario != null) {
            // Si no es un cliente
            if (usuario.getTipoUsuario().compareTo("Cliente") != 0) {
                general = false; // Enviarle una lista que es para empleados, vendedores o administrador (no es general).

                if (usuario.getTipoUsuario().compareTo("Administrador") == 0) {
                    return todos;
                } else if (usuario.getTipoUsuario().compareTo("Empleado") == 0) {
                    for (Producto p:
                            todos) {
                        // Añadir productos encontrados en empleado o en cliente, y que este disponible.
                        if (((p.getAutorizacion() & maskB) == maskB || (p.getAutorizacion() & maskA) == maskA) &&
                                ((p.getAutorizacion()) & maskD) == maskD) {
                            filtered.add(p);
                        }
                    }
                    return filtered;
                } else if (usuario.getTipoUsuario().compareTo("Vendedor") == 0) {
                    for (Producto p:
                            todos) {
                        /// Añadir productos encontrados en vendedor o en cliente, y que este disponible.
                        if (((p.getAutorizacion() & maskC) == maskC || (p.getAutorizacion() & maskA) == maskA) &&
                                ((p.getAutorizacion()) & maskD) == maskD) {
                            filtered.add(p);
                        }
                    }
                    return filtered;
                }

            }
        }

        if (general) {
            for (Producto p:
                    todos) {
                /// Añadir productos encontrados en cliente y que este disponible.
                if (((p.getAutorizacion() & maskA) == maskA) && ((p.getAutorizacion()) & maskD) == maskD) {
                    filtered.add(p);
                }
            }
            return filtered;
        }

        return null;
    }

    @Override
    public Iterable<Producto> getProductos() {
        return filtrarProductos(productoRepo.findAll());
    }

    @Override
    public Iterable<Producto> getProductos(String id, String nombre, Pageable page) {
        return filtrarProductos(productoRepo.findAllByIdProductoStartsWithAndNombreContainingIgnoreCase(String.valueOf(id), nombre, page));
    }

    @Override
    public Iterable<Producto> getProductos(String nombre, Pageable pageable) {
        return filtrarProductos(productoRepo.findAllByNombreContaining(nombre, pageable));
    }

    @Override
    public long contar() {
        return ((ArrayList<Producto>) getProductos()).size();
    }

    @Override
    public long contar(String id, String nombre) {
        return ((ArrayList<Producto>) filtrarProductos(productoRepo
                .findAllByIdProductoStartsWithAndNombreContainingIgnoreCase(String.valueOf(id), nombre))).size();
    }

    @Override
    public long contar(String nombre) {
        return ((ArrayList<Producto>) filtrarProductos(productoRepo.findAllByNombreContaining(nombre))).size();
    }

    @Override
    public Producto registrar(String name,
                              String desc,
                              String descCorta,
                              int cant,
                              int cantmin,
                              float precio,
                              MultipartFile multipartFile) throws IOException {
        Producto producto = new Producto(name, desc, descCorta, cant, precio, cantmin, null);
        productoRepo.save(producto);
        //chequea si hay una imagen
        if (!multipartFile.isEmpty()) {
            String fileName = String.format("%s.jpg", producto.getIdProducto());
            producto.setImageUrl("/sub/p/producto/img/" + fileName);
            Path uploadPath = Paths.get(target);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                productoRepo.save(producto);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
        }
        LoggerFactory.getLogger(ProductoServicio.class).info("Se registro productro: " + producto.toString());
        return producto;
    }

    @Override
    public boolean modificar(int id, boolean imgChange, String name, String desc, String descCorta, int cant, int cantmin, float precio, MultipartFile multipartFile) throws IOException {
        Producto producto = getProducto(id);
        if (producto == null)
            return false;
        producto.setNombre(name);
        producto.setDescripcion(desc);
        producto.setDescripcion_corta(descCorta);
        producto.setCantidad(cant);
        producto.setCantidad_min(cantmin);
        producto.setCosto(precio);
        if (imgChange) {
            if (!multipartFile.isEmpty()) {
                String fileName = String.format("%s.jpg", producto.getIdProducto());
                Path uploadPath = Paths.get(target);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                try (InputStream inputStream = multipartFile.getInputStream()) {
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                    producto.setImageUrl("/sub/p/producto/img/" + fileName);
                } catch (IOException ioe) {
                    throw new IOException("Could not save image file: " + fileName, ioe);
                }
            } else if (producto.getImageUrl() != null) {
                File file = new File(String.format("%s%s.jpg", target, producto.getIdProducto().toString()));
                file.delete();
                producto.setImageUrl(null);
            }
        }
        productoRepo.save(producto);
        return true;
    }
}
