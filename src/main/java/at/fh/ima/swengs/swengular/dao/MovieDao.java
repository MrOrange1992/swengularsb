package at.fh.ima.swengs.swengular.dao;


import at.fh.ima.swengs.swengular.model.Movie;
import at.fh.ima.swengs.swengular.service.GetProperties;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Properties;

@Repository
@Transactional
public class MovieDao
{
    private GetProperties gp = new GetProperties();
    private Properties properties = gp.getPropValues();
    private String apiKey = properties.getProperty("apiKey");

    /*
    // TODO
    public Movie mapMovie(TmdbMovies movies, int id, boolean fullContent)
    {
        Movie movieModel = new Movie();

        try
        {
            // TmdbMovies movies = new TmdbApi(apiKey).getMovies();
            MovieDb movie = movies.getMovie(id, "en", TmdbMovies.MovieMethod.credits);

            if (fullContent)
            {
                List<PersonCast> movieCast = movie.getCast();

                if (movie.getCast().size() >= 7)
                    movieCast = movieCast.subList(0, 6);

                for (PersonCast cast : movieCast) {
                    PersonPeople tmdbActor = tmdbPeople.getPersonInfo(cast.getId());

                    Actor actor = new Actor(tmdbActor.getId(), tmdbActor.getName(), tmdbActor.getBirthday());
                    movieModel.addActor(actor);
                    actor.addMovie(movieModel);
                }

                movieModel.setTmdb_id(movie.getId());
                movieModel.setOverview(movie.getOverview());
                movieModel.setVote_count(movie.getVoteCount());
                movieModel.setAdult(movie.isAdult());
                movieModel.setRuntime(movie.getRuntime());
                movieModel.setBudget(movie.getBudget());
                movieModel.setRevenue(movie.getRevenue());
                movieModel.setBackdropPath(movie.getBackdropPath());
                movieModel.setOriginal_name(movie.getOriginalTitle());

            }

            movieModel.setId(movie.getId());
            movieModel.setTitle(movie.getTitle());
            movieModel.setVote_average(movie.getVoteAverage());

            if (movie.getReleaseDate() != "") movieModel.setRelease_date(format.parse(movie.getReleaseDate()));

            movieModel.setPoster_path(movie.getPosterPath());
            movieModel.setHomepage(movie.getHomepage());

            // FR: Map themoviedbapi.model.Genre to GenreModel
            for (info.movito.themoviedbapi.model.Genre genre : movie.getGenres())
            {
                Genre gm = new Genre(genre.getId(), genre.getName());
                movieModel.addGenre(gm);
                gm.addMovie(movieModel);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return movieModel;
    }
    */
}
