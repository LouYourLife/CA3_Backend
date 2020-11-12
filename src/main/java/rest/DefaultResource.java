package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.MovieDTO;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import facades.FetchFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.POST;
import utils.HttpUtils;
 
/**
 * REST Web Service
 *
 * @author lam
 */
@Path("default")
public class DefaultResource {
    private final FetchFacade facade = new FetchFacade();
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDefault() throws IOException, InterruptedException, ExecutionException {
        //List<String> list = facade.fetchParallel();
        String list = HttpUtils.fetchDataParallel("https://swapi.dev/api/films/1/");
        //return GSON.toJson(list);
        return list;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("movielist")
    public String allMovies() throws IOException, InterruptedException, ExecutionException {
        List<String> list = facade.fetchParallel();
        return GSON.toJson(list);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("pitchmovies")
    @RolesAllowed("admin")
    public String allPitchMovies() throws IOException, InterruptedException, ExecutionException {
        List<MovieDTO> list = facade.getAllPitchMovies();
        return GSON.toJson(list);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("populate")
    public void populate() throws IOException, InterruptedException, ExecutionException {
        utils.SetupTestUsers.populateUser();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String x(String movie) {
        MovieDTO mDTO = GSON.fromJson(movie, MovieDTO.class);
        MovieDTO mdto = facade.addMovie(mDTO);
        String json = GSON.toJson(mdto);
        return json;
    }
   
}
