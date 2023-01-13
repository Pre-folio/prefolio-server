package prefolio.prefolioserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prefolio.prefolioserver.domain.OAuth;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<OAuth, Long> {
    public Optional<OAuth> findByEmail(String email);
}