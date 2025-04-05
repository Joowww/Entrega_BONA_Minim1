package edu.upc.dsa.models;

public class Avion {
    String id;
    String modelo;
    String compania;

    public Avion() {
    }

    public Avion(String id, String modelo, String compania) {
        this. id = id;
        this.modelo = modelo;
        this.compania = compania;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    @Override
    public String toString() {
        return "Avion [id=" + id + ", modelo=" + modelo + ", compania=" + compania + "]";
    }
}
