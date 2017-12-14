package at.fh.ima.swengs.swengular.tmdbMapper;

import at.fh.ima.swengs.swengular.service.GetProperties;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

import java.util.Properties;

public class test
{


    public static void main(String[] args)
    {
        GetProperties gp = new GetProperties();
        Properties properties = gp.getPropValues();

        String apiKey = properties.getProperty("apiKey");

        //TmdbSearch tmdbSearch = new TmdbApi(apiKey).getSearch();

        TmdbMovies movies = new TmdbApi(apiKey).getMovies();
        MovieDb movie = movies.getMovie(5353, "en");

        //MovieResultsPage result = tmdbSearch.searchMovie("Batman", null, "en-US", false, null);


        //System.out.println( result.toString());

    }


}




