package at.fh.ima.swengs.swengular.model;

import at.fh.ima.swengs.swengular.service.TmdbAPI;
import com.fasterxml.jackson.annotation.JsonInclude;
import info.movito.themoviedbapi.model.MovieDb;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MovieList
{
    @Version
    private long version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @ManyToOne()
    private User owner;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Integer> movieIDs;

    @JsonInclude()
    @Transient
    private Set<MovieDb> movies;

    @Transient
    private TmdbAPI tmdbAPI = new TmdbAPI();

    public MovieList(){ }

    public MovieList(String name, User owner)
    {
        this.name = name;
        this.owner = owner;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

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

    public Set<MovieDb> getMovies() { return movies; }

    public void setMovies(Set<MovieDb> movies) { this.movies = movies; }

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