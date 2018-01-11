package at.fh.ima.swengs.swengular.service;

//DEPRECATED
//import at.fh.ima.swengs.swengular.model.Genre;
//import at.fh.ima.swengs.swengular.model.Movie;
import at.fh.ima.swengs.swengular.model.Movie;
import at.fh.ima.swengs.swengular.model.MovieList;
import at.fh.ima.swengs.swengular.model.User;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Genre;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TmdbAPI
{
    //ATTRIBUTES
    //------------------------------------------------------------------------------------------------------------------
    private GetProperties gp = new GetProperties();
    private Properties properties = gp.getPropValues();
    private String apiKey = properties.getProperty("apiKey");
    private TmdbApi tmdbApi =  new TmdbApi(apiKey);
    private TmdbMovies tmdbMovies = tmdbApi.getMovies();

    private Map<Integer, Genre> genreIDMap = tmdbApi.getGenre().getGenreList("en").stream()
            .collect(Collectors.toMap(Genre::getId, Function.identity()));

    private Map<String, Genre> genreNameMap = tmdbApi.getGenre().getGenreList("en").stream()
            .collect(Collectors.toMap(Genre::getName, Function.identity()));
    //------------------------------------------------------------------------------------------------------------------



    //CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------
    public TmdbAPI() {}
    //------------------------------------------------------------------------------------------------------------------



    //GETTER/SETTER
    //------------------------------------------------------------------------------------------------------------------
    public TmdbMovies getTmdbMovies() { return tmdbMovies; }
    //------------------------------------------------------------------------------------------------------------------



    //METHODS
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Get movie by ID
     * @param movieID id of TMDBmovie
     * @return  TMDBmovie
     */
    public Movie getMovieByID(int movieID)
    {
        return new Movie(tmdbMovies.getMovie(movieID, "en"));
    }



    /**
     * Get TMDBmovie by ID
     * @param movieID id of TMDBmovie
     * @return  TMDBmovie
     */
    public MovieDb getMovieDbByID(int movieID) { return tmdbMovies.getMovie(movieID, "en", TmdbMovies.MovieMethod.credits); }



    /**
     * Get set of TMDB movies of multiple IDs
     * @param IDs set of IDs to get Tmdb content from
     * @return Set of TMDBmovies
     */
    public List<Movie> getMoviesOfIDs(Set<Integer> IDs)
    {
        return IDs.stream().map(movieID -> getMovieByID(movieID)).collect(Collectors.toList());
    }



    /**
     * Get TMDBGenre by ID
     * @param genreID ID of TMDBgenre
     * @return TMDBgenre
     */
    public Genre getGenreByID(int genreID)
    {
        return genreIDMap.get(genreID);
    }



    /**
     * Get TMDBGenre by name
     * @param genreName name of TMDBgenre
     * @return TMDBgenre
     */
    public Genre getGenreByName(String genreName)
    {
        return genreNameMap.get(genreName);
    }



    /**
     * Get all TMDBgenres (en)
     * @return Set of TMDBgenres
     */
    public Set<Genre> getAllGenres()
    {
        return new HashSet<Genre>(genreIDMap.values());
    }



    /**
     * Get collection of popular TMDBmovies
     * @param resultPages number of resultpages to return
     * @return set of Movies
     */
    public List<Movie> getPopularMovies(int resultPages)
    {
        return tmdbMovies.getPopularMovies("en", resultPages)
                .getResults().stream().map(movieDb -> new Movie(movieDb)).collect(Collectors.toList());
    }



    /**
     * Get collection of similar TMDBmovies
     * @param movieID movie to get similar results from
     * @param resultPages number of resultpages to return
     * @return  set of Movies
     */
    public List<Movie> getSimilarMovies(int movieID, int resultPages)
    {
        return tmdbMovies.getSimilarMovies(movieID,"en", resultPages)
                .getResults().stream().map(movieDb -> new Movie(movieDb)).collect(Collectors.toList());
    }



    /**
     * Function for search movies by name
     * @param movieName name to search for
     * @param resultPages number of returned resultpages
     * @return set of Movies
     */
    public List<Movie> getMoviesByName(String movieName, int resultPages)
    {
        return tmdbApi.getSearch().searchMovie(movieName, null, "en", false, resultPages)
                .getResults().stream().map(movieDb -> new Movie(movieDb)).collect(Collectors.toList());
    }
    //------------------------------------------------------------------------------------------------------------------




}
