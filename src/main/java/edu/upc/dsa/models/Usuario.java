package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String id;
    private String nombre;

    public Usuario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + "]";
    }

}
