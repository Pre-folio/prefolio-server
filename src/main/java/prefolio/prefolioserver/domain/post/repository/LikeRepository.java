package prefolio.prefolioserver.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prefolio.prefolioserver.domain.post.domain.Like;
import prefolio.prefolioserver.domain.user.domain.User;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);
    Long countByPostId(Long postId);

    @Query("SELECT COUNT(l.id) FROM Post p INNER JOIN Like l ON p.id = l.post.id WHERE p.user = :user")
    Long countByUser(@Param("user") User user);

    void deleteByPostId(Long postId);
}
