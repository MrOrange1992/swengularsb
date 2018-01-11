package at.fh.ima.swengs.swengular.model;

import at.fh.ima.swengs.swengular.service.TmdbAPI;
import com.fasterxml.jackson.annotation.*;
import info.movito.themoviedbapi.model.MovieDb;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class MovieList
{
    @Version
    @JsonIgnore
    private long version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private long ownerID;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Integer> movieIDs;

    @Transient
    private List<Movie> movies;

    @Transient
    @JsonIgnore
    private TmdbAPI tmdbAPI = new TmdbAPI();

    public MovieList(){ }

    public MovieList(String name, Long ownerID)
    {
        this.name = name;
        this.ownerID = ownerID;
        this.movieIDs = new HashSet<Integer>();
        //this.movies = tmdbAPI.getMoviesOfIDs(this.movieIDs);
    }


    public long getId() { return id; }

    /*public void setId(long id) {
        this.id = id;
    }*/

    public long getVersion() {
        return version;
    }

    /*public void setVersion(long version) {
        this.version = version;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOwnerID() { return ownerID; }

    public void setOwnerID(long ownerID) { this.ownerID = ownerID; }

    public Set<Integer> getMovieIDs() {
        return movieIDs;
    }

    public void setMovieIDs(Set<Integer> movieIDs) {
        this.movieIDs = movieIDs;
    }

    public void addMovieID(int movieID)
    {
        this.movieIDs.add(movieID);
        //this.movies.add(tmdbAPI.getMovieByID(movieID));
    }


    //TODO remove also content from movies??
    public void removeMovieID(int movieID)
    {
        this.movieIDs.remove(movieID);
        //this.movies.remove(tmdbAPI.getMovieByID(movieID));
        //System.out.println("DEBUG");
    }

    public List<Movie> getMovies() { return movies; }

    public void setMovies(List<Movie> movies) { this.movies = movies; }

    /**
     * Fill list instance with tmdb movies from list of IDs
     * @return updated Instance
     */
    public MovieList loadTmdbContent()
    {
        this.movies = tmdbAPI.getMoviesOfIDs(this.movieIDs);

        //TODO best practice to return updated instance??
        return this;
    }
}