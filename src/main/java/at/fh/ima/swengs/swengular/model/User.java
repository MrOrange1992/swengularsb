package at.fh.ima.swengs.swengular.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import info.movito.themoviedbapi.model.people.PersonCast;

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

    private String username;

    //@JsonIgnore
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Integer> genreIDs;

    @OneToMany (mappedBy = "ownerID", fetch = FetchType.EAGER)
    //@JsonManagedReference
    private Set<MovieList> movieLists;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<User> usersFollowing;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Integer> favouriteActorIDs;


    public User() { }

    /*public User(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.genreIDs = new HashSet<Integer>();
        this.movieLists = new HashSet<MovieList>();
        this.usersFollowing = new HashSet<User>();
        this.favouriteActorIDs = new HashSet<Integer>();
    }*/

    public User(String username, String password, Set<Integer> genreIDs)
    {
        this.username = username;
        this.password = password;
        this.genreIDs = genreIDs;
        this.movieLists = new HashSet<MovieList>();
        this.usersFollowing = new HashSet<User>();
        this.favouriteActorIDs = new HashSet<Integer>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

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

    public Set<Integer> getFavouriteActorIDs() { return favouriteActorIDs; }

    public void setFavouriteActorIDs(Set<Integer> favouriteActorIDs) { this.favouriteActorIDs = favouriteActorIDs; }

    public void addFavouriteActorID(int actorID) {
        this.favouriteActorIDs.add(actorID);
    }

}