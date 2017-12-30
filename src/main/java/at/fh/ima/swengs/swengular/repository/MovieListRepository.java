package at.fh.ima.swengs.swengular.repository;

//import at.fh.ima.swengs.swengular.model.Movie;
import at.fh.ima.swengs.swengular.model.MovieList;
import at.fh.ima.swengs.swengular.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface MovieListRepository extends PagingAndSortingRepository<MovieList, Long>
{
    public Set<MovieList> findAllByOwner(@Param("owner") User owner);

    public MovieList findByName(@Param("name") String name);

    public Set<MovieList> findAllByMovieIDsContaining(@Param("movieID") int movieID);

    public Set<MovieList> findBy();

}
