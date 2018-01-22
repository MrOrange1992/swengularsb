package at.fh.ima.swengs.swengular.controller;

import at.fh.ima.swengs.swengular.service.TmdbAPI;
import info.movito.themoviedbapi.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
public class GenreController {

    @Autowired
    TmdbAPI tmdbAPI;

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value="/genre", method = RequestMethod.GET)
    //------------------------------------------------------------------------------------------------------------------
    ResponseEntity<Set<Genre>> getAllGenres()
    {
        return new ResponseEntity<>(tmdbAPI.getAllGenres(), HttpStatus.OK);
    }

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value="/genre/{id}", method = RequestMethod.GET, params = "action=getByName")
    //------------------------------------------------------------------------------------------------------------------
    ResponseEntity<Genre> getByName(@PathVariable int id)
    {
        return new ResponseEntity<>(tmdbAPI.getGenreByID(id), HttpStatus.OK);
    }
}
