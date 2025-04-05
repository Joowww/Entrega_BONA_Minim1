package edu.upc.dsa;

import edu.upc.dsa.models.Maleta;
import edu.upc.dsa.models.Vuelo;
import edu.upc.dsa.models.Avion;
import edu.upc.dsa.models.Usuario;

import java.util.Date;
import java.util.List;

public interface SistemaGestion {
    Avion addAvion(String id, String modelo, String compania);
    Vuelo addVuelo(String id, Date horaSalida, Date horaLlegada, String avionId, String origen, String destino);
    void facturarMaleta(String vueloId, Usuario usuario);
    List<Maleta> getMaletasFacturadas(String vueloId);
    Usuario addUsuario(Usuario usuario);
    Usuario getUsuario(String id);
    Avion getAvion(String id);
    Vuelo getVuelo(String id);
    void clear();
    int sizeUsers();
    List<Avion> getAllAviones();
    List<Vuelo> getAllVuelos();
    List<Usuario> getAllUsuarios();
}
