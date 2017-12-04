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
    private long idGenre;

    private String name;

    @ManyToMany(mappedBy = "genres")
    private List<Movie> movies;

    private long version;



    public Genre(long idGenre, String name) {
        this.idGenre = idGenre;
        this.name = name;
        this.movies = new ArrayList<Movie>();
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

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
