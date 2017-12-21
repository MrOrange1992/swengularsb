package at.fh.ima.swengs.swengular.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jashanica on 4/12/17.
 */

@Entity
public class Genre {

    @Id
    private int idGenre;

    private String name;

    @ManyToMany()
    private Set<Movie> movies;

    private long version;

    public Genre()
    {
        this.movies = new HashSet<Movie>();
    }

    public Genre(int idGenre, String name)
    {
        this.idGenre = idGenre;
        this.name = name;
        this.movies = new HashSet<Movie>();
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    //TODO FR: handle if movie already exists
    public void addMovie(Movie movie) { this.movies.add(movie); }

    public long getId() {
        return idGenre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
