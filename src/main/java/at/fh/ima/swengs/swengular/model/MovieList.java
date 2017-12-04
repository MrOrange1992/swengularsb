package at.fh.ima.swengs.swengular.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MovieList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long listID;

    @Version
    private long version;

    private String name;

    private User user;

    @ManyToMany(mappedBy = "id")
    private Set<Movie> movies;

    public MovieList(){

    }
    public MovieList(String name, User user){
        this.name = name;
        this.user = user;
        this.movies = new HashSet<Movie>();

    }

    public long getListID() {
        return listID;
    }

    public void setListID(long listID) {
        this.listID = listID;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
