package com.mangoslr.application.controller.api;

import com.mangoslr.application.model.*;
import com.mangoslr.application.repositorios.*;
import com.mangoslr.application.servicio.UsuarioControlServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.mangoslr.application.servicio.AuthServicio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/historial")
public class apiHistorial {
    @Autowired
    private UsuarioRepo usuarioRepo;
    @Autowired
    private PersonaRepo personaRepo;
    @Autowired
    private FacturaRepo facturaRepo;
    @Autowired
    private ProductoRepo productoRepo;

    @Autowired
    private NotificacionRepo notificacionRepo;
    @Autowired
    UsuarioControlServicio usuarioServicio;

    @Autowired
    AuthServicio authServicio;

    //region Pedido
    // Retornar página en caso de necesitarla.
    private List<Pedido> pagedHistorial (String idUsuario, String nombreUsuario, Pageable pageable, Boolean enCurso) {
        List<Pedido> pedidos = historial(idUsuario, nombreUsuario, enCurso);
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), pedidos.size());
        Page<Pedido> page = new PageImpl<Pedido>(pedidos.subList(start, end), pageable, pedidos.size());
        return page.getContent();
    }

    private List<Pedido> historial(String idUsuario, String name, Boolean enCurso) {
        ArrayList<Pedido> result = new ArrayList<Pedido>();
        Usuario usuario = authServicio.getUserAuthenticated();
        if (usuario != null) {
            Pedido buscar;
            if (usuario.getTipoUsuario().compareTo("Administrador") == 0 ||
                    usuario.getTipoUsuario().compareTo("Empleado") == 0 ||
                    usuario.getTipoUsuario().compareTo("Vendedor") == 0) {
                // Al personal, se le entrega un historial completo de las ventas.
                for ( Usuario nuevo :
                        usuarioRepo.findAllByIdUsuarioStartsWithAndNombreUsuarioContainingIgnoreCase(idUsuario, name)) {
                    for (Factura nueva :
                            nuevo.getFacturas()) {
                        buscar = new Pedido(nuevo.getIdUsuario(),
                                nuevo.getNombreUsuario(),
                                nueva.getIdFactura(),
                                nueva.getFecha(),
                                nueva.getTotal(),
                                nueva.getEnCurso());
                        // Delimitar si esta en curso o no.
                        if (enCurso) {
                            if (buscar.getEnCurso() != null) {
                                if (buscar.getEnCurso())
                                    result.add(buscar);
                            }
                        } else
                            result.add(buscar);

                        buscar = null;
                    }
                }
            } else if (usuario.getTipoUsuario().compareTo("Cliente") == 0) {
                // Al usuario solo se le entregan las facturas con su nombre.
                // De todos modos el cliente solo puede ver su historial y no la parte de los "pedidos".
                for ( Factura facturaUsuario : usuario.getFacturas() ) {
                    buscar = new Pedido(usuario.getIdUsuario(),
                            usuario.getNombreUsuario(),
                            facturaUsuario.getIdFactura(),
                            facturaUsuario.getFecha(),
                            facturaUsuario.getTotal(),
                            facturaUsuario.getEnCurso());
                    result.add(buscar);
                }
            } else
                result = null;

        } else {
            return null;
        }
        return result;
    }

    @PostMapping(value = "/contar", produces = MediaType.APPLICATION_JSON_VALUE)
    public long contar(@RequestParam("id") String id, @RequestParam("nombre") String name) {
        // Se retorna la cantidad de productos en curso
        return historial(id, name,true).size();
    }

    @PostMapping(value = "/historialProductos", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Pedido> historialPedidos (@RequestParam("id") String id, @RequestParam("nombre") String name, @RequestParam("pagina") int pagina, @RequestParam("mostrar") int mostrar) {
        return pagedHistorial(id, name, PageRequest.of(pagina, mostrar), false);
    }

    @PostMapping(value = "/contarProductos", produces = MediaType.APPLICATION_JSON_VALUE)
    public long contarProductos(@RequestParam("id") String id, @RequestParam("nombre") String name) {
        // Se retorna la cantidad de productos en curso
        return historial(id, name,false).size();
    }

    @PostMapping(value = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Pedido> fetch(@RequestParam("id") String id, @RequestParam("nombre") String name, @RequestParam("pagina") int pagina, @RequestParam("mostrar") int mostrar) {
        // Páginas de los pedidos, retorna la lista en la pagina actual.
        return pagedHistorial(id, name, PageRequest.of(pagina, mostrar), true);
    }
    //endregion

    //region Notificaciones

    private List<Notificacion> pagedNotificaciones (Pageable pageable, int nuevas) {
        Usuario usuario = authServicio.getUserAuthenticated();
        if (usuario != null) {
            List<Notificacion> pedidos = usuario.getNotificaciones(nuevas);
            int start = (int)pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), pedidos.size());
            Page<Notificacion> page = new PageImpl<Notificacion>(pedidos.subList(start, end), pageable, pedidos.size());
            return page.getContent();
        } else
            return null;
    }

    @PostMapping(value = "/fetchNotificaciones", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Notificacion> fetchNotificaciones(@RequestParam("pagina") int pagina, @RequestParam("mostrar") int mostrar, @RequestParam("nuevas") int nuevas) {
        // Páginas de los pedidos, retorna la lista en la pagina actual.
        return pagedNotificaciones(PageRequest.of(pagina, mostrar), nuevas);
    }

    @PostMapping(value = "/contarNotificaciones", produces = MediaType.APPLICATION_JSON_VALUE)
    public long contarNotificaciones(@RequestParam("nuevas") int nuevas) {
        Usuario usuario = authServicio.getUserAuthenticated();

        if (usuario != null) {
            return usuario.getNotificaciones(nuevas).size();
        }
        return 0;
    }

    // Para marcar Notificaciones
    @PostMapping(value = "/marcarTodas", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean marcarNotificaciones(@RequestParam("id") String id, @RequestParam("nombre") String name) {
        Usuario usuario = authServicio.getUserAuthenticated();
        if (usuario != null) {
            for (Notificacion notificacion:
                 usuario.getNotificaciones(1)) {
                notificacion.setVisto(true);
                notificacionRepo.save(notificacion);
            }
            return true;
        }
        return false;
    }

    @PostMapping(value = "/dashboardData", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> dashboardData(@RequestParam("id") String id, @RequestParam("nombre") String name) {
        ArrayList<String> data = new ArrayList<>();

        Float total = 0.0f;
        Integer contar = 0;
        for (Factura factura :
             facturaRepo.findAll()) {
            if (!factura.getEnCurso()) {
                total += factura.getTotal();
            } else {
                contar++;
            }
        }
        data.add(total.toString());
        data.add(contar.toString());

        contar = 0;
        for (Producto producto :
             productoRepo.findAll()) {
            if (producto.getAutorizacion() > 7) {
                // Significa que esta disponible
                contar++;
            }
        }
        data.add(contar.toString());
        return data;
    }

    //endregion
}
