package nftauction.web.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import nftauction.web.model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Integer> {

  Optional<AppUser> findByEmail(String email);
Optional<AppUser> findByUsername(String username);
}
