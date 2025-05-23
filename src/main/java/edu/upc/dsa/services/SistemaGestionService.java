package edu.upc.dsa.services;


import edu.upc.dsa.SistemaGestion;
import edu.upc.dsa.SistemaGestionImpl;
import edu.upc.dsa.models.Maleta;
import edu.upc.dsa.models.Vuelo;
import edu.upc.dsa.models.Avion;
import edu.upc.dsa.models.Usuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Api(value = "/Operaciones", description = "Endpoint to SistemaGestion Service")
@Path("/Operaciones")
public class SistemaGestionService {

    private SistemaGestion sistemaGestion;

    public SistemaGestionService() {
        this.sistemaGestion = SistemaGestionImpl.getInstance();
        if (sistemaGestion.sizeUsers() == 0) {
            Avion avion1 = new Avion("A1", "Boeing 737", "Iberia");
            Avion avion2 = new Avion("A2", "Airbus A320", "Vueling");
            Usuario usuario1 = new Usuario("U1", "Joel", "Moreno");
            Usuario usuario2 = new Usuario("U2", "David", "Sanchez");

            this.sistemaGestion.addAvion(avion1.getId(), avion1.getModelo(), avion1.getCompania());
            this.sistemaGestion.addAvion(avion2.getId(), avion2.getModelo(), avion2.getCompania());
            this.sistemaGestion.addUsuario(usuario1);
            this.sistemaGestion.addUsuario(usuario2);

            this.sistemaGestion.addVuelo("V1", new Date(), new Date(), "A1", "Barcelona", "Madrid");
            this.sistemaGestion.addVuelo("V2", new Date(), new Date(), "A2", "Madrid", "Barcelona");

            this.sistemaGestion.facturarMaleta("V1", usuario1);
            this.sistemaGestion.facturarMaleta("V2", usuario1);
        }
    }

    @POST
    @ApiOperation(value = "create a new Avion", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful, creado", response = Avion.class),
            @ApiResponse(code = 200, message = "Successful, actualizado", response = Avion.class),
            @ApiResponse(code = 500, message = "Validation Error"),
            @ApiResponse(code = 409, message = "Parámetros duplicados")
    })
    @Path("/avion")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newAvion(Avion avion) {
        if (avion.getId() == null || avion.getModelo() == null || avion.getCompania() == null) {
            return Response.status(500).entity(avion).build();
        }
        boolean existiaPreviamente = sistemaGestion.getAvion(avion.getId()) != null;
        Avion avionResultado = sistemaGestion.addAvion(
                avion.getId(),
                avion.getModelo(),
                avion.getCompania()
        );
        if (avionResultado == null) {
            return Response.status(409).entity("Mismo ID con mismos parámetros").build();
        }
        if (existiaPreviamente) {
            return Response.status(200).entity(avionResultado).build();
        } else {
            return Response.status(201).entity(avionResultado).build();
        }
    }

    @POST
    @ApiOperation(value = "create a new Vuelo", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Vuelo.class),
            @ApiResponse(code = 500, message = "Validation Error"),
            @ApiResponse(code = 404, message = "Avion no encontrado"),
            //@ApiResponse(code = 409, message = "Add parameters")
    })
    @Path("/vuelo")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newVuelo(Vuelo vuelo) {
        if (vuelo.getId() == null || vuelo.getHoraSalida() == null || vuelo.getHoraLlegada() == null || vuelo.getAvion() == null || vuelo.getOrigen() == null || vuelo.getDestino() == null)
            return Response.status(500).entity(vuelo).build();
        if (this.sistemaGestion.getAvion(vuelo.getAvion().getId()) == null){
            return Response.status(404).entity("Avion no encontrado").build();
        }
        this.sistemaGestion.addVuelo(vuelo.getId(), vuelo.getHoraSalida(), vuelo.getHoraLlegada(), vuelo.getAvion().getId(), vuelo.getOrigen(), vuelo.getDestino());
        return Response.status(201).entity(vuelo).build();
    }

    @POST
    @ApiOperation(value = "create a new Usuario", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Usuario.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/usuario")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUsuario(Usuario usuario) {
        if (usuario.getId() == null || usuario.getNombre() == null)
            return Response.status(500).entity(usuario).build();
        this.sistemaGestion.addUsuario(usuario);
        return Response.status(201).entity(usuario).build();
    }

    @POST
    @ApiOperation(value = "facturar una Maleta", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Maleta.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/maleta")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response facturarMaleta(@QueryParam("vueloId") String vueloId, @QueryParam("usuarioId") String usuarioId) {
        Usuario usuario = this.sistemaGestion.getUsuario(usuarioId);
        if (usuario == null)
            return Response.status(500).entity("Usuario no encontrado").build();
        this.sistemaGestion.facturarMaleta(vueloId, usuario);
        return Response.status(201).entity("Maleta facturada").build();
    }

    @GET
    @ApiOperation(value = "get Maletas facturadas", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Maleta.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Vuelo not found")
    })
    @Path("/maletas/{vueloId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMaletasFacturadas(@PathParam("vueloId") String vueloId) {
        List<Maleta> maletas = this.sistemaGestion.getMaletasFacturadas(vueloId);
        if (maletas == null)
            return Response.status(404).entity("Vuelo no encontrado").build();
        GenericEntity<List<Maleta>> entity = new GenericEntity<List<Maleta>>(maletas) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get all Aviones", notes = "Retorna una lista de todos los aviones")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Avion.class, responseContainer = "List")
    })
    @Path("/aviones")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAviones() {
        List<Avion> aviones = this.sistemaGestion.getAllAviones();
        GenericEntity<List<Avion>> entity = new GenericEntity<List<Avion>>(aviones) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get all Vuelos", notes = "Retorna una lista de todos los vuelos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Vuelo.class, responseContainer = "List")
    })
    @Path("/vuelos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVuelos() {
        List<Vuelo> vuelos = this.sistemaGestion.getAllVuelos();
        GenericEntity<List<Vuelo>> entity = new GenericEntity<List<Vuelo>>(vuelos) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get all Usuarios", notes = "Retorna una lista de todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Usuario.class, responseContainer = "List")
    })
    @Path("/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsuarios() {
        List<Usuario> usuarios = this.sistemaGestion.getAllUsuarios();
        GenericEntity<List<Usuario>> entity = new GenericEntity<List<Usuario>>(usuarios) {};
        return Response.status(200).entity(entity).build();
    }
}