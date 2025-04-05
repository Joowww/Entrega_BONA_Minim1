package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Maleta {
    String id;
    Usuario usuario;
    Vuelo vuelo;

    public Maleta() {
    }

    public Maleta(Usuario usuario, Vuelo vuelo) {
        this.id = RandomUtils.getId();
        this.usuario = usuario;
        this.vuelo = vuelo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    @Override
    public String toString() {
        return "Maleta [id=" + id + ", usuario=" + usuario + "]";
    }

}
