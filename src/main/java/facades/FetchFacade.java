package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.MovieDTO;
import entities.Movie;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import utils.EMF_Creator;
import utils.HttpUtils;

public class FetchFacade {
    
//    private static FetchFacade instance;
//    private static EntityManagerFactory emf;
//    
//    public FetchFacade() {
//        
//    }
//    
//    public static FetchFacade getGMPFacade(EntityManagerFactory _emf) {
//        if (instance == null) {
//            emf = _emf;
//            instance = new FetchFacade();
//        }
//        return instance;
//    }

    class Default implements Callable<MovieDTO> {

        String url;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Default(String url) {
            this.url = url;
        }

        @Override
        public MovieDTO call() throws Exception {
            String raw = HttpUtils.fetchData(url);
            MovieDTO mdto = gson.fromJson(raw, MovieDTO.class);
            return mdto;
        }
    }

    public List<String> fetchParallel() throws InterruptedException, ExecutionException {
//        String[] hostList = {"https://api.chucknorris.io/jokes/random", "https://icanhazdadjoke.com",
//            "https://swapi.dev/api/planets/schema", "https://swapi.dev/api/vehicles/schema", "https://swapi.dev/api/species/schema"};
        String[] hostList = {"https://swapi.dev/api/films/1/", "https://swapi.dev/api/films/2/", "https://swapi.dev/api/films/3/", 
        "https://swapi.dev/api/films/4/", "https://swapi.dev/api/films/5/", "https://swapi.dev/api/films/6/"};
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ExecutorService executor = Executors.newCachedThreadPool();
        
        List<Future<MovieDTO>> futures = new ArrayList<>();
        List<String> retList = new ArrayList();

        for (String url : hostList) {
            Callable<MovieDTO> urlTask = new Default(url);
            Future<MovieDTO> future = executor.submit(urlTask);
            futures.add(future);
        }

        for (Future<MovieDTO> fut : futures) {
            retList.add(gson.toJson(fut.get()));
        }
       
        return retList;
    }
    
    public List<MovieDTO> getAllPitchMovies() {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery tq = em.createQuery("SELECT m FROM Movie m", Movie.class);
            List<MovieDTO> list = tq.getResultList();
            return list;
        } finally {
            em.close();
        }
    }
    
    public MovieDTO addMovie(MovieDTO m) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        Movie movie = new Movie(m.getTitle(), m.getEpisode_id(), m.getOpening_crawl(), m.getDirector(), m.getProducer(), m.getRelease_date(), m.getCharacters());
        MovieDTO mDTO = null;
        try {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
            mDTO = new MovieDTO(movie);
        } finally {
            em.close();
        }
        return mDTO;
    }

}
