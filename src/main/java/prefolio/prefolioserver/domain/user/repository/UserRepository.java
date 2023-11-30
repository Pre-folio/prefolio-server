package prefolio.prefolioserver.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prefolio.prefolioserver.domain.user.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);
    Optional<User> findByEmail(String email);
}