package edu.upc.dsa;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;
import java.util.*;
import org.apache.log4j.Logger;


public class SistemaGestionImpl implements SistemaGestion {
    private static SistemaGestionImpl instance;
    private Map<String, Avion> aviones;
    private Map<String, Vuelo> vuelos;
    private Map<String, Usuario> usuarios;
    List<Avion> AllAviones;
    List<Vuelo> AllVuelos;
    List<Usuario> AllUsuarios;
    final static Logger logger = Logger.getLogger(SistemaGestionImpl.class);

    private SistemaGestionImpl() {
        this.aviones = new HashMap<>();
        this.vuelos = new HashMap<>();
        this.usuarios = new HashMap<>();
        this.AllAviones = new ArrayList<>();
        this.AllVuelos = new ArrayList<>();
        this.AllUsuarios = new ArrayList<>();
    }

    public static SistemaGestion getInstance() {
        if (instance==null) instance = new SistemaGestionImpl();
        return instance;
    }

    public Avion addAvion(String id, String modelo, String compania) {
        logger.info("addAvion: id=" + id + ", modelo=" + modelo + ", compania=" + compania);
        try {
            if (aviones.containsKey(id)) {
                Avion avion = aviones.get(id);
                if (avion.getModelo().equals(modelo) && avion.getCompania().equals(compania)) {
                    logger.error("No se pueden usar los mismos valores para modelo y compañía en un ID existente");
                    throw new MismosParamConMismoIdException("El avión con ID " + id + " ya tiene esos valores.");
                }
                else {
                    avion.setModelo(modelo);
                    avion.setCompania(compania);
                    logger.info("Avion actualizado: " + avion);
                }

            }
            else {
                Avion avion = new Avion(id, modelo, compania);
                aviones.put(id, avion);
                logger.info("Avion añadido: " + avion);
            }
        }
        catch (MismosParamConMismoIdException ex) {
            logger.error("Excepción mismos parametros con mismo id: ", ex);
            return null;
        }
        return aviones.get(id);
    }


    public Vuelo addVuelo(String id, Date horaSalida, Date horaLlegada, String avionId, String origen, String destino) {
        logger.info("addVuelo: id=" + id + ", horaSalida=" + horaSalida + ", horaLlegada=" + horaLlegada + ", avionId=" + avionId + ", origen=" + origen + ", destino=" + destino);
        try {
            if (!aviones.containsKey(avionId)) {
                logger.error("Avion no existe: " + avionId);
                throw new AvionNoExisteException("Avion con ID " + avionId + " no existe");
            }
            if (vuelos.containsKey(id)) {
                Vuelo vuelo = vuelos.get(id);
                if (vuelo.getHoraSalida().equals(horaSalida) && vuelo.getHoraLlegada().equals(horaLlegada) && vuelo.getAvion().getId().equals(avionId) && vuelo.getOrigen().equals(origen) && vuelo.getDestino().equals(destino)) {
                    logger.error("No se pueden usar los mismos valores para el vuelo con ID existente");
                    throw new MismosParamConMismoIdException("El vuelo con ID " + id + " ya tiene esos valores.");
                }
                else {
                    vuelo.setHoraSalida(horaSalida);
                    vuelo.setHoraLlegada(horaLlegada);
                    vuelo.setAvion(aviones.get(avionId));
                    vuelo.setOrigen(origen);
                    vuelo.setDestino(destino);
                    logger.info("Vuelo actualizado: " + vuelo);
                }
            }
            else {
                Vuelo vuelo = new Vuelo(id, horaSalida, horaLlegada, aviones.get(avionId), origen, destino);
                vuelos.put(id, vuelo);
                logger.info("Vuelo añadido: " + vuelo);
            }
        } catch (AvionNoExisteException|MismosParamConMismoIdException ex) {
            logger.error("Error al añadir vuelo: " + ex.getMessage());
            return null;
        }
        return vuelos.get(id);
    }


