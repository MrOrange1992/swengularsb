package at.fh.ima.swengs.swengular.model;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Set;

@RepositoryRestResource
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long>
{
    public Set<Movie> findByTitle(@Param("title") String title);
}
