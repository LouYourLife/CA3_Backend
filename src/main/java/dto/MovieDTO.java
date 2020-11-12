package dto;

import entities.Movie;

/**
 *
 * @author vnord
 */
public class MovieDTO {
    private String url;
    //private String description;
    private String title;
    private int episode_id;
    private String opening_crawl;
    private String director;
    private String producer;
    private String release_date;
    private String[] characters;

    public MovieDTO(String URL, String title, int episode_id, String opening_crawl, String director, String producer, String release_date, String[] characters) {
        this.url = URL;
        this.title = title;
        this.episode_id = episode_id;
        this.opening_crawl = opening_crawl;
        this.director = director;
        this.producer = producer;
        this.release_date = release_date;
        this.characters = characters;
    }
    
    public MovieDTO(Movie m) {
        this.title = m.getTitle();
        this.episode_id = m.getEpisode_id();
        this.opening_crawl = m.getOpening_crawl();
        this.director = m.getDirector();
        this.producer = m.getProducer();
        this.release_date = m.getRelease_date();
    }

    public String getURL() {
        return url;
    }

    public void setURL(String URL) {
        this.url = URL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEpisode_id() {
        return episode_id;
    }

    public void setEpisode_id(int episode_id) {
        this.episode_id = episode_id;
    }

    public String getOpening_crawl() {
        return opening_crawl;
    }

    public void setOpening_crawl(String opening_crawl) {
        this.opening_crawl = opening_crawl;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String[] getCharacters() {
        return characters;
    }

    public void setCharacters(String[] characters) {
        this.characters = characters;
    }

    
    
    
}
