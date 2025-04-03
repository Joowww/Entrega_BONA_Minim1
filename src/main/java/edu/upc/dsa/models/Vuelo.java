package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class Vuelo {
    private String id;
    private Date horaSalida;
    private Date horaLlegada;
    private Avion avion;
    private String origen;
    private String destino;
    private Queue<Maleta> maletas;

    public Vuelo() {
        this.id = RandomUtils.getId();
        this.maletas = new LinkedList<>();
    }

    public Vuelo(String id, Date horaSalida, Date horaLlegada, Avion avion, String origen, String destino) {
        this();
        if (id != null) this.id = id;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.avion = avion;
        this.origen = origen;
        this.destino = destino;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Date horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Date getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(Date horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Queue<Maleta> getMaletas() {
        return maletas;
    }

    public void addMaleta(Maleta maleta) {
        this.maletas.add(maleta);
    }

    @Override
    public String toString() {
        return "Vuelo [id=" + id + ", horaSalida=" + horaSalida + ", horaLlegada=" + horaLlegada + ", avion=" + avion + ", origen=" + origen + ", destino=" + destino + "]";
    }
}
