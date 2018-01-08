package at.fh.ima.swengs.swengular.controller;

import at.fh.ima.swengs.swengular.Exceptions.MovieListNotFoundException;
import at.fh.ima.swengs.swengular.model.MovieList;
import at.fh.ima.swengs.swengular.repository.MovieListRepository;
import info.movito.themoviedbapi.model.MovieDb;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MovieListController
{
    @Autowired
    MovieListRepository movieListRepository;



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
    @RequestMapping(value="/movielist/{id}", method = RequestMethod.GET)
    //------------------------------------------------------------------------------------------------------------------
    MovieList getMovieListByID(@PathVariable long id)
    {
        MovieList movieList = movieListRepository.findById(id);

        if (movieList == null) throw new MovieListNotFoundException();

        return movieList;
    }



    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value="/movielist/{id}", method = RequestMethod.DELETE)
    //------------------------------------------------------------------------------------------------------------------
    ResponseEntity<MovieList> deleteMovieList(@PathVariable long id) {
        MovieList movieList = movieListRepository.findById(id);

        if (movieList == null) { return new ResponseEntity<MovieList>(HttpStatus.NOT_FOUND); }

        movieListRepository.delete(movieList);

        return new ResponseEntity<MovieList>(HttpStatus.NO_CONTENT);
    }



    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/movielist", method = RequestMethod.POST)
    //------------------------------------------------------------------------------------------------------------------
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


    //------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/movielist/dummyList",params = { "name" }, produces = MediaType.APPLICATION_JSON_VALUE)
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<MovieList> getDummyList(@RequestParam("name") String name)
    {
        //System.out.println("in dummy list");
        //System.out.println(name);
        MovieList movieList = movieListRepository.findByName(name);
        System.out.println(movieList.getOwner().getId());
        MovieList resultList = movieList.loadTmdbContent();
        //System.out.println(resultList.getName());
        // if (movieList == null) { return null; }
        //return movieList;
        //System.out.println(resultList.getMovies());
        if (resultList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }



    //------------------------------------------------------------------------------------------------------------------
    @ResponseStatus(value= HttpStatus.NOT_FOUND,reason="This movielist is not found in the system")
    //------------------------------------------------------------------------------------------------------------------



    //------------------------------------------------------------------------------------------------------------------
    @ExceptionHandler(MovieListNotFoundException.class)
    //------------------------------------------------------------------------------------------------------------------
    public void exceptionHandler() { }
}
