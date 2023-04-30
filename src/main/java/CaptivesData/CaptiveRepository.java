package CaptivesData;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// Repository to connect to the databse

public interface CaptiveRepository extends CrudRepository<Captive, Long> {

}
