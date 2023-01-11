package prefolio.prefolioserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prefolio.prefolioserver.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}