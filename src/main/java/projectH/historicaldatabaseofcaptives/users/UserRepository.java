package projectH.historicaldatabaseofcaptives.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select * from hdc_user hu where hu.user_name = ?1")
    Optional<UserDetails> findByUsername(String userName);
}
