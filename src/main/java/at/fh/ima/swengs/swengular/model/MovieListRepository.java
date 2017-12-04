package at.fh.ima.swengs.swengular.model;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MovieListRepository extends PagingAndSortingRepository<MovieList, Long> {

   
}
