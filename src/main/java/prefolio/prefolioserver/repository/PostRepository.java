package prefolio.prefolioserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prefolio.prefolioserver.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
