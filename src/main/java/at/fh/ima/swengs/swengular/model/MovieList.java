/*package at.fh.ima.swengs.swengular.model;

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

    @ManyToOne(cascade = CascadeType.ALL)
    private User users;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Movie> movies;

    public MovieList(){

    }
    public MovieList(String name, User user){
        this.name = name;
        this.users = users;
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

    public User getUsers() {
        return users;
    }

    public void setUsers(User user) {
        this.users = user;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
*/