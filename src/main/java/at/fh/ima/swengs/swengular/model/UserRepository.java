package at.fh.ima.swengs.swengular.model;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Long>
{
    Set<User> findByLastName(@Param("lastName") String lastName);
}
