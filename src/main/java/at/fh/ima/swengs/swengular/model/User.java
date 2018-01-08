package at.fh.ima.swengs.swengular.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class User
{
    @Version
    private long version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;

    private String lastName;

    @JsonIgnore
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Integer> genreIDs;

    @OneToMany (mappedBy = "ownerID", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<MovieList> movieLists;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<User> usersFollowing;


    public User() { }

    public User(String firstName, String lastName, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.genreIDs = new HashSet<Integer>();
        this.movieLists = new HashSet<MovieList>();
        this.usersFollowing = new HashSet<User>();
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

    public Set<Integer> getGenreIDs() {
        return genreIDs;
    }

    public void setGenreIDs(Set<Integer> genreIDs) {
        this.genreIDs = genreIDs;
    }

    public void addGenreID(int genreID) {
        this.genreIDs.add(genreID);
    }

    public void removeGenreID(int genreID) {
        this.genreIDs.remove(genreID);
    }

    public Set<MovieList> getMovieLists() {
        return movieLists;
    }

    public void setMovieLists(Set<MovieList> movieLists) {
        this.movieLists = movieLists;
    }

    public Set<User> getUsersFollowing() { return usersFollowing; }

    public User getUserFollowingByID(int userID)
    {
        return this.usersFollowing.stream().filter(user -> user.getId() != userID).collect(Collectors.toList()).get(0);
    }

    public void setUsersFollowing(Set<User> usersFollowing) { this.usersFollowing = usersFollowing; }

    public void addUserFollowing(User user) { this.usersFollowing.add(user); }

}