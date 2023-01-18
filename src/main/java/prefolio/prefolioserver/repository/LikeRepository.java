package prefolio.prefolioserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prefolio.prefolioserver.domain.Like;
import prefolio.prefolioserver.domain.Post;
import prefolio.prefolioserver.domain.User;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    public Optional<Like> findByUserIdAndPostId(Long userId, Long postId);
    public Long countByPostId(Long postId);
    public Optional<Long> countByUserId(Long userId);
}
