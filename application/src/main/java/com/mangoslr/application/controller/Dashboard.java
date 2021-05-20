package com.mangoslr.application.controller;

import com.mangoslr.application.model.Factura;
import com.mangoslr.application.model.Pedido;
import com.mangoslr.application.model.Producto;
import com.mangoslr.application.model.Usuario;
import com.mangoslr.application.repositorios.FacturaRepo;
import com.mangoslr.application.servicio.AuthServicio;
import com.mangoslr.application.servicio.ProductoServicio;
import com.mangoslr.application.servicio.UsuarioControlServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard")
public class Dashboard {
    //region Constructor, Structures and Init
    HashMap<String, String> destinos;
    HashMap<String, String> nombreDestino;

    @Autowired
    AuthServicio authServicio;

    @PostConstruct
    public void init() {
        destinos = new HashMap<String, String>();
        nombreDestino = new HashMap<String, String>();

        // Destinos segun la localización...
        destinos.put("autorizacion", "autorizacion");
        destinos.put("controldeproductos", "controldeproductos/control");
        destinos.put("controldeusuarios", "controldeusuarios/control");
        destinos.put("facturar", "facturar/facturar");
        destinos.put("historialdeventa", "historial_venta");
        destinos.put("perfil", "usuario");
        destinos.put("notificaciones", "notificaciones");
        destinos.put("pedidos", "pedidos");

        // Títulos (nombres de cada destino).
        nombreDestino.put("autorizacion", "Autorizaciones");
        nombreDestino.put("controldeproductos", "Inventario");
        nombreDestino.put("controldeusuarios", "Control de Usuarios");
        nombreDestino.put("facturar", "Facturación");
        nombreDestino.put("historialdeventa", "Historial");
        nombreDestino.put("perfil", "Perfil de usuario");
        nombreDestino.put("notificaciones", "Notificaciones");
        nombreDestino.put("pedidos", "Pedidos");
    }

    @RequestMapping
    public String dashboard(Model model) {
        model.addAttribute("titulo", "Dashboard");
        model.addAttribute("destino", "dashboard/dashboard");
        return "dashboard/main";
    }
    //endregion

    //region Productos
    @Autowired
    ProductoServicio productoServicio;

    @RequestMapping("/controldeproductos/agregar")
    public String control_productos_add(Model model) {
        model.addAttribute("titulo", "Control de Productos");
        model.addAttribute("destino", "dashboard/controldeproductos/agregar");
        model.addAttribute("agregar", true);
        return "dashboard/main";
    }

    @PostMapping(value = "/controldeproductos/agregar/proccess", produces = MediaType.APPLICATION_JSON_VALUE)
    public String registrarProducto(@RequestParam("nombre") String name,
                            @RequestParam("desc") String desc,
                            @RequestParam("descripCorta") String descCorta,
                            @RequestParam("cantidad") int cant,
                            @RequestParam("cantmin") int cantmin,
                            @RequestParam("precio") float precio,
                            @RequestParam("image") MultipartFile multipartFile) throws IOException {
        productoServicio.registrar(name, desc, descCorta, cant, cantmin, precio, multipartFile);
        return "redirect:/dashboard/controldeproductos";
    }

    @PostMapping(value = "/controldeproductos/modificar/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String modificarProducto(@PathVariable("id") int id,
                            @RequestParam(name="imgCambio", defaultValue = "false") boolean imgChange,
                            @RequestParam("nombre") String name,
                            @RequestParam("desc") String desc,
                            @RequestParam("descripCorta") String descCorta,
                            @RequestParam("cantidad") int cant,
                            @RequestParam("cantmin") int cantmin,
                            @RequestParam("precio") float precio,
                            @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if(!productoServicio.modificar(id, imgChange, name, desc, descCorta, cant, cantmin, precio, multipartFile))
            return "400";
        return "redirect:/dashboard/controldeproductos/ver/"+id+"/";
    }

    @GetMapping(value = "/controldeproductos/eliminar/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String eliminarProducto(@PathVariable("id") int id){
        if (!productoServicio.eliminar(id))
            return "redirect:/dashboard/controldeproductos/ver/{id}/?error=1";;
        return "redirect:/dashboard/controldeproductos";
    }


