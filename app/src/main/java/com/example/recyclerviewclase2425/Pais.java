package com.example.recyclerviewclase2425;

import java.io.Serializable;

public class Pais implements Serializable {
    private String nombre;
    private String urlBandera;

    public Pais(String nombre, String urlBandera) {
        this.nombre = nombre;
        this.urlBandera = urlBandera;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrlBandera() {
        return urlBandera;
    }
}
