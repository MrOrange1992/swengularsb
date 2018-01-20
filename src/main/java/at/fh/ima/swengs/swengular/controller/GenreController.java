package at.fh.ima.swengs.swengular.controller;

import at.fh.ima.swengs.swengular.service.TmdbAPI;
import info.movito.themoviedbapi.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
public class GenreController {

    @Autowired
    TmdbAPI tmdbAPI;

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value="/genre", method = RequestMethod.GET)
    //------------------------------------------------------------------------------------------------------------------
    ResponseEntity<Set<Genre>> getGenres()
    {
        Set<info.movito.themoviedbapi.model.Genre> test = tmdbAPI.getAllGenres();
        Set<info.movito.themoviedbapi.model.Genre> test2 = tmdbAPI.getAllGenres();

        return new ResponseEntity<Set<Genre>>(test, HttpStatus.OK);
    }
}