    @RequestMapping("/controldeproductos/ver/{id}/")
    public String control_productos_add(Model model, @PathVariable("id") int id) {
        Producto producto = productoServicio.getProducto(id);
        if (producto == null)
            return "404";
        model.addAttribute("titulo", "Control de Productos - " + String.valueOf(id));
        model.addAttribute("destino", "dashboard/controldeproductos/agregar");
        model.addAttribute("agregar", false);
        model.addAttribute("producto", producto);
        return "dashboard/main";
    }
    //endregion

    //region Usuario
    @Autowired
    UsuarioControlServicio usuarioServicio;

    @RequestMapping("/controldeusuarios/agregar")
    public String control_usuarios(Model model) {
        model.addAttribute("titulo", "Control de Usuarios");
        model.addAttribute("destino", "dashboard/controldeusuarios/agregar");
        model.addAttribute("agregar", true);
        model.addAttribute("tituloAgregar", "Añadir un nuevo usuario");
        return "dashboard/main";
    }

    @RequestMapping("/controldeusuarios/ver/{id}/")
    public String control_usuarios_add(Model model, @PathVariable("id") int id) {
        Usuario usuario = usuarioServicio.getUsuario(id);
        if (usuario == null)
            return "404";
        model.addAttribute("titulo", "Control de Usuarios - " + String.valueOf(id));
        model.addAttribute("destino", "dashboard/controldeusuarios/agregar");
        model.addAttribute("agregar", false);
        model.addAttribute("usuario", usuario);
        model.addAttribute("tituloAgregar", "Modificar usuario #" + id);
        //Object nuevo = model.getAttribute("usuario");
        return "dashboard/main";
    }

    /*@PostMapping(value = "/controldeusuarios/agregar/proccess", produces = MediaType.APPLICATION_JSON_VALUE)
    public String registrarUsuario(@RequestParam("name") String nombre,
                                   @RequestParam("lastname") String apellido,
                                   @RequestParam("dateofbirth") String fechaNacimiento,
                                   @RequestParam("address") String direccion,
                                   @RequestParam("gender") String sexo,
                                   @RequestParam("blood") String sangre,
                                   @RequestParam("phone") String telefono,
                                   @RequestParam("identity") String cedula,
                                   @RequestParam("username") String nombreUsuario,
                                   @RequestParam("password") String clave,
                                   @RequestParam("userType") String tipoUsuario,
                                   @RequestParam("email") String email) {
        usuarioServicio.registrar(nombre, apellido, fechaNacimiento, direccion, sexo, sangre,
                telefono, cedula, nombreUsuario, clave, tipoUsuario, email);
        return "redirect:/dashboard/controldeusuarios";
    }*/

    @PostMapping(value = "/dashboard/controldeusuarios/eliminar/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean eliminarUsuario(@PathVariable("id") int idUsuario) {
        return usuarioServicio.eliminar(idUsuario);
    }

    @PostMapping(value = "/controldeusuarios/modificar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String modificarUsuario(@PathVariable("id") int id,
                                   @RequestParam("name") String nombre,
                                   @RequestParam("lastname") String apellido,
                                   @RequestParam("dateofbirth") String fechaNacimiento,
                                   @RequestParam("address") String direccion,
                                   @RequestParam("gender") String sexo,
                                   @RequestParam("blood") String sangre,
                                   @RequestParam("phone") String telefono,
                                   @RequestParam("identity") String cedula,
                                   @RequestParam("username") String nombreUsuario,
                                   @RequestParam("password") String clave,
                                   @RequestParam("userType") String tipoUsuario,
                                   @RequestParam("email") String email) {
        if (!usuarioServicio.modificar(id, nombre, apellido, fechaNacimiento, direccion, sexo, sangre,
                telefono, cedula, nombreUsuario, clave, tipoUsuario, email))
            return "404";
        return "redirect:/dashboard/controldeusuarios";
    }
    //endregion

    //region Facturación
    @Autowired
    FacturaRepo facturaRepo;

