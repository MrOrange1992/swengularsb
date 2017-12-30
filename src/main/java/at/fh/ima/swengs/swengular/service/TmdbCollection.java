package at.fh.ima.swengs.swengular.service;

import at.fh.ima.swengs.swengular.model.User;
import info.movito.themoviedbapi.model.MovieDb;

import java.util.Set;

public class TmdbCollection
{
    private String name;

    private User owner;

    private Set<MovieDb> movies;

    public TmdbCollection(String name, User owner, Set<MovieDb> movies)
    {
        this.name = name;
        this.owner = owner;
        this.movies = movies;
    }
}
