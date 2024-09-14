package nftauction.web.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import nftauction.web.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);
Optional<User> findByUsername(String username);
}
