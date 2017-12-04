/*package at.fh.ima.swengs.swengular.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Version
    private long version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;

    private String lastName;

    @JsonIgnore
    private String password;

    @ManyToMany (mappedBy = "id")
    private Set<Genre> genres;

    @OneToMany (mappedBy = "id", cascade = CascadeType.ALL)
    private Set<MovieList> movieLists;

    public User() {
    }

    public User(String firstName,
                String lastName,
                String password,
                Set<Genre> genres,
                Set<MovieList> movieLists) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.genres = genres;
        this.movieLists = movieLists;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<MovieList> getMovieLists() {
        return movieLists;
    }

    public void setMovieLists(Set<MovieList> movieLists) {
        this.movieLists = movieLists;
    }
}
*/