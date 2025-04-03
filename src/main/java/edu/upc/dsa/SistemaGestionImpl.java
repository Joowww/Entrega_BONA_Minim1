package edu.upc.dsa;

import edu.upc.dsa.models.Vuelo;
import edu.upc.dsa.models.Avion;
import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.Maleta;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

public class SistemaGestionImpl implements SistemaGestion {
    private static SistemaGestionImpl instance;
    private List<Avion> aviones;
    private List<Vuelo> vuelos;
    private List<Usuario> usuarios;
    final static Logger logger = Logger.getLogger(SistemaGestionImpl.class);

    private SistemaGestionImpl() {
        this.aviones = new ArrayList<>();
        this.vuelos = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public static SistemaGestion getInstance() {
        if (instance==null) instance = new SistemaGestionImpl();
        return instance;
    }

    public void addAvion(String id, String modelo, String compania) {
        logger.info("addAvion: id=" + id + ", modelo=" + modelo + ", compania=" + compania);
        for (Avion avion : aviones) {
            if (avion.getId().equals(id)) {
                avion.setModelo(modelo);
                avion.setCompania(compania);
                logger.info("Avion updated: " + avion);
                return;
            }
        }
        Avion avion = new Avion(id, modelo, compania);
        aviones.add(avion);
        logger.info("Avion added: " + avion);
    }


    public void addVuelo(String id, Date horaSalida, Date horaLlegada, String avionId, String origen, String destino) {
        logger.info("addVuelo: id=" + id + ", horaSalida=" + horaSalida + ", horaLlegada=" + horaLlegada + ", avionId=" + avionId + ", origen=" + origen + ", destino=" + destino);
        try {
            Avion avion = null;
            for (Avion a : aviones) {
                if (a.getId().equals(avionId)) {
                    avion = a;
                    break;
                }
            }
            if (avion == null) {
                logger.error("Avion no existe: " + avionId);
                throw new AvionNoExisteException("Avion no existe");
            }
            for (Vuelo vuelo : vuelos) {
                if (vuelo.getId().equals(id)) {
                    vuelo.setHoraSalida(horaSalida);
                    vuelo.setHoraLlegada(horaLlegada);
                    vuelo.setAvion(avion);
                    vuelo.setOrigen(origen);
                    vuelo.setDestino(destino);
                    logger.info("Vuelo updated: " + vuelo);
                    return;
                }
            }
            Vuelo vuelo = new Vuelo(id, horaSalida, horaLlegada, avion, origen, destino);
            vuelos.add(vuelo);
            logger.info("Vuelo added: " + vuelo);
        } catch (AvionNoExisteException ex) {
            logger.error("Error adding vuelo: " + ex.getMessage());
        }
    }


    public void facturarMaleta(String vueloId, Usuario usuario) {
        logger.info("facturarMaleta: vueloId=" + vueloId + ", usuario=" + usuario);
        try {
            boolean usuarioExiste = false;
            for (Usuario u : usuarios) {
                if (u.getId().equals(usuario.getId())) {
                    usuarioExiste = true;
                    break;
                }
            }
            if (!usuarioExiste) {
                logger.error("Usuario no existe: " + usuario.getId());
                throw new UsuarioNoExisteException("Usuario no existe");
            }

            Vuelo vuelo = null;
            for (Vuelo v : vuelos) {
                if (v.getId().equals(vueloId)) {
                    vuelo = v;
                    break;
                }
            }
            if (vuelo == null) {
                logger.error("Vuelo no existe: " + vueloId);
                throw new VueloNoExisteException("Vuelo no existe");
            }

            Maleta maleta = new Maleta(usuario);
            vuelo.addMaleta(maleta);
            logger.info("Maleta added: " + maleta);
        } catch (VueloNoExisteException | UsuarioNoExisteException ex) {
            logger.error("Error facturando maleta: " + ex.getMessage());
        }
    }

    public List<Maleta> getMaletasFacturadas(String vueloId) {
        logger.info("getMaletasFacturadas: vueloId=" + vueloId);
        try {
            Vuelo vuelo = null;
            for (Vuelo v : vuelos) {
                if (v.getId().equals(vueloId)) {
                    vuelo = v;
                    break;
                }
            }
            if (vuelo == null) {
                logger.error("Vuelo no existe: " + vueloId);
                throw new VueloNoExisteException("Vuelo no existe");
            }
            List<Maleta> maletas = new ArrayList<>(vuelo.getMaletas());
            Collections.reverse(maletas);
            logger.info("Maletas found: " + maletas);
            return maletas;
        } catch (VueloNoExisteException ex) {
            logger.error("Error getting maletas facturadas: " + ex.getMessage());
            return null;
        }
    }

    public Avion getAvion(String id) {
        for (Avion avion : aviones) {
            if (avion.getId().equals(id)) {
                return avion;
            }
        }
        return null;
    }

    public Vuelo getVuelo(String id) {
        for (Vuelo vuelo : vuelos) {
            if (vuelo.getId().equals(id)) {
                return vuelo;
            }
        }
        return null;
    }

    public void addUsuario(Usuario usuario) {
        if (usuario != null && !usuarios.contains(usuario)) {
            usuarios.add(usuario);
            logger.info("Usuario added: " + usuario);
        }
//        this.usuarios.add(usuario);
//        logger.info("Usuario added: " + usuario);
    }

    public void clear() {
        aviones.clear();
        vuelos.clear();
        usuarios.clear();
        logger.info("Sistema gestion cleared");
    }

    @Override
    public int sizeUsers() {
        return usuarios.size();
    }

    public Usuario getUsuario(String u1) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(u1)) {
                return usuario;
            }
        }
        return null;
    }
}