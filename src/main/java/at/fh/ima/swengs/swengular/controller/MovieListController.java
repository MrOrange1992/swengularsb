package at.fh.ima.swengs.swengular.controller;

import at.fh.ima.swengs.swengular.Exceptions.MovieListNotFoundException;
import at.fh.ima.swengs.swengular.model.Movie;
import at.fh.ima.swengs.swengular.model.MovieList;
import at.fh.ima.swengs.swengular.model.User;
import at.fh.ima.swengs.swengular.repository.MovieListRepository;
import at.fh.ima.swengs.swengular.repository.UserRepository;
import at.fh.ima.swengs.swengular.service.TmdbAPI;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import info.movito.themoviedbapi.model.MovieDb;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTML;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class MovieListController
{
    @Autowired
    MovieListRepository movieListRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TmdbAPI tmdbAPI;

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value="/movielist", method = RequestMethod.GET, params = "action=getAllMovieLists")
    //------------------------------------------------------------------------------------------------------------------
    ResponseEntity<Set<MovieList>> getAllMovieLists()
    {
        Iterable<MovieList> movieLists = movieListRepository.findAll();

        if (movieLists == null || !movieLists.iterator().hasNext())
            return new ResponseEntity<Set<MovieList>>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<Set<MovieList>>(movieListRepository.findBy(), HttpStatus.OK);
    }

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value="/movielist/{id}", method = RequestMethod.GET, params = "action=getMovieListsOfUser")
    //------------------------------------------------------------------------------------------------------------------
    ResponseEntity<Set<MovieList>> getMovieListsOfUser(@PathVariable long id)
    {
        Set<MovieList> movieLists = movieListRepository.findAllByOwnerID(id);
        if (movieLists == null)  new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<Set<MovieList>>(movieLists, HttpStatus.OK);
    }

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/movielist/{id}", method = RequestMethod.GET, params = "action=getOwnerName")
    //------------------------------------------------------------------------------------------------------------------
    ResponseEntity<String> getOwnerName(@PathVariable long id)
    {
        User owner = userRepository.findById(id);

        if (owner == null) new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<String>(owner.getUsername(), HttpStatus.OK);
    }

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/movielist/{id}", method = RequestMethod.GET, params = "action=getMovieListByID")
    //------------------------------------------------------------------------------------------------------------------
    ResponseEntity<MovieList> getMovieListByID(@PathVariable long id)
    {
        MovieList movieList = movieListRepository.findById(id);

        if (movieList == null) new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(movieList.loadTmdbContent(), HttpStatus.OK);
    }

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/movielist/{id}", method = RequestMethod.GET, params = "action=getMovieListIDsOfOwner")
    //------------------------------------------------------------------------------------------------------------------
    ResponseEntity<Set<Long>> getMovieListIDsOfOwner(@PathVariable long id)
    {
        User owner = userRepository.findById(id);
        if (owner.getMovieLists() == null) new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Set<Long> movieListIDs = owner.getMovieLists().stream().map(movieList -> movieList.getId()).collect(Collectors.toSet());
        return new ResponseEntity<>(movieListIDs, HttpStatus.OK);
    }

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/movielist/", method = RequestMethod.POST, params = "action=createMovieList")
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<MovieList> createMovieList(@RequestBody MovieList movieList)
    {
        movieListRepository.save(movieList);
        MovieList list = movieListRepository.findByName(movieList.getName());

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/movielist/{id}", method = RequestMethod.PUT, params = "action=updateMovieList")
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<MovieList> updateMovieList(@PathVariable long id, @RequestBody MovieList movieListUpdate)
    {
        MovieList movieList = movieListRepository.findById(id);
        if (movieList == null) { return new ResponseEntity<MovieList>(HttpStatus.NOT_FOUND); }
        movieListRepository.save(movieListUpdate);
        return new ResponseEntity<MovieList>(HttpStatus.OK);
    }

    @RequestMapping(value = "/movielist/{id}", method = RequestMethod.PUT, params = "action=addMovieToList")
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<String> addMovieToList(@PathVariable long id, @RequestBody int movieID)
    {
        MovieList movieList = movieListRepository.findById(id);
        if (movieList == null) { return new ResponseEntity<String>(HttpStatus.NOT_FOUND); }
        movieList.addMovieID(movieID);
        movieListRepository.save(movieList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/movielist/{id}", method = RequestMethod.PUT, params = "action=deleteMovieFromList")
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<String> deleteMovieFromList(@PathVariable long id, @RequestBody int movieID)
    {
        MovieList movieList = movieListRepository.findById(id);
        if (movieList == null) { return new ResponseEntity<String>(HttpStatus.NOT_FOUND); }
        movieList.removeMovieID(movieID);
        movieListRepository.save(movieList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/movielist/{id}", method = RequestMethod.DELETE, params = "action=deleteMovieList")
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<MovieList> deleteMovieList(@PathVariable long id)
    {
        MovieList movieList = movieListRepository.findById(id);
        if (movieList == null) { return new ResponseEntity<MovieList>(HttpStatus.NOT_FOUND); }
        movieListRepository.delete(movieList);
        return new ResponseEntity<MovieList>(HttpStatus.OK);
    }

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/movielist/", method = RequestMethod.GET, params = "action=getPopularMovies")
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<MovieList> getPopularMovies()
    {
        try{
            MovieList popularMovieList = new MovieList();
            popularMovieList.setName("Popular Movies");
            popularMovieList.setMovies(tmdbAPI.getPopularMovies(1));
            return new ResponseEntity<>(popularMovieList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MovieList(), HttpStatus.NO_CONTENT);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/movielist/{movieName}", method = RequestMethod.GET, params = "action=searchByName")
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<MovieList> searchMoviesByName(@PathVariable("movieName") String movieName)
    {
        try{
            MovieList movieList = new MovieList();
            movieList.setMovies(tmdbAPI.searchMoviesByName(movieName, 1));
            return new ResponseEntity<>(movieList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MovieList(), HttpStatus.NO_CONTENT);
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/movielist/{movieID}", method = RequestMethod.GET, params = "action=getMovieDetails")
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<Movie> getMovieDetails(@PathVariable("movieID") int movieID)
    {
        Movie movie = tmdbAPI.getMovieByID(movieID);
        if (movie == null) { return new ResponseEntity<>(new Movie(), HttpStatus.NOT_FOUND); }
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }



    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/movielist/", method = RequestMethod.POST, params = "action=getRecommendations")
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<MovieList> createRecommendedMovieList(@RequestBody Set<Long> genreIdsList)
    {
        MovieList movieList = tmdbAPI.getRecommendedMovies(genreIdsList);
        if (movieList == null) { return new ResponseEntity<>(new MovieList(), HttpStatus.NOT_MODIFIED); }
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }



    //------------------------------------------------------------------------------------------------------------------
    @ExceptionHandler(MovieListNotFoundException.class)
    //------------------------------------------------------------------------------------------------------------------
    public void exceptionHandler() { }
}
