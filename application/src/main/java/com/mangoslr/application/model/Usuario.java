package com.mangoslr.application.model;

// Importar clases importantes...

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mangoslr.application.repositorios.NotificacionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

// Usuario = semi-completo.
/*
 * Modificar en el modelo:
 *   1. Verificar los atributos faltantes al modelo de clases. (Reparar esta parte).
 *   2. Luego de crear la clase controladora, agregar la parte de comprobar nombre de usuario en changeNombreUsuario.
 * */

@Entity
@Table(name = "Usuario")
public class Usuario implements UserDetails {
    private static String ROLE_PREFIX = "ROLE_";

    //region Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idUsuario", unique = true, nullable = false)
    private Integer idUsuario;

    @Column(length = 20, nullable = false)
    private String tipoUsuario;

    @Column(name="nombreUsuario", length = 20, unique = true, nullable = false)
    private String nombreUsuario;


    @Column(length = 20, nullable = false)
    private String clave;

    @Column(length = 150, nullable = false)
    private String correo;

    @Column(length = 256)
    private String imageUrl;

    // Foreign Keys
    @JsonIgnoreProperties({"usuario"})
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPersona", referencedColumnName = "idPersona")
    private Persona persona;

    @JsonIgnoreProperties({"usuarios"})
    @ManyToOne
    @JoinColumn(name = "idFarmacia")
    private Farmacia farmacia;

    @JsonIgnoreProperties({"usuario"})
    @OneToMany(mappedBy = "usuario")
    private List<Factura> facturas;

    @JsonIgnoreProperties({"usuario"})
    @OneToMany(mappedBy = "usuario")
    private List<Notificacion> notificaciones;
    //endregion

    //region carrito
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "carrito")
    @MapKeyJoinColumn(name = "idProducto")
    @Column(name = "cantidad")
    private Map<Producto, Integer> carrito;

    public Map<Producto, Integer> getCarrito() {
        return carrito;
    }

    public void setCarrito(Map<Producto, Integer> carrito) {
        this.carrito = carrito;
    }

    //endregion

    //region interface
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + tipoUsuario.toUpperCase(Locale.ROOT)));
        return list;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return "{noop}" + this.clave;
    }

    @Override
    public String getUsername() {
        return this.nombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    //endregion

    //region Constructores
    public Usuario() {
        super();
    }

    /*
        public Usuario(String nombreUsuario, String clave, Farmacia farmacia, Persona persona) {
            super();
            this.nombreUsuario = nombreUsuario;
            this.clave = clave;
            this.farmacia = farmacia;
            this.persona = persona;
        }

        public Usuario(String nombreUsuario, String correo, String clave, Farmacia farmacia, Persona persona) {
            super();
            this.nombreUsuario = nombreUsuario;
            this.correo = correo;
            this.clave = clave;
            this.farmacia = farmacia;
            this.persona = persona;
        }
    */
    public Usuario(String nombreUsuario, String correo, String clave, String tipoUsuario, Farmacia farmacia, Persona persona) {
        super();
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.clave = clave;
        this.tipoUsuario = tipoUsuario;
        this.farmacia = farmacia;
        this.persona = persona;
    }
    //endregion

    //region Getters and Setters

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    //region Usuario y contraseña
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getClave(String usuario) {
        if (usuario == nombreUsuario) {
            return clave;
        } else
            return null;
    }

    public Boolean changeClave(String claveAnterior, String nuevaClave) {
        // Asegurarse que se provea la clave anterior
        if (claveAnterior.compareTo(clave) == 0) {
            // Asegurarse que la clave nueva tenga 8 o mas caracteres
            if (nuevaClave.length() >= 8) {
                clave = nuevaClave;
                return true;
            }
        }
        // En caso de que no cumpla con ninguno de los anteriores.
        return false;
    }

    public Boolean changeNombreUsuario(String claveUsuario, String nuevoNombre) {
        // Asegurarse que se provea la clave anterior
        if (claveUsuario == clave) {
            if (nuevoNombre.length() >= 8) {
                // Luego comprobar que el usuario no este tomado...
                nombreUsuario = nuevoNombre;
            }
        }
        return false;
    }
    //endregion

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Farmacia getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public List<Notificacion> getNotificaciones(int nuevo) {
        if (nuevo == 0) { // Si es 0, retorna todas las notificaciones
            return notificaciones;
        } else if (nuevo == 1) { // si es 1, retorna solo las nuevas
            ArrayList<Notificacion> enviar = new ArrayList<Notificacion>();
            for (Notificacion elemento:
                    notificaciones) {
                if (!elemento.getVisto()) { // Si no ha sido visto (!visto)
                    enviar.add(elemento);
                }
            }
            return enviar;
        } else { // si no es ninguna de las anteriores, enviar todo lo viejo.
            ArrayList<Notificacion> enviar = new ArrayList<Notificacion>();
            for (Notificacion elemento:
                    notificaciones) {
                if (elemento.getVisto()) { // Si ha sido visto (!visto)
                    enviar.add(elemento);
                }
            }
            return enviar;
        }
    }

    public void setNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    //endregion
}
