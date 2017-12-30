package at.fh.ima.swengs.swengular.model;

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

    public MovieList(){ }

    public MovieList(String name, User owner)
    {
        this.name = name;
        this.owner = owner;
        this.movieIDs = new HashSet<Integer>();
    }

    public long getId() { return id; }

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

    public Set<Integer> getMovieIDs() {
        return movieIDs;
    }

    public void setMovieIDs(Set<Integer> movieIDs) {
        this.movieIDs = movieIDs;
    }

    public void addMovieID(int movieID) { this.movieIDs.add(movieID); }

    public void removeMovieID(int movieID)
    {
        this.movieIDs.remove(movieID);
        //System.out.println("DEBUG");
    }
}