package prefolio.prefolioserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prefolio.prefolioserver.domain.OAuth;

public interface AuthRepository extends JpaRepository<OAuth, Long> {

}