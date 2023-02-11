package prefolio.prefolioserver.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import prefolio.prefolioserver.domain.Like;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);
    Long countByPostId(Long postId);
    List<Like> findAll(Specification<Like> spec);
    @Transactional
    void deleteByPostId(Long postId);
}
