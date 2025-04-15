package com.example.recyclerviewclase2425;

import java.io.Serializable;

public class Pais implements Serializable {
    private String nombre;
    private String url;
    private String idioma;

    public Pais(String nombre, String url, String idioma) {
        this.nombre = nombre;
        this.url = url;
        this.idioma = idioma;
    }

    public Pais(String nombre, String url) {
        this.nombre = nombre;
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
}
