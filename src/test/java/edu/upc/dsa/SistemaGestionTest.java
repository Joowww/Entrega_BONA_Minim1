package edu.upc.dsa;

import edu.upc.dsa.models.Maleta;
import edu.upc.dsa.models.Vuelo;
import edu.upc.dsa.models.Avion;
import edu.upc.dsa.models.Usuario;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class SistemaGestionTest {
    SistemaGestion sistemaGestion;

    @Before
    public void setUp() {
        this.sistemaGestion = SistemaGestionImpl.getInstance();
        this.sistemaGestion.clear();

        Avion avion1 = new Avion("A1", "Boeing 737", "Iberia");
        Avion avion2 = new Avion("A2", "Airbus A320", "Vueling");
        Usuario usuario1 = new Usuario("U1", "Joel", "123");
        Usuario usuario2 = new Usuario("U2", "David", "123");

        this.sistemaGestion.addAvion(avion1.getId(), avion1.getModelo(), avion1.getCompania());
        this.sistemaGestion.addAvion(avion2.getId(), avion2.getModelo(), avion2.getCompania());
        this.sistemaGestion.addUsuario(usuario1);
        this.sistemaGestion.addUsuario(usuario2);

        this.sistemaGestion.addVuelo("V1", new Date(), new Date(), "A1", "Barcelona", "Madrid");
        this.sistemaGestion.addVuelo("V2", new Date(), new Date(), "A2", "Madrid", "Barcelona");

        this.sistemaGestion.facturarMaleta("V1", usuario1);
        this.sistemaGestion.facturarMaleta("V2", usuario1);
    }

    @After
    public void tearDown() {
        this.sistemaGestion.clear();
    }

    @Test
    public void testAddAvion() {
        this.sistemaGestion.addAvion("A3", "Boeing 747", "Lufthansa");
        Avion avion = ((SistemaGestionImpl) this.sistemaGestion).getAvion("A3");
        Assert.assertEquals("Boeing 747", avion.getModelo());
        Assert.assertEquals("Lufthansa", avion.getCompania());
    }

    @Test
    public void testAddVuelo() {
        this.sistemaGestion.addVuelo("V3", new Date(), new Date(), "A1", "Sevilla", "Valencia");
        Vuelo vuelo = ((SistemaGestionImpl) this.sistemaGestion).getVuelo("V3");
        Assert.assertNotNull(vuelo);
        Assert.assertEquals("Sevilla", vuelo.getOrigen());
        Assert.assertEquals("Valencia", vuelo.getDestino());
    }

    @Test
    public void testFacturarMaleta() {

        Usuario usuario = ((SistemaGestionImpl) this.sistemaGestion).getUsuario("U1");
        Assert.assertNotNull(usuario);
        Assert.assertEquals("Joel", usuario.getNombre());

       this.sistemaGestion.facturarMaleta("V1", usuario);
        List<Maleta> maletas = this.sistemaGestion.getMaletasFacturadas("V1");
        Assert.assertEquals(2, maletas.size());
        Assert.assertEquals(usuario, maletas.get(1).getUsuario());
    }


    @Test
    public void testGetMaletasFacturadas() {
        List<Maleta> maletas = this.sistemaGestion.getMaletasFacturadas("V1");
        Assert.assertEquals(1, maletas.size());
        Assert.assertEquals("Joel", maletas.get(0).getUsuario().getNombre());
    }

    @Test
    public void testGetAllAviones() {
        List<Avion> aviones = sistemaGestion.getAllAviones();
        Assert.assertEquals(2, aviones.size());
        Assert.assertEquals("Iberia", aviones.get(0).getCompania());
    }

    @Test
    public void testGetAllVuelos() {
        List<Vuelo> vuelos = sistemaGestion.getAllVuelos();
        Assert.assertEquals(2, vuelos.size());
        Assert.assertEquals("Barcelona", vuelos.get(0).getOrigen());
    }

    @Test
    public void testGetAllUsuarios() {
        List<Usuario> usuarios = sistemaGestion.getAllUsuarios();
        Assert.assertEquals(2, usuarios.size());
        Assert.assertEquals("Joel", usuarios.get(0).getNombre());
    }

    @Test
    public void testGetAllAfterAdd() {
        sistemaGestion.addAvion("A3", "Boeing 747", "Lufthansa");
        Assert.assertEquals(3, sistemaGestion.getAllAviones().size());
    }
}
