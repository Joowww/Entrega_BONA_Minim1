package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Maleta {
    private String id;
    private Usuario usuario;

    public Maleta() {
        this.id = RandomUtils.getId();
    }

    public Maleta(Usuario usuario) {
        this();
        this.usuario = usuario;
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

    @Override
    public String toString() {
        return "Maleta [id=" + id + ", usuario=" + usuario + "]";
    }

}
