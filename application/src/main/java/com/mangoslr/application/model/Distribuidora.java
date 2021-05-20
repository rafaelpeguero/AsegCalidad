package com.mangoslr.application.model;

import java.util.List;

public class Distribuidora {
    // Clase controladora
    //region Listas de Objetos
    List<Factura> facturas;
    List<Detalle> detalles;
    List<Farmacia> farmacias;
    List<Persona> personas;
    List<Producto> productos;
    List<Usuario> usuarios;
    //endregion

    //region Singleton
    private String connectionBd;
    private static Distribuidora distribuidora = null;

    public static Distribuidora getDistribuidora() {
        if (distribuidora == null) {
            distribuidora = new Distribuidora();
        }
        return distribuidora;
    }
    //endregion

    //region Constructor
    private Distribuidora() {
        super();
        // Metodo para cargar la información desde la base de datos...
        takeInformation();
    }
    //endregion

    //region Métodos.
    // cargar desde la base de datos. . .
    private void takeInformation() {
        // Desde aquí tomar la información de la bd... (Preliminar, no es el producto final)...
        // Solo una idea.
        return;
    }

    //endregion

    //region Métodos para tomar información de las listas y tomar las listas.

    public List<Factura> getFacturas() {
        return facturas;
    }

    public List<Farmacia> getFarmacias() {
        return farmacias;
    }

    public List<Persona> getPersonas() {
        return personas;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    //endregion
}
