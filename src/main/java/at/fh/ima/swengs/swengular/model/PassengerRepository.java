package at.fh.ima.swengs.swengular.model;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * http://docs.spring.io/spring-data/rest/docs/current/reference/html/
 */
@RepositoryRestResource
public interface PassengerRepository extends PagingAndSortingRepository<Passenger, Long> {

    //This would be exposed under the URL: http://localhost:8080/passengers/search/findByFirstNameAndLastName
    public List<Passenger> findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName")String lastName);

}
