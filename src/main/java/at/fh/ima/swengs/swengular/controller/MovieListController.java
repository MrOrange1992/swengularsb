package at.fh.ima.swengs.swengular.controller;

import at.fh.ima.swengs.swengular.Exceptions.MovieListNotFoundException;
import at.fh.ima.swengs.swengular.model.Movie;
import at.fh.ima.swengs.swengular.model.MovieList;
import at.fh.ima.swengs.swengular.model.User;
import at.fh.ima.swengs.swengular.repository.MovieListRepository;
import at.fh.ima.swengs.swengular.repository.UserRepository;
import at.fh.ima.swengs.swengular.service.TmdbAPI;
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
import java.util.List;
import java.util.Set;

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
    @RequestMapping(value="/movielist", method = RequestMethod.GET)
    //------------------------------------------------------------------------------------------------------------------
    ResponseEntity<Set<MovieList>> getAllMovieLists()
    {
        Iterable<MovieList> movieLists = movieListRepository.findAll();
        if (movieLists == null || !movieLists.iterator().hasNext())
            return new ResponseEntity<Set<MovieList>>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<Set<MovieList>>(movieListRepository.findBy(), HttpStatus.OK);
    }

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value="/movielist/{userid}", method = RequestMethod.GET)
    //------------------------------------------------------------------------------------------------------------------
    Set<MovieList> getMovieListsForUser(@PathVariable long userid)
    {
        Set<MovieList> movieLists = movieListRepository.findAllByOwnerID(userid);
        if (movieLists == null)
            throw new MovieListNotFoundException();
        return movieLists;
    }


    //------------------------------------------------------------------------------------------------------------------
    //@RequestMapping(value="/movielist/{id}", method = RequestMethod.GET)
    //------------------------------------------------------------------------------------------------------------------
    /*MovieList getMovieListByID(@PathVariable long id)
    {
        MovieList movieList = movieListRepository.findById(id);

        if (movieList == null) throw new MovieListNotFoundException();

        return movieList;
    }*/

    @RequestMapping(value = "/viewmovielist/{id}", method = RequestMethod.GET)
    //------------------------------------------------------------------------------------------------------------------
    MovieList getMovieListByID(@PathVariable long id)
    {
        //long lID = Long.parseLong(listID);

        MovieList movieList = movieListRepository.findById(id);

        if (movieList == null) throw new MovieListNotFoundException();

        return movieList.loadTmdbContent();
    }

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value="/movielist/{id}", method = RequestMethod.DELETE)
    //------------------------------------------------------------------------------------------------------------------
    ResponseEntity<MovieList> deleteMovieList(@PathVariable long id)
    {
        MovieList movieList = movieListRepository.findById(id);

        if (movieList == null) { return new ResponseEntity<MovieList>(HttpStatus.NOT_FOUND); }

        movieListRepository.delete(movieList);

        return new ResponseEntity<MovieList>(HttpStatus.NO_CONTENT);
    }



    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/movielist", method = RequestMethod.POST)
    public ResponseEntity<MovieList> createMovieList(@RequestBody MovieList movieList, UriComponentsBuilder ucBuilder)
    {
        movieListRepository.save(movieList);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/movielist/{id}").buildAndExpand(movieList.getId()).toUri());
        return new ResponseEntity<MovieList>(headers, HttpStatus.CREATED);
    }



    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/movielist/{id}", method = RequestMethod.PUT)
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<MovieList> updateMovieList(@PathVariable long id,@RequestBody MovieList movieListUpdate) {

        MovieList movieList = movieListRepository.findById(id);

        if (movieList == null) { return new ResponseEntity<MovieList>(HttpStatus.NOT_FOUND); }

        movieListRepository.save(movieListUpdate);

        return new ResponseEntity<MovieList>(HttpStatus.NO_CONTENT);
    }



    @RequestMapping(value = "/addmovietolist/",params = { "movieID", "listID" }, method = RequestMethod.POST)
    //------------------------------------------------------------------------------------------------------------------
    public String addMovieToList(@RequestParam("movieID") String movieID, @RequestParam("listID") String listID)
    {
        try {
            int mID = Integer.parseInt(movieID);
            long lID = Long.parseLong(listID);
            MovieList movieList = movieListRepository.findById(lID);
            movieList.addMovieID(mID);
            return "Success";
        }catch(Exception e){
            return "Failure: "+e.getMessage();
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/movielist/getPopularMovies", produces = MediaType.APPLICATION_JSON_VALUE)
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<MovieList> getPopularMovies()
    {
        MovieList popularMovieList = new MovieList();

        popularMovieList.setName("Popular Movies");

        popularMovieList.setMovies(tmdbAPI.getPopularMovies(1));

        return new ResponseEntity<>(popularMovieList, HttpStatus.OK);
    }


    //------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/movielist/searchMoviesByName", params = { "movieName" }, produces = MediaType.APPLICATION_JSON_VALUE)
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<MovieList> searchMoviesByName(@RequestParam("movieName") String movieName)
    {
        MovieList movieList = new MovieList();
        movieList.setMovies(tmdbAPI.searchMoviesByName(movieName, 1));

        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    //------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/movielist/getMovieDetails", params = { "movieID" }, produces = MediaType.APPLICATION_JSON_VALUE)
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<Movie> getMovieDetails(@RequestParam("movieID") String movieID)
    {
        return new ResponseEntity<>(tmdbAPI.getMovieByID(Integer.parseInt(movieID)), HttpStatus.OK);
    }



    //------------------------------------------------------------------------------------------------------------------
    @ResponseStatus(value= HttpStatus.NOT_FOUND,reason="This movielist is not found in the system")
    //------------------------------------------------------------------------------------------------------------------



    //------------------------------------------------------------------------------------------------------------------
    @ExceptionHandler(MovieListNotFoundException.class)
    //------------------------------------------------------------------------------------------------------------------
    public void exceptionHandler() { }
}
