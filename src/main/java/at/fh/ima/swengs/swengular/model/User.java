package at.fh.ima.swengs.swengular.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany (mappedBy =  "User")
    private List<Genre> genres;

    @OneToMany (mappedBy = "User")
    private List<MovieList> movieLists;

    public User() {
    }

    public User(String firstName,
                String lastName,
                String password,
                List<Genre> genres,
                List<MovieList> movieLists) {
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

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<MovieList> getMovieLists() {
        return movieLists;
    }

    public void setMovieLists(List<MovieList> movieLists) {
        this.movieLists = movieLists;
    }
}
