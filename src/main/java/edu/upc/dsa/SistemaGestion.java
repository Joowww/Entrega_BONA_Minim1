package edu.upc.dsa;

import edu.upc.dsa.models.Maleta;
import edu.upc.dsa.models.Vuelo;
import edu.upc.dsa.models.Avion;
import edu.upc.dsa.models.Usuario;

import java.util.Date;
import java.util.List;

public interface SistemaGestion {
    void addAvion(String id, String modelo, String compania);
    void addVuelo(String id, Date horaSalida, Date horaLlegada, String avionId, String origen, String destino);
    void facturarMaleta(String vueloId, Usuario usuario);
    List<Maleta> getMaletasFacturadas(String vueloId);

    void clear();

    int sizeUsers();
}
