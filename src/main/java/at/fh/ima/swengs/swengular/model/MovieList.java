package at.fh.ima.swengs.swengular.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MovieList
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private long version;

    private String name;

    @ManyToOne()
    private User owner;

    @ManyToMany()
    private Set<Movie> movies;

    public MovieList(){

    }
    public MovieList(String name, User user){
        this.name = name;
        this.owner = owner;
        this.movies = new HashSet<Movie>();

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

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

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}