    public void facturarMaleta(String vueloId, Usuario usuario) {
        logger.info("facturarMaleta: vueloId=" + vueloId + ", usuario=" + usuario.getId());
        try {
            if(!usuarios.containsKey(usuario.getId())) {
                logger.error("Usuario no existe: " + usuario.getId());
                throw new UsuarioNoExisteException("Usuario " + usuario.getId() + " no existe");
            }
            if (!vuelos.containsKey(vueloId)) {
                logger.error("Vuelo no existe: " + vueloId);
                throw new VueloNoExisteException("Vuelo" + vueloId + "no existe");
            }
            else {
                Vuelo vuelo = vuelos.get(vueloId);
                Maleta maleta = new Maleta(usuario, vuelos.get(vueloId));
                vuelo.addMaleta(maleta);
                logger.info("Maleta added: " + maleta);
            }
        } catch (VueloNoExisteException | UsuarioNoExisteException ex) {
            logger.error("Error facturando maleta: " + ex.getMessage());
        }
    }

    public List<Maleta> getMaletasFacturadas(String vueloId) {
        logger.info("getMaletasFacturadas: vueloId=" + vueloId);
        try {
            if (!vuelos.containsKey(vueloId)) {
                logger.error("Vuelo no existe: " + vueloId);
                throw new VueloNoExisteException("Vuelo" + vueloId + " no existe");
            }
            else{
                Vuelo vuelo = vuelos.get(vueloId);
                Stack<Maleta> maletasStack = vuelo.getMaletas();
                List<Maleta> maletasList = new ArrayList<>();
                Stack<Maleta> tempStack = new Stack<>();
                tempStack.addAll(maletasStack);
                while (!tempStack.isEmpty()) {
                    maletasList.add(tempStack.pop());
                }
                logger.info("Maletas facturadas: " + maletasList);
                return maletasList;
            }
        } catch (VueloNoExisteException ex) {
            logger.error("Error al obtener maletas facturadas: " + ex.getMessage());
            return null;
        }
    }

    public Usuario addUsuario(Usuario usuario) {
        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }

    public Avion getAvion(String id){
        try {
            if (!aviones.containsKey(id)) {
                logger.error("Avion no existe: " + id);
                throw new AvionNoExisteException("Avion con ID " + id + "no existe");
            }
            else {
                Avion avion = aviones.get(id);
                logger.info("Avion obtenido: " + avion);
                return avion;
            }
        } catch (AvionNoExisteException ex) {
            logger.error("Error al obtener avion: " + ex.getMessage());
            return null;
        }
    }

    public Vuelo getVuelo(String id){
        try {
            logger.info("getVuelo: id=" + id);
            if (!vuelos.containsKey(id)) {
                logger.error("Vuelo no existe: " + id);
                throw new VueloNoExisteException("Vuelo con ID " + id + " no existe");
            }
            else {
                Vuelo vuelo = vuelos.get(id);
                logger.info("Vuelo obtenido: " + vuelo);
                return vuelo;
            }
        } catch (VueloNoExisteException ex) {
            logger.error("Error al obtener vuelo: " + ex.getMessage());
            return null;
        }
    }

    public Usuario getUsuario(String id) {
        try {
            logger.info("getUsuario: id=" + id);
            if (!usuarios.containsKey(id)) {
                logger.error("Usuario no existe: " + id);
                throw new UsuarioNoExisteException("Usuario con ID " + id + " no existe");
            }
            else {
                Usuario usuario = usuarios.get(id);
                logger.info("Usuario obtenido: " + usuario);
                return usuario;
            }
        } catch (UsuarioNoExisteException ex) {
            logger.error("Error al obtener usuario: " + ex.getMessage());
            return null;
        }
    }
    public List<Usuario> getAllUsuarios() {
        logger.info("getAllUsuarios");
        return new ArrayList<>(usuarios.values());
    }

    public List<Vuelo> getAllVuelos() {
        logger.info("getAllVuelos");
        return new ArrayList<>(vuelos.values());
    }

    public List<Avion> getAllAviones() {
        logger.info("getAllAviones");
        return new ArrayList<>(aviones.values());
    }

    public void clear() {
        aviones.clear();
        vuelos.clear();
        usuarios.clear();
        logger.info("Sistema gestion cleared");
    }

    public int sizeUsers() {
        return usuarios.size();
    }
}