    @RequestMapping("/facturar/ver/{id}")
    public String ver_factura(Model model,@PathVariable("id") int id) {
        Optional<Factura> hayFactura = facturaRepo.findById(id);
        if(!hayFactura.isPresent())
            return "access-denied";
        Factura factura = hayFactura.get();
        model.addAttribute("titulo", "Factura - "+id);
        model.addAttribute("destino", "dashboard/facturar/ver");
        model.addAttribute("factura", factura);
        return "dashboard/main";
    }

    @RequestMapping("/facturar/productos")
    public String facturar_seleccionar(Model model) {
        model.addAttribute("titulo", "Control de productos");
        model.addAttribute("destino", "dashboard/facturar/productos");
        return "dashboard/main";
    }

    // Para los pedidos
    @RequestMapping("/dashboard/historial/activar/{id}/")
    public String activarPedido(Model model, @PathVariable("id") int idFactura) {
        Factura factura = facturaRepo.findByIdFactura(idFactura);
        if (factura != null) {
            factura.setEnCurso(false);
            facturaRepo.save(factura);
        }
        return "redirect:/dashboard/pedidos";
    }
    //endregion

    //endregion

    //region Dashboard Menu Mapping
    @RequestMapping("/{destino}")
    public String parse(Model model, @PathVariable("destino") String destino) {
        String lugar = destinos.get(destino);
        String title = nombreDestino.get(destino);
        if (lugar == null || title == null) {
            return "404";
        }
        model.addAttribute("titulo", title);
        model.addAttribute("usuario", authServicio.getUserAuthenticated());
        model.addAttribute("destino", "dashboard/" + lugar);
        return "dashboard/main";
    }
    //endregion

    //region oldstuff

    // @RequestMapping("/controldeusuarios/agregar")
    // public String control_usuarios_add(Model model) {
    //     model.addAttribute("titulo", "Control de Usuarios");
    //     model.addAttribute("destino", "dashboard/controldeusuarios/agregar");
    //     return "dashboard/main";
    // }

    // @RequestMapping("/controldeusuarios/modificar")
    // public String control_usuarios_mod(Model model) {
    //     model.addAttribute("titulo", "Control de Usuarios");
    //     model.addAttribute("destino", "dashboard/controldeusuarios/modificar");
    //     return "dashboard/main";
    // }

     /*
    @RequestMapping("/{directorio}/{destino}")
    public String control_dashboard(Model model, @PathVariable("directorio") String directorio,
                                    @PathVariable("destino") String destino) {
        String lugar = destinos.get(directorio);
        String title = nombreDestino.get(directorio);
        if (lugar == null || title == null) {
            return "404";
        }
        // Enviar la variable con la direccion
        model.addAttribute("titulo", title);
        model.addAttribute("destino", "/"+lugar+"/"+destino);
        return "dashboard/main";
    }*/

    //control de productos
    /*@RequestMapping("/controldeproductos")
    public String controlproductos(Model model) {
        model.addAttribute("titulo", "Control de productos");
        model.addAttribute("destino", "dashboard/producto/control");
        return "dashboard/main";
    }*/

//    @RequestMapping("/autorizacion")
//    public String autorizacion(Model model) {
//        return "dashboard/autorizacion";
//    }

//    @RequestMapping("/controldeusuarios")
//    public String controlusuarios(Model model) {
//        return "dashboard/control_usuarios";
//    }

//    @RequestMapping("/facturar")
//    public String facturar(Model model) {
//        return "dashboard/facturar";
//    }

//    @RequestMapping("/historialdecompra")
//    public String historialcompra(Model model) {
//        return "dashboard/historial_compra";
//    }

//    @RequestMapping("/historialdeventa")
//    public String historialventa(Model model) {
//        return "dashboard/historial_venta";
//    }
//
//    @RequestMapping("/inventario")
//    public String inventario(Model model) {
//        return "dashboard/inventario";
//    }
//
//    @RequestMapping("/notificaciones")
//    public String notificacion(Model model) {
//        return "dashboard/notificaciones";
//    }
//
//    @RequestMapping("/perfil")
//    public String perfil(Model model) {
//        return "dashboard/usuario";
//    }
    //endregion
}
