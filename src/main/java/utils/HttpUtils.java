package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.MovieDTO;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//Callable
class GetMovieDTO implements Callable<MovieDTO>{
    String url = "https://swapi.dev/api/films/schema";
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    GetMovieDTO(String url) {
        this.url = url;
    }
    @Override
    public MovieDTO call() throws Exception {
        String x = HttpUtils.fetchData(url);
        MovieDTO mDTO = gson.fromJson(x, MovieDTO.class);
        return mDTO;
    }
}

public class HttpUtils {

    public static String fetchData(String _url) throws MalformedURLException, IOException {
        URL url = new URL(_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        //con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("User-Agent", "server");

        Scanner scan = new Scanner(con.getInputStream());
        String jsonStr = "";
        while (scan.hasNext()) {
            jsonStr += scan.nextLine();
        }
        scan.close();
        return jsonStr;
    }
    
    public static String fetchDataParallel(String movieURL) throws InterruptedException, ExecutionException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ExecutorService executor = Executors.newCachedThreadPool();
        //String movieURL = "https://swapi.dev/api/films/schema";
        //String movieURL = "https://swapi.dev/api/films/1/";
        
        Callable<MovieDTO> callC = new GetMovieDTO(movieURL);
        
        
        Future<MovieDTO> futureC = executor.submit(callC);
        
        
        //OurDTO our = new OurDTO(futureC.get(), futureD.get());
        String combined = gson.toJson(futureC.get());
        return combined;
    }
}
